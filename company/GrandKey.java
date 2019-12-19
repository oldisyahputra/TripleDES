package com.company;

public class GrandKey {

    public Key key1;
    public Key key2;
    public Key key3;

    public GrandKey(byte [] key) {

        byte[][] tmpKey = new byte[3][7];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 7; j++) {
                tmpKey[i][j] = key[7*i + j];
            }
        }
        this.key1 = new Key(tmpKey[0]);
        this.key2 = new Key(tmpKey[1]);
        this.key3 = new Key(tmpKey[2]);

    }

    public Key getFirstKey() {
        return this.key1;
    }

    public Key getSecondKey() {
        return this.key2;
    }

    public Key getThirdKey() {
        return this.key3;
    }

}
