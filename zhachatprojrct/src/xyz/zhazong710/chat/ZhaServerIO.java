package xyz.zhazong710.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

/**
 * 
 * @author zhazong710
 * 闸总博客 www.zhazong710.xyz
 * 
 * @since 2021年11月13日
 * @version 0.2.1
 * 
 * 服务端多线程收发
 */
public class ZhaServerIO implements Runnable {
	
	private int port;
	public static ArrayList<Socket> userList = null;
	public static Vector<String> userName = null;
	public static ServerSocket ss = null;
	public static boolean flag = true;
	
	public ZhaServerIO(int port) throws IOException {
		this.port = port;
	}

	@Override
	public void run() {
		
		Socket s = null;
		userList = new ArrayList<Socket>();
		userName = new Vector<String>();
		
		try {
			ss = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while (flag) {
			try {
				
				s = ss.accept();
				userList.add(s);
				
				String id = s.getInetAddress().getHostName();
				ZhaServerFrame.sta.append(id + "————已连接" + "\r\n");
				System.out.println(id + "————已连接");
				
				new Thread(new ReceiveServer(s,userList,userName)).start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}

//发送
class SendServer {
	
	SendServer(ArrayList<Socket> userList,Object message,String info) throws IOException{
		
		String messages = info + message;
		PrintWriter pwOut = null;
		
		for(Socket s : userList){
			pwOut = new PrintWriter(s.getOutputStream(),true);
			pwOut.println(messages);
		}
		
	}
	
}

//接收
//info信息：1为收到消息，2为用户加入 ，3为用户断开
class ReceiveServer implements Runnable {
	
	private Socket socket;
	private ArrayList<Socket> userList;
	private Vector<String> userName;
	
	public ReceiveServer(Socket s,ArrayList<Socket> userList,Vector<String> userName) {
		this.socket = s;
		this.userList = userList;
		this.userName = userName;
	}
	
	@Override
	public void run() {
		
		try {
			
			BufferedReader sbr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(true) {
				
				char info = (char)sbr.read();
				String line = sbr.readLine();
				
				if(info == '1') {
					
					ZhaServerFrame.sta.append(line + "\r\n");
					ZhaServerFrame.sta.setCaretPosition(ZhaServerFrame.sta.getText().length());
					new SendServer(userList, line, "1");
				
				}else if (info == '2') {
					
					userName.add(line);
					ZhaServerFrame.suser.setListData(userName);
					new SendServer(userList, userName, "2");
					
				}else if(info == '3') {
					
					userName.remove(line);
					userList.remove(socket);
					ZhaServerFrame.suser.setListData(userName);
					new SendServer(userList, userName, "3");
					socket.close();
					break;
					
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
