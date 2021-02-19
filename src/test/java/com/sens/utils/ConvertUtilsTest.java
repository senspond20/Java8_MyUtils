package com.sens.utils;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// ConvertUtils Test
public class ConvertUtilsTest {

    @BeforeEach
    void setup(){
    }

    @Test
    void test_2차원배열List변환1(){
        Object[][] array = {{"123","12","242"},{12,"ㅇㄹ","ㄹ3"},{"12","ㄹ3"},{34.1232,"가나다","함",12}};
  
        // List<List<Object>> list = new ConvertUtils().asLists(array);
        List<List<Object>> list = Arrays.stream(array).map(Arrays::asList).collect(Collectors.toList());

        System.out.println(list);
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        System.out.println(list.get(3));
    }

    
    <T> List<List<T>> asLists(T array[][]) { 
        Objects.requireNonNull(array); 
        return new AbstractList<List<T>>() { 
            @Override public List<T> get(int index) { 
                T[] a = array[index]; if (a == null) { 
                    return Collections.emptyList(); 
                } return Arrays.asList(a); 
            } @Override public int size() { 
                return array.length; 
            } 
        }; 
    }
    
    @Test
    void test_2차원배열List변환2(){
        Object[][] array = {{"123","12","242"},{12,"ㅇㄹ","ㄹ3"},{"12","ㄹ3"},{34.1232,"가나다","함",12}};

        // List<List<Object>> list = new ConvertUtils().toNestedList(array);

        List<List<Object>> list = asLists(array);
        System.out.println(list);
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        System.out.println(list.get(3));
    }

    @Test
    void test_2차원배열List변환3(){

        List<List<Object>> list = new ArrayList<List<Object>>();
        Object[][] array = {{"123","12","242"},{12,"ㅇㄹ","ㄹ3"},{"12","ㄹ3"},{34.1232,"가나다","함",12}};
        for(int i=0; i < array.length; i++){
            list.add(Arrays.asList(array[i]));
        }
        System.out.println(list);
        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
    }

    @Test
    void test__(){

        List<String> list = Arrays.asList("1110", "1010", "1011", "1110");
        int[][] array = new int[list.size()][list.get(0).length()];
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).length(); j++) {
                array[i][j] = list.get(i).charAt(j)-'0';
                }
        }
        // 출력
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.println(array[i][j]);
                }
        }  
    }

    @Test
    void test_List2차원배열변환1(){
        List<List<Object>> list = new ArrayList<List<Object>>();
        list.add(Arrays.asList("가나다", "안녕", "가을", 1110, 123, 533));
        list.add(Arrays.asList("Java", "C#", 1011, "Spring","안녕이"));
        list.add(Arrays.asList("문자열", 1010, "서울시", "11AC"));
        list.add(Arrays.asList(1110, "가을이", "하늘이"));

        // 행은 고정이고 열은 가변인 동적배열
        Object[][] array = new Object[list.size()][]; 
        
        // 배열에 집어넣기
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i).toArray();
        }

        // 출력
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.printf("%s\t",array[i][j]);
                }
                System.out.println();
        }  
    }

    @Test
    void test_List2차원배열변환2(){

        List<List<Object>> list = new ArrayList<List<Object>>();
        
        list.add(Arrays.asList("가나다", "안녕", "가을", 1110, 123, 533));
        list.add(Arrays.asList("Java", "C#", 1011, "Spring","안녕이"));
        list.add(Arrays.asList("문자열", 1010, "서울시", "11AC"));
        list.add(Arrays.asList(1.97, "가을이", "하늘이"));

        Object[][] array  = list.stream()
                                .map(item -> item.stream().toArray())
                                .toArray(Object[][]::new);

        // 출력
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.printf("%s\t",array[i][j]);
                }
                System.out.println();
        }  
       
    }


}
