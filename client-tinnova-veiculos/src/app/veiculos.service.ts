import {take} from 'rxjs/internal/operators';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { pipe } from 'rxjs';
import { Veiculo } from './model';

const baseUrl = 'http://localhost:8080/veiculos'

@Injectable()
export class VeiculoService {

  constructor(private http: HttpClient) { }

  consultar(): Promise<any> {
    return this.http.get(`${baseUrl}`)
      .toPromise();
  }

  adicionar(veiculo: Veiculo): Promise<any> {
    const headers = new HttpHeaders()
      .append('Content-Type', 'application/json');


    if(veiculo.id) {
      return this.atualizar(veiculo);
    }

    return this.http.post<Veiculo>(`${baseUrl}`, JSON.stringify(veiculo), { headers })
      .toPromise();
  }

  atualizarParaVendido(id: number) {
    const headers = new HttpHeaders()
      .append('Content-Type', 'application/json');

    let dadosVeiculoVendido = { vendido: true }
    return this.http.patch<Veiculo>(`${baseUrl}/${id}`, dadosVeiculoVendido, { headers })
      .toPromise();
  }


  excluir(id: number): Promise<void> {
    return this.http.delete(`${baseUrl}/${id}`)
      .toPromise()
      .then(() => null);
  }

  atualizar(veiculo: Veiculo): Promise<any> {
    return this.http.put(`${baseUrl}/${veiculo.id}`, veiculo)
      .toPromise();
  }

  consultarNaoVendidos(): Promise<any> {
    return this.http.get<Veiculo>(`${baseUrl}/find?q=apenas-nao-vendidos:true`)
      .toPromise();
  }

  consultarPorDecada(decada: number): Promise<any> {
    return this.http.get(`${baseUrl}/find?q=decada:${decada}`)
      .toPromise();
  }

  consultarUltimaSemana(): Promise<any> {
    return this.http.get(`${baseUrl}/find?q=ultima-semana:true`)
      .toPromise();
  }

  consultarPorFabricante(fabricante: string): Promise<any> {
    return this.http.get(`${baseUrl}/find?q=fabricante:${fabricante}`)
      .pipe(take(1))
      .toPromise();
  }

}