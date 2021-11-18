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
 * 服务端多线程收发
 */
public class ZhaServerLink {
	
	public static Socket socket = null;
	private String portString = null;
	
	public ZhaServerLink(String portString) {
		this.portString = portString;
	}
	
	public ZhaServerLink() {
		
	}
	
	//开启方法
	public void sLink() {
		
		if(ZhaServerIO.ss != null && !ZhaServerIO.ss.isClosed()) {
			
			JOptionPane.showMessageDialog(null, "服务端已启动，无需再次启动");
			
		}else {
			
			if("".equals(portString)) {
				JOptionPane.showMessageDialog(null, "失败，端口号为空");
			}else {
				
				int ports = Integer.parseInt(portString);
				if(ports != 0) {
					
					try {
						
						ZhaServerIO.flag = true;
						JOptionPane.showMessageDialog(null, "服务端启动成功");
						new Thread(new ZhaServerIO(ports)).start();
						
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "启动失败，检查端口");
						e.printStackTrace();
					}
					
				}
				
			}
			
		}
		
	}
	
	//关闭方法
	public void sBreak() {
		
		if(ZhaServerIO.ss == null || ZhaServerIO.ss.isClosed()){
			
			JOptionPane.showMessageDialog(null, "服务端已关闭，无需再次关闭");
			
		}else {
			
			if(ZhaServerIO.userList != null && ZhaServerIO.userList.size() != 0){
				
				try {
					new SendServer(ZhaServerIO.userList, "" , 4 + "");
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
			try {
				
				ZhaServerIO.ss.close();
				ZhaServerIO.ss = null;
				ZhaServerIO.userList = null;
				ZhaServerIO.flag = false;
				JOptionPane.showMessageDialog(null, "服务器关闭成功");
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
}
