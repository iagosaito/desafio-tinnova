package br.com.tinnova.apiveiculo.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VeiculoNotFoundException extends RuntimeException {

    public VeiculoNotFoundException(Long idVeiculo) {
        super(String.format("Não existe cadastro de Veículo de ID %s", idVeiculo));
    }

}
