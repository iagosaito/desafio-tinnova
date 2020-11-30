# Spring Boot RESTFul API para Cadastro de Veículos
REST APIs implementada utilizando Spring Boot e um H2 Database.

## Como executar?

* Faça o Build to projeto executando 
```
`mvn clean package`
```

## REST APIs Endpoints
### Create a Todo Note resource
```
POST /todo
Accept: application/json
Content-Type: application/json

{
"title" : "Spring Boot",
"description" : "Setup spring boot application",
}

```

### Update a Todo Note resource
```
PUT /todo/{Note Id}
Accept: application/json
Content-Type: application/json

{
"id" : "id"
"title" : "Spring Boot",
"description" : "Setup spring boot application",
}

```

### Retrieve a list of Todo Notes
```
Get /todo
Accept: application/json
Content-Type: application/json

```

### Find a Todo Note Resource
```
Get /todo/{Note Id}
Accept: application/json
Content-Type: application/json
```

### Delete a Todo Note Resource
```
DELETE /todo/{Note Id}
Accept: application/json
Content-Type: application/json
```

### Get information about system health, configurations, etc.
```
http://localhost:8081/health
http://localhost:8081/env
http://localhost:8081/info
http://localhost:8081/metrics
http://localhost:8081/logfile

```
### To view Swagger 2 API docs
```
Run the server and browse to localhost:8080/swagger-ui.html
```
