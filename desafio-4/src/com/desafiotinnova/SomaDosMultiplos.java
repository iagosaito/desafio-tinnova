package com.desafiotinnova;

import java.util.Scanner;

public class SomaDosMultiplos {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite um número inteiro: ");

        int numero = scanner.nextInt();

        int somaMultiplos = 0;

        for (int i = 3; i <= numero; i++) {
            if (isMultiploDeTres(i) || isMultiploDeCinco(i)) {
                somaMultiplos += i;
            }
        }

        System.out.println("Soma dos múltiplos de 3 ou 5: " + somaMultiplos);

    }

    private static boolean isMultiploDeCinco(int numero) {
        return numero % 5 == 0;
    }

    private static boolean isMultiploDeTres(int numero) {
        return numero % 3 == 0;
    }
}
