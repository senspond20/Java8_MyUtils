package com.sens.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *  @apiNote CSVUtils을 검증한다.
 */
public class CSVUtilsTest {

    final String fileName = "bmt.csv";
    final Path path = Paths.get(System.getProperty("user.dir"), "src", "resources", "data", fileName);
    final CSVUtils cs = CSVUtils.getInstance();
    CSVFileVo csv = null;
    
    @BeforeEach
    void setup() throws IOException {
        csv = cs.loadCSV(path, ",", true);
    }

    /**
     * loadCSV 테스트 
     * @throws IOException
     */
    @Test
    void test1() throws IOException {

       File file = csv.getFile();
       // 파일 정보
       System.out.printf("FileName [%s] %s\n",file.getName() ,file.getAbsolutePath());
       System.out.printf("MaxRow[%d] MaxCol[%d]\n",  csv.getMaxRow(), csv.getMaxCol() );

       // 해더정보
       System.out.println("Header : " + Arrays.toString(csv.getHeader()));
    
       // 데이터
       System.out.println("Data : " + csv.getData().toString());
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

        // 데이터 출력
        for(int i = 0; i < data.length; i ++){
            for(int j = 0; j < data[i].length; j++){
                System.out.printf("%s\t",data[i][j]); 
            }
            System.out.println();
        }
    }


}
