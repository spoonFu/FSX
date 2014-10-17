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
	/**时间格式化 yyyy/MM/dd HH:mm:ss*/
	public static SimpleDateFormat allSDF=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	/**时间格式化到天 yyyyMMdd*/
	public static SimpleDateFormat daySDF=new SimpleDateFormat("yyyyMMdd");

	private static Log log = LogFactory.getLog(ZipUtil.class);
	/**
	* 压缩
	* 
	* @param target
	*            压缩产生的zip包文件名--带路径,如果为null或空则默认按文件名生产压缩文件名
	* @param source
	*            文件或目录的绝对路径
	* @throws FileNotFoundException
	* @throws IOException
	* @return zipName zip名字
	*/
	public static File zip(String orgid, String source, String target) throws FileNotFoundException, IOException {
		log.debug("==========压缩文件开始===========");
		String zipPath = "";
		String zipName="";//xx.zip
		File sfile = new File(source);
		// 当文件夹内没有文件时不执行压缩
		if(getNoZipSize(sfile)==0)
			return null;
		if (sfile.isDirectory())
			zipPath = sfile.getName()+File.separator;
		/** 设置输出文件路径及文件名 **/
		if (target==null||target.trim().equals("")) {
			if (sfile.isDirectory()) {
				zipName=daySDF.format(new Date())+".zip";
				target = source+".zip";
			} else {
				target = source.substring(0, source.lastIndexOf("."))+".zip";
				//获取名字
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
			log.debug("==========压缩文件异常===========");
			throw ex;
		} finally {
			if (null!=zos) {
				zos.close();
			}
		}
		log.debug("==========压缩文件结束===========");
		return new File(target);
	}

	/**
	 * 获取文件夹内非ZIP文件数量
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
	* 压缩
	* 
	* @param zos
	*            压缩输出流
	* @param zipPath
	*            zip内的相对路径
	* @param source
	*            被压缩的文件或文件夹绝对路径
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
					//打包时忽略.zip包
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
	* 压缩文件
	* 
	* @param zos
	*            压缩输出流
	* @param file
	*            文件对象
	* @param relativePath
	*            相对路径
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
	* 创建目录
	* 
	* @param zos
	*            zip输出流
	* @param relativePath
	*            相对路径
	* @throws IOException
	*/
	private static void createZipNode(ZipOutputStream zos, String zipPath) throws IOException {
		ZipEntry zipEntry = new ZipEntry(zipPath);
		zos.putNextEntry(zipEntry);
		zos.closeEntry();
	}

	/**
	* 解压缩zip包
	* 
	* @param zipFilePath
	*            zip文件路径
	* @param targetPath
	*            解压缩到的位置，如果为null或空字符串则默认解压缩到跟zip包同目录跟zip包同名的文件夹下
	* @throws IOException
	*/
	public static void unzip(String orgid, String zipFilePath, String targetPath) throws IOException {
		log.debug("==========解压文件开始===========");
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
					log.debug("=========正在解压到："+filePath+"===========");
					if (zipEntry.getSize()>0) {
						// 文件
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
						// 空目录
						buildFile(filePath, true);
					}
				}
			}
			zipFile.close();
		} catch (IOException ex) {
			log.debug("==========解压文件异常===========");
			throw ex;
		} finally {
			if (null!=zipFile)
				zipFile = null;
			if (null!=is)
				is.close();
			if (null!=os)
				os.close();
		}
		log.debug("==========解压文件结束===========");
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