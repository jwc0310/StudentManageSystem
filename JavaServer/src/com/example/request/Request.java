package com.example.request;

import java.io.Serializable;

public class Request implements Serializable {

	private static final long serialVersionUID = 1L;
	//1 add,2 delete,3 modify,4 find
	public int type;
	public String condition;
	
	public Request(){
		
	}
	public Request(int type,String con){
		this.type = type;
		this.condition = con;
	}
	
	public void setType(int type){
		this.type = type;
	}
	public int getType(){
		return type;
	}
	public void setCondition(String con){
		this.condition = con;
	}
	public String getCondition(){
		return condition;
	}
	
	
}
