package br.com.tinnova.apiveiculo.infraestrutura;

import br.com.tinnova.apiveiculo.core.util.StringToMapConverterQuery;
import br.com.tinnova.apiveiculo.domain.model.Veiculo;
import br.com.tinnova.apiveiculo.domain.repository.VeiculoRepository;
import br.com.tinnova.apiveiculo.domain.service.VeiculoFiltroQueryService;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class VeiculoFiltroQueryServiceImpl implements VeiculoFiltroQueryService {

    private final VeiculoRepository veiculoRepository;

    public VeiculoFiltroQueryServiceImpl(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    @Override
    public List<Veiculo> filtrar(String q) {

        Map<String, String> filtros = StringToMapConverterQuery.converter(q);

        if (filtros.containsKey("fabricante")) {
            return veiculoRepository.findAllByMarcaIgnoreCase(filtros.get("fabricante"));
        } else if (filtros.containsKey("apenas-nao-vendidos")) {

            if (filtros.get("apenas-nao-vendidos").equalsIgnoreCase("true")) {
                return veiculoRepository.findAllVeiculosNaoVendidos();
            }
            return veiculoRepository.findAllVendidos();

        } else if (filtros.containsKey("decada")) {
            return veiculoRepository.findAllByDecada(Integer.valueOf(filtros.get("decada")));
        } else if (filtros.containsKey("ultima-semana")) {
            return veiculoRepository.findAllByUltimaSemana(LocalDateTime.now().minusDays(7), LocalDateTime.now());
        }

        return Collections.emptyList();
    }
}
