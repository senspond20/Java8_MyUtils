package com.sens.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.sens.utils.CSVFileVo.Builder;

enum EncodingType { // 인코딩 타입
    UTF8, EUCKR, Cp1252
}
/**
 * CSVUtils 인터페이스 
 */
interface CSVUtilsInterface {

    /* CSV 파일 확장자인지 검사한다. */
    public boolean isCSVFileCheck(String fileName);
    
    /* CSV 파일을 읽어온다. -> CSVFileVo 에 담아 리턴 */
    public CSVFileVo loadCSV(Path path, String rgx, boolean isHeader) throws IOException;
 
    /* CSV 파일 데이터를 2차원 배열로 변환하다. */
    public Object[][] convertCSVToMatrix(CSVFileVo csvfile);

    /* Object List 을 CSV 파일로 저장한다. */
    public boolean saveCSV(Path path, String rgx, List<Object> list, Character character, boolean isHeader, boolean isAppend) throws IOException;

    /* 2차원 배열을 CSV 파일로 저장한다. */
    public boolean saveCSV(Path path, String rgx, Object[][] data, boolean isHeader);
}

/**
 * @apiNote
 * @author  senshig
 * @date    2021.02.16 ~ 17
 */
public class CSVUtils implements CSVUtilsInterface {
   
    /**
     *  싱글톤 패턴
     */
    private static CSVUtils instance = null;
    private CSVUtils() {
    }
    public static CSVUtils getInstance() {
        synchronized (CSVUtils.class) {
            if (instance == null) {
                instance = new CSVUtils();
            }
            return instance;
        }
    }

   /**@name    isCSVFileCheck
    * @apiNote                  : CSV 파일인지 체크한다.
    * @param   path             : 파일 경로
    * @return  boolean
    */
    @Override
    public boolean isCSVFileCheck(String fileName) {
        String ext = BaseFileUtils.getExtFromFileName(fileName);
       // String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        if (!ext.toLowerCase().equals("csv")) {
           return false;
        }
        return true;
    }
    /**
     * @name    loadCSV
     * @apiNote                 : CSV 파일을 읽어온다. 
     * @param   path            : 파일명을 포함한 파일 경로
     * @param   rgx             : 구분자 (보편적으로 ","를 구분자로 사용됨)
     * @param   isHeader        : 해더 존재여부 
     *                          (false이면 CSVFileVo의 header는 null이 리턴되며 CSVFileVo data에 해더까지 같이 담아온다 
     *                           , true일시 해더까지 포함되서 maxRow 가 계산)
     * @return  CSVFileVo       : File, maxRow, maxCol, header, data 
     */
    @Override
    public CSVFileVo loadCSV(Path path, String rgx, boolean isHeader) throws IOException {

        File file = new File(path.toUri());
        if (!file.exists()) {
            throw new FileNotFoundException("해당 파일이 존재하지 않습니다.");
        }
        // 파일 인코딩된 charset을 알아온다.
        Charset charset = BaseFileUtils.findFileEncoding(file);
        int maxRow = 0, maxCol = 0;
 
        Builder builder = new CSVFileVo.Builder();
        List<Object> list = new ArrayList<Object>();
 
        try ( // try ~ catch ~ resources
       
            BufferedReader br = new BufferedReader(new FileReader(file, charset));) {
            String line = "";
            int tempCol = 0;
            // BufferedReader 에서 데이터를 읽어오면서 최대 행과 열수를 계산하며 list에 담는다.
            while ((line = br.readLine()) != null) {
                String curr[] = line.split(rgx);
                tempCol = curr.length;
                // 현재 열의 크기가 더 크면 maxCol에 담는다.
                if (tempCol > maxCol) {
                    maxCol = tempCol;
                }
                if(isHeader && maxRow == 0){
                    builder.header(curr);
                }else{
                    list.add(Arrays.asList(curr));
                }
                maxRow++;
            }
            br.close();
        } catch (IOException e) {
            throw new IOException("파일을 불러오는데 실패하였습니다.");
        }

        return  builder.file(file)
                       .maxRow(maxRow)
                       .maxCol(maxCol)
                       .data(list)
                       .build();   
    }

    /**
     * @name   convertCSVToMatrix
     * @apiNote                  : CSV 파일을 읽어온다. -> 레가시 코드로 다시 작성해볼것
     * @param  csvfile           : CSVFileVo 객체
     * @return Object[][]
     */
    @Override
    public Object[][] convertCSVToMatrix(CSVFileVo csvfile) {
        return csvfile.getData()
                      .stream()
                      .map(item -> ((Collection<?>) item).stream().toArray())
                      .toArray(Object[][]::new);
        
    }

    
    /**
     * @name saveCSV
     * @apiNote : CSV 파일을 읽어온다.
     * @param path     : 파일명을 포함한 파일 경로
     * @param rgx      : 구분자
     * @param isHeader : 해더 포함여부
     * @param isAppend : 이어쓰기 여부
     * @return boolean
     * @throws IOException
     */
    @Override
    public boolean saveCSV(Path path, String rgx, List<Object> list, Character character, boolean isHeader,
            boolean isAppend) throws IOException {
        
        
        System.out.println("list : " + list);

        return BaseFileUtils.fileSaveUsingLegarcy(path.toFile(), "안녕하세요", StandardCharsets.UTF_8, isAppend);
    }

    /**
     * @name    saveCSV
     * @apiNote                  : CSV 파일을 읽어온다.
     * @param   path             : 파일명을 포함한 파일 경로
     * @param   rgx              : 구분자
     * @param   isHeader         : 해더 포함여부
     * @return  boolean
     */
    @Override
    public boolean saveCSV(Path path, String rgx, Object[][] data, boolean isHeader) {
        

      
        return false;
    }


    public void convertChartset(){

    }

}
