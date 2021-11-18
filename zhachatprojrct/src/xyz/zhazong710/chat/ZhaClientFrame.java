package xyz.zhazong710.chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 
 * @author zhazong710
 * 闸总博客 www.zhazong710.xyz
 * 
 * @since 2021年11月13日
 * @version 0.2.1
 * 
 * 客户端界面
 */
public class ZhaClientFrame extends JFrame {
	
	public static JTextArea cta;  //文本显示
	private JButton cstart; //连接按钮
	private JButton cstop;  //断开按钮
	private JLabel cjl1;   //输入端口号标题
	private JTextField cport; //输入端口
	private JLabel cjl2;   //当前在线标题
	public static JList<String> cuser;  //当前在线用户
	private JScrollPane cp1;  //用户滚动条
	private JLabel cjl3;   //当前信息标题
	private JScrollPane cp2;  //文本滚动条
	private JLabel cjl4;   //输入IP标题
	private JTextField cip; //输入IP
	private JLabel names;   //用户名
	private JTextField name; //输入用户名
	private JTextField ctf;  //发生框
	private JButton csend;   //发送按钮
	private JButton chelp;  //帮助按钮
	
	public static Socket socket = null;
	
	public ZhaClientFrame() {
		initZCF();
	}

	//初始化界面
	private void initZCF() {
		
		this.setTitle("闸总聊天室客户端-zhazong chat client");
		this.setLayout(null);
		this.setBounds(550, 250, 700, 500);
		this.setResizable(false); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		cjl4 = new JLabel("服务器IP:");
		cjl4.setBounds(5, 4, 80, 60);
		this.add(cjl4);
		
		cip = new JTextField();
		cip.setBounds(60, 20, 105, 30);
		cip.setText("127.0.0.1");
		this.add(cip);
		
		cjl1 = new JLabel("端口:");
		cjl1.setBounds(175, 4, 80, 60);
		this.add(cjl1);
		
		cport = new JTextField();
		cport.setBounds(205, 20, 50, 30);
		cport.setText("9710");
		this.add(cport);
		
		names = new JLabel("昵称:");
		names.setBounds(260, 4, 80, 60);
		this.add(names);
		
		name = new JTextField();
		name.setBounds(295, 20, 90, 30);
		name.setText("闸总用户");
		this.add(name);
		
		cstart = new JButton("连接服务器");
		cstart.setBounds(400, 18, 120, 35);
		cport.setText("9710");
		this.add(cstart);
		
		cstop = new JButton("断开连接");
		cstop.setBounds(530, 18, 120, 35);
		this.add(cstop);
		
		cjl2 = new JLabel("当前在线用户");
		cjl2.setBounds(560, 70, 80, 30);
		this.add(cjl2);
		
		cuser = new JList<String>();
		cp1 = new JScrollPane(cuser);
		cp1.setBounds(530, 95, 140, 250);
		this.add(cp1);
		
		cjl3 = new JLabel("当前信息");
		cjl3.setBounds(230, 70, 80, 30);
		this.add(cjl3);
		
		cta = new JTextArea();
		cta.setBounds(35, 95, 450, 250);
		cta.setEditable(false);
		cta.setLineWrap(true);
		cta.setWrapStyleWord(true);
		cp2 = new JScrollPane(cta);
		cp2.setBounds(35, 95, 450, 250);
		this.add(cp2);
		
		ctf = new JTextField();
		ctf.setBounds(35, 360, 450, 80);
		ctf.setText("请输入内容");
		this.add(ctf);
		
		csend = new JButton("发送");
		csend.setBounds(530, 360, 140, 35);
		this.add(csend);
		
		chelp = new JButton("帮助");
		chelp.setBounds(530, 405, 140, 35);
		this.add(chelp);
		
		cEvent();
		
		this.setVisible(true); 
		
	}
	
	//监听事件
	private void cEvent() {
		
		//连接按钮
		cstart.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String ipString = cip.getText();       //获取ip地址
				String portString = cport.getText();   //获取端口号
				
				ZhaClientLink zcl = new ZhaClientLink(ipString, portString, getName(), socket);
				zcl.zLink();
				socket = zcl.re();
				
			}
		});
		
		//断开按钮
		cstop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ZhaClientLink zcl2 = new ZhaClientLink(getName(), socket);
				zcl2.zBreak();
				socket = zcl2.re();
			}
		});
		
		//发送按钮
		csend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendMsg();
			}
		});	
		
		//发送回车
		ctf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					sendMsg();
				}
			}
		});
		
		//帮助按钮
		chelp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(null, "此程序为zhazong710开发，欢迎访问闸总博客\n需要帮助欢迎留言\n闸总博客地址可谷歌");
				
			}
			
		});
		
	}
	
	//发送方法
	private void sendMsg() {
		
		String msg = ctf.getText();           //获取信息
		
		if("".equals(msg)){
			
			JOptionPane.showMessageDialog(this, "内容不能为空");
			
		}else if(socket == null || !socket.isConnected()) {
			
			JOptionPane.showMessageDialog(this, "未连接，无法发送消息");
			
		}else {
			
			try {
				
				new SendClient(socket,getName() + "：" + msg,"1");
				ctf.setText(null);
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "发送失败");
				e.printStackTrace();
			}
			
		}
		
	}
	
	//获取昵称
	public String getName(){
		return name.getText();
	}
	
}
