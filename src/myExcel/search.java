package search;
//查询某个时间，某个阅览室所有座位的情况（占用，空闲XX分钟，未分配）
import java.time.LocalDateTime ;
import java.time.Month;
import java.util.ArrayList;
import jxl.Cell;
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
import java.io.*;

public class search {
	public LocalDateTime checkTime;//查询的时间存入checkTime中
	public int RoomNum;//阅览室数（需要直接赋值）
	public int seatNum;//阅览室的座位数（需要直接赋值，暂定每个阅览室座位数相同）
	public int checkRoom;//查询的阅览室号
	public int seatState[][]=new int[RoomNum-1][seatNum-1];//所有阅览室的座位状态 ，seatState[1][2]表示1号阅览室2号座位状态【0：占用】【-1：未分配】【正整数XX：空闲XX分钟】
	public String seatOwn[][]=new String[RoomNum-1][seatNum-1];//座位所拥有情况，seatOwn[1][0]="旅行社151105"表示阅览室1的座位0被分配给这名学生
	public void giveSeat() throws IOException {//初始化座位情况
		for(int i=0;i<RoomNum;i++)
		{
			for(int j=0;j<seatNum;j++)
			{
				System.out.println("初始化座位：请输入第"+(i)+"号阅览室的的"+j+"号座位的拥有者：");
				String putin=null;
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				putin=br.readLine();
				System.out.println("写入完毕：请输入第"+(i)+"号阅览室的的"+j+"号座位的拥有者为："+putin);
			}
		}
	}
	public void getCheckTime() {
		checkTime=LocalDateTime.now();//获取查询时间，debug用直接调用现在的时间
		/*
		 * 从界面获取查询的时间，待完成
		 */
	}

	public void getSeatNum(){
		seatNum=10;//获取阅览室座位数
		/*
		 * 从界面获取阅览室座位数，待完成
		 * 
		 */
	}
	public void getAllSeatState(int a,LocalDateTime checkTime) throws BiffException, IOException {//更新a号阅览室座位状态(a从0开始）
		if(a>RoomNum||a<0)
		{
			System.out.println("阅览室号输入有误，输入范围：0-"+RoomNum);
		}
		else {
			for(int i=0;i<seatNum;i++)
			{
				int state;
				String owner = seatOwn[a][i];
				if (owner==null)
				{
					state=-1;
				}
				else {
					state = getStudentState(checkTime,owner);//state表示a号阅览室i号座位状态（0为占用，正整数为空闲XX分钟）
				}
				seatState[a][i]=state;
			}
		}
	}
	
	public int getStudentState(LocalDateTime checkTime,String tclass) throws BiffException, IOException {//查询某个学生在某个时间的状态,返回0表示没课，返回其他值表示上课中，还剩X分钟下课
				File file = new File("./word/class.xls");
				Workbook wb = Workbook.getWorkbook(file);
	            int weekNum=getWeekNum(checkTime);
	            if(weekNum==0)//查询的周数在这之外，返回占用（0）
	            {
	            	return 0;
	            }
	            Sheet sheet=wb.getSheet(weekNum-1);
	            int row =sheet.getRows();
	            int col = sheet.getColumns();
	            for(int j=0;j<col;j++) {
	            	String cell1 = sheet.getCell(0, j).getContents();//cell1是学生名字
	            	if(cell1==tclass)//找到对应学生
	            	{
	            		int weekday=getWeekDay(checkTime);
	            		int temprow=weekday*5;
	            		int rowpoint;
	            		for(int i=0;i<5;i++) 
	            		{
	            			rowpoint=temprow+i;
	            			String cell2 = sheet.getCell(rowpoint, j).getContents();
	            			if(cell2.equals("1")) {//找到一个有课的
	            				int min =getMin(checkTime,i+1);
	            				if(min!=0)
	            				{
	            					return min;
	            				}
	            			}
	            		}
	            	}
	            }
	            return 0;  


	}
	public int getWeekNum(LocalDateTime checkTime) {//根据时间获取周数，若不在课程周内，返回0，否则返回第X周
		int day = checkTime.getDayOfYear();
		int weekNum=(day+1)/7-9;
		if(weekNum<1||weekNum>20)
		{
			return 0;
		}
		else return weekNum;
	}
	public int getWeekDay(LocalDateTime checkTime) {//返回星期X，由于课表只有周一到周五的课，所以返回值为0表示周末，返回1-5表示周1-5
		int dayNum=checkTime.getDayOfYear();
		int weekDay=dayNum%7+1;
		if(weekDay<1||weekDay>5)
		{
			return 0;
		}
		else
		{
			return weekDay;
		}
	}
	public float getTime(LocalDateTime checkTime) {
		float hour=checkTime.getHour();
		float min=checkTime.getMinute();
		float time =hour+min/60;
		return time;
	}
	public boolean inClass(LocalDateTime checkTime,LocalDateTime beginTime,LocalDateTime endTime) {//给定查询时间，课程开始时间，课程结束时间，判断是否在上课
		if(checkTime.isAfter(beginTime)&&checkTime.isBefore(endTime))
			return true;
		return false;
	}
	public int getMin(LocalDateTime checkTime,LocalDateTime endTime)//获取下课时间（返回分钟数，若分钟数非正，返回0）
	{
		int checkHour=checkTime.getHour();
		int checkMin=checkTime.getMinute();
		int endHour=endTime.getHour();
		int endMin=endTime.getMinute();
		int min=(endHour-checkHour)*60+(endMin-checkMin);
		if(min>0)
			return min;
		return 0;
	}
	public int getMin(LocalDateTime checkTime,int i)//给定查询时间，课程号i（i=1-5）, 获取状态（还剩XX分钟下课，或者返回0表示没上课）
	{	
		int year=checkTime.getYear();
		int month = checkTime.getMonthValue();
		int day = checkTime.getDayOfMonth();
		LocalDateTime beginTime;
		LocalDateTime endTime;
		switch(i) {
		case 1:
			beginTime=LocalDateTime.of(year, month, day, 8, 0);
			endTime=LocalDateTime.of(year, month, day, 9, 40);
			break ;
		case 2:
			beginTime=LocalDateTime.of(year, month, day, 10, 0);
			endTime=LocalDateTime.of(year, month, day, 11, 40);
			break ;
		case 3:
			beginTime=LocalDateTime.of(year, month, day, 13, 30);
			endTime=LocalDateTime.of(year, month, day, 15, 10);
			break ;
		case 4:
			beginTime=LocalDateTime.of(year, month, day, 15, 30);
			endTime=LocalDateTime.of(year, month, day, 17, 10);
			break ;
		case 5:
			beginTime=LocalDateTime.of(year, month, day, 18, 30);
			endTime=LocalDateTime.of(year, month, day, 20, 10);
			break ;
			default:
				return 0;
		}
		int min=getMin(checkTime,endTime);
		return min;
	}
}
