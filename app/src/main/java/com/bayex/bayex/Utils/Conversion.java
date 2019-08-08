package com.bayex.bayex.Utils;

import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class Conversion {
    public static int byteMoved = 0;

    public Conversion(){

    }

    public static double conByteToDouble(byte[] byteArray){
        byte[] cuttedByteArray = cuttedByteArray8(byteArray);
        return ByteBuffer.wrap(cuttedByteArray).order(ByteOrder.LITTLE_ENDIAN).getDouble();
    }

    public static int conByteToInt(byte[] byteArray){
        int i = byteMoved;
        while( ((char)byteArray[i]) == '\0' || (char)byteArray[i] == '\u0001' || (char)byteArray[i] == '\u0009')
        {
            i++;
            //Log.d(Consts.NAMES.appNameForLogs, String.valueOf(byteCounter));

        }
        /*if (cuttedByteArray != null) {
            int value = 0;
            value += (cuttedByteArray[3] & 0x000000FF) << 24;
            value += (cuttedByteArray[2] & 0x000000FF) << 16;
            value += (cuttedByteArray[1] & 0x000000FF) << 8;
            value += (cuttedByteArray[0] & 0x000000FF);
            return value;
        }*/
Log.d(Consts.NAMES.appNameForLogs, String.valueOf(i));
        byteMoved += i;
        byte[] cuttedByteArray = cuttedByteArray4(byteArray);
        if (cuttedByteArray != null) {
            int value = 0;
            value += (cuttedByteArray[3] & 0x000000FF) << 24;
            value += (cuttedByteArray[2] & 0x000000FF) << 16;
            value += (cuttedByteArray[1] & 0x000000FF) << 8;
            value += (cuttedByteArray[0] & 0x000000FF);

            return value;
        }
        //return ByteBuffer.wrap(cuttedByteArray).order(ByteOrder.LITTLE_ENDIAN).getShort();
        return -1;
    }

    public static String conByteToString(byte[] byteArray){
        int byteCounter = 0;
        for(int i = byteMoved; i < byteArray.length; i++){
            byteCounter++;
            if( ((char)byteArray[i]) == '\0' || (char)byteArray[i] == '\u0001' || (char)byteArray[i] == '\u0009')
            {
                //Log.d(Consts.NAMES.appNameForLogs, String.valueOf(byteCounter));
                break;
            }
        }
        byte[] slicedArray = Arrays.copyOfRange(byteArray, byteMoved, byteMoved + byteCounter);
        //byte[] utf16bytes = (new String(slicedArray))

        String stringFromByte = null;
        try {
            stringFromByte = new String(slicedArray, "ISO-8859-2");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byteMoved += byteCounter;
        //Log.d(Consts.NAMES.appNameForLogs, String.valueOf(byteMoved));
        return stringFromByte.trim();
    }

    public static String conByteToString2(byte[] byteArray){
        int byteCounter = 0;
        for(int i = byteMoved; i < byteArray.length; i++){
            byteCounter++;
            if( ((char)byteArray[i]) == '\u0001')
            {
                Log.d(Consts.NAMES.appNameForLogs, String.valueOf(byteCounter));
                break;
            }
        }
        byte[] slicedArray = Arrays.copyOfRange(byteArray, byteMoved, byteMoved + byteCounter);
        //byte[] utf16bytes = (new String(slicedArray))

        String stringFromByte = null;
        try {
            stringFromByte = new String(slicedArray, "ISO-8859-2");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byteMoved += byteCounter;
        //Log.d(Consts.NAMES.appNameForLogs, String.valueOf(byteMoved));
        return stringFromByte.trim();
    }


    private static byte[] cuttedByteArray8(byte[] byteArray){
        byte[] newByteArray = new byte[8];
        for(int i = 0; i < 8; i++){
            newByteArray[i] = byteArray[byteMoved + i];
        }
        byteMoved += 8;
        return newByteArray;
    }

    private static byte[] cuttedByteArray4(byte[] byteArray){
        byte[] newByteArray = new byte[4];
        for(int i = 0; i < 4; i++){
            newByteArray[i] = byteArray[byteMoved + i];
        }
        byteMoved += 4;
        return newByteArray;
    }
    private static byte[] cuttedByteArray2(byte[] byteArray){
        byte[] newByteArray = new byte[2];
        for(int i = 0; i < 2; i++){
            newByteArray[i] = byteArray[byteMoved + i];
        }
        byteMoved += 2;
        return newByteArray;
    }
}
