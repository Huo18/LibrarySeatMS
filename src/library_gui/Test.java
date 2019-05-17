package library_gui;
import myExcel.*;
public class Test {


	public static void main(String[] args) throws Exception {
		
	    //实例化
		myExcel scd = new myExcel();	
		//读取./word/课程总表.xls     // 备注 原 课程总表.xls L和M重叠，当前word文件夹下为已修正结果
		myExcel.readExcel();
		
		new UserUi();
		
		
	}
}