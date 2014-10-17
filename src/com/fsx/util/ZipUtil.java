package com.fsx.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

public class ZipUtil {
	private static int BUFFERSIZE = 2048;
	/**ʱ���ʽ�� yyyy/MM/dd HH:mm:ss*/
	public static SimpleDateFormat allSDF=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	/**ʱ���ʽ������ yyyyMMdd*/
	public static SimpleDateFormat daySDF=new SimpleDateFormat("yyyyMMdd");

	private static Log log = LogFactory.getLog(ZipUtil.class);
	/**
	* ѹ��
	* 
	* @param target
	*            ѹ��������zip���ļ���--��·��,���Ϊnull�����Ĭ�ϰ��ļ�������ѹ���ļ���
	* @param source
	*            �ļ���Ŀ¼�ľ���·��
	* @throws FileNotFoundException
	* @throws IOException
	* @return zipName zip����
	*/
	public static File zip(String orgid, String source, String target) throws FileNotFoundException, IOException {
		log.debug("==========ѹ���ļ���ʼ===========");
		String zipPath = "";
		String zipName="";//xx.zip
		File sfile = new File(source);
		// ���ļ�����û���ļ�ʱ��ִ��ѹ��
		if(getNoZipSize(sfile)==0)
			return null;
		if (sfile.isDirectory())
			zipPath = sfile.getName()+File.separator;
		/** ��������ļ�·�����ļ��� **/
		if (target==null||target.trim().equals("")) {
			if (sfile.isDirectory()) {
				zipName=daySDF.format(new Date())+".zip";
				target = source+".zip";
			} else {
				target = source.substring(0, source.lastIndexOf("."))+".zip";
				//��ȡ����
				File tmp=new File(target);
				zipName=tmp.getName();
				tmp=null;
			}
		} else {
			File tfile = new File(target);
			if (sfile.isDirectory()) {
				zipName = sfile.getName()+".zip";
			} else {
				zipName = sfile.getName().substring(0, sfile.getName().lastIndexOf("."))+".zip";
			}
			if (tfile.isDirectory()){
				target = target+File.separator+zipName;
			}
		}
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(target));
		try {
			if (!zipPath.equals(""))
				createZipNode(zos, zipPath);
			zip(zos, zipPath, source);
		} catch (IOException ex) {
			log.debug("==========ѹ���ļ��쳣===========");
			throw ex;
		} finally {
			if (null!=zos) {
				zos.close();
			}
		}
		log.debug("==========ѹ���ļ�����===========");
		return new File(target);
	}

	/**
	 * ��ȡ�ļ����ڷ�ZIP�ļ�����
	 * @return
	 */
	private static int getNoZipSize(File dir){
		if(!dir.isDirectory())
			return 0;
		int retSize = 0;
		String[] fname = dir.list();
		for(String n : fname){
			if(n.endsWith(".zip"))
				continue;
			retSize++;
		}
		return retSize;
	}
	
	/**
	* ѹ��
	* 
	* @param zos
	*            ѹ�������
	* @param zipPath
	*            zip�ڵ����·��
	* @param source
	*            ��ѹ�����ļ����ļ��о���·��
	* @throws IOException
	*/
	private static void zip(ZipOutputStream zos, String zipPath, String source) throws IOException {
		File file = new File(source);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File tempFile : files) {
				if (tempFile.isDirectory()) {
					String newZipPath = zipPath+tempFile.getName()+File.separator;
					createZipNode(zos, newZipPath);
					zip(zos, newZipPath, tempFile.getPath());
				} else if(tempFile.getName().endsWith(".zip")){
					//���ʱ����.zip��
					continue;
				}else{
					zipFile(zos, tempFile, zipPath);
				}
			}
		} else {
			zipFile(zos, file, zipPath);
		}
	}

	/**
	* ѹ���ļ�
	* 
	* @param zos
	*            ѹ�������
	* @param file
	*            �ļ�����
	* @param relativePath
	*            ���·��
	* @throws IOException
	*/
	private static void zipFile(ZipOutputStream zos, File file, String relativePath) throws IOException {
		ZipEntry entry = new ZipEntry(relativePath+file.getName());
		zos.putNextEntry(entry);
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			int length = 0;
			byte[] buffer = new byte[BUFFERSIZE];
			while ((length = is.read(buffer, 0, BUFFERSIZE))>=0) {
				zos.write(buffer, 0, length);
			}
			zos.flush();
			zos.closeEntry();
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (null!=is)
				is.close();
		}
	}

	/**
	* ����Ŀ¼
	* 
	* @param zos
	*            zip�����
	* @param relativePath
	*            ���·��
	* @throws IOException
	*/
	private static void createZipNode(ZipOutputStream zos, String zipPath) throws IOException {
		ZipEntry zipEntry = new ZipEntry(zipPath);
		zos.putNextEntry(zipEntry);
		zos.closeEntry();
	}

	/**
	* ��ѹ��zip��
	* 
	* @param zipFilePath
	*            zip�ļ�·��
	* @param targetPath
	*            ��ѹ������λ�ã����Ϊnull����ַ�����Ĭ�Ͻ�ѹ������zip��ͬĿ¼��zip��ͬ�����ļ�����
	* @throws IOException
	*/
	public static void unzip(String orgid, String zipFilePath, String targetPath) throws IOException {
		log.debug("==========��ѹ�ļ���ʼ===========");
		OutputStream os = null;
		InputStream is = null;
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(zipFilePath);
			if (null==targetPath||"".equals(targetPath)) {
				targetPath = zipFilePath.substring(0, zipFilePath.lastIndexOf("."));
			}
			Enumeration<ZipEntry> entryEnum = zipFile.getEntries();
			if (null!=entryEnum) {
				ZipEntry zipEntry = null;
				String filePath = "";
				while (entryEnum.hasMoreElements()) {
					zipEntry = entryEnum.nextElement();
					filePath = targetPath+File.separator+zipEntry.getName();
					log.debug("=========���ڽ�ѹ����"+filePath+"===========");
					if (zipEntry.getSize()>0) {
						// �ļ�
						File targetFile = buildFile(filePath, false);
						os = new BufferedOutputStream(new FileOutputStream(targetFile));
						is = zipFile.getInputStream(zipEntry);
						byte[] buffer = new byte[4096];
						int readLen = 0;
						while ((readLen = is.read(buffer, 0, BUFFERSIZE))>=0) {
							os.write(buffer, 0, readLen);
						}
						os.flush();
						os.close();
					} else {
						// ��Ŀ¼
						buildFile(filePath, true);
					}
				}
			}
			zipFile.close();
		} catch (IOException ex) {
			log.debug("==========��ѹ�ļ��쳣===========");
			throw ex;
		} finally {
			if (null!=zipFile)
				zipFile = null;
			if (null!=is)
				is.close();
			if (null!=os)
				os.close();
		}
		log.debug("==========��ѹ�ļ�����===========");
	}

	private static File buildFile(String fileName, boolean isDirectory) {
		File target = new File(fileName);
		if (isDirectory) {
			target.mkdirs();
		} else {
			if (!target.getParentFile().exists()) {
				target.getParentFile().mkdirs();
				target = new File(target.getAbsolutePath());
			}
		}
		return target;
	}
}