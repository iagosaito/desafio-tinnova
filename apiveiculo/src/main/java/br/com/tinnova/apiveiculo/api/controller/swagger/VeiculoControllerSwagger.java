package br.com.tinnova.apiveiculo.api.controller.swagger;

import br.com.tinnova.apiveiculo.api.dto.VeiculoDto;
import br.com.tinnova.apiveiculo.domain.model.Veiculo;
import io.swagger.annotations.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(tags = "Veículos")
public interface VeiculoControllerSwagger {

    @ApiOperation("Lista os Veículos")
    List<Veiculo> listar();

    @ApiOperation("Busca um Veículos pelo ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID inválido"),
            @ApiResponse(code = 404, message = "Veículo não encontrado")
    })
    Veiculo buscar(@ApiParam(value = "ID do Veículo", example = "1") Long id);

    @ApiOperation("Filtra os Veículos por Fabricante, Década, Não Vendidos e Última Semana")
    List<Veiculo> filtrar(@ApiParam(name = "q", required = true,
            value = "Parâmetro de busca por veículos. 4 opções: (fabricante:ford | decada:1970 | ultima-semana:true | apenas-nao-vendidos:true)",
            example = "fabricante:ford,decada:1980,ultima-semana:true,apenas-nao-vendidos:true") String filtro);

    @ApiOperation("Cadastra um Veículo")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Veículo cadastrado com sucesso", response = Veiculo.class)
    })
    Veiculo cadastrar(@ApiParam(value = "Representação dos dados de um novo veículo")
                              VeiculoDto veiculoDto);

    @ApiOperation("Atualiza um novo Veículo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Veículo atualizado com sucesso", response = Veiculo.class),
            @ApiResponse(code = 404, message = "Veículo não encontrado", response = Veiculo.class)
    })
    Veiculo atualizar(@ApiParam(value = "Representação de novos dados de um veículo")
                              VeiculoDto veiculoDto,
                      @ApiParam(value = "ID do Veículo", example = "1")
                              Long id);

    @ApiOperation("Atualiza parcialmente um novo Veículo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Veículo parcialmente atualizado com sucesso", response = Veiculo.class),
            @ApiResponse(code = 404, message = "Veículo não encontrado", response = Veiculo.class)
    })
    Veiculo atualizarParcial(@ApiParam(value = "ID do Veículo", example = "1")
                                     Long id,
                             @ApiParam(value = "Representação de dados parciais de um novo veículo", example = "{ 'ano': 1970 }")
                                     Map<String, Object> dadosVeiculo,
                             @ApiParam(hidden = true)
                                     HttpServletRequest request);

    @ApiOperation("Exclui um Veículo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Veículo excluído com sucesso"),
            @ApiResponse(code = 404, message = "Veículo não encontrado")
    })
    void excluir(@ApiParam(value = "ID do Veículo", example = "1")
                         Long id);
}
