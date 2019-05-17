package library_gui;
import myExcel.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import entity.User;
import jxl.read.biff.BiffException;

public class UserUi{

	// 成员变量
	private User user;//用户
	private JFrame jf = new JFrame("图书馆系统");//窗口 
	private Container con = jf.getContentPane();// 获得面板

	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	private Dimension sc = toolkit.getScreenSize();// 获得屏幕尺寸
	private JLabel lb_welcome = null;// 欢迎标签

	JTable rtb = null;
	JTable rtb2 = null;

	private JButton btnsearch = new JButton("搜索");// 搜索按钮
	private CalendarPanel cp= new CalendarPanel();//时间标签
	private TimePanel tp = new TimePanel();
	
	private JMenuBar menuBar = new JMenuBar();// 菜单条


	private JLabel card0 = new JLabel();// 标签
	private JLabel card1 = new JLabel();
	
	private Font font = new Font("楷体", 0, 20);
	private Font font0 = new Font("楷体", 0, 25);
	private Font font1 = new Font("楷体", 0, 16);
	private Font font2 = new Font("楷体", 0, 15);

	private JTable tb = null;
	private SeatPanel panel1;
	private SeatPanel panel2;

	JButton[] card1_btn = null;
	int x = 0;

	int k = 0;


	public UserUi() {
		
		jf.setSize(1000, 618);
		jf.setLocation((sc.width - 1000) / 2, (sc.height - 618) / 2);

		jf.setResizable(false);// 窗口大小不可变
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		con.setVisible(true);
		
		btnsearch.setBounds(300, 0, 150, 30);
		btnsearch.setFont(font1);
		btnsearch.addActionListener(new ActionListener() {//点下搜索
		@Override
		public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//System.out.println(cp.getText());//日期格式XXX-yy-zz
				String[] time = tp.getText();
				//System.out.println(time[0]);//小时(字符串)
				//System.out.println(time[1]);//分钟(字符串)
				int hour = tp.getHour();//小时(整型)
				int min = tp.getMin();//分钟(整型)
				int year=cp.getYear();//年
				int month=cp.getMonth();//月
				int date=cp.getDate();//日
				search search = new search() ;
				search.getCheckTime();
				LocalDateTime checkTime;
				checkTime=LocalDateTime.of(year, month, date, hour, min);
				try {
					search.getAllSeatState(0, checkTime);
				} catch (BiffException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for(int i=0;i<4;i++)
				{
					panel1.changeSeatState(i, search.seatState[0][i]);
				}
				try {
					search.getAllSeatState(1, checkTime);
				} catch (BiffException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for(int i=0;i<4;i++)
				{
					panel2.changeSeatState(i, search.seatState[1][i]);
				}
				//System.out.println(cp.getYear());
				//System.out.println(cp.getMonth());
				//System.out.println(cp.getDate());
				//System.out.println(hour);
				//System.out.println(min);
				//改变某个房间某个座位状态
				//panel2.changeSeatState(2, 0);
				//panel1.changeSeatState(1, 40);
				//panel1.changeSeatState(2, 40);
			}
		});

		lb_welcome = new JLabel("欢迎使用选座系统");
		lb_welcome.setFont(new Font("楷体", Font.BOLD, 34));
		lb_welcome.setForeground(Color.BLUE);

		jf.setJMenuBar(menuBar);
		menuBar.add(cp);
		menuBar.add(tp);
		menuBar.add(btnsearch);
		menuBar.add(lb_welcome);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.RIGHT);// 点击栏位置
		// 选项卡面板类
		tabbedPane.setFont(font);// 选项栏栏字体，字号
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);// 每个选项卡滚动模式
		
		con.add(tabbedPane);
		
		tabbedPane.addTab("301教室", card0);
		panel1 = new SeatPanel(301);
		card0.add(panel1);

		// card1
		tabbedPane.addTab("302教室", card1);
		panel2 = new SeatPanel(302);
		panel2.setBounds(0, 0, 850, 602);
		card1.add(panel2);

		tabbedPane.setSelectedIndex(0); // 设置默认选中的card
	}
	
}
