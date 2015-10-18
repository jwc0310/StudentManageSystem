package com.my.stu;

public class StuInfo {

	private int id;
	private String name;
	private String sex;
	private int age;
	private String tel;
	
	public StuInfo(){
		
	}
	
	public StuInfo(int id,String name,String sex,int age,String tel){
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.age =age;
		this.tel = tel;
	}
	
	public void setId(int id){
		this.id = id;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setSex(String sex){
		this.sex = sex;
	}
	public void setAge(int age){
		this.age = age;
	}
	public void setTel(String tel){
		this.tel = tel;
	}
	public int getId(){
		return id;
	}
	public String getName(){
		return name;
	}
	public String getSex(){
		return sex;
	}
	public int getAge(){
		return age;
	}
	public String getTel(){
		return tel;
	}
}
