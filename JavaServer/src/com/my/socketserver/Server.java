package com.my.socketserver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
	//ServerPort 
	private static final int SERVERPORT = 54321;
	//client connection
	private static List<Socket> mClientList = new ArrayList<Socket>();
	
	//thread pool
	private ExecutorService mExecutorService;
	
	//serversocket对象
	private ServerSocket mServerSocket;
	
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
		
		
		
		public ThreadServer(Socket socket) throws IOException{
			this.mSocket = socket;
			mBufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			mStrMSG = "user: "+this.mSocket.getInetAddress()+" come total: "+mClientList.size();
			sendMessage();
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
						mStrMSG = mSocket.getInetAddress()+":"+mStrMSG;
						sendMessage();
					}
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
	}
	
	
	
}
