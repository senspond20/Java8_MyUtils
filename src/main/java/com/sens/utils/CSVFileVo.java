package com.sens.utils;

import java.io.File;
import java.util.List;

public class CSVFileVo {
    private File file;
    private int maxRow;
    private int maxCol;
    private Object[] header;
    private List<Object> data;

    public CSVFileVo(){}

    /* Getter */
    public File getFile(){
        return this.file;
    }
    public int getMaxRow(){
        return this.maxRow;
    }
    public int getMaxCol(){
        return this.maxCol;
    }
    public Object[] getHeader(){
        return this.header;
    }
    public List<Object> getData(){
        return this.data;
    }

    /* builder 패턴 */
    private CSVFileVo(Builder builder) {
        this.file   = builder.file;
        this.maxRow = builder.maxRow;
        this.maxCol = builder.maxCol;
        this.header = builder.header;
        this.data   = builder.data;
    }
  
    public static class Builder{
        
        /* 필수없음/ all 선택 */
        private File file = null;
        private int maxRow = 0;
        private int maxCol = 0;
        private Object[] header = null;
        private List<Object> data = null;

        public Builder(){  }
        /**
         * 메소드 체이닝
         */
        public Builder file(File file) {
            this.file = file;
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

        public Builder header(Object[] header) {
            this.header = header;
            return this;
        }
        public Builder data(List<Object> data) {
            this.data = data;
            return this;
        }

        public CSVFileVo build() {
            return new CSVFileVo(this);
        }
    }

  
}
