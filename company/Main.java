package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        byte [] startKey = new byte[]{1, 2, 3, 7, 5, 6, 7, 8, 4, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 26, 21};
        GrandKey grandKey = new GrandKey(startKey);
        TripleDES des = new TripleDES(grandKey);

        try {
            des.encrypt("text.txt", "code");
            des.decrypt("code", "decode.txt");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
