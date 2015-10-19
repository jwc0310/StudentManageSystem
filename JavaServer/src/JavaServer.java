import java.sql.ResultSet;
import java.util.List;

import com.my.mysql.DBHelper;
import com.my.socketserver.Server;


public class JavaServer {

	static DBHelper db1 = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("[]:Start");
		new Server();
		
		
		
		/*
		String sql = "select * from students";
		db1 = new DBHelper("mydatabase");
		
		List<Object> list = db1.query(sql, null);
		
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i));
		}
		
		
		System.out.println("分割线---------");
		String sql2 = "update students set name = '大大' where id = 1";
		String sql3 = "delete from students where id = 2";
		String sql4 = "insert into students (id,name,sex,age,tel) values (8,'庞','女',22,'15031360392')";
		
		
		
		if(db1.insert(sql4, null)){
			System.out.println("id 2 insert success !");
		}
		
		
		if(db1.delete(sql3)){
			System.out.println("id 2 delete success !");
		}
	
		int k = db1.update(sql2, null);
		
		if(k>0){
			System.out.println("更新成功！");
		}
		
		list = db1.query(sql, null);
		
		for(int j=0;j<list.size();j++){
			System.out.println(list.get(j));
		}
		
		
		
		db1.close();
		
		
		*/
	}

}
