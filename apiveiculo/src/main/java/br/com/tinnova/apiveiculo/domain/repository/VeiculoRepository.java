package br.com.tinnova.apiveiculo.domain.repository;

import br.com.tinnova.apiveiculo.domain.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    List<Veiculo> findAllByMarcaIgnoreCase(String fabricante);

    @Query("SELECT v FROM Veiculo v WHERE ano >= :decada AND ano < :decada + 10")
    List<Veiculo> findAllByDecada(Integer decada);

    @Query("SELECT v FROM Veiculo v WHERE vendido = false")
    List<Veiculo> findAllVeiculosNaoVendidos();

    @Query("SELECT v FROM Veiculo v WHERE v.created BETWEEN :dataInicial AND :dataFinal")
    List<Veiculo> findAllByUltimaSemana(LocalDateTime dataInicial, LocalDateTime dataFinal);

    @Query("SELECT v FROM Veiculo v WHERE vendido = true")
    List<Veiculo> findAllVendidos();
}
