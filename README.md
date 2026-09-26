# API de Cadastro de Pacientes

Backend REST para cadastro e consulta de pacientes, implementado em Java com Spring Boot. A API usa MySQL para persistencia e Springdoc OpenAPI para documentacao interativa.

## Requisitos

- Java 21
- MySQL acessivel pela aplicacao
- IntelliJ IDEA ou Maven Wrapper

## Configuracao

A aplicacao le a configuracao do banco pelas variaveis de ambiente abaixo. Os valores depois de `:` sao os padroes definidos em `application.properties`.

| Variavel | Padrao |
| --- | --- |
| `DB_URL` | `jdbc:mysql://localhost:3306/Fortec?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC` |
| `DB_USERNAME` | `root` |
| `DB_PASSWORD` | `root` |

Crie o banco e disponibilize um schema compativel antes de iniciar a aplicacao. O Hibernate esta configurado com `ddl-auto=validate`: ele valida o schema existente e nao cria nem atualiza tabelas automaticamente.

## Executar

No IntelliJ, abra o projeto Maven, configure o JDK 21 e execute a classe `br.com.fortec.FortecApplication`.

Ou, no PowerShell, na pasta do projeto:

```powershell
.\mvnw.cmd spring-boot:run
```

Para executar os testes:

```powershell
.\mvnw.cmd test
```

## Swagger / OpenAPI

O projeto ja inclui `springdoc-openapi-starter-webmvc-ui`. Com a aplicacao em execucao local, acesse:

- Swagger UI: http://localhost:8080/swagger-ui/index.html
- Especificacao OpenAPI em JSON: http://localhost:8080/v3/api-docs

A interface permite consultar os endpoints e enviar requisicoes. Use a funcao de teste somente contra um ambiente e um banco apropriados; operacoes de escrita e exclusao alteram os dados.

## Endpoints

Os endpoints de pacientes usam o prefixo `/api/pacientes`.

| Metodo | Caminho | Descricao |
| --- | --- | --- |
| `GET` | `/api/pacientes/health` | Verifica disponibilidade; retorna `UP`. |
| `POST` | `/api/pacientes` | Cadastra um paciente. Retorna `201 Created`. |
| `GET` | `/api/pacientes` | Lista os pacientes. |
| `GET` | `/api/pacientes/buscar-nome?nome=...` | Busca por parte do nome, sem diferenciar maiusculas/minusculas. |
| `GET` | `/api/pacientes/{id}` | Busca pelo identificador. |
| `GET` | `/api/pacientes/cpf/{cpf}` | Busca pelo CPF. |
| `PUT` | `/api/pacientes/{id}` | Atualiza os dados do paciente. |
| `DELETE` | `/api/pacientes/{id}` | Exclui pelo identificador. Retorna `204 No Content`. |
| `DELETE` | `/api/pacientes/cpf/{cpf}` | Exclui pelo CPF. Retorna `204 No Content`. |

### Exemplo de cadastro

```http
POST /api/pacientes
Content-Type: application/json
```

```json
{
  "nome": "Paciente Exemplo",
  "cpf": "12345678900",
  "dataNascimento": "1990-01-31",
  "email": "paciente@example.com"
}
```

O exemplo usa dados ficticios. Os campos obrigatorios sao `nome`, `cpf` e `dataNascimento`. O CPF deve conter 11 digitos; quando informado, o email deve ter formato valido. Os demais campos opcionais e seus limites estao definidos em `PacienteRequestDTO` e `PacienteUpdateDTO`.

### Respostas de erro

As respostas de erro usam um objeto JSON com a propriedade `erro`.

| Status | Situacao |
| --- | --- |
| `400 Bad Request` | Dados invalidos; a resposta informa o primeiro campo invalido encontrado. |
| `404 Not Found` | Paciente nao encontrado. |
| `409 Conflict` | CPF ja cadastrado. |

## Seguranca

Os dados tratados podem conter informacoes pessoais e de saude. O Swagger UI e a especificacao OpenAPI documentam a API, mas nao protegem seus endpoints. Em producao, restrinja o acesso a documentacao e proteja a API com os controles de autenticacao e autorizacao adequados. Evite testar `POST`, `PUT` ou `DELETE` contra dados reais.