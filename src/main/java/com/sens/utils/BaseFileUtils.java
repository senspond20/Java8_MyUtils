package com.sens.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * static 
 */
public class BaseFileUtils {
    private BaseFileUtils() { } // 생성자 금지

    /**
     * @apiNote             : 파일 확장자를 추출한다.
     * @param fileName
     * @return
     */
    public static String getExtFromFileName(String fileName){
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length())
                       .toLowerCase();
    }
    /**
     * @apiNote             : 디렉토리 경로에 폴더가 존재하는지 검사하고 폴더들을 생성한다.
     * @param dirPath
     */
    public static String isExistsCheckAndMkdirs(Path dirPath){
        File file = new File(dirPath.toUri());
        if(!file.exists()){
            file.mkdirs();
            // file.mkdir();   
        }
        return file.toPath().toString();
    }

    public static File isExistsCheckAndMakeFile(File file) throws IOException {
        if(file !=null && !file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new IOException("잘못된 파일 경로입니다 -> 파일 생성 실패");
            }
        }
        return file;
    }

    /**
     * @apiNote                     : 파일저장 1(FileWriter 를 이용한 방법)
     * @param file                  : file객체 
     * @param data
     * @param charset               : charset
     * @param isAppend              : 이어쓰기 여부
     * @return
     * @throws IOException
     */
    public static boolean fileSaveUsingFileWriter(File file, String data, Charset charset, boolean isAppend) throws IOException {
        boolean isSucess = false;    

        if(file !=null && !file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new IOException("잘못된 파일 경로입니다 -> 파일 생성 실패");
            }
        }
        try(     
                FileWriter fw = new FileWriter(file,charset, isAppend);
                BufferedWriter bw = new BufferedWriter(fw);
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

    /**
     * @apiNote                 : 파일저장 레가시 코드 : 
     * @param file              : file객체
     * @param data              : 저장한 데이터
     * @param charset           : charset
     * @param isAppend          : 파일 이어쓰기 여부
     * @return
     * @throws IOException
     */
    public static boolean fileSaveUsingLegarcy(File file, String data, Charset charset, boolean isAppend) throws IOException{
        boolean isSucess = false;
        BufferedWriter bw = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;

        if(file !=null && !file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new IOException("잘못된 파일 경로입니다 -> 파일 생성 실패");
            }
        }
        try{
            fos = new FileOutputStream(file.getPath(), isAppend);
            osw = new OutputStreamWriter(fos, charset);
            bw =  new BufferedWriter(osw);

            bw.write(data);
            isSucess = true;
        }catch(IOException e){
            throw new IOException("파일 저장에 실패하였습니다.");
        }finally{
            try {
                bw.close();
                osw.close();
                fos.close();
                isSucess = false;
            } catch (IOException e) {
                throw new IOException("파일 저장후 파일IO객체를 close하는데 실패하였습니다.");
            }
        }
        return isSucess;
    }



    public static File fileRead(){
        return null;
    }


}
