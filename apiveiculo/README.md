# Spring Boot RESTFul API para Cadastro de Veículos
REST APIs implementada utilizando Spring Boot e H2 Database.

## Como executar?

Está aplicação está utilizando um servidor Tomcat embutido rodando na porta 8080. Não é necessário instalação do Tomcat. Você pode rodar utilizando apenas o comando ```java -jar```

* Clone este repositório

* Utilize Java 1.8+ e Maven 3x+

* Para realizar o build do projeto e rodar os testes 
```
`.mvn clean package ou ./mvnw clean package`
```

* Para rodar apenas os testes unitário com JUnit 
```
`mvn test ou ./mvnw teste`
```


## REST APIs Endpoints

* Cadastrar um Veículo: ``` /veiculos (POST) ```
* Atualizar um Veículo: ``` /veiculos/{id} (PUT)```
* Listar os Veículo: ``` /veiculos (GET)```
* Buscar um Veículo por ID: ``` /veiculos/{id} (GET)```
* Atualizar Parcialmente um Veículo: ``` /veiculos/{id} (PATCH)```
* Excluir um Veículo: ``` /veiculos/{id} (DELETE)```
* Filtra os Veículo por parâmetros: ``` /veiculos/find?q={parametros} (GET)```

### Para visualizar a documentação completa da API no Swagger
```
Execute o servidor e acesse o navegador em: localhost:8080/swagger-ui.html
```
