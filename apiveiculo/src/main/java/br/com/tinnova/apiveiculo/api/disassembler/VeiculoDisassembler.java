package br.com.tinnova.apiveiculo.api.disassembler;

import br.com.tinnova.apiveiculo.api.dto.VeiculoDto;
import br.com.tinnova.apiveiculo.domain.model.Veiculo;
import org.springframework.stereotype.Component;

@Component
public class VeiculoDisassembler {

    public Veiculo toDomainObject(VeiculoDto veiculoDto) {

        Veiculo veiculo = new Veiculo();
        veiculo.setVeiculo(veiculoDto.getVeiculo());
        veiculo.setMarca(veiculoDto.getMarca());
        veiculo.setAno(veiculoDto.getAno());
        veiculo.setDescricao(veiculoDto.getDescricao());
        veiculo.setVendido(veiculoDto.getVendido());

        return veiculo;
    }
}