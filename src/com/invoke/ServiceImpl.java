package com.invoke;
public class ServiceImpl implements IService {

	@Override
	public String fsx() {
		System.out.println("fsx!");
		return "fsx!!!!!";
	}

}
