package com.my.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.my.stu.StuInfo;


public class DBHelper {

	public static final String url = "jdbc:mysql://127.0.0.1/";
	public static final String name= "com.mysql.jdbc.Driver";
	public static final String user = "root";
	public static final String password = "666666";
	
	
	
	private ResultSet ret=null;
	
	public Connection conn = null;
	public PreparedStatement pst = null;
	public Statement st = null;

	
	
	public DBHelper(String database){
		try{
			//指定连接类型
			Class.forName(name);
			System.out.println("数据库驱动加载成功！！");
			//获取连接
			conn = DriverManager.getConnection(url+database+"?characterEncoding=utf8",user,password);
			System.out.println("数据库连接成功！！");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//增
	public boolean insert(String sql,Object[] params){
		
		if(params != null){
			for(int i=0;i<params.length;i++){
				sql = sql+" "+params[i];
			}
		}
		
		try{
			
			st = conn.createStatement();
			int result = st.executeUpdate(sql);
			if(result >0)
				return true;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
	//删
	public boolean delete(String sql){
		try{
			pst = conn.prepareStatement(sql);
			int result = pst.executeUpdate();
			if(result>0)
				return true;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	//改
	public int update(String sql,Object[] params){
		
		int affectedLine = 0;
		
		try{
			st = conn.createStatement();
			
			if(params != null){
				for(int i=0;i<params.length;i++){
					sql = sql+" "+params[i];
				}
			}
			affectedLine =st.executeUpdate(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return affectedLine;
	}
	
	
	
	
	private ResultSet executeQueryRS(String sql,Object[] params){
		
		try{
			//pst = conn.prepareStatement(sql);
			st = (Statement) conn.createStatement();
			if(params != null){
				for(int i=0;i<params.length;i++){
					//pst.setObject(i+1, params[i]);
					sql=sql+" "+params[i];
				}
			}
			//ret = pst.executeQuery();
			ret = st.executeQuery(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return ret;
	}
	
	public List<StuInfo> queryStu(String sql,Object[] params){
		ResultSet rs = executeQueryRS(sql,params);
		
		ResultSetMetaData rsmd = null;
		int columnCount = 0;
		
		try {
			rsmd = rs.getMetaData();
			columnCount = rsmd.getColumnCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<StuInfo> stuList = new ArrayList<StuInfo>();
		try {
			while(rs.next()){
				StuInfo stu = new StuInfo();
				stu.setId(Integer.parseInt(rs.getString("id")));
				stu.setName(rs.getString("name"));
				stu.setSex(rs.getString("sex"));
				stu.setAge(Integer.parseInt(rs.getString("age")));
				stu.setTel(rs.getString("tel"));
				
				stuList.add(stu);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stuList;
	}
	
	//查
	public List<Object> query(String sql,Object[] params){
		ResultSet rs = executeQueryRS(sql,params);
		ResultSetMetaData rsmd = null;
		int columnCount = 0;
		
		try{
			rsmd = rs.getMetaData();
			columnCount = rsmd.getColumnCount();
		}catch(Exception e){
			e.printStackTrace();
		}
		List<Object> list = new ArrayList<Object>();
		try{
			while(rs.next()){
				Map<String,Object> map = new HashMap<String,Object>();
				for(int i=1;i<=columnCount;i++){
					/*
					String id = ret.getString(1);
					String name = ret.getString(2);
					String sex = ret.getString(3);
					String age = ret.getString(4);
					String tel = ret.getString(5);
					
					System.out.println(id+"\t"+name+ "\t"+sex+"\t"+age+"\t"+tel);
					*/
					map.put(rsmd.getColumnLabel(i), rs.getObject(i));
				}
				list.add(map);
				System.out.println(ret.getString(1)+"\t"+ret.getString(2)+ "\t"+ret.getString(3)+"\t"+ret.getString(4)+"\t"+ret.getString(5));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		
		return list;
	}
	
	
	
	
	public void close(){
		try{
			
			if(conn != null){
				System.out.println("close conn");
				this.conn.close();
			}
			if(pst != null){
				System.out.println("close pst");
				this.pst.close();
			}
			if(ret != null){
				this.ret.close();
			}
			
			if(st != null){
				this.st.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
