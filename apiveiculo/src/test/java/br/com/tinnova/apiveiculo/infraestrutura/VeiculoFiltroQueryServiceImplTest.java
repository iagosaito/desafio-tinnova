package br.com.tinnova.apiveiculo.infraestrutura;

import br.com.tinnova.apiveiculo.domain.model.Veiculo;
import br.com.tinnova.apiveiculo.domain.repository.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class VeiculoFiltroQueryServiceImplTest {

    private VeiculoFiltroQueryServiceImpl veiculoFiltroQueryService;

    @MockBean
    private VeiculoRepository veiculoRepository;

    @BeforeEach
    void setUp() {
        veiculoFiltroQueryService = new VeiculoFiltroQueryServiceImpl(veiculoRepository);
    }

    @DisplayName("Deve retornar os veículos cadastrados na última semana")
    @Test
    void deveRetornarUmaListaDeVeiculosCadastradosNaUltimaSemana() {
        final String query = "ultima-semana:true";

        when(veiculoRepository.findAllByUltimaSemana(any(), any())).thenReturn(Collections.emptyList());

        List<Veiculo> veiculoList = veiculoFiltroQueryService.filtrar(query);

        assertThat(veiculoList).isNotNull();

        verify(veiculoRepository, times(1)).findAllByUltimaSemana(any(), any());
    }

    @DisplayName("Deve retornar os veículos por Fabricante")
    @Test
    void deveRetornarUmaListaDeVeiculosPorFabricante() {
        final String query = "fabricante:ford";

        when(veiculoRepository.findAllByMarcaIgnoreCase(anyString())).thenReturn(Collections.emptyList());

        List<Veiculo> veiculoList = veiculoFiltroQueryService.filtrar(query);

        assertThat(veiculoList).isNotNull();

        verify(veiculoRepository, times(1)).findAllByMarcaIgnoreCase(anyString());
    }

    @DisplayName("Deve retornar os veículos pela Década")
    @Test
    void deveRetornarUmaListaDeVeiculosPorDecada() {
        final String query = "decada:1970";

        when(veiculoRepository.findAllByDecada(anyInt())).thenReturn(Collections.emptyList());

        List<Veiculo> veiculoList = veiculoFiltroQueryService.filtrar(query);

        assertThat(veiculoList).isNotNull();

        verify(veiculoRepository, times(1)).findAllByDecada(anyInt());
    }

    @DisplayName("Deve retornar os veículos não vendidos")
    @Test
    void deveRetornarUmaListaDeVeiculosNaoVendidos() {
        final String query = "apenas-nao-vendidos:true";

        when(veiculoRepository.findAllVeiculosNaoVendidos()).thenReturn(Collections.emptyList());

        List<Veiculo> veiculoList = veiculoFiltroQueryService.filtrar(query);

        assertThat(veiculoList).isNotNull();

        verify(veiculoRepository, times(1)).findAllVeiculosNaoVendidos();
    }
}
