package com.example.socketclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

public class Client {
	
	Socket socket = null;
	PrintWriter pw;
	BufferedReader br;
	
	public Client(){
		
	}
	public void connect(){
		try {
			socket = new Socket("192.168.61.110",54321);
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String str){
		pw.println(str);
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
