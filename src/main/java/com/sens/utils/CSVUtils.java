package com.sens.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sens.utils.CSVFileVo.Builder;

/**
 * 
 */
interface CSVUtilsInterface {

    public CSVFileVo loadCSV(Path path, String rgx) throws IOException;

    public Object[][] convertCSVListToMatrix(List<Object> list);

    public boolean saveCSV(Path path, String rgx);
}

/**
 * @apiNote
 */
public class CSVUtils implements CSVUtilsInterface {
    private static CSVUtils csvUtils = null;

    /**
     * @apiNote
     */
    private CSVUtils() {
    }

    /**
     * @apiNote
     */
    public static void getInstance() {
        synchronized (CSVUtils.class) {
            if (csvUtils == null) {
                csvUtils = new CSVUtils();
            }
        }
    }

    /**
     * @apiNote
     */
    @Override
    public CSVFileVo loadCSV(Path path, String rgx) throws IOException {

        File file = new File(path.toUri());
        if (!file.exists()) {
            throw new FileNotFoundException("해당 파일이 존재하지 않습니다.");
        }
        int maxRow = 0, maxCol = 0;
        List<Object> list = new ArrayList<Object>();
        try ( // try ~ catch ~ resources
                BufferedReader br = new BufferedReader(new FileReader(file));) {
            String line = "";
            int tempCol = 0;
            // BufferedReader 에서 데이터를 읽어오면서 최대 행과 열수를 계산하며 list에 담는다.
            while ((line = br.readLine()) != null) {
                Object curr[] = line.split(rgx);
                tempCol = curr.length;
                // 현재 열의 크기가 더 크면 maxCol에 담는다.
                if (tempCol > maxCol) {
                    maxCol = tempCol;
                }
                list.add(Arrays.asList(curr));
                maxRow++;
            }
            br.close();
        } catch (IOException e) {
            throw new IOException("파일을 불러오는데 실패하였습니다.");
        }

        Builder builder = new CSVFileVo.Builder();
        
        return  builder.file(file)
                       .maxRow(maxRow)
                       .maxCol(maxCol)
                       .data(list)
                       .build();   
    }
   /**
     * @apiNote
     */
    @Override
    public Object[][] convertCSVListToMatrix(List<Object> list) {
        return list.stream()
                   .map(item -> ((Collection<?>) item).stream().toArray())
                   .toArray(Object[][]::new);
    }

   /**
     * @apiNote
     */
    @Override
    public boolean saveCSV(Path path, String rgx) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
