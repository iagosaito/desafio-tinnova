package br.com.tinnova.apiveiculo.domain.service;

import br.com.tinnova.apiveiculo.domain.exception.VeiculoNotFoundException;
import br.com.tinnova.apiveiculo.domain.model.Veiculo;
import br.com.tinnova.apiveiculo.domain.repository.VeiculoRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    public List<Veiculo> listar() {
        return veiculoRepository.findAll();
    }

    public Veiculo buscar(Long idVeiculo) {
        return veiculoRepository.findById(idVeiculo).orElseThrow(() -> new VeiculoNotFoundException(idVeiculo));
    }

    @Transactional
    public Veiculo salvar(Veiculo veiculo) {
        return veiculoRepository.save(veiculo);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            veiculoRepository.deleteById(id);
            veiculoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new VeiculoNotFoundException(id);
        }
    }

}
