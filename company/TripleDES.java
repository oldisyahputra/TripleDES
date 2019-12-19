package com.company;


import java.io.*;
import java.util.*;

public class TripleDES {

    private int [] tableIP = new int[]{58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7};

    private int [] tableNotIP = new int[]{40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62, 30, 37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59, 27, 34, 2, 42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17, 57, 25};


    private int [] tableE = new int[]{32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1};

    private int [] tableP = new int[]{16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9, 19, 13, 30, 6, 22, 11, 4, 25};

    private int [][][] tableS = new int[][][]{{{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
            {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
            {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
            {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}},

            {{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                    {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                    {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                    {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}},

            {{10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                    {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                    {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                    {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}},

            {{7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                    {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                    {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                    {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}},

            {{2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                    {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                    {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                    {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}},

            {{12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                    {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 3, 14, 0, 11, 3, 8},
                    {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                    {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}},

            {{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                    {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                    {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                    {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}},

            {{13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                    {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                    {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                    {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}}};

    ArrayList<ArrayList<Integer>> pastKeys1 = new ArrayList<>();
    ArrayList<ArrayList<Integer>> pastKeys2 = new ArrayList<>();
    ArrayList<ArrayList<Integer>> pastKeys3 = new ArrayList<>();
    GrandKey grandKey;


    public TripleDES(GrandKey grandKey) {

        this.grandKey = grandKey;

    }

    public ArrayList<Integer> encode(ArrayList<Integer> dataToEncode, Key key, ArrayList<ArrayList<Integer>> pastKeys) {

        ArrayList IP = new ArrayList();
        for (int i = 0; i < tableIP.length; i++) {
            IP.add(dataToEncode.get(tableIP[i] - 1));
        }//начальная перестановка
        dataToEncode = IP;

        ArrayList<Integer> L = new ArrayList<>();
        ArrayList<Integer> R = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            L.add(dataToEncode.get(i));
            R.add(dataToEncode.get(i + 32));
        }

        for (int h = 0; h < 16; h++) {
            key.newRoundKey(h + 1);//ключ для очередного раунда
            pastKeys.add(key.getKey());

            ArrayList<Integer> E = new ArrayList();
            for (int i = 0; i < tableE.length; i++) {
                E.add(R.get(tableE[i] - 1));
            }//расширение E для R


            ArrayList<Integer> nowKey = key.getKey();
            for (int i = 0; i < 48; i++) {
                E.set(i, (E.get(i) + nowKey.get(i)) % 2);
            }//сложение R с ключем

            ArrayList<Integer> B = new ArrayList<>();
            for (int i = 0; i < 48; i += 6) {
                int line = 0;
                if (E.get(i) == 1) {
                    line++;
                    line <<= 1;
                }
                if (E.get(i + 5) == 1) {
                    line++;
                }
                int column = 0;
                column = (column | E.get(i + 1)) << 1;
                column = (column | E.get(i + 2)) << 1;
                column = (column | E.get(i + 3)) << 1;
                column = (column | E.get(i + 4));
                int replacement = tableS[i / 6][line][column];

                B.add((replacement & 0b1000) >>> 3);
                B.add((replacement & 0b0100) >>> 2);
                B.add((replacement & 0b0010) >>> 1);
                B.add((replacement & 0b0001));
            }//получение B

            ArrayList<Integer> P = new ArrayList();
            for (int i = 0; i < tableP.length; i++) {
                P.add(B.get(tableP[i] - 1));
            }//перестановка P


            ArrayList tmpR = new ArrayList();
            for (int i = 0; i < 32; i++) {
                tmpR.add(i, (P.get(i) + L.get(i)) % 2);
            }//сложение f(R) и L
            L = R;
            R = tmpR;
        }
        ArrayList<Integer> tmp = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            tmp.add(L.get(i));
        }//соединение L и R
        for (int i = 0; i < 32; i++) {
            tmp.add(R.get(i));
        }//

        ArrayList<Integer> notIP = new ArrayList<>();
        for (int i = 0; i < tableNotIP.length; i++) {
            notIP.add(i, tmp.get(tableNotIP[i] - 1));
        }//перестановка notIP

        return notIP;

    }

    public ArrayList<Integer> decode(ArrayList<Integer> input, ArrayList<ArrayList<Integer>> pastKeys) {

        ArrayList<Integer> IP = new ArrayList<>();
        for (int i = 0; i < tableIP.length; i++) {
            IP.add(i, input.get(tableIP[i] - 1));
        }//начальная перестановка IP
        input = IP;

        ArrayList<Integer> L = new ArrayList<>();
        ArrayList<Integer> R = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            L.add(input.get(i));
            R.add(input.get(i + 32));
        }


        for (int h = 0; h < 16; h++) {
            //key.newRoundKey(h + 1);//ключ для очередного раунда

            ArrayList<Integer> E = new ArrayList();
            for (int i = 0; i < tableE.length; i++) {
                E.add(L.get(tableE[i] - 1));
            }//расширение E для L

            ArrayList<Integer> nowKey = pastKeys.get(15 - h);
            for (int i = 0; i < 48; i++) {
                E.set(i, (E.get(i) + nowKey.get(i)) % 2);
            }//сложение L с ключем

            ArrayList<Integer> B = new ArrayList<>();
            for (int i = 0; i < 48; i += 6) {
                int line = 0;
                if (E.get(i) == 1) {
                    line++;
                    line <<= 1;
                }
                if (E.get(i + 5) == 1) {
                    line++;
                }
                int column = 0;
                column = (column | E.get(i + 1)) << 1;
                column = (column | E.get(i + 2)) << 1;
                column = (column | E.get(i + 3)) << 1;
                column = (column | E.get(i + 4));

                //System.out.println(line + " " + column);
                int replacement = tableS[i / 6][line][column];
            /*System.out.println(i/6);
            System.out.println(replacement);*/
                B.add((replacement & 0b1000) >>> 3);
                B.add((replacement & 0b0100) >>> 2);
                B.add((replacement & 0b0010) >>> 1);
                B.add((replacement & 0b0001));
            }//получение B

            ArrayList<Integer> P = new ArrayList();
            for (int i = 0; i < tableP.length; i++) {
                P.add(B.get(tableP[i] - 1));
            }//перестановка P

            ArrayList tmpL = new ArrayList();
            for (int i = 0; i < 32; i++) {
                tmpL.add(i, (P.get(i) + R.get(i)) % 2);
            }//сложение f(L) и R
            R = L;
            L = tmpL;
        }

        ArrayList<Integer> tmp = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            tmp.add(L.get(i));
        }//соединение L и R
        for (int i = 0; i < 32; i++) {
            tmp.add(R.get(i));
        }//

        ArrayList<Integer> notIP = new ArrayList<>();
        for (int i = 0; i < tableNotIP.length; i++) {
            notIP.add(i, tmp.get(tableNotIP[i] - 1));
        }//перестановка notIP

        return notIP;

    }

    public ArrayList<byte []> encrypt(String inputFile, String outputFile) throws Exception {

        ArrayList<byte []> input;
        ArrayList<byte []> output = new ArrayList<>();
        input = read(inputFile);

        for (int u = 0; u < input.size(); u++) {
            ArrayList<Integer> data = new ArrayList<>();
            for (int i = 0; i < 64; i++) {
                int byteNumber = i / 8;
                int bitNumber = i % 8;
                data.add((input.get(u)[byteNumber] >>> (7 - bitNumber)) & 0b1);
            }//перевод в int

            ArrayList<Integer> tmp;
            Key key1 = grandKey.getFirstKey();
            Key key2 = grandKey.getSecondKey();
            Key key3 = grandKey.getThirdKey();
            tmp = encode(data, key1, this.pastKeys1);
            tmp = encode(tmp, key2, this.pastKeys2);
            tmp = encode(tmp, key3, this.pastKeys3);

            byte[] out = new byte[8];
            for (int i = 0; i < 64; i++) {
                out[i / 8] = (byte) ((out[i / 8] + tmp.get(i)));
                if (i % 8 != 7) {
                    out[i / 8] = (byte) (out[i / 8] << 1);
                }
            }//перевод в byte
            output.add(out);
        }
        write(outputFile, output, false);
        correlation(input, output);
        return output;

    }

    public ArrayList<byte []> decrypt(String inputFile, String outputFile) throws Exception {

        ArrayList<byte []> input;
        ArrayList<byte []> output = new ArrayList<>();
        input = read(inputFile);

        for (int u = 0; u < input.size(); u++) {
            ArrayList<Integer> data = new ArrayList<>();
            for (int i = 0; i < 64; i++) {
                int byteNumber = i / 8;
                int bitNumber = i % 8;
                data.add((input.get(u)[byteNumber] >>> (7 - bitNumber)) & 0b1);
            }//перевод в int

            ArrayList<Integer> tmp;
            tmp = decode(data, this.pastKeys3);
            tmp = decode(tmp, this.pastKeys2);
            tmp = decode(tmp, this.pastKeys1);

            byte[] out = new byte[8];
            for (int i = 0; i < 64; i++) {
                out[i / 8] = (byte) ((out[i / 8] + tmp.get(i)));
                if (i % 8 != 7) {
                    out[i / 8] = (byte) (out[i / 8] << 1);
                }
            }
            output.add(out);
        }
        write(outputFile, output, true);
        return output;

    }

    public ArrayList<byte[]> read(String filename) throws Exception {

        ArrayList<byte[]> fileBytes = new ArrayList<>();
        FileInputStream fin = new FileInputStream(filename);
        byte [] buffer = new byte[fin.available()];
        fin.read(buffer, 0, fin.available());
        fin.close();

        int fullBlocks = buffer.length / 8;

        byte[] tmp = new byte[8];
        for (int i = 0; i < fullBlocks; i++) {
            tmp = new byte[8];
            for (int j = 0; j < 8; j++)
                tmp[j] = buffer[i * 8 + j];
            fileBytes.add(i, tmp);
        }

        if ((buffer.length % 8) != 0) {
            tmp = new byte[8];
            for (int i = 8 * fullBlocks; i < buffer.length; i++)
                tmp[i % 8] = buffer[i];

            for (int i = buffer.length % 8; i < tmp.length; i++) {
                if (i == buffer.length % 8)
                    tmp[i] = 1;
                else
                tmp[i] = 0;
            }
            fileBytes.add(tmp);
        }

        return fileBytes;
    }

    public void write(String filename, ArrayList<byte[]> data, boolean isDecrypt) {

        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            if (isDecrypt) {
                System.out.println("decrypt");
                int addedBits = 0;
                for (int q = 7; q > 0; q--) {
                    if (data.get(data.size() - 1)[q] == 1) {
                        addedBits++;
                        break;
                    }
                    addedBits++;
                }
                byte[] image = new byte[8 * data.size() - addedBits];
                int counter = 0;
                for (int i = 0; i < data.size(); i++) {
                    for (int j = 0; j < 8; j++) {

                        if (counter >= 8 * data.size() - addedBits)
                            break;

                        image[counter] = (byte) (data.get(i)[j] & 0xFF);
                        counter++;
                    }
                }

                FileOutputStream stream = new FileOutputStream(file);
                stream.write(image);
                stream.close();
            }
            else {
                System.out.println("encrypt");
                byte[] image = new byte[8 * data.size()];

                int counter = 0;
                for (int i = 0; i < data.size(); i++) {
                    for (int j = 0; j < 8; j++) {

                        if (counter >= 8 * data.size())
                            break;

                        image[counter] = (byte) (data.get(i)[j] & 0xFF);
                        counter++;
                    }
                }

                FileOutputStream stream = new FileOutputStream(file);
                stream.write(image);
                stream.close();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void correlation(ArrayList<byte[]> input, ArrayList<byte[]> output) {
        ArrayList<Integer> inputData = new ArrayList<>();
        ArrayList<Integer> outputData = new ArrayList<>();
        for (int j = 0; j < input.size(); j++) {
            for (int i = 0; i < 64; i++) {
                int byteNumber = i / 8;
                int bitNumber = i % 8;
                inputData.add((input.get(j)[byteNumber] >>> (7 - bitNumber)) & 0b1);
            }
        }

        for (int j = 0; j < output.size(); j++) {
            for (int i = 0; i < 64; i++) {
                int byteNumber = i / 8;
                int bitNumber = i % 8;
                outputData.add((output.get(j)[byteNumber] >>> (7 - bitNumber)) & 0b1);
            }
        }

        double same = 0;
        double different = 0;
        double sumOfOutput = 0;
        for (int i = 0; i < inputData.size(); i++) {
            sumOfOutput += outputData.get(i);
            if (inputData.get(i) == outputData.get(i))
                same++;
            else
                different++;
        }


        double correlation = (same - different)/inputData.size();
        double rasp = sumOfOutput/inputData.size();
        System.out.println(correlation);
        System.out.println(rasp);

    }

}
