package com.sens.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * static 
 */
public class BaseFileUtils {
    private BaseFileUtils() { } // 생성자 금지

    public static String getExtFromFileName(String fileName){
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length())
                       .toLowerCase();
    }

    public static void isExistsCheckAndMkdirs(Path dirPath){
        File file = new File(dirPath.toUri());
        if(!file.exists()){
            file.mkdirs();
        }
        
    }
    // public static File makeFile(){
    //     return new File(Paths.get(dirPath.toString(), fileName).toString());
    // }

    /**
     * 
     * @param file
     * @param data
     * @param charset               : charset
     * @param isAppend              : 이어쓰기 여부
     * @return
     * @throws IOException
     */
    public static boolean fileWrite(File file, String data, Charset charset, boolean isAppend) throws IOException {
        boolean isSucess = false;    
        try( 
            
        
                FileWriter fw = new FileWriter(file,charset, isAppend);
                BufferedWriter bw = new BufferedWriter( fw );
            )
        {
            bw.write(data); //버퍼에 데이터 입력
            bw.flush(); //버퍼의 내용을 파일에 쓰기
            isSucess = true;
        }catch ( IOException e ) {
            throw new IOException("파일 저장에 실패하였습니다.");
        }
        return isSucess;
    }

    public static File fileRead(){
        return null;
    }


}
