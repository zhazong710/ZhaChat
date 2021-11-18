package xyz.zhazong710.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * 
 * @author zhazong710
 * 闸总博客 www.zhazong710.xyz
 * 
 * @since 2021年11月13日
 * @version 0.2.1
 * 
 * 客户端多线程收发
 * info信息：1为发送消息，2为用户加入 ，3为用户断开 ，4为服务端关闭
 */
public class ZhaClientIO {

	public ZhaClientIO(Socket socket) throws UnknownHostException {
		new Thread(new ReceiveClient(socket)).start();
	}

}

//发送
class SendClient {

	public SendClient(Socket s, String name, String info) throws IOException {
		
		String messages = info + name;
		PrintWriter pwSC = new PrintWriter(s.getOutputStream(),true);
		pwSC.println(messages);
		
	}

}

//接收
class ReceiveClient implements Runnable {
	
	private Socket socket;
	
	public ReceiveClient(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		
		try {
			
			BufferedReader cbr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(true) {
				
				char info = (char)cbr.read();
				String line = cbr.readLine();
				
				if(info == '1') {
					
					ZhaClientFrame.cta.append(line + "\r\n");
					ZhaClientFrame.cta.setCaretPosition(ZhaClientFrame.cta.getText().length());
					
				}else if(info == '2' || info == '3') {
					
					String sub = line.substring(1, line.length()-1);
					String[] data = sub.split(",");
					ZhaClientFrame.cuser.clearSelection();
					ZhaClientFrame.cuser.setListData(data);
					
				}else if(info == '4') {
					
					ZhaClientFrame.socket.close();
					ZhaClientFrame.socket = null;
					break;
					
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}