package com.sens.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

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
    public Object[][] convertCSVToMatrix(CSVFileVo csvfile, boolean isHeader);

    /* CSV 파일 데이터를 저장한다 */
    public CSVFileVo saveCSV(Path path, Charset charset, String rgx, List<List<Object>> data, boolean isHeader) throws IOException;
    
     /* 기존 CSV 파일에 데이터를 이어서 저장한다 */
    public CSVFileVo saveCSVAppend(CSVFileVo csv, List<List<Object>> appendData) throws IOException;

}

/**
 * @apiNote
 * @author  senshig
 * @date    2021.02.16 ~ 20
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
     * @param   isHeader        : 해더 존재여부 true, false
     * @return  CSVFileVo       : File, charset, rgx, maxRow, maxCol, header, data 
     */
    @Override
    public CSVFileVo loadCSV(Path path, String rgx, boolean isHeader) throws IOException {

        File file = new File(path.toUri());
        if (!file.exists()) {
            throw new FileNotFoundException("File Not Found!");
        }
        // 파일 인코딩된 charset을 알아온다.
        Charset charset = BaseFileUtils.findFileEncoding(file);
        int maxRow = 0, maxCol = 0;
 
        Builder builder = new CSVFileVo.Builder();
        List<List<Object>> data = new ArrayList<List<Object>>();
 
        try ( // try ~ catch ~ resources 
       
            BufferedReader br = new BufferedReader(new FileReader(file, charset));) {
            String line = "";
            int tempCol = 0;
            
            // BufferedReader 에서 데이터를 읽어오면서 최대 행과 열수를 계산하며 list에 담는다.
            while ((line = br.readLine()) != null) {

                // "," 가 아닐시 정상적이지 않다.-> 수정 필요
                Object curr[] = line.split(rgx);
                
                tempCol = curr.length;
                // 현재 열의 크기가 더 크면 maxCol에 담는다.
                if (tempCol > maxCol) {
                    maxCol = tempCol;
                }
                data.add(Arrays.asList(curr));
                maxRow++;
            }
            br.close();
        } catch (IOException e) {
            throw new IOException("File Load Fail!");
        }

        return  builder.file(file)
                       .charset(charset)
                       .rgx(rgx)
                       .maxRow(maxRow)
                       .maxCol(maxCol)
                       .header(isHeader)
                       .data(data)
                       .build();   
    }

    /**
     * @name   convertCSVToMatrix
     * @apiNote                  : CSV 파일을 읽어온다. 
     * @param  csvfile           : CSVFileVo 객체
     * @return Object[][]
     */
    @Override
    public Object[][] convertCSVToMatrix(CSVFileVo csvfile, boolean isHeader) {

        // 행은 고정이고 열은 가변인 동적배열
        List<List<Object>> list = csvfile.getData();
        Object[][] array = new Object[list.size()][]; 

        // 해더가 true 이면 1행부터 시작, false이면 0행부터 시작 
        int i = isHeader ? 1 : 0;
 
        // 배열에 집어넣기
        for (i = 0; i < array.length; i++) {
            array[i] = list.get(i).toArray();
        }
        return array;
        
    }

   
    

    /**
     * @apiNote                   : CSV 파일로 저장한다.
     * @param path                : 파일경로
     * @param charset             : charset (한글이 들어가서 win Excel로 바로 열였을때 안깨질려면 EUC-KR로 저장)
     * @param rgx                 : 구분자 - 엑셀로 바로 열였을때 행과열로 데이터가 들어가게 하려면 구분자 "," 로 써야한다. 
     * @param data                : 데이터
     * @param isHeader            : 해더 존재유무
     * @return                    : CSVFileVo 리턴
     * @throws IOException
     */
    @Override
    public CSVFileVo saveCSV(Path path, Charset charset, String rgx, List<List<Object>> data, boolean isHeader) throws IOException {
     
        File file = path.toFile();
        BufferedWriter bw = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
   
        Builder builder = new CSVFileVo.Builder();
        final int maxRow = data.size();
        int maxCol = 0;

        if(file !=null && !file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new IOException("잘못된 파일 경로입니다 -> 파일 생성 실패");
            }
        }
        try{
            fos = new FileOutputStream(file.getPath());
            osw = new OutputStreamWriter(fos, charset);
            bw =  new BufferedWriter(osw);

            int tempCol = 0;
            for(List<Object> row : data){
                for(int c =0; c < row.size(); c++){
                    bw.append(row.get(c).toString());
                    if(c != row.size() -1){
                        bw.append(rgx);       // 구분자
                    }
                    tempCol++;
                }          
                if(tempCol > maxCol){
                    maxCol = tempCol;
                }
                tempCol = 0;
                bw.newLine();
            }
        }catch(IOException e){
            throw new IOException("File Save Fail!");
        }finally{
            try {
                bw.close();
                osw.close();
                fos.close();
            } catch (IOException e) {
                throw new IOException("IO close Fail");
            }
        }

        return  builder.file(file)
                       .charset(charset)
                       .rgx(rgx)
                       .maxRow(maxRow)
                       .maxCol(maxCol)
                       .header(isHeader)
                       .data(data)
                       .build();   
        
    }

    /**
     * 기존 csv파일정보를 담고 있는 CSVFileVo를 읽어와 데이터를 이어서 쓴다.
     * @param csv              : CSVFileVo
     * @param appendData       : 이어쓰기 할 데이터
     * @return
     * @throws IOException
     */
    @Override
    public CSVFileVo saveCSVAppend(CSVFileVo csv, List<List<Object>> appendData) throws IOException {
       
        File file = csv.getFile();
        BufferedWriter bw = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
   
        Builder builder = new CSVFileVo.Builder();
        final int MAXROW = csv.getMaxRow() + appendData.size();
        final String RGX = csv.getRgx();

        int maxCol = csv.getMaxCol();
        List<List<Object>> data = csv.getData();
        if(file !=null && !file.exists()){
             throw new IOException("CSVFileVo 에 잘못된 file 파일객체가 담겨있습니다.");
        }
        try{
            fos = new FileOutputStream(file.getPath(), true); // 이어쓰기
            osw = new OutputStreamWriter(fos, BaseFileUtils.findFileEncoding(file));
            bw =  new BufferedWriter(osw);

            int tempCol = 0;
            for(List<Object> row : appendData){
                 data.add(row);
                 for(int c =0; c < row.size(); c++){
                    bw.append(row.get(c).toString());
                    if(c != row.size() -1){
                        bw.append(RGX);       // 구분자
                    }
                    tempCol++;
                }       
                if(tempCol > maxCol){
                    maxCol += tempCol;
                }
                tempCol = 0;
                bw.newLine();
            }
        }catch(IOException e){
            throw new IOException("File Save Fail!");
        }finally{
            try {
                bw.close();
                osw.close();
                fos.close();
            } catch (IOException e) {
                throw new IOException("IO close Fail");
            }
        }

        return  builder.file(file)
                       .maxRow(MAXROW)
                       .maxCol(maxCol)
                       .data(data)
                       .build();  

    }


     // CSVFileVo 를 불러와 구분자를 전부 변경시켜서 저장한다. 
     public void convertSaveRgx(String rgx, CSVFileVo csvFile){

        
    }
}
