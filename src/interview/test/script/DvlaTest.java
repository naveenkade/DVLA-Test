package interview.test.script;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import interview.test.dom.SupportedFile;
import interview.test.service.SupportedFilesService;

public class DvlaTest {
	public static void main(String args[]) throws Exception {
		//Initialising Web driver
		System.setProperty("webdriver.chrome.driver","C://Users//sasi//Desktop//Files_Directory//chromedriver.exe");
		WebDriver driver=new ChromeDriver();
		//getting list of supported files
		Collection<SupportedFile> supportedFiles = new SupportedFilesService().getFiles();
		for(SupportedFile supportedFile : supportedFiles) {
			List<DVLAData> dvlaDataList = getData(supportedFile.getFileName());//Excel data will be returned as list of objects

			System.out.println("Size is"+dvlaDataList.size());      

			for (DVLAData dvlaData : dvlaDataList)  {
				driver.get("https://www.gov.uk/get-vehicle-information-from-dvla");
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				driver.findElement(By.xpath(".//*[@id='get-started']/a")).click();

				//Entering vehicle registration number
				driver.findElement(By.id("Vrm")).sendKeys(dvlaData.regno);
				driver.findElement(By.xpath(".//*[@id='content']/form/div/div/div[2]/fieldset/button")).click();

				//capturing make and colour from UI
				String makescreen= driver.findElement(By.xpath(".//*[@id='pr3']/div/ul/li[2]/span[2]/strong")).getText();
				String colorscreen=driver.findElement(By.xpath(".//*[@id='pr3']/div/ul/li[3]/span[2]/strong")).getText();

				System.out.println(makescreen);
				System.out.println(colorscreen);
				System.out.println(dvlaData.make);
				System.out.println(dvlaData.colour);
				//            Assert.assertEquals(makescreen.trim(),obj.get(i).make.trim(),"Make is not equal");
				//            Assert.assertEquals(colorscreen.trim(),obj.get(i).color.trim(),"colour is not equal");

				if (makescreen.trim().equals(dvlaData.make.trim()))
					System.out.println("make is equal");
				else
					System.out.println("make is notequal");


				if (colorscreen.trim().equals(dvlaData.colour.trim()))
					System.out.println("color is equal");
				else
					System.out.println("color is notequal");
				
			}
		}
		driver.close();
	}

	//https://www.apache.org/dyn/closer.lua/poi/release/bin/poi-bin-3.17-20170915.zip
    //to download Apache POI
	public static List<DVLAData> getData(String fileName) {
		List<DVLAData> obj=new ArrayList<>();
		try(FileInputStream fis = new FileInputStream(new File(fileName));Workbook workbook = new XSSFWorkbook(fis);){
			Sheet datasheet=workbook.getSheetAt(0);
			Iterator<Row> iterator=datasheet.iterator();
			iterator.next();
			//capturing the data from excel
			while (iterator.hasNext()) {
				Row currentRow=iterator.next();
				Iterator<Cell> cellIterator=currentRow.iterator();
				DVLAData obj1=new DVLAData();
				cellIterator.hasNext();
				Cell celldata=cellIterator.next();
				obj1.regno=celldata.getStringCellValue();
				celldata=cellIterator.next();
				obj1.colour=celldata.getStringCellValue();
				celldata=cellIterator.next();
				obj1.make=celldata.getStringCellValue();

				obj.add(obj1);
			}
			System.out.println ("Data is :"+obj );
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return obj;
	}

}
