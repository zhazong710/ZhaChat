package xyz.zhazong710.chat;

import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;


/**
 * 
 * @author zhazong710
 * 闸总博客 www.zhazong710.xyz
 * 
 * @since 2021年11月13日
 * @version 0.2.1
 * 
 * 客户端连接
 */
public class ZhaClientLink {
		
	public static Socket socket = null;
	
	private String ipString = null;
	private String portString = null;
	private String nameString = null;
	
	public ZhaClientLink(String ipString, String portString, String nameString, Socket socket) {
		this.ipString = ipString;
		this.portString = portString;
		this.nameString = nameString;
		this.socket = socket;
	}
	
	public ZhaClientLink(String nameString, Socket socket) {
		this.nameString = nameString;
		this.socket = socket;
	}

	//建立连接方法
	public void zLink() {
		
		if(socket != null && socket.isConnected()){	
			
			JOptionPane.showMessageDialog(null, "连接已成功！无需再次连接");
			
		}else {
			
			if("".equals(ipString) || "".equals(portString)){
				
				JOptionPane.showMessageDialog(null, "失败，ip或端口号为空");
				
			}else {
				
				try {
					
					int ports = Integer.parseInt(portString);
					socket = new Socket(ipString,ports);
					JOptionPane.showMessageDialog(null, "已连接");
					
					new SendClient(socket, nameString, 2 + "");
					new Thread(new ReceiveClient(socket)).start();
					
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "连接失败，或ip和端口错误，或服务器未启动");
					e.printStackTrace();
				}
				
			}
			
		}

	}
	
	//断开连接方法
	public void zBreak() {
		
		if(socket == null){
			
			JOptionPane.showMessageDialog(null, "连接已断开，无需再次断开");
			
		}else if(socket != null && socket.isConnected()) {
			
			try {
				
				new SendClient(socket, nameString, "3");
				socket.close();
				socket = null;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	public Socket re() {
		return socket;
	}
		
}
