package com.yakshop.net.model;

//this class is model for refer yakshop model.
public class YakShopModel {
	private int id;
	
	private String name;
	
	private double age;
	
	private Sex yakSex;
	
	private double ageAtLastShave;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getAge() {
		return age;
	}
	public void setAge(double age) {
		this.age = age;
	}
	public Sex getYakSex() {
		return yakSex;
	}
	public void setYakSex(Sex yakSex) {
		this.yakSex = yakSex;
	}
	public double getAgeAtLastShave() {
		return ageAtLastShave;
	}
	public void setAgeAtLastShave(double ageAtLastShave) {
		this.ageAtLastShave = ageAtLastShave;
	}
	

}
