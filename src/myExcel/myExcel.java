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

        //实例化
    	myExcel scd = new myExcel();
    	
    	//读取./word/课程总表.xls     // 备注 原 课程总表.xls L和M重叠，当前word文件夹下为已修正结果
    	scd.readExcel();
    	
//    	
//    	//查询处理结果
//        File file3 = new File("./word/class.xls");
//        Workbook wb3 = Workbook.getWorkbook(file3);
//        
//        //查询接口
//        int myweeks=19;//当前周数 1-20 
//        String tclass="1640701";//当前班级 输入具体班号
//        int tweek=1; //周 1-5  
//        int ttime=1; //第 1-5 节
//        
//        // myout '1' 有课    '0'  无课    3 有错误
//        char myout = scd.readExcel(myweeks,tclass,tweek,ttime, file3); 
//        System.out.println(myout);       
//        char i='1',j='0',k=3;
//        if(myout==i) {
//        	System.out.println("当前有课");
//        }else if(myout==j){
//        	System.out.println("当前无课");
//        }else if(myout==k){
//        	System.out.println("输出有误");
//        }else{
//        	System.out.println("其他情况");//不可能有这个返回
//        }                       
    }  	

/////////////////////////////////////////////////////////////////////////////////////////////
    //调用课表     myweeks 当前周数 1-20      tclass="1640701" 当前班级 输入具体班号    tweek  周 1-5     ttime 第 1-5 节   
    public char readExcel(int myweeks, String tclass,int tweek, int ttime,File file) {  
        try {  
            // 创建输入流，读取Excel  
            InputStream is = new FileInputStream(file.getAbsolutePath());  
            // jxl提供的Workbook类  
            Workbook wb = Workbook.getWorkbook(is);  
            int mytime=(tweek-1)*5+ttime;
            if(mytime>25||mytime<1) {
            	System.out.println("课程时间输入错误：超出列表范围");
            	char i=3;
            	return i;
            }
            if(myweeks>20||myweeks<1) {
            	System.out.println("教学周输入错误：超出列表范围");
            	char i=3;
            	return i;
            }
           
            char myFlag=0;
            Sheet sheet = wb.getSheet(myweeks-1);  
            for (int row = 0;row <= sheet.getRows(); row++) {
            	String cell2 = sheet.getCell(0, row).getContents();
            	while (cell2.indexOf(tclass, 0) != -1) {    
                    //getCell(列, 行)
                    String cellout = sheet.getCell(mytime, row).getContents();  
                    char[] cellout1=cellout.toCharArray();
                    myFlag=1;         
                    return cellout1[0];	                    
            	}   	
            }
            
            if(myFlag==0) {
            	System.out.println("未找到该班级：请重新输入");
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
    //读取./word/课程总表.xls   新建./word/class.xls  供主函数查询
    public static void readExcel( ) throws Exception {  	
        //创建文件读取总课表 课程总表 classwork
        File sch1 = new File("./word/课程总表.xls");
        Workbook wb1 = Workbook.getWorkbook(sch1);
        Sheet sheet1 = wb1.getSheet(0);
        //创建文件存储分周课表
        File sch2 = new File("./word/class.xls");
        WritableWorkbook wb2 = Workbook.createWorkbook(sch2);

        for (int week = 1; week <= 20; week++) {
            //创建sheet
            WritableSheet sheet = wb2.createSheet("第" + week + "周", week - 1);
        }
        //创建sheet数组
        WritableSheet[] sheets = wb2.getSheets();

        //正文进行遍历
        for (int row = 4;row <= sheet1.getRows()-2; row++) {        	
        	String cell1 = sheet1.getCell(0, row).getContents();
            for (int i = 1; i <= 20; i++) {
                sheets[i - 1].addCell(new Label(0, row-4, cell1));
            }
        	
            for (int col = 1; col <= sheet1.getColumns()-1; col++) {
                //将正文所有表格加上边框
                for (int i = 1; i <= 20; i++) {
                    sheets[i - 1].addCell(new Label(col, row-4, "0"));
                }

                //获取总表单元格内容
                String cell = sheet1.getCell(col, row).getContents();
                //System.out.println(cell); 
                //定义截取周数的firstposition                       
                int fpos = -1;
                //定义单双周Tfpos
                int Tfpos = -1;
                //定义截取周数的lastposition                        
                int lpos;
                
                //定义每周是否有该课程的数组
                boolean weeks[] = new boolean[20];
                for (boolean week : weeks) {
                    week = false;
                }
                
                while (cell.indexOf("[", fpos + 1) != -1) {                
                    //排除同样带有[的[人]的干扰
                    if (cell.indexOf("人]", fpos + 1) != -1) {
                        fpos = cell.indexOf("人]", fpos + 1);
                    }
                                               
                    fpos = cell.indexOf("[", fpos + 1);
                    lpos = cell.indexOf("周", fpos + 1);
                    
                    Tfpos = cell.indexOf("]", fpos + 1);
                    //截取该课程的周数
                    String course = cell.substring(fpos + 1, lpos);
                    //System.out.println(course);
                    //截取该课程的周数
                    String Tcourse = cell.substring(lpos, Tfpos); 
                    //按照，将课程周数截开
                    //例如[1,4,7-11单周]操作后得到{"1","4","7-11单"}数组
                    String[] wks = course.split(",");
                    //将数组拆成单周并且在weeks[]记录                                                                                                 
                    //例如{"1","4","7-11单"}操作后得到{1,4,7,9,11}并且对应的5周作标记
                    for (String wk : wks) {
                        int mpos = wk.indexOf("-");
                        if (mpos != -1) {
                            int first = Integer.parseInt(wk.substring(0, mpos));
                            int last = Integer.parseInt(wk.substring(mpos + 1,wk.length()));
                            
                            if(Tcourse.length()<3) {
                                for (int t = first; t <= last; t++) {
                                    weeks[t] = true;   
                                }
                            } else if (Tcourse.indexOf("单", 0) != -1) {
                                for (int t = first; t <= last; t++) {
                                    if (t % 2 != 0) {
                                        weeks[t] = true;
                                    }
                                }
                            } else if (Tcourse.indexOf("双", 0) != -1) {
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
                    //修改对应周数的sheet
                    for (int i = 1; i <= 19; i++) {
                        if (weeks[i] == true) {
                            sheets[i - 1].addCell(new Label(col, row-4, "1"));
                        }
                        else {
                        	sheets[i - 1].addCell(new Label(col, row-4, "0"));
                        }
                    }
                    //排除同样带有[的[节]的干扰
                    if (cell.indexOf("节", fpos + 1) != -1) {
                        fpos = cell.indexOf("节", fpos + 1);
                    } 
                }
            }
        }
        //写入Excel
        wb2.write();
        //关闭
        wb1.close();
        wb2.close();
    }
}