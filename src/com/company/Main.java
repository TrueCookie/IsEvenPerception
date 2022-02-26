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

        HashSet<ArrayList<Object>> markedData = DataPreparator.getMarkedData("resources/learn");

        Neuro neuro1 = new Neuro();

        int teachIterCount = traceSamples(neuro1, markedData, Mode.TEACH);

        neuro1.printWeights();
        System.out.println(teachIterCount);

        String endTime = dtf.format(now);
        System.out.println(startTime);
        System.out.println(endTime);

        //TEST
        System.out.println("<<<<<<<<<<<<<<<<<<<<<______________TEST____________>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        HashSet<ArrayList<Object>> testData = DataPreparator.getMarkedData("resources/test");
        int testIterCount = traceSamples(neuro1, testData, Mode.TEST);

        System.out.println(testIterCount);
    }

    private static int traceSamples(Neuro neuro, HashSet<ArrayList<Object>> markedData, Mode mode){
        int iterCount = 0;
        boolean errorFlag = true;
        while (errorFlag) {
            errorFlag = false;
            for (ArrayList<Object> sample : markedData){
                boolean singleErrorFlag = false;

                boolean resIsEven = neuro.isEven((BufferedImage) sample.get(0));
                boolean answerIsEven = (boolean) sample.get(1);
                int desc = (int) sample.get(2);

                if (!resIsEven && answerIsEven) {      //First Hebbian Rule
                    if(mode == Mode.TEACH){ neuro.teach(1);}
                    singleErrorFlag = true;
                } else if (resIsEven && !answerIsEven) { //Second Hebbian Rule
                    if(mode == Mode.TEACH){ neuro.teach(2);}
                    singleErrorFlag = true;
                }else {
                    System.out.println(desc + " - " + getResString(resIsEven) + " - SUCCESS");
                }

                if(singleErrorFlag){    //Если была хотя бы одна ошибка
                    if(mode == Mode.TEACH){ errorFlag = true;}
                    System.out.println(desc + " - " + getResString(resIsEven) + " - FAIL");
                }
            }
            System.out.println("-------------------------------------->");
            iterCount++;
        }

        return iterCount;
    }

    private static String getResString(boolean resIsEven){
        if(resIsEven){
            return "Even";
        }else{
            return "Odd ";
        }
    }
}
