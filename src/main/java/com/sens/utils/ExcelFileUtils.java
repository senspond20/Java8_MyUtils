package com.sens.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import com.sens.utils.model.DataSet;
import com.sens.utils.model.ExcelType;
import com.sens.utils.model.ExcelWorkBook;
import com.sens.utils.model.DataSet.Builder;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

interface ExcelFileUtilsInteface{
    
    public ExcelWorkBook loadExcelFile(File file) throws IOException;

    public DataSet loadDate(ExcelWorkBook excelWorkBook, int sheetPageNo, boolean isHeader);

}

/**
 * @name ExcelFileUtils
 * -- poi 라이브러리 의존성
 * <!-- 엑셀(xls) -->
    <dependency>
      <groupId>org.apache.poi</groupId>
      <artifactId>poi</artifactId>
      <version>4.1.2</version>
    </dependency>
    <!-- 엑셀 (xlsx) -->
    <dependency>
      <groupId>org.apache.poi</groupId>
      <artifactId>poi-ooxml</artifactId>
      <version>4.1.2</version>
    </dependency>
 *
 *           Workbook         Sheet       Row        Cell
 *  xsl  : HSSFWorkbook -> HSSFSheet -> HSSFRow -> HSSFCell -> 값 획득
 *  xslx : XSSFWorkbook -> XSSFSheet -> XSSFRow -> XSSFCell -> 값 획득
 *
 */
public class ExcelFileUtils {
    
    public ExcelFileUtils() {
    
    }                
    public ExcelWorkBook loadExcelFile(File file) throws IOException {
        ExcelWorkBook vo = new ExcelWorkBook();
        try (   // try ~ catch ~ resources
                FileInputStream fis = new FileInputStream(file);
            ) {
                Workbook workbook = null;
                String ext = BaseFileUtils.getExtFromFileName(file.getName());
                if(ext.equals("xlsx")){
                    workbook = new XSSFWorkbook(fis);
                    vo.setExcelType(ExcelType.XLSX);
                }
                else if(ext.equals("xls")){
                    // workbook = new 
                    vo.setExcelType(ExcelType.CSV);
                }
                else if(ext.equals("csv")){

                }else{
                    throw new RuntimeException("지원되지 않는 형식의 파일입니다.");
                }
                
                vo.setWorkBook(workbook);

                fis.close();
                workbook.close();
        } catch (IOException e) {
            throw new IOException("불러오기 실패");
        }
        return vo;
    }

    public DataSet loadDate(ExcelWorkBook excelWorkBook, int sheetPageNo, boolean isHeader){

        Workbook workbook = excelWorkBook.getWorkBook();
        ExcelType type = excelWorkBook.getExcelType();
        Sheet sheet = null;
        
        if(type.equals(ExcelType.XLSX)){
            sheet = (XSSFSheet) workbook.getSheetAt(sheetPageNo);
        }
        
        Builder builder = new DataSet.Builder();
        builder.maxRow(sheet.getPhysicalNumberOfRows());
        builder.maxCol(1);
        

        return null;

    }

    public void loadXLSX(Path path, int SheetPageNo) throws IOException {
        FileInputStream fis = new FileInputStream(path.toFile());
        XSSFWorkbook workbook=new XSSFWorkbook(fis);

        int rowindex=0;
        int columnindex=0;

        //시트 수 (첫번째에만 존재하므로 0을 준다)
        //만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
        XSSFSheet sheet = workbook.getSheetAt(SheetPageNo);
        //행의 수
        int rows=sheet.getPhysicalNumberOfRows();
       
        for(rowindex=1;rowindex<rows;rowindex++){
            //행을읽는다
            XSSFRow row=sheet.getRow(rowindex);
            if(row !=null){
                //셀의 수
                int cells=row.getPhysicalNumberOfCells();
                for(columnindex=0;columnindex<=cells;columnindex++){
                    //셀값을 읽는다
                    XSSFCell cell=row.getCell(columnindex);
                    String value="";
                    //셀이 빈값일경우를 위한 널체크
                    
                    if(cell==null){
                        continue;
                    }else{
                        //타입별로 내용 읽기
                        // CellType cellType = cell.getCellType();
                        switch (cell.getCellType()){
                        case FORMULA:
                            value=cell.getCellFormula();
                            break;
                        case NUMERIC:
                            value=cell.getNumericCellValue()+"";
                            break;
                        case STRING:
                            value=cell.getStringCellValue()+"";
                            break;
                        case BLANK:
                            value=cell.getBooleanCellValue()+"";
                            break;
                        case ERROR:
                            value=cell.getErrorCellValue()+"";
                            break;
                        default:
                            break;
                        }
                    }

                    // data[rowindex][columnindex] = value;
                    System.out.println("각 셀 내용 :"+value);
                }
            }
        }
        workbook.close();
        
        // return data;
    }
}
