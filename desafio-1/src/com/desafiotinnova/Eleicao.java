package com.desafiotinnova;

public class Eleicao {

    private final double totalEleitores;
    private final double votosValidos;
    private final double votosBrancos;
    private final double votosNulos;

    public Eleicao(double votosValidos, double votosBrancos, double votosNulos) {
        this.votosValidos = votosValidos;
        this.votosBrancos = votosBrancos;
        this.votosNulos = votosNulos;

        this.totalEleitores = votosBrancos + votosNulos + votosValidos;
    }

    public double getPercentualVotosValidos() {
        return (votosValidos / totalEleitores) * 100;
    }

    public double getPercentualVotosBrancos() {
        return (votosBrancos / totalEleitores) * 100;
    }

    public double getPercentualVotosNulos() {
        return (votosNulos / totalEleitores) * 100;
    }
}