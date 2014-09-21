package framework;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Set;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class WrapperMethods {
	private WebDriver driver;
	public static String username;
	public static String password;
	private int i = 0;
	private int col=0;
	String colText; 
	String result;
	
	
      
	public WrapperMethods(WebDriver driver) throws IOException {
		this.driver = driver;
		
	}
	
		public void getUrl(String url) throws IOException, RowsExceededException, WriteException, BiffException
	{
		try {
			
			// Maximize the window
			driver.manage().window().maximize();
			
			System.out.println("in url load ");
			driver.get(url);
			System.out.println("the page with the url " + url+ "is loaded successfully");
			takeSnap();
			colText="Loading Url "+url;
			result = "Passed";
			writeExcel(colText,result,col);
			 col++;
			
		} catch (Exception e) {
		
			writeExcel(colText,result,col);
			System.out.println("Url load error");
		}
	}
	
	public void setValueById(String id, String value) throws RowsExceededException, WriteException, IOException, BiffException
	{
		try {
			driver.findElement(By.id(id)).sendKeys(value);
			colText = "Input in field : "+id;
		//	takeSnap();
			result = "Passed";
			System.out.println(colText+result);
			writeExcel(colText,result,col);
			 col++;
		} 
		
		catch (Exception e) {
			
			colText = "Input in field : "+id;
			result = "Failed";
			System.out.println(colText+result);
			writeExcel(colText,result,col);
			 col++;
		}
	}
	
	public void clickById(String id) throws RowsExceededException, WriteException, IOException, BiffException
	{
		try {
			driver.findElement(By.id(id)).click();
			colText = "Click Link : "+id;
			takeSnap();
			result = "Passed";
			System.out.println(colText+result);
			writeExcel(colText,result,col);
			 col++;
		} catch (Exception e) {
			colText = "Click Link : "+id;

			result = "Failed";
			System.out.println(colText+result);
			writeExcel(colText,result,col);
			 col++;
		}
		
	}
	
	
	public void switchWindow(String windowTitle) throws IOException, RowsExceededException, WriteException, BiffException, InterruptedException
	{
		/*Thread.sleep(3000);*/
	Set<String> windows = driver.getWindowHandles();
		for(String win:windows)
		{
			
			driver.switchTo().window(win);
			try {
				if (driver.getTitle().contains(windowTitle)) {
				
					takeSnap();
					result = "Passed";
					colText = "Switched window"+windowTitle;
					writeExcel(colText,result,col);
					 col++;
				    break;
				}
			} catch (Exception e) {
				//TODO Auto-generated catch block
				result = "Failed";
				colText = "Switched window failed"+windowTitle;
				writeExcel(colText,result,col);
				 col++;
			}
			
		}
	}
	
	public void switchFrameByStartsWith(String fname) throws IOException, RowsExceededException, WriteException, BiffException
	{
	
		
		try {
			driver.switchTo().frame(driver.findElement(By.xpath("//frame[starts-with(@id, fname)]")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			writeExcel(colText,result,col);
			e.printStackTrace();
		}
			
	}
	
	
	public boolean clickLink(String linktext) throws IOException, RowsExceededException, WriteException, BiffException {
		boolean bReturn = false;
		try {
			// click on link
			driver.findElement(By.linkText(linktext)).click();
			bReturn = true;
			System.out.println("The link with text " + linktext		+ " is clicked successfully");
		} catch (NoSuchElementException e) {
			writeExcel(colText,result,col);
			System.out.println("The link with text " + linktext		+ " is not available");

		} finally {
			takeSnap();
		}
		return bReturn;
	}
	
	public boolean takeSnap() throws IOException {
		i++;
		try {
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(src, new File("D:\\snaps\\" + i + ".jpg"));
			return true;
		} catch (WebDriverException e) {
			System.out.println("The snapshot couldnt be taken as the driver no longer exists");
			return false;
		}

	}

	
	public void readExcel() throws BiffException, IOException
	{
		System.out.println("in read start");
		Workbook wb;
		Sheet sht;
		
		wb = Workbook.getWorkbook(new File(Constant.rBook));
		sht = wb.getSheet(0);
		int cnt = sht.getRows();
	
		for(int i=1;i<cnt;i++)
		{
			Cell c1 = sht.getCell(0,1);
		  username = c1.getContents();
		
			Cell c2 = sht.getCell(1,1);
			password = c2.getContents();
						
		}
	   
		System.out.println("name "+ username);
		System.out.println("name "+ password);
		wb.close();
		System.out.println("in read end");
			
	}


	
	   public void writeExcel(String text,String result,int col) throws IOException, RowsExceededException, WriteException, BiffException {
		  
		   
	
		Workbook existingWorkbook = Workbook.getWorkbook(new File(Constant.wBook));
		WritableWorkbook workbookCopy = Workbook.createWorkbook(new File(Constant.wBook), existingWorkbook);
		WritableSheet sheetToEdit = workbookCopy.getSheet("report");
		
		  int step_num = col+1;
		  	
		  String scn = "HYPERLINK(\"D:\\snaps\\"+step_num+".jpg\",\"Click\")";
	      Label lb = new Label (0, col, Integer.toString(step_num));
	      Label lb1 = new Label (1, col, text);
	      Label lb2 = new Label (2, col, result);
	      Formula link = new Formula(3,col,scn);
	      
	      System.out.println(text+""+result+"");
	      
	      sheetToEdit.addCell(lb);
	      sheetToEdit.addCell(lb1);
	      sheetToEdit.addCell(lb2);
	      sheetToEdit.addCell(link);
	      
	 	  workbookCopy.write();
	      workbookCopy.close();
         existingWorkbook.close();
     
         System.out.println("col val"+col);
	       
	   

	    }
	
	
}
