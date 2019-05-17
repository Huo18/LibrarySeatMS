package myExcel;
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.io.InputStream; 
import java.util.ArrayList;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class myExcel {

    public static void main(String[] args) throws Exception {

        //ʵ����
    	myExcel scd = new myExcel();
    	
    	//��ȡ./word/�γ��ܱ�.xls     // ��ע ԭ �γ��ܱ�.xls L��M�ص�����ǰword�ļ�����Ϊ���������
    	scd.readExcel();
    	
//    	
//    	//��ѯ������
//        File file3 = new File("./word/class.xls");
//        Workbook wb3 = Workbook.getWorkbook(file3);
//        
//        //��ѯ�ӿ�
//        int myweeks=19;//��ǰ���� 1-20 
//        String tclass="1640701";//��ǰ�༶ ���������
//        int tweek=1; //�� 1-5  
//        int ttime=1; //�� 1-5 ��
//        
//        // myout '1' �п�    '0'  �޿�    3 �д���
//        char myout = scd.readExcel(myweeks,tclass,tweek,ttime, file3); 
//        System.out.println(myout);       
//        char i='1',j='0',k=3;
//        if(myout==i) {
//        	System.out.println("��ǰ�п�");
//        }else if(myout==j){
//        	System.out.println("��ǰ�޿�");
//        }else if(myout==k){
//        	System.out.println("�������");
//        }else{
//        	System.out.println("�������");//���������������
//        }                       
    }  	

/////////////////////////////////////////////////////////////////////////////////////////////
    //���ÿα�     myweeks ��ǰ���� 1-20      tclass="1640701" ��ǰ�༶ ���������    tweek  �� 1-5     ttime �� 1-5 ��   
    public char readExcel(int myweeks, String tclass,int tweek, int ttime,File file) {  
        try {  
            // ��������������ȡExcel  
            InputStream is = new FileInputStream(file.getAbsolutePath());  
            // jxl�ṩ��Workbook��  
            Workbook wb = Workbook.getWorkbook(is);  
            int mytime=(tweek-1)*5+ttime;
            if(mytime>25||mytime<1) {
            	System.out.println("�γ�ʱ��������󣺳����б�Χ");
            	char i=3;
            	return i;
            }
            if(myweeks>20||myweeks<1) {
            	System.out.println("��ѧ��������󣺳����б�Χ");
            	char i=3;
            	return i;
            }
           
            char myFlag=0;
            Sheet sheet = wb.getSheet(myweeks-1);  
            for (int row = 0;row <= sheet.getRows(); row++) {
            	String cell2 = sheet.getCell(0, row).getContents();
            	while (cell2.indexOf(tclass, 0) != -1) {    
                    //getCell(��, ��)
                    String cellout = sheet.getCell(mytime, row).getContents();  
                    char[] cellout1=cellout.toCharArray();
                    myFlag=1;         
                    return cellout1[0];	                    
            	}   	
            }
            
            if(myFlag==0) {
            	System.out.println("δ�ҵ��ð༶������������");
            	char i=3;
            	return i;
            }

        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (BiffException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
		return 0;       
    }
    
///////////////////////////////////////////////////////////////////////////////////////////
    //��ȡ./word/�γ��ܱ�.xls   �½�./word/class.xls  ����������ѯ
    public static void readExcel( ) throws Exception {  	
        //�����ļ���ȡ�ܿα� �γ��ܱ� classwork
        File sch1 = new File("./word/�γ��ܱ�.xls");
        Workbook wb1 = Workbook.getWorkbook(sch1);
        Sheet sheet1 = wb1.getSheet(0);
        //�����ļ��洢���ܿα�
        File sch2 = new File("./word/class.xls");
        WritableWorkbook wb2 = Workbook.createWorkbook(sch2);

        for (int week = 1; week <= 20; week++) {
            //����sheet
            WritableSheet sheet = wb2.createSheet("��" + week + "��", week - 1);
        }
        //����sheet����
        WritableSheet[] sheets = wb2.getSheets();

        //���Ľ��б���
        for (int row = 4;row <= sheet1.getRows()-2; row++) {        	
        	String cell1 = sheet1.getCell(0, row).getContents();
            for (int i = 1; i <= 20; i++) {
                sheets[i - 1].addCell(new Label(0, row-4, cell1));
            }
        	
            for (int col = 1; col <= sheet1.getColumns()-1; col++) {
                //���������б����ϱ߿�
                for (int i = 1; i <= 20; i++) {
                    sheets[i - 1].addCell(new Label(col, row-4, "0"));
                }

                //��ȡ�ܱ�Ԫ������
                String cell = sheet1.getCell(col, row).getContents();
                //System.out.println(cell); 
                //�����ȡ������firstposition                       
                int fpos = -1;
                //���嵥˫��Tfpos
                int Tfpos = -1;
                //�����ȡ������lastposition                        
                int lpos;
                
                //����ÿ���Ƿ��иÿγ̵�����
                boolean weeks[] = new boolean[20];
                for (boolean week : weeks) {
                    week = false;
                }
                
                while (cell.indexOf("[", fpos + 1) != -1) {                
                    //�ų�ͬ������[��[��]�ĸ���
                    if (cell.indexOf("��]", fpos + 1) != -1) {
                        fpos = cell.indexOf("��]", fpos + 1);
                    }
                                               
                    fpos = cell.indexOf("[", fpos + 1);
                    lpos = cell.indexOf("��", fpos + 1);
                    
                    Tfpos = cell.indexOf("]", fpos + 1);
                    //��ȡ�ÿγ̵�����
                    String course = cell.substring(fpos + 1, lpos);
                    //System.out.println(course);
                    //��ȡ�ÿγ̵�����
                    String Tcourse = cell.substring(lpos, Tfpos); 
                    //���գ����γ������ؿ�
                    //����[1,4,7-11����]������õ�{"1","4","7-11��"}����
                    String[] wks = course.split(",");
                    //�������ɵ��ܲ�����weeks[]��¼                                                                                                 
                    //����{"1","4","7-11��"}������õ�{1,4,7,9,11}���Ҷ�Ӧ��5�������
                    for (String wk : wks) {
                        int mpos = wk.indexOf("-");
                        if (mpos != -1) {
                            int first = Integer.parseInt(wk.substring(0, mpos));
                            int last = Integer.parseInt(wk.substring(mpos + 1,wk.length()));
                            
                            if(Tcourse.length()<3) {
                                for (int t = first; t <= last; t++) {
                                    weeks[t] = true;   
                                }
                            } else if (Tcourse.indexOf("��", 0) != -1) {
                                for (int t = first; t <= last; t++) {
                                    if (t % 2 != 0) {
                                        weeks[t] = true;
                                    }
                                }
                            } else if (Tcourse.indexOf("˫", 0) != -1) {
                                for (int t = first; t <= last; t++) {
                                    if (t % 2 == 0) {
                                        weeks[t] = true;  
                                    }
                                }
                            } else {
                                for (int t = first; t <= last; t++) {
                                    weeks[t] = true;
                                }
                            }
                        } else {
                            weeks[Integer.parseInt(wk)] = true;
                        }
                    }
                    //�޸Ķ�Ӧ������sheet
                    for (int i = 1; i <= 19; i++) {
                        if (weeks[i] == true) {
                            sheets[i - 1].addCell(new Label(col, row-4, "1"));
                        }
                        else {
                        	sheets[i - 1].addCell(new Label(col, row-4, "0"));
                        }
                    }
                    //�ų�ͬ������[��[��]�ĸ���
                    if (cell.indexOf("��", fpos + 1) != -1) {
                        fpos = cell.indexOf("��", fpos + 1);
                    } 
                }
            }
        }
        //д��Excel
        wb2.write();
        //�ر�
        wb1.close();
        wb2.close();
    }
}