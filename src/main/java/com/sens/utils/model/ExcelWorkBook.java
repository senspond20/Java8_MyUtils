package com.sens.utils.model;

import org.apache.poi.ss.usermodel.Workbook;

public class ExcelWorkBook{

    private Workbook workbook;
    
    private ExcelType excelType;

    public ExcelWorkBook(){}
    public ExcelWorkBook(Workbook workbook, ExcelType excelType){
        this.workbook = workbook;
        this.excelType = excelType;
    }
    /* getter */
    public Workbook getWorkBook(){
        return this.workbook;
    }
    public ExcelType getExcelType(){
        return this.excelType;
    }
    /* setter */
    public void setWorkBook(Workbook workbook){
        this.workbook = workbook;
    }
    public void setExcelType(ExcelType excelType){
        this.excelType = excelType;
    }
}
