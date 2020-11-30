import { Component, OnInit, ViewChild } from '@angular/core';
import { from } from 'rxjs';
import { FormControl, NgForm } from '@angular/forms';
import { VeiculoService } from './veiculos.service';
import { Veiculo } from './model'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  @ViewChild('form', { read: NgForm }) form: any;

  veiculos = [];
  veiculosDecada = [];
  veiculosUltimaSemana = [];

  quantidadeNaoVendidos: number;
  display: boolean = false;
  veiculo: Veiculo = new Veiculo;

  tipoFiltro: string;
  quantidadePorDecada: number;
  quantidadePorFabricante: number;


  valorAno: number = 1940;
  btnLabelCadastrarOuAtualizar = "Cadastrar Veículo";

  marcas = [
    { label: 'FORD', value: 'FORD' },
    { label: 'Chevrolet', value: 'CHEVROLET' },
    { label: 'Volkswagen', value: 'VOLKSWAGEN' },
    { label: 'Fiat', value: 'FIAT' }
  ];

  vendido = [
    { label: 'Sim', value: true },
    { label: 'Não', value: false }
  ];

  decadas = [
    { label: 1940, value: 1940 },
    { label: 1950, value: 1950 },
    { label: 1960, value: 1960 },
    { label: 1970, value: 1970 },
    { label: 1980, value: 1980 },
    { label: 1990, value: 1990 },
    { label: 2000, value: 2000 },
    { label: 2010, value: 2010 },
    { label: 2020, value: 2020 }
  ]

  constructor(private veiculoService: VeiculoService) { }

  ngOnInit(): void {
    this.consultar();
    this.consultarQuantidadeNaoVendidos();
  }

  showDialog() {
    this.display = true;
  }

  consultar() {
    this.veiculoService.consultar()
      .then(dados => {
        this.veiculos = dados;
      })
  }

  consultarQuantidadeNaoVendidos() {

    let veiculosNaoVendidos: Array<Veiculo>;
    this.veiculoService.consultarNaoVendidos()
      .then(dados => {
        veiculosNaoVendidos = dados;
        this.quantidadeNaoVendidos = veiculosNaoVendidos.length;
      }).catch(erro => alert("Erro ao buscar veículos não vendidos"));
  }

  consultarVeiculosUltimaSemana() {
    this.veiculoService.consultarUltimaSemana()
      .then(dados => {
        this.veiculosUltimaSemana = dados;
      })
  }

  consultarVeiculosPorDecada(event: any) {

    let veiculosPorDecada: Array<Veiculo>;
    this.veiculoService.consultarPorDecada(event.value)
      .then((dados) => {
        veiculosPorDecada = dados;
        this.quantidadePorDecada = veiculosPorDecada.length;
      })

  }

  consultarVeiculosPorFabricante(event: any) {

    let veiculosPorFabricante: Array<Veiculo>;
    this.veiculoService.consultarPorFabricante(event.value)
      .then((dados) => {
        veiculosPorFabricante = dados;
        this.quantidadePorFabricante = veiculosPorFabricante.length;
      })

  }

  excluir(id: number) {
    this.veiculoService.excluir(id)
      .then(() => {
        alert('Veículo excluído com sucesso');
        this.consultar();
        this.consultarQuantidadeNaoVendidos();
      })
  }

  atualizar(veiculo: Veiculo) {
    this.btnLabelCadastrarOuAtualizar = "Atualizar Veículo"

    Object.assign(this.veiculo, veiculo);
  }

  vender(id: number) {
    this.veiculoService.atualizarParaVendido(id)
      .then(() => {
        alert("Veículo vendido com sucesso")

        this.veiculo = new Veiculo();
        this.consultar();
        this.consultarQuantidadeNaoVendidos();
      }).catch(erro => alert("Erro ao atualizar veículo para vendido"))
  }

  salvar(form: FormControl) {

    if (this.veiculo.ano > 2022 || this.veiculo.ano < 1940) {
      this.veiculo.ano = 2020;
      alert('Ano deve estar entre 1940 a 2022');
    } else {
      let successMessage = 'Veículo adicionado com sucesso!';
      let errorMessage = 'Erro ao adicionar veículo. Tente mais tarde!';

      if (this.veiculo.id) {
        successMessage = 'Veículo alterado com sucesso!';
        errorMessage = 'Erro ao alterar veículo. Tente mais tarde!';
      }

      this.veiculoService.adicionar(this.veiculo)
        .then(() => {
          alert(successMessage);
          form.reset();
          this.veiculo = new Veiculo();
          this.consultar();
          this.consultarQuantidadeNaoVendidos();
          this.btnLabelCadastrarOuAtualizar = "Cadastrar Veículo";
        })
        .catch(erro => alert(errorMessage));
      }

  }

  limparFormulario() {
    this.form.reset();
    this.btnLabelCadastrarOuAtualizar = "Cadastrar Veículo";
  }

}
