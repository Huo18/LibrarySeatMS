package library_gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TimePanel extends JPanel{
	
	private  JSpinner hourSpin;
	private  JSpinner minuteSpin;
	
	public TimePanel() {
		Calendar c =  Calendar.getInstance();
		int currentHour = c.get(Calendar.HOUR_OF_DAY);//鏃�
		hourSpin=new JSpinner(new SpinnerNumberModel(currentHour, 0, 23,1));
		hourSpin.setPreferredSize(new Dimension(35, 20));
		hourSpin.setName("Hour");
		this.add(hourSpin);
		JLabel hourLabel = new JLabel("时");
		this.add(hourLabel);	
		int currentMin = c.get(Calendar.MINUTE);//鍒�
		minuteSpin = new JSpinner(new SpinnerNumberModel(currentMin, 0, 59, 1));
		minuteSpin.setPreferredSize(new Dimension(35, 20));
		minuteSpin.setName("Minute");
		this.add(minuteSpin);
		JLabel minuteLabel = new JLabel("分");
		this.add(minuteLabel);
		
	}
	public String[] getText() {//鑾峰彇灏忔椂鍜屽垎閽�
		String[] time = new String[2]; 
		time[0] = hourSpin.getValue().toString();
		time[1] = minuteSpin.getValue().toString();
		return time;
		
	}
	public int getHour() {
		int hour =(int) hourSpin.getValue();
		return hour;
	}
	public int getMin() {
		int Min =(int) minuteSpin.getValue();
		return Min;
	}
	 private int getSelectedHour() {
		  return ((Integer) hourSpin.getValue()).intValue();
	}
	 private int getSelectedMinute() {
		  return ((Integer) minuteSpin.getValue()).intValue();
	}
}
