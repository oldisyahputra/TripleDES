package com.company;

import java.util.*;

public class Key {

    private int [] forStart  = new int [56];
    private ArrayList startKey = new ArrayList<Integer>();
    private int[] tableC0 = new int[]{57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36};
    private int[] tableD0 = new int[]{63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4};
    private ArrayList C0 = new ArrayList<Integer>();
    private ArrayList D0 = new ArrayList<Integer>();
    private int[] tableKey = new int[]{14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32};
    private ArrayList<Integer> key = new ArrayList<>();



    public Key(byte [] key) {

        for (int i = 0; i < 56; i++) {
            int byteNumber = i/8;
            int bitNumber = i%8;
            forStart[i] = ((key[byteNumber] >>> (7 - bitNumber)) & 0b1);
        }//перевод из байтов в int

        for (int i = 0; i < forStart.length; i += 7) {
            int sum = 0;
            for (int j = i; j < i + 7; j++) {
                sum += forStart[j];
                startKey.add(forStart[j]);
            }
            if (sum % 2 == 0) {
                startKey.add(i + 7, 1);
            } else {
                startKey.add(i + 7, 0);
            }
        }//формирование нечетности в байте


        for (int i = 0; i < tableC0.length; i++) {
            C0.add(startKey.get(tableC0[i] - 1));
            D0.add(startKey.get(tableD0[i] - 1));
        }//формирование C0 и D0
    }

    public void newRoundKey(int i) {

        /*System.out.println(C0);
        System.out.println(D0);*/

        int shift = 0;
        int firstC0 = 0;
        int secondC0 = 0;
        int firstD0 = 0;
        int secondD0 = 0;
        if ((i == 1) || (i == 2) || (i == 9) || (i == 16)) {
            shift = 1;
            firstC0 = (int) C0.get(0);
            firstD0 = (int) D0.get(0);
        }
        else {
            shift = 2;
            firstC0 = (int) C0.get(0);
            secondC0 = (int) C0.get(1);
            firstD0 = (int) D0.get(0);
            secondD0 = (int) D0.get(1);
        }
        for (int q = 0; q < C0.size() - shift; q++) {
            C0.set(q, C0.get(q + shift));
            D0.set(q, D0.get(q + shift));
        }
        if (shift == 1) {
            C0.set(C0.size() - 1, firstC0);
            D0.set(D0.size() - 1, firstD0);
        }
        else {
            C0.set(C0.size() - 2, firstC0);
            C0.set(C0.size() - 1, secondC0);
            D0.set(D0.size() - 2, firstD0);
            D0.set(D0.size() - 1, secondD0);
        }

        ArrayList C0D0 = new ArrayList(C0);
        for (int q = 0; q < D0.size(); q++) {
            C0D0.add(D0.get(q));
        }
        //System.out.println(C0D0);
        ArrayList key = new ArrayList();
        for (int q = 0; q < tableKey.length; q++) {
            key.add(C0D0.get(tableKey[q] - 1));
        }
        this.key = key;
        //System.out.println(key.size());

    }//создание ключа для следующего раунда

    public ArrayList getKey() {
        return this.key;
    }

}
