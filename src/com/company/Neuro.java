package com.company;

import java.awt.image.*;
import java.util.*;

public class Neuro {
    double[] weights = new double[64];
    double corrDelta = 0.1;
    double tetta = 30;

    int[] lastXValues = new int[64];

    public Neuro(){
        for (int i = 0; i < 64; i++) {
            weights[i] = 0;
        }
    }

    public void increaseW(int wIndex){
        weights[wIndex] = weights[wIndex] + corrDelta;
    }

    public void decreaseW(int wIndex){
        weights[wIndex] = weights[wIndex] - corrDelta;
    }

    public boolean isEven(BufferedImage bi){
        int[] imgArr = ImgReader.initImgArr(bi);
        lastXValues = imgArr;

        double sum = 0;
        for (int i = 0; i < 64; i++) {
            sum += imgArr[i]*weights[i];
        }

        return sum > tetta;
    }

    public void teach(int hebbianRule) {
        for (int i = 0; i < 64; i++) {
            if(lastXValues[i] == 1){
                if(hebbianRule == 1){
                    weights[i] += 1;
                }
                else if(hebbianRule == 2){
                    weights[i] -= 1;
                }
            }
        }
    }

    public void printWeights(){
        for (int i = 0; i < 64; i++) {
            if (i % 8 == 0){ System.out.print("\n");}
            System.out.printf("%2.0f ", weights[i]);
        }
    }
}
