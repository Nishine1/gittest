package com.hisoft.java;

public class Test01 {
    public static void main(String[] args) {
//        Scanner sc = new Scanner(system.in);
        for(int x =1;x<10;x++) {
            for (int y = 1; y < 10; y++) {
                if (y <= x) {
                    System.out.print(x + "*" + y + "=" + x * y + "\t");
                }
            }
            System.out.println();
        }
    }
}
