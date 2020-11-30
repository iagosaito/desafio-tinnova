package br.com.tinnova.apiveiculo.api.controller;

import br.com.tinnova.apiveiculo.api.controller.swagger.VeiculoControllerSwagger;
import br.com.tinnova.apiveiculo.api.disassembler.VeiculoDisassembler;
import br.com.tinnova.apiveiculo.api.dto.VeiculoDto;
import br.com.tinnova.apiveiculo.domain.model.Veiculo;
import br.com.tinnova.apiveiculo.domain.service.VeiculoFiltroQueryService;
import br.com.tinnova.apiveiculo.domain.service.VeiculoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/veiculos")
@CrossOrigin
public class VeiculoController implements VeiculoControllerSwagger {

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private VeiculoDisassembler veiculoDisassembler;

    @Autowired
    private VeiculoFiltroQueryService veiculoFiltroQueryService;

    @Override
    @GetMapping
    public List<Veiculo> listar() {
        return veiculoService.listar();
    }

    @Override
    @GetMapping("/{id}")
    public Veiculo buscar(@PathVariable Long id) {
        return veiculoService.buscar(id);
    }

    @Override
    @GetMapping("/find")
    public List<Veiculo> filtrar(@RequestParam(name = "q") String filtro) {
        return veiculoFiltroQueryService.filtrar(filtro);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Veiculo cadastrar(@RequestBody @Valid VeiculoDto veiculoDto) {
        Veiculo veiculo = veiculoDisassembler.toDomainObject(veiculoDto);
        return veiculoService.salvar(veiculo);
    }

    @Override
    @PutMapping("/{id}")
    public Veiculo atualizar(@RequestBody @Valid VeiculoDto veiculoDto,
                             @PathVariable Long id) {

        Veiculo veiculo = veiculoService.buscar(id);

        BeanUtils.copyProperties(veiculoDto, veiculo);

        return veiculoService.salvar(veiculo);
    }

    @Override
    @PatchMapping("/{id}")
    public Veiculo atualizarParcial(@PathVariable Long id,
                                    @RequestBody Map<String, Object> dadosVeiculo,
                                    HttpServletRequest request) {
        Veiculo veiculo = veiculoService.buscar(id);

        merge(dadosVeiculo, veiculo, request);

        return veiculoService.salvar(veiculo);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        veiculoService.excluir(id);
    }

    private void merge(@RequestBody Map<String, Object> dadosVeiculo, Veiculo veiculoDestino, HttpServletRequest request) {

        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            Veiculo veiculoOrigem = objectMapper.convertValue(dadosVeiculo, Veiculo.class);

            dadosVeiculo.forEach((nomePropriedade, valorPropriedade) -> {
                Field field = ReflectionUtils.findField(Veiculo.class, nomePropriedade);
                field.setAccessible(true);

                Object novoValor = ReflectionUtils.getField(field, veiculoOrigem);
                ReflectionUtils.setField(field, veiculoDestino, novoValor);
            });
        } catch (IllegalArgumentException e) {
            throw new HttpMessageNotReadableException(e.getMessage(), serverHttpRequest);
        }
    }
}
