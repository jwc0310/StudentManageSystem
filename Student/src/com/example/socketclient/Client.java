package com.example.socketclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.example.request.Request;

import android.util.Log;

public class Client {
	
	Socket socket = null;
	//传输字符
	PrintWriter pw;
	BufferedReader br;
	
	//传输对象
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;
	
	String str = null;
	public Client(){
		
	}
	public void connect(){
		try {
			socket = new Socket("192.168.61.105",54321);
			//pw = new PrintWriter(socket.getOutputStream(),true);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			oos = new ObjectOutputStream(socket.getOutputStream());
			
			new Thread(run).start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Runnable run = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			while(true){
				try {
					if((str = br.readLine())!=null){
						Log.i("Andy", str+"\n");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	};
	
	public void sendMessage(Request req) throws IOException{
		//pw.println(str);
		//pw.flush();
		
		oos.writeObject(req);
		oos.flush();
		//
		
	}
	
	public String getMessage(){
		String str = null;
		try {
			str = br.readLine();
			Log.i("Andy", str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	
	void close(){
		try{
			if(pw != null){
				pw.close();
			}
			if(br != null){
				br.close();
			}
			if(socket != null){
				socket.close();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}
