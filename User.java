package com.example.rentalsystem;

import java.util.Date;

public class User {

	private String name;
	private String password;


	public User () {
		System.out.println();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String x)
	{
		this.name = x;
	}

	public String getPassword(){
		return password;
	}

	public void setPassword(String y) {
		this.password = y;
	}
}