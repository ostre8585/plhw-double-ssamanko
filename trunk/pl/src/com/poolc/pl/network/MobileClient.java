package com.poolc.pl.network;


import java.io.*;
import java.net.*;
import java.util.TimerTask;

public class MobileClient extends TimerTask {
	private static PrintWriter pw;
	private static Socket sock;
	
	//private static final String serverAddr = "192.168.0.16";
	private static final String serverAddr = "165.132.134.217"; //hellc
	
	public MobileClient() {
	}
	
	@Override
	public void run() {
		
		if(sock.isConnected() == false) {
			try {
				sock = new Socket(serverAddr, 2222);
				try {
					pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		pw.println("okokok");
		pw.flush();
//		String str = null;
//		
//		//send data
//		while(str != null) {
//			pw.println(str);
//			pw.flush();
//		}
//		//remove data
	}
	
}
