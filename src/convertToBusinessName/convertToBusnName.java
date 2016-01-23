package convertToBusinessName;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class convertToBusnName {
	public static void main(String[] args) throws IOException {

		writeToFile(processExcel(readFile()));
	}

	private static ArrayList<String>  readFile() throws IOException {
		ArrayList<String> inputTechNames = new ArrayList<String>();
		FileInputStream fileIn = new FileInputStream(new File("input.xlsx"));
		Workbook wbIn = new XSSFWorkbook(fileIn);
		//Sheet sheetIn = wbIn.getSheet("new sheet");
		Sheet sheetIn = wbIn.getSheetAt(0);
		Iterator<Row> iterator = sheetIn.iterator();

		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			//Cell cell = cellIterator.next();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				inputTechNames.add(cell.getStringCellValue());						
			}
		}
		wbIn.close();
		return inputTechNames;
	}

	private static ArrayList<String[]> processExcel(ArrayList<String> techNames) {
		ArrayList<String[]> busNames = new ArrayList<String[]>();
		String techName= null;
		String name=null;
		char upperLetter;
		String[] names= null;

		for (int j = 0; j < techNames.size(); j++) {
			//techName will contain a single techName.
			techName = (String) techNames.get(j);
			//		String[] names= techName.split("(?<=.)(?=\\p{Lu})");
			//		String[] names= techName.split("(?=\\p{Lu})"); //separate by upper case with unicode
			names= techName.split("(?=[A-Z])"); //Separate by upper case letters. 
			String[] busNameStringArray = new String[names.length];
			for (int i = 0; i < names.length; i++) 
			{
				name=names[i];
				if(name.startsWith("_"))
				{
					name= name.substring(1, name.length());
				}
				if(name.endsWith("_"))
				{
					name= name.substring(0, name.length()-1);
				}
				if(!Character.isUpperCase((name.charAt(0))))
				{
					upperLetter =  name.toUpperCase().charAt(0);
					name = upperLetter +  name.substring(1, name.length());
				}
				busNameStringArray[i] = name; 
			}		
			busNames.add(busNameStringArray);
		}
		return busNames;
	}

	private static void writeToFile(ArrayList<String[]> busName) throws IOException {
		String firstWord=null;
		String secondWord = null;

		Workbook wb = new XSSFWorkbook();
		FileOutputStream fileOut = null;	
		Sheet sheet = wb.createSheet("new sheet");
		fileOut = new FileOutputStream("workbook.xlsx");
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("Business Name");

		for (int i = 0; i < busName.size(); i++) {
			System.out.println("Big for loop.... iteration: " +i);
			for (int j = 0; j < busName.get(i).length; j++) {	
				if(j> 0){
					secondWord = busName.get(i)[j];
					firstWord=firstWord+" "+secondWord;
				}

				else{
					firstWord = busName.get(i)[j];
				}
			}
			System.out.println("this is the name: " +firstWord);
			row = sheet.createRow(i);
			row.createCell(0).setCellValue(firstWord);
		}
		wb.write(fileOut);
		wb.close();
		fileOut.close();
		System.out.println("Wrote to excel successfully ....");
	}
}