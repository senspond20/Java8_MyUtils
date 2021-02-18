package com.sens.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class BaseFileUtilsTest {
    


    /**
     * 파일 저장 테스트1
     */
    @Test
    void test_파일저장1(){
        long before = System.currentTimeMillis();
        final Path path = Paths.get(System.getProperty("user.dir"), "src", "resources", "data", "test.txt");
        try {
            BaseFileUtils.fileSaveUsingLegarcy(path.toFile(), "안녕하세요", StandardCharsets.UTF_8, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long after = System.currentTimeMillis();
        System.out.printf("%s -[%d%s]\n", "수행시간" ,after-before,"ms");
    }

   /**
     * 파일 저장 테스트1
     */
    @Test
    void test_파일저장2(){
        long before = System.currentTimeMillis();
        final Path path = Paths.get(System.getProperty("user.dir"), "src", "resources", "data", "test2.txt");
        try {
            BaseFileUtils.fileSaveUsingFileWriter(path.toFile(), "안녕하세요dfdf\n", StandardCharsets.UTF_8, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long after = System.currentTimeMillis();
        System.out.printf("%s -[%d%s]\n", "수행시간" ,after-before,"ms");
    }
}
