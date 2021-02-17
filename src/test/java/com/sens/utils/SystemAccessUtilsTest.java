package com.sens.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SystemAccessUtilsTest {

    SystemAccessUtils sysUtils = SystemAccessUtils.getInstance();
    final String fileName = "bmt.csv";
    final Path path = Paths.get(System.getProperty("user.dir"), "src", "resources", "data", fileName);
    boolean isWindows = false;

    Process process = null;

    /**
     * OS마다 명령어가 다르므로 OS를 확인할 필요가 있다.
     */

    @BeforeEach
    void setup() {
        String os = sysUtils.getSystemOS();
        if (os.toLowerCase().contains("windows")) {
            isWindows = true;
        }
        System.out.printf("[%s] -> windows is %s\n", os, isWindows);


    }

    @Test
    void test1() {
        if (isWindows) {
            // bmt.csv 를 메모장 프로그램으로 연다.
            process = sysUtils.runExec(new String[] { "notepad.exe", path.toString() });
           
            System.out.println(process.pid());
        } else {
            // 리눅스 일때
            // 맥 OS일때..
            // 안드로이드 일떄
        }
        System.out.println(process.toString());
    }

    @Test
    void test2(){
        if (isWindows) {
           
            process = sysUtils.runExec(new String[] { "process.exe"});

        } else {
            // 리눅스 일때
            // 맥 OS일때..
            // 안드로이드 일떄
        }
        System.out.println(process.toString());
    }

    @Test
    void test3() {
        if (process != null) {
            System.out.println(process.toString());
            process.destroy();
        }

    }
    @Test
    void testInte() throws InterruptedException {
        throw new InterruptedException();
    }

    @Test
    void test4() throws IOException, InterruptedException {
        String[] command = new String[] { "echo", "hello" };
        //sysUtils.byRuntime(command);
        // sysUtils.byProcessBuilder(command);
        // sysUtils.byProcessBuilderRedirect(command);
    }

    @Test
    void test5() throws IOException, InterruptedException {
        if (isWindows) {
           
            String result = sysUtils.runExecAndGetString(new String[] {"ipconfig" });
            System.out.println(result);

        } else {
            // 리눅스 일때
            // 맥 OS일때..
            // 안드로이드 일떄
        }
        System.out.println(process.toString());
    }


}
