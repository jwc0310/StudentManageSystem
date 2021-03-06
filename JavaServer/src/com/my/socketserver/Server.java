package com.my.socketserver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.request.Request;
import com.my.mysql.DBHelper;
import com.my.stu.StuInfo;


public class Server {
	//ServerPort 
	private static final int SERVERPORT = 54321;
	public static final int DB_INSERT =1;
	public static final int DB_DELETE = 2;
	public static final int DB_MODIFY =3;
	public static final int DB_QUERY = 4;
	//client connection
	private static List<Socket> mClientList = new ArrayList<Socket>();
	
	//thread pool
	private ExecutorService mExecutorService;
	
	//serversocket对象
	private ServerSocket mServerSocket;
	
	public static String request = null;
	
	public Server(){
		try{
			
			mServerSocket = new ServerSocket(SERVERPORT);
			mExecutorService = Executors.newCachedThreadPool();
			System.out.println("Start...");
			
			Socket client = null;
			
			while(true){
				client = mServerSocket.accept();
				System.out.println("connect");
				mClientList.add(client);
				mExecutorService.execute(new ThreadServer(client));
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	

	
	static class ThreadServer implements Runnable{

		private Socket mSocket;
		private BufferedReader mBufferedReader;
		private PrintWriter mPrintWriter;
		
		
		private String mStrMSG;
		
		private ObjectInputStream ois = null;
		private ObjectOutputStream oos = null;
		
		public ThreadServer(Socket socket) throws IOException{
			this.mSocket = socket;
			//mBufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//mStrMSG = "user: "+this.mSocket.getInetAddress()+" come total: "+mClientList.size();
			//sendMessage();
		}
		
		//发送消息给所有客户端
		private void sendMessage() throws IOException{
			System.out.println(mStrMSG);
			for(Socket client : mClientList){
				mPrintWriter = new PrintWriter(client.getOutputStream(),true);
				mPrintWriter.println(mStrMSG);
			}
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				ois = new ObjectInputStream(mSocket.getInputStream());
				oos = new ObjectOutputStream(mSocket.getOutputStream());
				Object obj = null;
				try {
					while(true){
						
						if((obj = ois.readObject())!=null){
							Request req = (Request)obj;
							System.out.println("Request type "+req.getType());
							System.out.println(req.getCondition());
							
							DBHelper dbh = new DBHelper("mydatabase");
							List<StuInfo> list = new ArrayList<StuInfo>();
							switch(req.type){
							case DB_QUERY:
								list = (dbh.queryStu(req.getCondition(), null));
								for(StuInfo m : list){
									System.out.println("-------------------");
									System.out.println(m.getId());
									System.out.println(m.getName());
									System.out.println(m.getSex());
									System.out.println(m.getAge());
									System.out.println(m.getTel());
								}
								oos.writeObject(list);
								oos.flush();
								
								break;
							default:
								break;
							}
							
							
						}
					}
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*
			try{
				while((mStrMSG = mBufferedReader.readLine())!=null){
					if(mStrMSG.trim().equals("exit")){
						mClientList.remove(mSocket);
						mBufferedReader.close();
						mPrintWriter.close();
						mStrMSG="user: "+this.mSocket.getInetAddress()+"exit total:"+mClientList.size();
						mSocket.close();
						break;
					}else{
						request = mStrMSG;
						System.out.println(request);
						mStrMSG = mSocket.getInetAddress()+":"+mStrMSG;
						sendMessage();
					}
				}
			}catch(IOException e){
				e.printStackTrace();
			}
			*/
		}
		
	}
	
	
	
}
