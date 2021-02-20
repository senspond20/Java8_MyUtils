package com.sens.utils;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CSVFileVo {
    private File file;
    private Charset charset;
    private String rgx;
    private int maxRow;
    private int maxCol;
    private boolean isHeader;
    private List<List<Object>> data;

    public CSVFileVo(){}

    /* Getter */
    public File getFile(){
        return this.file;
    }
    public Charset getCharset(){
        return this.charset;
    }
    public String getRgx(){
        return this.rgx;
    }
    public int getMaxRow(){
        return this.maxRow;
    }
    public int getMaxCol(){
        return this.maxCol;
    }
    public boolean getHeader(){
        return this.isHeader;
    }
    public List<List<Object>> getData(){
        return this.data;
    }

    /* setter */
    public void setRgx(String rgx){
        this.rgx = rgx;
    }
    public void setCharset(Charset charset){
        this.charset = charset;
    }
    /* update */
    public void updateCSV(int maxRow, int maxCol, List<List<Object>> data){
        this.maxRow = maxRow;
        this.maxCol = maxCol;
        this.data = data;
    }

    /* builder 패턴 */
    private CSVFileVo(Builder builder) {
        this.file   = builder.file;
        this.charset = builder.charset;
        this.rgx = builder.rgx;
        this.maxRow = builder.maxRow;
        this.maxCol = builder.maxCol;
        this.isHeader = builder.isHeader;
        this.data   = builder.data;
    }
  
    public static class Builder{
        
        /* 필수없음/ all 선택 */
        private File file = null;
        private Charset charset = StandardCharsets.UTF_8;
        private String rgx = ",";
        private int maxRow = 0;
        private int maxCol = 0;
        private boolean isHeader = false;
        private List<List<Object>> data = null;

        public Builder(){  }
        /**
         * 메소드 체이닝
         */
        public Builder file(File file) {
            this.file = file;
            return this;    
        }
        public Builder rgx(String rgx){
            this.rgx = rgx;
            return this;
        }
        public Builder charset(Charset charset){
            this.charset = charset;
            return this;
        }
        public Builder maxRow(int maxRow) {
            this.maxRow = maxRow;
            return this;
        }
        public Builder maxCol(int maxCol) {
            this.maxCol = maxCol;
            return this;
        }

        public Builder header(boolean isHeader) {
            this.isHeader = isHeader;
            return this;
        }
        public Builder data(List<List<Object>> data) {
            this.data = data;
            return this;
        }

        public CSVFileVo build() {
            return new CSVFileVo(this);
        }
    }

  
}
