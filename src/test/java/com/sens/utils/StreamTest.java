package com.sens.utils;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class StreamTest {
 
    @Test
    void test_lotto0(){

    }
    @Test
    void test_lotto1(){
        // 1~45사이의 랜덤한 정수들을 중복없이(distinct) 6개(limit6) 뽑아서 오름차순(sorted)으로 배열(toArray) 
        int[] lotto = new Random().ints(1, 46).distinct().limit(6).sorted().toArray();

        // 출력
        System.out.println(Arrays.toString(lotto));
    }
    
    @Test
    void test_lotto2(){
        IntStream intStream = new Random().ints(1, 46);  // 1~45사이의 정수
        Stream<String> lottoStream = intStream.distinct().limit(6).sorted()
                                        .mapToObj(i->i+",");    // 정수를 문자열로 변환
        lottoStream.forEach(System.out::print); // 12,14,23,29,45,
    }

    @Test
    void Test_Sql(){

        String[] param = new String[] {" 1", "100", "154","155","21"};
        String paramforIn = String.join(",", param);
        StringBuilder sql = new StringBuilder();
        sql.append("Select * from raw_contacts where _id in (")
           .append(paramforIn).append(")");
        System.out.println(sql.toString());

    }

    @Test
    void test_Insert(){
        String[] head = new String[]{"title,content,writer"};
        String table ="(" + String.join(",", head) + ")";
        System.out.println(table);
    }
    
    void test_fa(){
        char[] 초성 = {'가','나','다','라','마','바'};
        
    }

}
