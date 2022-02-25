package com.company;

import java.awt.image.*;
import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.AbstractMap.*;
import javax.imageio.ImageIO;

public class Main {

    public static void main(String[] args) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String startTime = dtf.format(now);

        BufferedImage[] bis = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            bis[i] = ImgReader.readImg("resources/" + (i+1) + ".png");
        }


        Map<Integer, SimpleEntry<BufferedImage, Boolean>> testImgMap = new HashMap<>();
        testImgMap.put(0, new SimpleEntry<>(bis[0], false));
        testImgMap.put(1, new SimpleEntry<>(bis[1], true));
        testImgMap.put(2, new SimpleEntry<>(bis[2], false));
        testImgMap.put(3, new SimpleEntry<>(bis[3], true));

        Neuro neuro1 = new Neuro();

        int iterCount = 0;
        boolean errorFlag = true;
        while (errorFlag) {
            errorFlag = false;
            for (int i = 0; i < 4; i++) {
                boolean singleErrorFlag = false;
                SimpleEntry<BufferedImage, Boolean> sample = testImgMap.get(i);
                boolean resIsEven = neuro1.isEven(sample.getKey());
                boolean answerIsEven = sample.getValue();

                if (!resIsEven && answerIsEven) {      //First Hebbian Rule
                    neuro1.teach(1);
                    singleErrorFlag = true;
                } else if (resIsEven && !answerIsEven) { //Second Hebbian Rule
                    neuro1.teach(2);
                    singleErrorFlag = true;
                }

                if(singleErrorFlag) errorFlag = true; //Если была хотя бы одна ошибка
            }
            iterCount++;
        }

        neuro1.printWeights();
        System.out.println(iterCount);

        String endTime = dtf.format(now);

        System.out.println(startTime);
        System.out.println(endTime);
    }

    private static void testImgReader(int[] imgArr){
        for (int i = 0; i < 64; i++) {
            if (i % 8 == 0){ System.out.print("\n");}
            System.out.print(imgArr[i]);
        }
    }

}
