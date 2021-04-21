package com.sens;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import com.sens.utils.BaseFileUtils;
import com.sens.utils.CSVFileVo;
import com.sens.utils.CSVUtils;
import com.sens.utils.model.TreeNodeJ;

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

        TreeNodeJ<String> root = new TreeNodeJ<String>("root");
        {
            TreeNodeJ<String> node1 = root.addChild("node1");
            TreeNodeJ<String> node2 = root.addChild("node2");
            TreeNodeJ<String> node3 = root.addChild("node3");
            {
                TreeNodeJ<String> node4 = node1.addChild("node4");
                TreeNodeJ<String> node5 = node1.addChild("node5");
                {
                    TreeNodeJ<String> node6 = node3.addChild("node6");
                }
            }
        }
        System.out.println(root);



        // final String fileName = "bmt.csv";
        // final Path path = Paths.get(System.getProperty("user.dir"), "src", "resources", "data", fileName);

        // CSVUtils csvUtils = CSVUtils.getInstance();
        // CSVFileVo vo = null;
        // try {
        //     vo = csvUtils.loadCSV(path, ",", true);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }


        //  System.out.println(Arrays.asList(vo.getHeader()));
        //  System.out.println(vo.getData());

        //  final Path path2 = Paths.get(System.getProperty("user.dir"), "src", "resources", "data", "test.txt");
        //  try {
        //      BaseFileUtils.fileSaveUsingLegarcy(path2.toFile(), "안녕하세요dfdfd232", StandardCharsets.UTF_8, true);
        //  } catch (IOException e) {
        //      e.printStackTrace();
        //  }


        // ExcelFileUtils ex = new ExcelFileUtils();
        

        // final Path path2 = Paths.get(System.getProperty("user.dir"), "src", "resources", "data", "abc.xlsx");

        // try {
        //     ex.loadExcel(path2, 2);
        //     // ex.loadXLSX(path2, 2);
        // } catch (IOException e) {
        //     
        //     e.printStackTrace();
        // }

     }
}
