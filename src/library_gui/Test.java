package library_gui;
import myExcel.*;
public class Test {


	public static void main(String[] args) throws Exception {
		
	    //ʵ����
		myExcel scd = new myExcel();	
		//��ȡ./word/�γ��ܱ�.xls     // ��ע ԭ �γ��ܱ�.xls L��M�ص�����ǰword�ļ�����Ϊ���������
		myExcel.readExcel();
		
		new UserUi();
		
		
	}
}