package com.company;

import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.AbstractMap.*;

public class DataPreparator {

    private static SimpleEntry<BufferedImage, Boolean> generateLearnMapEntry(File fileEntry){
        String filePath = fileEntry.getPath();
        BufferedImage bufferedImage = ImgReader.readImg(filePath);
        //String desc = getFileNameNumber(filePath);
        boolean isEvenAnswer = isEven(getFileNameNumber(filePath));

        return new SimpleEntry<>(bufferedImage, isEvenAnswer);
    }


    public static HashSet<SimpleEntry<BufferedImage, Boolean>> getMarkedData(final File directory) {
        HashSet<SimpleEntry<BufferedImage, Boolean>> markedData = new HashSet<>();
        for (final File fileEntry : Objects.requireNonNull(directory.listFiles())) {
            if (fileEntry.isDirectory()) {
                getMarkedData(fileEntry);
            } else {
                markedData.add(generateLearnMapEntry(fileEntry));
            }
        }
        return markedData;
    }

    public static HashSet<SimpleEntry<BufferedImage, Boolean>> getMarkedData(String dirPath) {
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
