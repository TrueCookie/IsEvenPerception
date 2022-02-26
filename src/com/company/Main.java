package com.company;

import java.awt.image.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.AbstractMap.*;

public class Main {

    private enum Mode{
        TEACH,
        TEST
    }

    public static void main(String[] args) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String startTime = dtf.format(now);

        HashSet<SimpleEntry<BufferedImage, Boolean>> markedData = DataPreparator.getMarkedData("resources/learn");

        Neuro neuro1 = new Neuro();

        int teachIterCount = traceSamples(neuro1, markedData, Mode.TEACH);

        neuro1.printWeights();
        System.out.println(teachIterCount);

        String endTime = dtf.format(now);
        System.out.println(startTime);
        System.out.println(endTime);

        //TEST
        System.out.println("<<<<<<<<<<<<<<<<<<<<<______________TEST____________>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        HashSet<SimpleEntry<BufferedImage, Boolean>> testData = DataPreparator.getMarkedData("resources/test");
        int testIterCount = traceSamples(neuro1, markedData, Mode.TEST);

        System.out.println(testIterCount);
    }

    private static int traceSamples(Neuro neuro, HashSet<SimpleEntry<BufferedImage, Boolean>> markedData, Mode mode){
        int iterCount = 0;
        boolean errorFlag = true;
        while (errorFlag) {
            errorFlag = false;
            for (SimpleEntry<BufferedImage, Boolean> sample : markedData){
                boolean singleErrorFlag = false;
                boolean resIsEven = neuro.isEven(sample.getKey());
                boolean answerIsEven = sample.getValue();

                if (!resIsEven && answerIsEven) {      //First Hebbian Rule
                    if(mode == Mode.TEACH){ neuro.teach(1);}
                    singleErrorFlag = true;
                    System.out.println("FAIL");
                } else if (resIsEven && !answerIsEven) { //Second Hebbian Rule
                    if(mode == Mode.TEACH){ neuro.teach(2);}
                    singleErrorFlag = true;
                    System.out.println("FAIL");
                }else {
                    System.out.println("SUCCESS");
                }

                if(singleErrorFlag) errorFlag = true; //Если была хотя бы одна ошибка
            }
            System.out.println("-------------------------------------->");
            iterCount++;
        }

        return iterCount;
    }
}
