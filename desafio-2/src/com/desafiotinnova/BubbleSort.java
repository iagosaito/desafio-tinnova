package com.desafiotinnova;

import java.util.Arrays;

public class BubbleSort {

    public static void main(String[] args) {

        int[] vetor = new int[8];
        vetor[0] = 5;
        vetor[1] = 3;
        vetor[2] = 2;
        vetor[3] = 4;
        vetor[4] = 7;
        vetor[5] = 1;
        vetor[6] = 0;
        vetor[7] = 6;


        for (int i = 0; i < vetor.length; i++) {
            for (int cont = 1; cont < vetor.length - i; cont++) {
                int numEsquerda = vetor[cont - 1];
                int numDireita = vetor[cont];

                if (numEsquerda > numDireita) {
                    vetor[cont - 1] = numDireita;
                    vetor[cont] = numEsquerda;
                }
            }
        }

        System.out.println("Vetor Ordenado: " + Arrays.toString(vetor));
    }
}
