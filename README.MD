# PlantWise API - README

Este README fornece instruções para instalação e execução da API PlantWise, além de um guia sobre como testar os recursos, os formatos JSON utilizados e os endpoints disponíveis.

## Instalação

Para instalar e executar a API PlantWise, siga as etapas abaixo:

1. Certifique-se de ter o JDK (Java Development Kit) instalado em sua máquina. Você pode baixá-lo e instalá-lo a partir do site oficial da Oracle.
2. Faça o download do código-fonte da API PlantWise do repositório oficial.
3. Descompacte o arquivo baixado em uma pasta de sua preferência.
4. Abra sua IDE favorita (recomendamos IntelliJ IDEA) e navegue até a pasta raiz da API PlantWise.

## Execução

Após a conclusão da etapa de instalação, siga as instruções abaixo para executar a API PlantWise:

1. Na mesma IDE, inicie a aplicação Maven.
2. Aguarde até que a API seja iniciada. Você verá uma mensagem indicando que o servidor está em execução na porta especificada (geralmente, a porta 8080).
3. A API PlantWise está agora em execução e pronta para receber solicitações.

## Testando os Recursos

A API PlantWise fornece os seguintes recursos e endpoints:

### Usuários

#### Criar um usuário

**Endpoint:** `POST /api/usuarios/registrar`

Este endpoint permite criar um novo usuário.

**Parâmetros do corpo (formato JSON):**
```json
{
  "nome": "Pedro",
  "email": "teste@gmail.com",
  "senha": "Senha123"
}
```

**Respostas:**
- `201 Created`: Usuário criado com sucesso.
- `400 Bad Request`: Parâmetros inválidos.

#### Realizar login

**Endpoint:** `POST /api/usuarios/login`

Este endpoint permite realizar o login de um usuário e retorna um token de autenticação.

**Parâmetros do corpo (formato JSON):**
```json
{
  "email": "teste@gmail.com",
  "senha": "Senha123"
}
```

**Resposta:**
- `200 OK`: Login realizado com sucesso. O token de autenticação será retornado no corpo da resposta.

#### Obter todos os usuários

**Endpoint:** `GET /api/usuarios`

Este endpoint retorna todos os usuários registrados.

**Parâmetros de consulta:**
- `nome` (opcional): O nome do usuário para filtrar os resultados.
- `page` (opcional): O número da página a ser retornada (padrão: 0).
- `size` (opcional): O número de registros por página (padrão: 5).

#### Obter um usuário pelo ID

**Endpoint:** `GET /api/usuarios/{id}`

Este endpoint retorna um usuário específico com base em seu ID.

**Parâmetros da URL:**
- `{id}` (obrigatório): O ID do usuário a ser retornado.

#### Excluir um usuário

**Endpoint:** `DELETE /api/usuarios/{id}`

Este endpoint exclui um usuário com base em seu ID.

**Parâmetros da URL:**
- `{id}` (obrigatório): O ID do usuário a ser excl

uído.

### Análises

#### Registrar uma análise de usuário

**Endpoint:** `POST /api/analises/usuario/{id}`

Este endpoint permite que um usuário registre uma análise de planta.

**Parâmetros da URL:**
- `{id}` (obrigatório): O ID do usuário que está registrando a análise.

**Parâmetros do corpo (formato JSON):**
```json
{
  "planta": "Nome da planta",
  "doencaEncontrada": true,
  "doenca": "Nome da doença"
}
```

**Respostas:**
- `201 Created`: Análise registrada com sucesso.
- `400 Bad Request`: Parâmetros inválidos.

#### Registrar uma análise

**Endpoint:** `POST /api/analises/{id}`

Este endpoint permite registrar uma análise vinculada a um usuário específico.

**Parâmetros da URL:**
- `{id}` (obrigatório): O ID do usuário ao qual a análise será associada.

**Parâmetros do corpo (formato JSON):**
```json
{
  "planta": "Nome da planta",
  "doencaEncontrada": true,
  "doenca": "Nome da doença (opcional)"
}
```

**Respostas:**
- `201 Created`: Análise registrada com sucesso.
- `400 Bad Request`: Parâmetros inválidos.

#### Obter todas as análises

**Endpoint:** `GET /api/analises`

Este endpoint retorna todas as análises registradas.

**Parâmetros de consulta:**
- `planta` (opcional): O nome da planta para filtrar as análises.
- `page` (opcional): O número da página a ser retornada (padrão: 0).
- `size` (opcional): O número de registros por página (padrão: 5).

#### Obter uma análise pelo ID

**Endpoint:** `GET /api/analises/{id}`

Este endpoint retorna uma análise específica com base em seu ID.

**Parâmetros da URL:**
- `{id}` (obrigatório): O ID da análise a ser retornada.

#### Excluir uma análise

**Endpoint:** `DELETE /api/analises/{id}`

Este endpoint exclui uma análise com base em seu ID.

**Parâmetros da URL:**
- `{id}` (obrigatório): O ID da análise a ser excluída.

## Formato JSON

A API PlantWise utiliza o formato JSON para enviar e receber dados. Abaixo estão exemplos de como os objetos devem ser estruturados em JSON.

### Objeto de Análise

```json
{
  "id": 1,
  "usuario": {
    "id": 1,
    "nome": "Nome do usuário",
    "email": "email@example.com",
    "senha": "Senha123"
  },
  "doencaEncontrada": true,
  "doenca": "Nome da doença",
  "planta": "Nome da planta"
}
```

### Objeto de Usuário

```json
{
  "id": 1,
  "nome": "Nome do usuário",
  "email": "email@example.com",
  "senha": "Senha123",
  "analises": [
    {
      "id": 1,
      "doencaEncontrada": true,
      "doenca": "Nome da doença",
      "planta": "Nome da planta"
    }
  ]
}
```