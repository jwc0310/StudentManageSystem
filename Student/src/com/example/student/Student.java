package com.example.student;

import java.io.Serializable;

public class Student implements Serializable {

	private long id;
	private String name;
	private int age;
	private String sex;
	private String like;
	private String phoneNumber;
	private String trainDate;
	private String modifyDateTime;
	
	public Student() {
		super();
	}
	
	public Student(long id,String name, int age, String sex,String like,String phoneNumber,
			String trainDate,String modifyDateTime){
		super();
		this.id = id;
		this.name = name;
		this.age =age;
		this.sex = sex;
		this.like = like;
		this.phoneNumber = phoneNumber;
		this.trainDate = trainDate;
		this.modifyDateTime = modifyDateTime;
	}
	
	public Student(String name, int age, String sex,String like,String phoneNumber,
			String trainDate,String modifyDateTime){
		super();
		this.name = name;
		this.age =age;
		this.sex = sex;
		this.like = like;
		this.phoneNumber = phoneNumber;
		this.trainDate = trainDate;
		this.modifyDateTime = modifyDateTime;
	}
	
	@Override
	public int hashCode(){
		
		final int prime = 31;
		int result = 1;
		
		result = prime*result +age;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((like == null) ? 0 : like.hashCode());
		result = prime * result + ((modifyDateTime == null) ? 0 : modifyDateTime.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result + ((trainDate == null) ? 0 : trainDate.hashCode());
		
		
		return result;
		
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        if (age != other.age)
            return false;
        if (id != other.id)
            return false;
        if (like == null) {
            if (other.like != null)
                return false;
        } else if (!like.equals(other.like))
            return false;
        if (modifyDateTime == null) {
            if (other.modifyDateTime != null)
                 return false;
         } else if (!modifyDateTime.equals(other.modifyDateTime))
             return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
             return false;
         if (phoneNumber == null) {
             if (other.phoneNumber != null)
                 return false;
        } else if (!phoneNumber.equals(other.phoneNumber))
            return false;
        if (sex == null) {
            if (other.sex != null)
                return false;
        } else if (!sex.equals(other.sex))
            return false;
        if (trainDate == null) {
            if (other.trainDate != null)
                return false;
        } else if (!trainDate.equals(other.trainDate))
            return false;
        return true;
    }
	
	
	
	@Override
	public String toString(){
		return "Student [id="+id+", name="+name+"]";
	}
	
	
	public long getId() {
		return id;
      }
    public void setId(long id) {
        this.id = id;
      }
    public String getName() {
        return name;
      }
    public void setName(String name) {
        this.name = name;
      }
    public int getAge() {
        return age;
      }
    public void setAge(int age) {
        this.age = age;
      }
    public String getSex() {
        return sex;
      }
    public void setSex(String sex) {
        this.sex = sex;
      }
    public String getLike() {
        return like;
      }
    public void setLike(String like) {
        this.like = like;
      }
    public String getPhoneNumber() {
        return phoneNumber;
      }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
      }
    public String getTrainDate() {
        return trainDate;
      }
    public void setTrainDate(String trainDate) {
        this.trainDate = trainDate;
      }
    public String getModifyDateTime() {
        return modifyDateTime;
      }
    public void setModifyDateTime(String modifyDateTime) {
        this.modifyDateTime = modifyDateTime;
      }
}