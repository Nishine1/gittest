package com.hisoft.java;

/**
 * @program: project01
 * @description:
 * @author: nyg
 * @create: 2021-06-10 15:26:09
 **/
public class Test {
   public static int findYear(int year){
       int count = 0;
       for(int i = year;i<2022;i++){
           if(i%4==0||i%400==0){
               if(i%100!=0){
                   count++;
               }
           }
       }
       return count;
   }

    public static void main(String[] args) {
         int n =findYear(2000);
        System.out.println(n);
    }
}
