package com.sens.utils;
import java.util.List;
public class HtmlTableMaker {

    private final String THEAD = "thead";
    private final String TBODY = "tbody";
    private final String TH = "th";
    private final String TR = "tr";
    private final String TD = "td";

    public HtmlTableMaker(){  }

    /**
     * createDomElement
     * @param tag
     * @param data
     * @return
     */
    public String cde(String tag, Object data){
        return "<" + tag + ">" + data + "</" + tag + ">";
    }

    public String cde_c(String tag, String className, String data){
        return "<" + tag + " class="+ className + ">" + data + "</" + tag + ">";
    }

    /* 배열로 생성하는 것들 */
    public String createHeader(Object[] header) {
        StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < header.length; i++) {
            sb.append(cde(TH, header[i]));
        }
        String head = cde(THEAD, cde(TR, sb.toString()));
        sb = null;
        return head;
    }
    public String createBody(Object[][] body) {
        String tr = "", td = "";
        for (int i = 0; i < body.length; i++) {
            for(int j=0; j < body[i].length; j++){
                td += cde(TD, body[i][j]);
            }
            tr += cde(TR, td);
            td = "";
        }
        return cde(TBODY, tr);
    }
    public String createTable(Object[] header, Object[][] body){
        return createHeader(header).concat(createBody(body));
    }

    /* list로 생성하는것들 */
    public String createHeaderFromList(List<Object> header){

        header.stream().map(i -> cde(TH, i)).toArray();
        
        return cde(THEAD, cde(TR, cde(TH, header)));
    }

    // public String createBodyFromList(List<List<Object>> body){
       
    // }


}
