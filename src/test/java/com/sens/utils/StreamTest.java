package com.sens.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class StreamTest {
 
    @Test
    void test_lotto0(){
        // int[] lotto = new Random().ints(1, 46).distinct().limit(6)
        // .toArray();

//         List<Integer> ilist = new Random().ints(1, 46).distinct().limit(6).sorted()
//         .mapToObj(i-> Integer.valueOf(i))
//         .collect(Collectors.toList());

// System.out.println(ilist);
    }
    @Test
    void test_lotto1(){
        // 1~45까지 중복없는 6자리 난수 생성
        IntStream intStream = new Random().ints(1, 46).distinct().limit(6);

        // 배열로 받는 방법
        // 로또 출력 
        int[] iArray = intStream.toArray();
        System.out.println("로또 출력 \n" + Arrays.toString(iArray) +"\n");
       
        // 배열로 받아서 리스트변환
        List<Integer> list = Arrays.stream(iArray).boxed().collect(Collectors.toList());
        list.sort((a,b)-> a.compareTo(b)); // 정렬

        // 다시 배열로 변경
        int[] iArraySorted = list.stream().mapToInt(i->i).toArray();
        System.out.println("로또 정렬 \n" + Arrays.toString(iArraySorted) +"\n"); // 원본유지
       

        // 오름차순 정렬 출력
        // int[] iArraySorted = intStream.sorted().toArray(); // Stream은 한번 사용하고 나면 닫혀버린다.

        // int[] iArraySorted = Arrays.sort(iArray);
        // int[] iArraySorted 
        // System.out.println(Arrays.toString(iArraySorted));

        // Integer [] intArr = new Integer [] {1,3,5,2,4}; 
        
        // Arrays.sort(intArr, Comparator.naturalOrder());	


        // System.out.println(Arrays.toString(intArr));


    }
    @Test
    void test_old(){

        int[] iArry = new int[6];
        for (int i = 0; i < iArry.length; i++) {
            iArry[i] = (int) (Math.random() * 45 + 1);
            for (int j = 0; j < i; j++) {
                if (iArry[i] == iArry[j]) {
                    i--;
                }
            }
        }

        System.out.println("로또 출력 \n" + Arrays.toString(iArry) +"\n");
        int[] backup = Arrays.copyOf(iArry, 6);
        final int size = iArry.length;     
         int temp = 0; int j = 0; 
      /*  for(int i = 1; i < size; i++){  
            for(int j = 0; j < i; j++){
                if( iArry[i] < iArry[j]){
                    temp = iArry[i];
                    iArry[i] = iArry[j];
                    iArry[j] = temp;
                }
            }                
        }*/

   
    /*    for (int i = 0; i < size - 1; i++){
            for (j = size - 1; j > i; j--){
                if (iArry[j - 1] > iArry[j]){
                    temp = iArry[j - 1];
                    iArry[j - 1] = iArry[j];
                    iArry[j] = temp;
                }
            }
       }*/

     
       int select = 0;
       for (int i = 0; i < size - 1; i++) {
        select = 0;
        for (j = 1; j < size - i; j++) {
            if (iArry[select] < iArry[j]) {
              select = j;  
           }
        }
        temp = iArry[select];
        iArry[select] = iArry[size-i-1];
        iArry[size-i-1] = temp;

     }
  
     /*
        for (int i = 1; i < size; i++) {
            temp = iArry[i];
            j = i-1;
            while ( j >= 0 && temp < iArry[j]) {
            iArry[j+1] = iArry[j];
                j--;
            }
            iArry[j+1] = temp;
        }*/
        

        System.out.println("로또 정렬 \n" + Arrays.toString(iArry) +"\n");

        System.out.println("backup \n" + Arrays.toString(backup) +"\n");
    }
    
    @Test
    void test_lottoL(){
        // 1~45까지 중복없는 6자리 난수 생성
        Stream<Integer> streamList = new Random().ints(1, 46).distinct().limit(6).mapToObj(i -> Integer.valueOf(i));

        // 로또 출력
        List<Integer> iList = streamList.collect(Collectors.toList());
        System.out.println("로또 출력 \n" + iList +"\n");

        // 오름차순 정렬 출력
        // Stream은 한번 사용하고 나면 닫혀버린다.
        // List<Integer> iListSorted = streamList.sorted().collect(Collectors.toList());  
        
        List<Integer> iListSorted = iList.stream().sorted().collect(Collectors.toList()); 
        System.out.println("오름차순 출력 - 방법1\n" + iListSorted +"\n"); //(원본유지)
        
        iList.sort((a,b)-> a.compareTo(b));
        // Collections.sort(iList, (a,b)-> a.compareTo(b));
        System.out.println("오름차순 출력 - 방법2\n" + iList +"\n"); // (원본변경)
    }



    @Test
    void test_lotto2(){
        IntStream intStream = new Random().ints(1, 46);  // 1~45사이의 정수
        Stream<String> lottoStream = intStream.distinct().limit(6).sorted()
                                        .mapToObj(i->i+",");    // 정수를 문자열로 변환
        lottoStream.forEach(System.out::print); // 12,14,23,29,45,
    }

    

    @Test
    void test_lotto3(){
        // 1~45사이의 랜덤한 정수들을 중복없이(distinct) 6개(limit6) 뽑아서 오름차순(sorted)으로 배열(toArray) 
        // Stream<Integer> lottoStream = new Random().ints(1, 46).distinct().limit(6).sorted().mapToObj(i-> Integer.valueOf(i));

        // List<List<Integer>> list = new ArrayList<>();
        
       

        int[] lotto = new Random().ints(1, 46).distinct().limit(6).sorted().toArray();

        List<Integer> list = Arrays.stream(lotto).boxed().collect(Collectors.toList());
           



        List<Integer> intList = new ArrayList<Integer>(lotto.length);
        for (int i : lotto)
        {
            intList.add(i);
        }

        System.out.println(intList);
     
        System.out.println(Arrays.toString(lotto));
     
        System.out.println(list);
        // 출력
        // System.out.println();

        Stream<Integer> lottoStream = new Random().ints(1, 46).distinct().limit(6).sorted().mapToObj(i-> Integer.valueOf(i));
        
        
        List<Integer> ilist = lottoStream.collect(Collectors.toList());

        System.out.println(ilist);


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
