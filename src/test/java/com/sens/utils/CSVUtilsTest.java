package com.sens.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *  @apiNote CSVUtils을 검증한다.
 */
public class CSVUtilsTest {

    // /CSVUtils cs = null;
    //CSVFileVo csv = null;
    
    @BeforeEach
    void setup() throws IOException {
        // cs = CSVUtils.getInstance();
    }
    /**
     * loadCSV 테스트 
     * @throws IOException
     */

    void print(CSVFileVo csv) throws IOException {
        File file = csv.getFile();
        System.out.printf("FileName [%s] %s\n",file.getName() ,file.getAbsolutePath());
        // System.out.println("FileType : " +  Files.probeContentType(file.toPath()));
        System.out.printf("fileCharset [%s]\n", csv.getCharset());
        System.out.printf("rgx [%s]\n", csv.getRgx());
        System.out.printf("MaxRow[%d] MaxCol[%d]\n",  csv.getMaxRow(), csv.getMaxCol() );
        System.out.println("Header : " + csv.getHeader());      // 해더유무
        System.out.println("Data : " + csv.getData().toString());     // 데이터
    }
    @Test
    void test_CSV읽어오기1() throws IOException {

       final String fileName = "korean2.csv";
       final Path path = Paths.get(System.getProperty("user.dir"), "src", "resources", "data", fileName);

       CSVUtils cs = CSVUtils.getInstance();
       CSVFileVo csv = cs.loadCSV(path, ",", true);

       // 파일 정보 출력
       print(csv);
    }

    @Test
    void test_CSV읽어오기2() throws IOException {

       final String fileName = "korean2.csv";
       final Path path = Paths.get(System.getProperty("user.dir"), "src", "resources", "data", fileName);
       CSVUtils cs = CSVUtils.getInstance();
       CSVFileVo csv = cs.loadCSV(path, ",", true);

       // 파일 정보
      // 파일 정보 출력
       print(csv);

       List<List<Object>> o = csv.getData();
       System.out.println("======================");
       System.out.printf("%s %s %s\n", o.get(0).get(0), o.get(0).get(1), o.get(0).get(2));
    }


    @Test
    void test_CSV저장() throws IOException {
        final String fileName = "save.csv";
        final Path path = Paths.get(System.getProperty("user.dir"), "src", "resources", "data", fileName);

        CSVUtils cs = CSVUtils.getInstance();

        List<List<Object>> data = new ArrayList<List<Object>>();
        data.add(Arrays.asList("1 Head", "2 Head", "3 Head", "4 Head", "5 Head", "6 Head"));
        data.add(Arrays.asList("가나다", "안녕", "가을", 1110, 123, 533));
        data.add(Arrays.asList("Java", "C#", 1011, "Spring","안녕이"));
        data.add(Arrays.asList("문자열", 1010, "서울시", "11AC"));
        data.add(Arrays.asList(1.97, "가을이", "하늘이"));

        // save2.csv 에다가 data를 구분자 | 로 저장하고
        CSVFileVo csv = cs.saveCSV(path, StandardCharsets.UTF_8, "|" , data, true);
 
        // 파일 정보 출력
        print(csv);

        List<List<Object>> o = csv.getData();
        System.out.println("======================");
        System.out.printf("%s %s %s\n", o.get(0).get(0), o.get(0).get(1), o.get(0).get(2));
    }

    @Test
    void test_CSV읽어오기_다른_구분자의_경우() throws IOException {

       final String fileName = "save.csv";
       final Path path = Paths.get(System.getProperty("user.dir"), "src", "resources", "data", fileName);
       CSVUtils cs = CSVUtils.getInstance();
       CSVFileVo csv = cs.loadCSV(path, "|", true);
      
        // 파일 정보 출력
        print(csv);
      
        List<List<Object>> o = csv.getData();
        System.out.println("======================");
        System.out.printf("%s %s %s\n", o.get(0).get(0), o.get(0).get(1), o.get(0).get(2));
    }

    @Test
    void test_CSV저장_이어쓰기() throws IOException {

        CSVUtils cs = CSVUtils.getInstance();
        final String fileName = "save.csv";
        final Path path = Paths.get(System.getProperty("user.dir"), "src", "resources", "data", fileName);

        // save.csv 파일을 불러와서 데이터 이어쓰기
        CSVFileVo csvFile = cs.loadCSV(path, "|", true);
        List<List<Object>> appendData = new ArrayList<List<Object>>();
        appendData.add(Arrays.asList("이어쓰기1", "안녕", "이어써", 1110, 123, 533,45454));
        appendData.add(Arrays.asList("Java", "C#", 1011, "Spring","안녕이",54545,454545,45454,45454));

       // save2.csv 에다가 appendData 를 이어쓰기
        csvFile = cs.saveCSVAppend(csvFile, appendData);
          
        // 파일 정보 출력
        print(csvFile);
      
        List<List<Object>> o = csvFile.getData();
        System.out.println("======================");
        System.out.printf("%s %s %s\n", o.get(0).get(0), o.get(0).get(1), o.get(0).get(2));
    }


    
  

    @Test
    void test_CSV저장_엑셀바로열기한글() throws IOException {
        final String fileName = "save_win.csv";
        final Path path = Paths.get(System.getProperty("user.dir"), "src", "resources", "data", fileName);

        List<List<Object>> data = new ArrayList<List<Object>>();
        data.add(Arrays.asList("1 Head", "2 Head", "3 Head", "4 Head", "5 Head", "6 Head"));
        data.add(Arrays.asList("가나다", "안녕", "가을", 1110, 123, 533));
        data.add(Arrays.asList("Java", "C#", 1011, "Spring","안녕이"));
        data.add(Arrays.asList("문자열", 1010, "서울시", "11AC"));
        data.add(Arrays.asList(1.97, "가을이", "하늘이"));

        CSVUtils cs = CSVUtils.getInstance();

        // 윈도우용 엑셀로 바로 열였을때 한글 안깨지게 -> EUC-KR 로 저장해야 한다.
        CSVFileVo csv = cs.saveCSV(path, Charset.forName("EUC-KR"), ",", data, true); 

        // 파일 정보 출력
        print(csv);

        List<List<Object>> o = csv.getData();
        System.out.println("======================");
        System.out.printf("%s %s %s\n", o.get(0).get(0), o.get(0).get(1), o.get(0).get(2));

    }


        /**
     * loadCSV 테스트 
     * @throws IOException
     */
    @Test
    void test_CSV읽어오기_한글() throws IOException {
    //    File file = csv.getFile();
                
       // 파일 정보
    //    System.out.printf("FileName [%s] %s\n",newFile.getName() ,newFile.getAbsolutePath());
    //    System.out.printf("MaxRow[%d] MaxCol[%d]\n",  csv.getMaxRow(), csv.getMaxCol() );
    //    // 해더정보
    //    System.out.println("Header : " + Arrays.toString(csv.getHeader()));
    //    // 데이터
    //    System.out.println("Data : " + csv.getData().toString());
       
    }

    /**
     * convertCSVToMatrix 테스트 - CSVFileVo 의 데이터를 2차원 배열로 변환
     * @throws IOException
     */
    @Test
    void test2() throws IOException {
        
        Object[] header = csv.getHeader();
        Object[][] data = cs.convertCSVToMatrix(csv);

         // 헤더 출력
        for(int h = 0; h < header.length; h++){
            System.out.printf("%s\t",header[h]);
        }
        System.out.println();

        //데이터 출력
        for(int i = 0; i < data.length; i ++){
            for(int j = 0; j < data[i].length; j++){
                System.out.printf("%s\t",data[i][j]); 
            }
            System.out.println();
        }
    }


}
