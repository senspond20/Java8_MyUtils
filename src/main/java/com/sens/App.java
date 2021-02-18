package com.sens;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import com.sens.utils.CSVFileVo;
import com.sens.utils.CSVUtils;
import com.sens.utils.ExcelFileUtils;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * 
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {

        final String fileName = "test.csv";
        final Path path = Paths.get(System.getProperty("user.dir"), "src", "resources", "data", fileName);

        CSVUtils csvUtils = CSVUtils.getInstance();
        CSVFileVo vo = null;
        try {
            vo = csvUtils.loadCSV(path, ",", true);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // System.out.println(Arrays.asList(vo.getHeader()));
        // System.out.println(vo.getData());



        ExcelFileUtils ex = new ExcelFileUtils();
        

        final Path path2 = Paths.get(System.getProperty("user.dir"), "src", "resources", "data", "abc.xlsx");

        try {
            ex.loadExcel(path2, 2);
            // ex.loadXLSX(path2, 2);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
