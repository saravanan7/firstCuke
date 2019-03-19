package libraryMethods;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFObjectData;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import framework.methods.ScenarioStore;

public class InputdataManager {

	static ScenarioStore scenario=new ScenarioStore();

	/**
	 ************************************************************* 
	 * @throws IOException 
	 * @Purpose - Methods to get the value from any excel
	 ************************************************************* 
	 */

	public static synchronized XSSFWorkbook getWorkBook(FileInputStream objFileInputStream) throws IOException{
		XSSFWorkbook objWorkbook=null;
		try{

			objWorkbook = new XSSFWorkbook(objFileInputStream);
		}catch(Exception e){
			System.out.println("Exception occured - getWorkBook: "+e.toString());
			e.printStackTrace();
		}
		return objWorkbook;
	}

	public static synchronized XSSFSheet getWorkSheet(XSSFWorkbook objWorkbook, String sheetName){
		XSSFSheet objWorkSheet=null;
		try{
			objWorkSheet=objWorkbook.getSheet(sheetName.trim());
		}catch(Exception e){
			System.out.println("Exception occured - getWorkSheet: "+e.toString());
			e.printStackTrace();
		}finally{

		}
		return objWorkSheet;
	}

	public static synchronized String getDataFromExcel(String inputFileName, String sheetName, String rowValue, String columnValue) throws IOException{
		String strData=null;
		FileInputStream objFileInputStream=null;
		XSSFSheet objSheet = null;
		int physicalRows = 0,desiredRow=0,desiredCol=0;
		XSSFRow objRow = null;
		try{
			System.out.println("systemenv: "+System.getProperty("user.dir"));
			if (StringUtils.isNotEmpty(inputFileName) && StringUtils.isNotEmpty(sheetName) && StringUtils.isNotEmpty(rowValue) && StringUtils.isNotEmpty(columnValue)) {
				objFileInputStream = new FileInputStream(inputFileName);
				objSheet=getWorkSheet(getWorkBook(objFileInputStream), sheetName);
				physicalRows=objSheet.getPhysicalNumberOfRows();
				objRow = objSheet.getRow(0);
				int firstColPosition = objRow.getFirstCellNum();
				int lastColPosition = objRow.getLastCellNum();

				for(int dr=0;dr<physicalRows;dr++){
					String currentRowID=objSheet.getRow(dr).getCell(firstColPosition).getStringCellValue();
					if(currentRowID.equals(rowValue)){
						desiredRow=dr;
						break;
					}
				}

				for(int dc=firstColPosition;dc<lastColPosition;dc++){
					String desiredColID=objSheet.getRow(0).getCell(dc).getStringCellValue();
					if(desiredColID.equals(columnValue)){
						desiredCol=dc;
						break;
					}
				}
				strData=objSheet.getRow(desiredRow).getCell(desiredCol).toString();
				if (org.apache.commons.lang3.StringUtils.isEmpty(strData) || org.apache.commons.lang3.StringUtils.isBlank(strData)) {
					strData = null;
				}
			}

		}catch(Exception e){
			System.out.println("Exception occured - getDataFromExcel: "+e.toString());
			e.printStackTrace();
		}finally{
			objFileInputStream.close();
		}
		return strData;
	}


	public static String getvalueFromAnyExcel(String inputFileName, String sheetName, String rowValue, String columnValue) throws IOException {
		String outputCellValue = null;
		XSSFSheet objSheet = null;
		XSSFRow objRow = null;
		XSSFCell objCell = null;
		XSSFWorkbook objWorkbook=null;
		FileInputStream objfileInputStream=null;

		try {

			if (StringUtils.isNotEmpty(inputFileName) && StringUtils.isNotEmpty(sheetName) && StringUtils.isNotEmpty(rowValue) && StringUtils.isNotEmpty(columnValue)) {

				int intSheetPhyTotalRows = 0;
				String rowIDValue, colHeaderName, rowCellValue, colCellValue = null;
				HashMap<String, String> rowIDMap, colNameMap = null;

				objfileInputStream = new FileInputStream(inputFileName);

				objWorkbook = new XSSFWorkbook(objfileInputStream);
				objSheet = objWorkbook.getSheet(sheetName.trim());
				intSheetPhyTotalRows = objSheet.getPhysicalNumberOfRows();

				// Get all the row values in FIRST column / ID column and
				// add to map

				rowIDMap = new HashMap<String, String>();

				for (int idRowCnt = 1; idRowCnt <= intSheetPhyTotalRows - 1; idRowCnt++) {
					objRow = objSheet.getRow(idRowCnt);
					if (objRow != null) {

						objCell = objRow.getCell(0);

						if (objCell != null && objCell.getCellType() != CellType.BLANK) {
							objCell.setCellType(CellType.STRING);
							rowIDValue = objCell.getStringCellValue();
							rowIDMap.put(rowIDValue.toUpperCase(), Integer.toString(idRowCnt));
						}
					}
				}

				// Get all the column header values and add to map

				colNameMap = new HashMap<String, String>();
				objRow = objSheet.getRow(0);
				int intFirstCol = objRow.getFirstCellNum();
				int intLastCol = objRow.getLastCellNum();

				for (int colHeaderCnt = intFirstCol; colHeaderCnt <= intLastCol - 1; colHeaderCnt++) {
					objCell = objRow.getCell(colHeaderCnt);
					if (objCell != null && objCell.getCellType() != CellType.BLANK) {
						objCell.setCellType(CellType.STRING);
						colHeaderName = objCell.getStringCellValue().trim();

						if (StringUtils.isNotEmpty(colHeaderName) && StringUtils.isNotBlank(colHeaderName)) {
							colNameMap.put(colHeaderName.toUpperCase(), Integer.toString(colHeaderCnt));
						}
					}
				}

				// Fetch the corresponding value based on the ROW & COLUMN
				// combination

				rowCellValue = rowIDMap.get(rowValue.toUpperCase().trim());
				colCellValue = colNameMap.get(columnValue.toUpperCase().trim());

				objRow = objSheet.getRow(Integer.parseInt(rowCellValue));

				if (objRow != null) {

					objCell = objRow.getCell(0);

					if (objCell != null && objCell.getCellType() != CellType.BLANK) {
						objCell = objRow.getCell(Integer.parseInt(colCellValue));
						objCell.setCellType(CellType.STRING);

						outputCellValue = objCell.getStringCellValue().trim();
					}
				}

				if (org.apache.commons.lang3.StringUtils.isEmpty(outputCellValue) || org.apache.commons.lang3.StringUtils.isBlank(outputCellValue)) {
					outputCellValue = null;
				}

			}else{
				throw new Exception("Input file details are not given properly"); 
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			objfileInputStream.close();
		}
		return outputCellValue;
	}



}
