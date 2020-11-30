export class Veiculo {
    id: number;
    veiculo: string;
    marca: string;
    ano: number;
    descricao: string;
    vendido: boolean = false;
    created: Date;
    update: Date;
}

export class VeiculoDecada {
    decada: number;
    quantidadeVeiculos: number;
}