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
		ArrayList<String> techNames = new ArrayList<String>();
		ArrayList<String[]> businessNames = new ArrayList<String[]>();
		String techName = "_first_NameGiven";


		techNames = readFile();
		businessNames=processExcel(techNames);
		writeToFile(businessNames);


		//String[] names= techName.split("(?==[A-Z])");
		//		String[] names= techName.split("(?<=.)(?=\\p{Lu})");
		//		String[] names= techName.split("(?=\\p{Lu})"); //separate by upper case with unicode




	}



	/*techNames is an arrayList that contains all the techNames. 
	 * 
	 */
	private static ArrayList processExcel(ArrayList techNames) {
		ArrayList<String[]> busNames = new ArrayList<String[]>();
		String techName= null;
		String name=null;
		char upperLetter;
		String[] names= null;
		int count = 0;

		System.out.println("inside process Excel....");
		for (int j = 0; j < techNames.size(); j++) {
			//techName will contain a single techName.
			techName = (String) techNames.get(j);

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
			//						for (int i = 0; i < busNameStringArray.length; i++) {
			//							System.out.println("length of busNameStringArray is: " + busNameStringArray.length);
			//							System.out.println(busNameStringArray[i]);
			//						}
			busNames.add(busNameStringArray);
		}
		//				for (int i = 0; i < busNames.size(); i++) {
		//					for (int j = 0; j < busNames.get(i).length; j++) {				
		//						System.out.println(busNames.get(i)[j]);
		//					}
		//				}
		return busNames;
	}

	private static void writeToFile(ArrayList<String[]> busName) throws IOException {
		String firstWord=null;
		String secondWord = null;
		char upperLetter;

		Workbook wb = new XSSFWorkbook();
		FileOutputStream fileOut = null;	
		Sheet sheet = wb.createSheet("new sheet");
		fileOut = new FileOutputStream("workbook.xlsx");
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("Business Name");
		//		for (int i = 0; i < busName.size(); i++) {
		//			for(int j =0; j< busName.get(i;j++)
		//			{
		//			name =  busName.get(i)[j];
		//
		//			row = sheet.createRow(i+1);
		//			row.createCell(0).setCellValue(name);
		//			}
		//}
		for (int i = 0; i < busName.size(); i++) {
			System.out.println("Big for loop.... iteration: " +i);
			for (int j = 0; j < busName.get(i).length; j++) {	
				System.out.println("small for loop...iteration: "+ j);
				System.out.println(busName.get(i)[j]);


				if(j> 0){
					//System.out.println("inside if...");
					//secondWord= firstWord;
					secondWord = busName.get(i)[j];
					firstWord=firstWord+" "+secondWord;
				}

				else{
				firstWord = busName.get(i)[j];
				}
				
//
//				if(j> 0){
//					System.out.println("inside if...");
//					firstWord= secondWord+ " " +firstWord;
//					System.out.println("this is the total word " + firstWord);
//				}
			}
			System.out.println("this is the name: " +firstWord);

			row = sheet.createRow(i);
			row.createCell(0).setCellValue(firstWord);
		}
		wb.write(fileOut);
		wb.close();
		fileOut.close();
	}




	private static ArrayList  readFile() throws IOException {
		System.out.println("Inside readFile!!");
		ArrayList<String> inputTechNames = new ArrayList<String>();
		FileInputStream fileIn = new FileInputStream(new File("input.xlsx"));
		Workbook wbIn = new XSSFWorkbook(fileIn);
		Sheet sheetIn = wbIn.getSheet("new sheet");
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

		//		for (int i = 0; i < inputTechNames.size(); i++) {
		//			System.out.println(inputTechNames.get(i));
		//		}

		wbIn.close();
		return inputTechNames;
	}
}
