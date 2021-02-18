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

    @Test
    void test_인코딩charset체크() throws IOException {
        final Path path1 = Paths.get(System.getProperty("user.dir"), "src", "resources", "data", "bmt.csv");
        System.out.println("file1 : " + BaseFileUtils.findFileEncoding(path1.toFile()));

        final Path path2 = Paths.get(System.getProperty("user.dir"), "src", "resources", "data", "korean2.csv");
        System.out.println("file2 : " + BaseFileUtils.findFileEncoding(path2.toFile()));
    }

    @Test
    void test_파일인코딩UTF8변환() throws IOException {
        final Path path = Paths.get(System.getProperty("user.dir"), "src", "resources", "data", "korean2.csv");
        BaseFileUtils.fileConvertUTF8(path.toFile(), false);
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
