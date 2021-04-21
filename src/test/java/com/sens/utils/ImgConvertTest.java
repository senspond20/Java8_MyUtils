package com.sens.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ImgConvertTest {

    final Path filePath = Paths.get(System.getProperty("user.dir"),"/src/resources/img/");
    private File originFile;
    private File convertFile;

    @BeforeEach
    void setup(){
        final String originFileName = "018a7661-6495-45-20210416175331.jpg";
        // final String originFileName = "55af5c76-be4b-44-20210416141436.PNG";
        originFile = new File(Paths.get(filePath.toString(), originFileName).toString());
    }

    @Test
    void test() throws Exception{
        if(!originFile.exists())  return;

        System.out.println("============ Origin ===============");
        System.out.println(originFile.getName() +  " : " + originFile.length() + " Byte");    

        Long startTime = System.currentTimeMillis();
        convertFile = compressImage(originFile, filePath.toString());
        Long endTime = System.currentTimeMillis();
        System.out.println("변환시간 : " + (endTime - startTime) + "ms");
        System.out.println("=========== Convert ================");
        System.out.println(convertFile.getName() +  " : " + convertFile.length() + " Byte");    
    }   


    public File compressImage(File imageFile, String destination) throws Exception {
        File compressedImageFile = new File(Paths.get(destination,"convert-" + imageFile.getName()).toString());
        InputStream is = new FileInputStream(imageFile);
        OutputStream os = new FileOutputStream(compressedImageFile);
    
        float quality = 0.5f;
    
        BufferedImage image = ImageIO.read(is);
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
    
        if (!writers.hasNext())
            throw new IllegalStateException("No writers found");
    
        ImageWriter writer = (ImageWriter) writers.next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);
    
        ImageWriteParam param = writer.getDefaultWriteParam();
    
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);
        writer.write(null, new IIOImage(image, null, null), param);
        is.close();
        os.close();
        ios.close();
        writer.dispose();
        return compressedImageFile;
    }
}
