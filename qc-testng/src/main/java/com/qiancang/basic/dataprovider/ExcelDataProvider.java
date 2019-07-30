package com.qiancang.basic.dataprovider;

import com.qiancang.basic.log.LogUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class ExcelDataProvider {

	/**
	 * @Description 读取sheet中的数据到二维数组中,并返回
	 * 格式如:{{"A","1"},{"B","2"}}
	 **/
	public static String[][] getSheetData(Sheet sheet) {

		int rowCount=sheet.getLastRowNum()-sheet.getFirstRowNum();
		List<String[]> records =new ArrayList <String[]>();
		
    	for(int i=1;i<rowCount;i++){
    		Row row = sheet.getRow(i);
    		String fields[] = new String[row.getLastCellNum()];
    		for(int j=0;j<row.getLastCellNum();j++){
    			fields[j]=row.getCell(j).getStringCellValue();
    		}
    		records.add(fields);
    		
    	}

		String[][] results=new String[records.size()][];
    	
    	for(int i=0;i<records.size();i++){
    		results[i]=records.get(i);
    	}

    	return results;

		
	}

	//根据excel路径和指定的sheetname,读取sheet表的记录,返回二维数组
	//外层数组表示行row,内层数组表示单元格cells
	public static String[][] getSheetData(String filePath,String... sheetname) {
		Workbook workbook = getWorkbook(filePath);
		Sheet sheet = null;
		String[][] sheetData = null;
		if (workbook != null ){
			sheet = getSheet(workbook, sheetname);
		}
		if (sheet != null){
			sheetData = getSheetData(sheet);
		}
		return sheetData;
	}

	//默认通过索引获取sheet,如果有传值sheetname则根据名称获取sheet
	public static Sheet getSheet(Workbook workbook,String... sheetname){

		Sheet sheet = workbook.getSheetAt(0);
		if (sheetname.length > 0){
			sheet = workbook.getSheet(sheetname[0]);
		}

		return sheet;
	}

	public static Workbook getWorkbook(String filePath){
		Workbook workbook = null;
		try {
			File file = new File(filePath);
			FileInputStream inputStream = new FileInputStream(file);
			workbook = null;
			String extensionName = filePath.substring(filePath.indexOf("."));
			if (extensionName.equals(".xls")){
				workbook= new HSSFWorkbook(inputStream);
			}
			else if (extensionName.equals(".xlsx")){
				workbook= new XSSFWorkbook(inputStream);
			}
			else{
				LogUtils.info("文件格式不正确");
			}
		} catch (IOException e) {
			LogUtils.error(e.getMessage(),e);
		}

		return workbook;
	}

	@DataProvider
	public Object[][] providerExcelMethod(Method method) {
		Object[][] o  = null;
		return o;
	}

	public static void main(String[] args) {


	}

}
