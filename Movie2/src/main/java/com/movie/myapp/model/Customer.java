package com.movie.myapp.model;
import java.io.Serializable;

public class Customer implements Serializable {

	private int customerNum;
	private String id;
	private String pass;
	private String gender;
	private int age;
	
	public Customer(int num, String id, String pass, String gender, int age) {
		super();
		this.customerNum = num;
		this.id = id;
		this.pass = pass;
		this.gender = gender;
		this.age= age;
	}
	
	public int getCustomerNum() {
		return customerNum;
	}

	public void setCustomerNum(int customerNum) {
		this.customerNum = customerNum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Customer() {}

}
