package xyz.zhazong710.chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
 * 服务端界面
 */
public class ZhaServerFrame extends JFrame {
	
	public static JTextArea sta;  //文本显示
	private JButton sstart; //开启按钮
	private JButton sstop;  //关闭按钮
	private JLabel sjl1;   //输入端口号标题
	private JTextField sport; //输入端口
	private JLabel sjl2;   //当前在线标题
	public static JList<String> suser;  //当前在线用户
	private JScrollPane sp1;  //用户滚动条
	private JLabel sjl3;   //当前信息标题
	private JScrollPane sp2;  //文本滚动条
	private JButton shelp; //帮助按钮
	
	public ZhaServerFrame() {
		initZSF();
	}

	//初始化界面
	private void initZSF() {
		
		this.setTitle("闸总聊天室服务端-zhazong chat server");
		this.setLayout(null);
		this.setBounds(200, 150, 700, 500);
		this.setResizable(false); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		sjl1 = new JLabel("输入端口号:");
		sjl1.setBounds(50, 4, 80, 60);
		this.add(sjl1);
		
		sport = new JTextField();
		sport.setBounds(125, 20, 105, 30);
		this.add(sport);
		
		sstart = new JButton("开启服务");
		sstart.setBounds(250, 18, 120, 35);
		sport.setText("9710");
		this.add(sstart);
		
		sstop = new JButton("关闭服务");
		sstop.setBounds(400, 18, 120, 35);
		this.add(sstop);
		
		sjl2 = new JLabel("当前在线用户");
		sjl2.setBounds(560, 70, 80, 30);
		this.add(sjl2);
		
		suser = new JList<String>();
		sp1 = new JScrollPane(suser);
		sp1.setBounds(530, 95, 140, 340);
		this.add(sp1);
		
		sjl3 = new JLabel("当前信息");
		sjl3.setBounds(230, 70, 80, 30);
		this.add(sjl3);
		
		sta = new JTextArea();
		sta.setBounds(35, 95, 450, 340);
		sta.setEditable(false);
		sta.setLineWrap(true);
		sta.setWrapStyleWord(true);
		sp2 = new JScrollPane(sta);
		sp2.setBounds(35, 95, 450, 340);
		this.add(sp2);
		
		shelp= new JButton("帮助");
		shelp.setBounds(550, 18, 120, 35);
		this.add(shelp);
		
		sEvent();
		
		this.setVisible(true); 
		
	}

	//监听事件
	private void sEvent() {
		
		//开启按钮
		sstart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String portString = sport.getText();   //获取端口号
				ZhaServerLink zsl = new ZhaServerLink(portString);
				zsl.sLink();
				
			}
		});
		
		//关闭按钮
		sstop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				ZhaServerLink zsl2 = new ZhaServerLink();
				zsl2.sBreak();
				
			}
		});
		
		shelp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JOptionPane.showMessageDialog(null, "此程序为zhazong710开发，欢迎访问闸总博客\n需要帮助欢迎留言\n闸总博客地址可谷歌");
				
			}
			
		});
		
	}
	
}
