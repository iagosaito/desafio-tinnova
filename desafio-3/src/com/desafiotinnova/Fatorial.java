package com.desafiotinnova;

import java.util.Scanner;

public class Fatorial {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite um número: ");

        int numero = scanner.nextInt();

        int fatorial = calcularFatorial(numero);

        System.out.printf("Fatorial do número %d é: %d%n", numero, fatorial);

    }

    public static int calcularFatorial(int n) {

        if (n == 1 || n == 0) {
            return 1;
        }

        return n * calcularFatorial(n - 1);
    }
}
