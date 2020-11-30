package br.com.tinnova.apiveiculo.domain.service;

import br.com.tinnova.apiveiculo.domain.model.Veiculo;

import java.util.List;

public interface VeiculoFiltroQueryService {

    List<Veiculo> filtrar(String filtro);
}
