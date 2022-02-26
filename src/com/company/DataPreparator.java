package com.company;

import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.AbstractMap.*;

public class DataPreparator {

    private static ArrayList<Object> generateLearnMapEntry(File fileEntry){
        String filePath = fileEntry.getPath();
        BufferedImage bufferedImage = ImgReader.readImg(filePath);
        int desc = getFileNameNumber(filePath);
        boolean isEvenAnswer = isEven(desc);

        //return new SimpleEntry<>(bufferedImage, isEvenAnswer);
        return new ArrayList<>(){
            {
                add(bufferedImage);
                add(isEvenAnswer);
                add(desc);
            }
        };
    }


    public static HashSet<ArrayList<Object>> getMarkedData(final File directory) {
        HashSet<ArrayList<Object>> markedData = new HashSet<>();
        for (final File fileEntry : Objects.requireNonNull(directory.listFiles())) {
            if (fileEntry.isDirectory()) {
                getMarkedData(fileEntry);
            } else {
                markedData.add(generateLearnMapEntry(fileEntry));
            }
        }
        return markedData;
    }

    public static HashSet<ArrayList<Object>> getMarkedData(String dirPath) {
        File dirFile = new File(dirPath);
        return getMarkedData(dirFile);
    }

    private static int getFileNameNumber(String fileName){
        int len = fileName.length();
        int nameIndex = fileName.lastIndexOf('\\');
        String nameNumber = fileName.substring(nameIndex+1 ,len-4);

        int underscoreIndex = nameNumber.indexOf('_');
        if(underscoreIndex != -1){
            nameNumber = nameNumber.substring(0, underscoreIndex-1);
        }

        return Integer.parseInt(nameNumber);
    }

    private static boolean isEven(int number){
        return number % 2 == 0;
    }
}
