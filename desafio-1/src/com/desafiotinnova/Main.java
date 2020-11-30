package com.desafiotinnova;

public class Main {

    public static void main(String[] args) {

        final double votosValidos = 800;
        final double votosBrancos = 150;
        final double votosNulos = 50;

        Eleicao eleicao = new Eleicao(votosValidos, votosBrancos, votosNulos);


        System.out.println("==== Resultado das eleições ======\n");

        System.out.println("Votos Válidos: " + eleicao.getPercentualVotosValidos() + "%");
        System.out.println("Votos Brancos: " + eleicao.getPercentualVotosBrancos() + "%");
        System.out.println("Votos Nulos: " + eleicao.getPercentualVotosNulos() + "%");
    }
}
