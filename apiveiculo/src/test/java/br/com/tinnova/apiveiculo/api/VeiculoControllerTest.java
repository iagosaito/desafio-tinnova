package br.com.tinnova.apiveiculo.api;

import br.com.tinnova.apiveiculo.api.controller.VeiculoController;
import br.com.tinnova.apiveiculo.api.disassembler.VeiculoDisassembler;
import br.com.tinnova.apiveiculo.api.dto.VeiculoDto;
import br.com.tinnova.apiveiculo.domain.model.Veiculo;
import br.com.tinnova.apiveiculo.domain.service.VeiculoFiltroQueryService;
import br.com.tinnova.apiveiculo.domain.service.VeiculoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = VeiculoController.class)
@AutoConfigureMockMvc
public class VeiculoControllerTest {

    private static String VEICULO_URI = "/veiculos";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VeiculoDisassembler veiculoDisassembler;

    @MockBean
    private VeiculoService veiculoService;

    @MockBean
    private VeiculoFiltroQueryService veiculoFiltroQueryService;

    @Test
    @DisplayName("Deve listar os veículos com sucesso")
    void deveListarVeiculosComSucessoTest() throws Exception {
        final Veiculo veiculo = criarNovoVeiculo();

        given(veiculoService.listar()).willReturn(Collections.singletonList(veiculo));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(VEICULO_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0]veiculo").value(veiculo.getVeiculo()))
                .andExpect(jsonPath("[0]ano").value(veiculo.getAno()))
                .andExpect(jsonPath("[0]marca").value(veiculo.getMarca()))
                .andExpect(jsonPath("[0]vendido").value(veiculo.getVendido()))
                .andExpect(jsonPath("[0]id").value(veiculo.getId()))
                .andExpect(jsonPath("[0]descricao").value(veiculo.getDescricao()));

        verify(veiculoService, times(1)).listar();
    }

    @Test
    @DisplayName("Deve buscar um veículo por ID com sucesso")
    void deveBuscarVeiculoPeloIdComSucessoTest() throws Exception {

        final Veiculo veiculo = criarNovoVeiculo();

        given(veiculoService.buscar(anyLong())).willReturn(veiculo);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(VEICULO_URI + "/" + veiculo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("veiculo").value(veiculo.getVeiculo()))
                .andExpect(jsonPath("ano").value(veiculo.getAno()))
                .andExpect(jsonPath("marca").value(veiculo.getMarca()))
                .andExpect(jsonPath("vendido").value(veiculo.getVendido()))
                .andExpect(jsonPath("id").value(veiculo.getId()))
                .andExpect(jsonPath("descricao").value(veiculo.getDescricao()));

        verify(veiculoService, times(1)).buscar(veiculo.getId());
    }

    @Test
    @DisplayName("Deve excluir um veículo por ID com sucesso")
    void deveExcluirVeiculoPeloIdComSucessoTest() throws Exception {

        final Veiculo veiculo = criarNovoVeiculo();

        willDoNothing().given(veiculoService).excluir(anyLong());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(VEICULO_URI + "/" + veiculo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNoContent());

        verify(veiculoService, times(1)).excluir(veiculo.getId());
    }

    @Test
    @DisplayName("Deve cadastrar Veículo com sucesso")
    void deveCadastrarVeiculoComSucessoTest() throws Exception {
        final VeiculoDto veiculoDto = criarNovoVeiculoDto();
        final Veiculo veiculo = criarNovoVeiculo();

        final String jsonVeiculoDto = new ObjectMapper().writeValueAsString(veiculoDto);

        given(veiculoService.salvar(any(Veiculo.class))).willReturn(veiculo);
        given(veiculoDisassembler.toDomainObject(any(VeiculoDto.class))).willCallRealMethod();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(VEICULO_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonVeiculoDto);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("veiculo").value(veiculo.getVeiculo()))
                .andExpect(jsonPath("ano").value(veiculo.getAno()))
                .andExpect(jsonPath("marca").value(veiculo.getMarca()))
                .andExpect(jsonPath("vendido").value(veiculo.getVendido()))
                .andExpect(jsonPath("id").value(veiculo.getId()))
                .andExpect(jsonPath("descricao").value(veiculo.getDescricao()));

        verify(veiculoService, times(1)).salvar(any(Veiculo.class));
        verify(veiculoDisassembler, times(1)).toDomainObject(any(VeiculoDto.class));
    }

    @ParameterizedTest
    @MethodSource("entradaComDadosInvalidos")
    @DisplayName("Deve falhar ao cadastrar veículo com dados inválidos")
    void deveFalharAoCadastrarVeiculoComDadosInvalidos(VeiculoDto veiculoDto) throws Exception {
        final String jsonVeiculoDto = new ObjectMapper().writeValueAsString(veiculoDto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(VEICULO_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonVeiculoDto);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());

        verify(veiculoService, never()).salvar(any(Veiculo.class));
        verify(veiculoDisassembler, never()).toDomainObject(any(VeiculoDto.class));
    }

    static Stream<Arguments> entradaComDadosInvalidos() {

        VeiculoDto veiculoDtoComAnoNulo = criarNovoVeiculoDto();
        veiculoDtoComAnoNulo.setAno(null);

        VeiculoDto veiculoDtoComVeiculoEmBranco = criarNovoVeiculoDto();
        veiculoDtoComVeiculoEmBranco.setVeiculo("");

        VeiculoDto veiculoDtoComMarcaEmBranco = criarNovoVeiculoDto();
        veiculoDtoComMarcaEmBranco.setMarca("");

        VeiculoDto veiculoDtoComVendidoNulo = criarNovoVeiculoDto();
        veiculoDtoComVendidoNulo.setVendido(null);

        return Stream.of(
                Arguments.of(veiculoDtoComAnoNulo),
                Arguments.of(veiculoDtoComVeiculoEmBranco),
                Arguments.of(veiculoDtoComMarcaEmBranco),
                Arguments.of(veiculoDtoComVendidoNulo)
        );
    }

    @Test
    @DisplayName("Deve atualizar Veículo com sucesso")
    void deveAtualizarVeiculoComSucessoTest() throws Exception {
        final VeiculoDto veiculoDto = criarNovoVeiculoDto();
        final Veiculo veiculo = criarNovoVeiculo();

        final String jsonVeiculoDto = new ObjectMapper().writeValueAsString(veiculoDto);

        given(veiculoService.buscar(anyLong())).willReturn(veiculo);
        given(veiculoService.salvar(any(Veiculo.class))).willReturn(veiculo);
        given(veiculoDisassembler.toDomainObject(any(VeiculoDto.class))).willCallRealMethod();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(VEICULO_URI + "/" + veiculo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonVeiculoDto);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("veiculo").value(veiculo.getVeiculo()))
                .andExpect(jsonPath("ano").value(veiculo.getAno()))
                .andExpect(jsonPath("marca").value(veiculo.getMarca()))
                .andExpect(jsonPath("vendido").value(veiculo.getVendido()))
                .andExpect(jsonPath("id").value(veiculo.getId()))
                .andExpect(jsonPath("descricao").value(veiculo.getDescricao()));

        verify(veiculoService, times(1)).salvar(any(Veiculo.class));
        verify(veiculoService, times(1)).buscar(anyLong());
    }

    @Test
    @DisplayName("Deve atualizar parcialmente um Veículo com sucesso")
    void deveAtualizarParcialVeiculoComSucessoTest() throws Exception {
        Map<String, Object> dadosVeiculo = new HashMap<>();
        dadosVeiculo.put("ano", 1900);

        final Veiculo veiculo = criarNovoVeiculo();
        final String jsonVeiculoDto = new ObjectMapper().writeValueAsString(dadosVeiculo);

        given(veiculoService.buscar(anyLong())).willReturn(veiculo);
        given(veiculoService.salvar(any(Veiculo.class))).willReturn(veiculo);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .patch(VEICULO_URI + "/" + veiculo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonVeiculoDto);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("veiculo").value(veiculo.getVeiculo()))
                .andExpect(jsonPath("ano").value(veiculo.getAno()))
                .andExpect(jsonPath("marca").value(veiculo.getMarca()))
                .andExpect(jsonPath("vendido").value(veiculo.getVendido()))
                .andExpect(jsonPath("id").value(veiculo.getId()))
                .andExpect(jsonPath("descricao").value(veiculo.getDescricao()));

        verify(veiculoService, times(1)).salvar(any(Veiculo.class));
        verify(veiculoService, times(1)).buscar(anyLong());
    }

    @Test
    @DisplayName("Deve falhar ao atualizar parcialmente um Veículo com campos inexistentes")
    void deveFalharAoAtualizarParcialmenteVeiculoComDadosInexistentes() throws Exception {
        Map<String, Object> dadosVeiculo = new HashMap<>();
        dadosVeiculo.put("campoInexistente", 1900);

        final Veiculo veiculo = criarNovoVeiculo();
        final String jsonVeiculoDto = new ObjectMapper().writeValueAsString(dadosVeiculo);

        given(veiculoService.buscar(anyLong())).willReturn(veiculo);
        given(veiculoService.salvar(any(Veiculo.class))).willReturn(veiculo);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .patch(VEICULO_URI + "/" + veiculo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonVeiculoDto);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());

        verify(veiculoService, never()).salvar(any(Veiculo.class));
        verify(veiculoService, times(1)).buscar(anyLong());
    }

    @ParameterizedTest
    @MethodSource("parametrosDeFiltro")
    @DisplayName("Deve buscar os veículos da última semana com sucesso")
    void deveFiltrarUltimosVeiculosComSucesso(String parametrosDeFiltro) throws Exception {
        final Veiculo veiculo = criarNovoVeiculo();

        given(veiculoFiltroQueryService.filtrar(anyString())).willReturn(Collections.singletonList(veiculo));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(VEICULO_URI + "/find")
                .param("q", parametrosDeFiltro)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0]veiculo").value(veiculo.getVeiculo()))
                .andExpect(jsonPath("[0]ano").value(veiculo.getAno()))
                .andExpect(jsonPath("[0]marca").value(veiculo.getMarca()))
                .andExpect(jsonPath("[0]vendido").value(veiculo.getVendido()))
                .andExpect(jsonPath("[0]id").value(veiculo.getId()))
                .andExpect(jsonPath("[0]descricao").value(veiculo.getDescricao()));

        verify(veiculoFiltroQueryService, times(1)).filtrar(anyString());
    }

    static Stream<Arguments> parametrosDeFiltro() {

        return Stream.of(
                Arguments.of("ultima-semana:true"),
                Arguments.of("decada:1970"),
                Arguments.of("fabricante:ford"),
                Arguments.of("apenas-nao-vendidos:true")
        );
    }

    private static VeiculoDto criarNovoVeiculoDto() {
        VeiculoDto veiculoDto = new VeiculoDto();
        veiculoDto.setAno(1978);
        veiculoDto.setMarca("Volkswagen");
        veiculoDto.setDescricao("Utilizado no clipe daquela banda famosa");
        veiculoDto.setVendido(true);
        veiculoDto.setVeiculo("Fusca");

        return veiculoDto;
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
