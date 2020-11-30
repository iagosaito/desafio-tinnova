package br.com.tinnova.apiveiculo.api.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class VeiculoDto {

    @NotBlank
    @ApiModelProperty(example = "Fusca", required = true)
    private String veiculo;

    @NotBlank
    @ApiModelProperty(example = "Volkswagen", required = true, value = "marca")
    private String marca;

    @NotNull
    @ApiModelProperty(example = "1940", required = true)
    private Integer ano;

    @ApiModelProperty(example = "Fusca famoso de um clipe musica tão famoso quanto")
    private String descricao;

    @NotNull
    @ApiModelProperty(example = "true", required = true)
    private Boolean vendido;

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getVendido() {
        return vendido;
    }

    public void setVendido(Boolean vendido) {
        this.vendido = vendido;
    }
}
