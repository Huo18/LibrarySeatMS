package search;
//��ѯĳ��ʱ�䣬ĳ��������������λ�������ռ�ã�����XX���ӣ�δ���䣩
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
	public LocalDateTime checkTime;//��ѯ��ʱ�����checkTime��
	public int RoomNum;//������������Ҫֱ�Ӹ�ֵ��
	public int seatNum;//�����ҵ���λ������Ҫֱ�Ӹ�ֵ���ݶ�ÿ����������λ����ͬ��
	public int checkRoom;//��ѯ�������Һ�
	public int seatState[][]=new int[RoomNum-1][seatNum-1];//���������ҵ���λ״̬ ��seatState[1][2]��ʾ1��������2����λ״̬��0��ռ�á���-1��δ���䡿��������XX������XX���ӡ�
	public String seatOwn[][]=new String[RoomNum-1][seatNum-1];//��λ��ӵ�������seatOwn[1][0]="������151105"��ʾ������1����λ0�����������ѧ��
	public void giveSeat() throws IOException {//��ʼ����λ���
		for(int i=0;i<RoomNum;i++)
		{
			for(int j=0;j<seatNum;j++)
			{
				System.out.println("��ʼ����λ���������"+(i)+"�������ҵĵ�"+j+"����λ��ӵ���ߣ�");
				String putin=null;
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				putin=br.readLine();
				System.out.println("д����ϣ��������"+(i)+"�������ҵĵ�"+j+"����λ��ӵ����Ϊ��"+putin);
			}
		}
	}
	public void getCheckTime() {
		checkTime=LocalDateTime.now();//��ȡ��ѯʱ�䣬debug��ֱ�ӵ������ڵ�ʱ��
		/*
		 * �ӽ����ȡ��ѯ��ʱ�䣬�����
		 */
	}

	public void getSeatNum(){
		seatNum=10;//��ȡ��������λ��
		/*
		 * �ӽ����ȡ��������λ���������
		 * 
		 */
	}
	public void getAllSeatState(int a,LocalDateTime checkTime) throws BiffException, IOException {//����a����������λ״̬(a��0��ʼ��
		if(a>RoomNum||a<0)
		{
			System.out.println("�����Һ������������뷶Χ��0-"+RoomNum);
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
					state = getStudentState(checkTime,owner);//state��ʾa��������i����λ״̬��0Ϊռ�ã�������Ϊ����XX���ӣ�
				}
				seatState[a][i]=state;
			}
		}
	}
	
	public int getStudentState(LocalDateTime checkTime,String tclass) throws BiffException, IOException {//��ѯĳ��ѧ����ĳ��ʱ���״̬,����0��ʾû�Σ���������ֵ��ʾ�Ͽ��У���ʣX�����¿�
				File file = new File("./word/class.xls");
				Workbook wb = Workbook.getWorkbook(file);
	            int weekNum=getWeekNum(checkTime);
	            if(weekNum==0)//��ѯ����������֮�⣬����ռ�ã�0��
	            {
	            	return 0;
	            }
	            Sheet sheet=wb.getSheet(weekNum-1);
	            int row =sheet.getRows();
	            int col = sheet.getColumns();
	            for(int j=0;j<col;j++) {
	            	String cell1 = sheet.getCell(0, j).getContents();//cell1��ѧ������
	            	if(cell1==tclass)//�ҵ���Ӧѧ��
	            	{
	            		int weekday=getWeekDay(checkTime);
	            		int temprow=weekday*5;
	            		int rowpoint;
	            		for(int i=0;i<5;i++) 
	            		{
	            			rowpoint=temprow+i;
	            			String cell2 = sheet.getCell(rowpoint, j).getContents();
	            			if(cell2.equals("1")) {//�ҵ�һ���пε�
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
	public int getWeekNum(LocalDateTime checkTime) {//����ʱ���ȡ�����������ڿγ����ڣ�����0�����򷵻ص�X��
		int day = checkTime.getDayOfYear();
		int weekNum=(day+1)/7-9;
		if(weekNum<1||weekNum>20)
		{
			return 0;
		}
		else return weekNum;
	}
	public int getWeekDay(LocalDateTime checkTime) {//��������X�����ڿα�ֻ����һ������ĿΣ����Է���ֵΪ0��ʾ��ĩ������1-5��ʾ��1-5
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
	public boolean inClass(LocalDateTime checkTime,LocalDateTime beginTime,LocalDateTime endTime) {//������ѯʱ�䣬�γ̿�ʼʱ�䣬�γ̽���ʱ�䣬�ж��Ƿ����Ͽ�
		if(checkTime.isAfter(beginTime)&&checkTime.isBefore(endTime))
			return true;
		return false;
	}
	public int getMin(LocalDateTime checkTime,LocalDateTime endTime)//��ȡ�¿�ʱ�䣨���ط�������������������������0��
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
	public int getMin(LocalDateTime checkTime,int i)//������ѯʱ�䣬�γ̺�i��i=1-5��, ��ȡ״̬����ʣXX�����¿Σ����߷���0��ʾû�ϿΣ�
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
