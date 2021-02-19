package com.sens.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import org.mozilla.universalchardet.UniversalDetector;

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
     * @apiNote  file의 Charset을 알아온다. - 라이브러리 의존성 com.googlecode.juniversalchardet
     * @param file
     * @return
     * @throws IOException
     */
    public static Charset findFileEncoding(File file) throws IOException {

        /*
        FileInputStream fis = new FileInputStream(file);
        System.out.println(System.getProperty("file.encoding"));

        byte[] CODE = new byte[4];
        fis.read(CODE, 0, 4);
        if((CODE[0] & 0xFF) == 0xEF && (CODE[1] & 0xFF) == 0xBB && (CODE[2] & 0xFF) == 0xBF ){
            System.out.println("UTF-8");
        }
        fis.close();
        return true;*/
        Charset charset = null;
        byte[] buf = new byte[4096]; 
        try (
              FileInputStream fis = new FileInputStream(file); 
        ){
            UniversalDetector detector = new UniversalDetector(null); 
            int nread; 
            while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
                 detector.handleData(buf, 0, nread); 
                } 
                detector.dataEnd(); 
                String encoding = detector.getDetectedCharset(); 
                if (encoding != null) { 
                    charset = Charset.forName(encoding);
                }else { 
                    throw new RuntimeException("파일 Chaset Find에 실패하였습니다.");
                } 
                detector.reset();
                fis.close();
        }catch(IOException e){
            throw new IOException(e.getMessage());
        }
        return charset;
    }
    /**
     * @apiNote  파일을 읽어서 utf8 로 변환해서 
     * @param file        읽어올 file 객체
     * @param isOverWrite 원본파일에 덮어쓰며 저장할 것인지 새로운 파일생성으로 이름에-utf8을 붙여서 저장할것인지 여부
     * @return
     * @throws IOException
     */
    public static boolean fileConvertUTF8(File file, boolean isOverWrite) throws IOException {

        if (!file.exists()) {
            throw new FileNotFoundException("해당 파일이 존재하지 않습니다.");
        }
        String origin = file.getName();
        String dotExt = origin.substring(origin.lastIndexOf("."), origin.length());
        String newFile = origin.substring(0, origin.lastIndexOf(".")).concat("-utf8").concat(dotExt);

        try ( // try ~ catch ~ resources
  
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new FileReader(file));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFile), StandardCharsets.UTF_8));
        ){
            String line ="";
            while ((line = br.readLine()) != null) {
                bw.append(line);
                bw.append("\n");
            }
        }
        catch (IOException e) {
            throw new IOException("파일 UTF8 변환에 실패하였습니다.");
        }
        
        return true;
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
        }catch(IOException e){
            throw new IOException("파일 저장에 실패하였습니다.");
        }finally{
            try {
                bw.close();
                osw.close();
                fos.close();
                isSucess = true;
            } catch (IOException e) {
                throw new IOException("파일 저장후 파일IO객체를 close하는데 실패하였습니다.");
            }
        }
        return isSucess;
    }


    public static File fileRead(File file, Charset charset) throws IOException{
        if (!file.exists()) {
            throw new FileNotFoundException("해당 파일이 존재하지 않습니다.");
        }

        return null;
    }


}
