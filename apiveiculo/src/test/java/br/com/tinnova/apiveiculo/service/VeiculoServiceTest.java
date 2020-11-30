package br.com.tinnova.apiveiculo.service;

import br.com.tinnova.apiveiculo.domain.exception.VeiculoNotFoundException;
import br.com.tinnova.apiveiculo.domain.model.Veiculo;
import br.com.tinnova.apiveiculo.domain.repository.VeiculoRepository;
import br.com.tinnova.apiveiculo.domain.service.VeiculoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class VeiculoServiceTest {

    private VeiculoService veiculoService;

    @MockBean
    private VeiculoRepository veiculoRepository;

    @BeforeEach
    void setUp() {
        veiculoService = new VeiculoService(veiculoRepository);
    }

    @DisplayName("Deve retornar uma lista de veículos")
    @Test
    void deveRetornarListaVeiculosComSucesso() {
        Veiculo veiculo = criarNovoVeiculo();

        when(veiculoRepository.findAll()).thenReturn(Collections.singletonList(veiculo));

        List<Veiculo> veiculosEncontrados = veiculoService.listar();

        assertThat(veiculosEncontrados).hasSize(1);
    }

    @DisplayName("Deve buscar um veículo por ID com sucesso")
    @Test
    void deveBuscarUmVeiculoPorIdComSucesso() {
        Veiculo veiculo = criarNovoVeiculo();

        when(veiculoRepository.findById(anyLong())).thenReturn(Optional.of(veiculo));

        Veiculo veiculoEncontrado = veiculoService.buscar(1L);

        assertThat(veiculoEncontrado).isNotNull();
        assertThat(veiculoEncontrado.getId()).isEqualTo(veiculo.getId());
        assertThat(veiculoEncontrado.getAno()).isEqualTo(veiculo.getAno());
        assertThat(veiculoEncontrado.getDescricao()).isEqualTo(veiculo.getDescricao());
        assertThat(veiculoEncontrado.getMarca()).isEqualTo(veiculo.getMarca());
        assertThat(veiculoEncontrado.getVeiculo()).isEqualTo(veiculo.getVeiculo());
        assertThat(veiculoEncontrado.getVendido()).isEqualTo(veiculo.getVendido());
    }

    @DisplayName("Deve lançar VeiculoNotFoundException ao buscar um veículo por ID inexistente")
    @Test
    void deveLancarVeiculoNotFoundExceptionAoBuscarUmVeiculoPorIdInexistente() {
        Veiculo veiculo = criarNovoVeiculo();

        when(veiculoRepository.findById(anyLong())).thenReturn(Optional.empty());

        final Throwable exception = catchThrowable(() -> veiculoService.buscar(1L));

        assertThat(exception)
                .isInstanceOf(VeiculoNotFoundException.class)
                .hasMessage(String.format("Não existe cadastro de Veículo de ID %s", veiculo.getId()));
    }

    @DisplayName("Deve salvar um veículo com sucesso")
    @Test
    void deveSalvarVeiculoComSucesso() {
        Veiculo veiculo = criarNovoVeiculo();

        when(veiculoRepository.save(any(Veiculo.class))).thenReturn(veiculo);

        Veiculo veiculoSalvo = veiculoService.salvar(veiculo);

        assertThat(veiculoSalvo).isNotNull();
        assertThat(veiculoSalvo.getId()).isEqualTo(veiculo.getId());
        assertThat(veiculoSalvo.getAno()).isEqualTo(veiculo.getAno());
        assertThat(veiculoSalvo.getDescricao()).isEqualTo(veiculo.getDescricao());
        assertThat(veiculoSalvo.getMarca()).isEqualTo(veiculo.getMarca());
        assertThat(veiculoSalvo.getVeiculo()).isEqualTo(veiculo.getVeiculo());
        assertThat(veiculoSalvo.getVendido()).isEqualTo(veiculo.getVendido());

        verify(veiculoRepository, times(1)).save(any(Veiculo.class));
    }

    @DisplayName("Deve excluir um Veículo com sucesso")
    @Test
    void deveExcluirVeiculoComSucesso() {
        final Long idVeiculo = 1l;

        doNothing().when(veiculoRepository).deleteById(anyLong());
        doNothing().when(veiculoRepository).flush();

        final Throwable exception = catchThrowable(() -> veiculoService.excluir(idVeiculo));

        assertThat(exception).isNull();

        verify(veiculoRepository, times(1)).deleteById(anyLong());
        verify(veiculoRepository, times(1)).flush();
    }

    @DisplayName("Deve lançar VeículoNotFoundExeption ao excluir um Veículo inexistente")
    @Test
    void deveLancarVeiculoNotFoundExceptionAoExcluirVeiculoInexistente() {
        final Long idVeiculo = 1l;

        doNothing().when(veiculoRepository).deleteById(anyLong());
        doThrow(EmptyResultDataAccessException.class).when(veiculoRepository).flush();

        final Throwable exception = catchThrowable(() -> veiculoService.excluir(idVeiculo));

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo(String.format("Não existe cadastro de Veículo de ID %s", idVeiculo));

        verify(veiculoRepository, times(1)).deleteById(anyLong());
        verify(veiculoRepository, times(1)).flush();
    }

    private Veiculo criarNovoVeiculo() {
        Veiculo veiculo = new Veiculo();

        veiculo.setVeiculo("Fusca");
        veiculo.setId(1L);
        veiculo.setDescricao("Fusca");
        veiculo.setMarca("Volkswagen");
        veiculo.setAno(1978);
        veiculo.setVendido(false);

        return veiculo;
    }
}
