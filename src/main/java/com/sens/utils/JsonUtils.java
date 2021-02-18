package com.sens.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtils {
    
    /**
     * Map을 json으로 변환한다.
     *
     * @param map Map<String, Object>.
     * @return JSONObject.
     */
    public static JSONObject getJsonObjectFromMap( Map<String, Object> map )
    {
        JSONObject jsonObject = new JSONObject();
        for( Map.Entry<String, Object> entry : map.entrySet() ) {
            jsonObject.put(entry.getKey(), entry.getValue());
        }
        return jsonObject;
    }


    public static Map<String,Object> getMapFromJsonObject(JSONObject jsonObject )
    {
        Map<String,Object> map = new HashMap<String,Object>();
    
        jsonObject.toString();
        
        // return jsonObject;
        return null;
    }

   /**
     * JsonArray를 List<Map<String, String>>으로 변환한다.
     *
     * @param jsonArray JSONArray.
     * @return List<Map<String, Object>>.
     */
    public static List<Map<String, Object>> getListMapFromJsonArray( JSONArray jsonArray )
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        
        if( jsonArray != null )
        {
            int jsonSize = jsonArray.length();
            for( int i = 0; i < jsonSize; i++ )
            {
                Map<String, Object> map = getMapFromJsonObject( ( JSONObject ) jsonArray.get(i) );
                list.add( map );
            }
        }
        
        return list;
    }
    /**
     * 
     * @param jsonArrayStr
     * @return
     */
    public List<?> jsonArraytoListmap(String jsonArrayStr){

        List<Map<String,Object>> list = new ArrayList<>();    
        Pattern pattern = Pattern.compile("\\{[^\\{\\}]*\\}");  // { } 리스트 
        Pattern sPattern = Pattern.compile("[\"][^\\,\\{\\}]*"); // " : " 리스트
        Matcher matcher = pattern.matcher(jsonArrayStr);
        Matcher sMatcher = null;
        Map<String,Object> map = null;
        
        while(matcher.find()){
            // map = new HashMap<String, Object>();    hashMap은 순서 보장을 하지 않지만 속도가 더 빠르다.
            map = new LinkedHashMap<String, Object>(); 
            sMatcher = sPattern.matcher(matcher.group()); // { } 을 뽑고
            while (sMatcher.find()) {
                //" : " 을 뽑은 다음
                // "를 없애고 : 를 구분자로 나눈다.
                String[] mk = sMatcher.group().replaceAll("\"", "").split("\\:"); 
                map.put(mk[0], mk[1]);
            }
            list.add(map);
        }
        return list;
    }

    /*
    public static void main(String[] args)
            throws JsonParseException, JsonMappingException, MalformedURLException, IOException {
 
        ObjectMapper mapper = new ObjectMapper();
 
        // URL 에 있는 JSON String 을 Map으로 변환하기
        Map<String, Object> data = mapper.readValue(
                     new URL("https://gturnquist-quoters.cfapps.io/api/random"),
                     new TypeReference<Map<String,Object>>(){});
 
 
 
 
        // {type=success, value={id=9, quote=So easy it is to switch container in #springboot.}}
        System.out.println(data);
 
        // {id=9, quote=So easy it is to switch container in #springboot.}
        System.out.println(data.get("value"));
 
 
 
 
 
 
        // Map을 JSON String 으로 변환
        // {"type":"success","value":{"id":9,"quote":"So easy it is to switch container in #springboot."}}
        System.out.println(mapper.writeValueAsString(data));
 
 
        // Map을 보기쉬운 JSON String 으로 변환
        
           {
              "type" : "success",
              "value" : {
                "id" : 9,
                "quote" : "So easy it is to switch container in #springboot."
              }
           }
        
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data));
    }
        */



}
