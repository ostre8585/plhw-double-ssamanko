package com.poolc.pl.network;


import java.io.*;
import java.net.*;

public class MobileClient {
	private final static MobileClient instance = new MobileClient();
	private BufferedReader br;
	private PrintWriter pw;
	
	private Socket sock;
	
	public MobileClient() {
		try {		
			sock = new Socket("127.0.0.1", 2222);
			br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static MobileClient getInstance() {
		return instance;
	}
	
	public void run() {
		//do something
		
		
		
		
		
		//when sock close 
//		try {
//			br.close();
//			pw.close();
//			sock.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
}
