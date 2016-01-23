package convertToBusinessName;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class makeNewWorkbook{
	
	public static void main(String[] args) throws IOException {

	    Workbook wb = new XSSFWorkbook();
	    FileOutputStream fileOut = null;
	  
	    Sheet sheet = wb.createSheet("new sheet");
	    Row row = sheet.createRow(0);
	    row.createCell(0).setCellValue(1);
	    
	    
	    
	    
	    
			fileOut = new FileOutputStream("workbook.xlsx");

	
			wb.write(fileOut);
			wb.close();
			fileOut.close();
		
	}
	
}