## Endpoints

### Usuários

#### `POST /api/usuarios/registrar`
Cria um novo usuário.

**Parâmetros do corpo:**
- `nome` (string, obrigatório): Nome do usuário.
- `email` (string, obrigatório): Endereço de e-mail do usuário.
- `senha` (string, obrigatório): Senha do usuário.

**Respostas:**
- `201 Created`: Usuário criado com sucesso.
- `400 Bad Request`: Parâmetros inválidos.

#### `POST /api/usuarios/login`
Realiza o login do usuário.

**Parâmetros do corpo:**
- `email` (string, obrigatório): Endereço de e-mail do usuário.
- `senha` (string, obrigatório): Senha do usuário.

**Respostas:**
- `200 OK`: Login realizado com sucesso. Retorna o token de autenticação.
- `400 Bad Request`: Parâmetros inválidos.
- `404 Not Found`: Usuário não encontrado.

#### `GET /api/usuarios`
Lista todos os usuários cadastrados.

**Parâmetros da consulta:**
- `busca` (string, opcional): Termo de busca para filtrar os usuários.
- `page` (int, opcional): Número da página a ser retornada. Padrão: 0.
- `size` (int, opcional): Tamanho da página. Padrão: 5.
- `sort` (string, opcional): Ordenação dos resultados. Exemplo: `sort=nome,asc`.

**Respostas:**
- `200 OK`: Retorna uma lista paginada de usuários.
- `400 Bad Request`: Parâmetros inválidos.

#### `GET /api/usuarios/{id}`
Obtém informações de um usuário específico.

**Parâmetros do caminho:**
- `id` (long, obrigatório): ID do usuário.

**Respostas:**
- `200 OK`: Retorna as informações do usuário.
- `404 Not Found`: Usuário não encontrado.

#### `DELETE /api/usuarios/{id}`
Exclui um usuário.

**Parâmetros do caminho:**
- `id` (long, obrigatório): ID do usuário.

**Respostas:**
- `204 No Content`: Usuário excluído com sucesso.
- `404 Not Found`: Usuário não encontrado.

### Análises

#### `POST /api/analises/image`
Realiza a análise de uma imagem de planta.

**Parâmetros do corpo:**
- `userId` (long, obrigatório): ID do usuário.
- `image` (string, obrigatório): Imagem da planta (caminho ou base64).

**Respostas:**
- `201 Created`: Análise da imagem realizada com sucesso. Retorna os resultados da análise.
- `400 Bad Request`: Parâmetros inválidos.

#### `POST /api/analises/usuario/{id}`
Registra uma análise de planta associada a um usuário.

**Parâmetros do caminho:**
- `id` (long, obrigatório): ID do usuário.

**Parâmetros do corpo:**
- `userId` (long, obrigatório): ID do usuário.
- `image` (string, obrigatório): Imagem da planta (caminho ou base64).

**Respostas:**
- `201 Created`: Análise registrada com sucesso. Retorna os resultados da análise.
- `400 Bad Request`: Parâmetros inválidos.

#### `POST /api/analises/{

id}`
Registra uma nova análise de planta.

**Parâmetros do caminho:**
- `id` (long, obrigatório): ID da análise.

**Parâmetros do corpo:**
- `userId` (long, obrigatório): ID do usuário.
- `image` (string, obrigatório): Imagem da planta (caminho ou base64).

**Respostas:**
- `201 Created`: Análise registrada com sucesso. Retorna os resultados da análise.
- `400 Bad Request`: Parâmetros inválidos.

#### `GET /api/analises/usuario/{id}`
Lista todas as análises realizadas por um usuário específico.

**Parâmetros do caminho:**
- `id` (long, obrigatório): ID do usuário.

**Respostas:**
- `200 OK`: Retorna uma lista de análises realizadas pelo usuário.
- `404 Not Found`: Usuário não encontrado.

#### `GET /api/analises/doenca/{doenca}`
Lista todas as análises relacionadas a uma doença específica.

**Parâmetros do caminho:**
- `doenca` (string, obrigatório): Nome da doença.

**Respostas:**
- `200 OK`: Retorna uma lista de análises relacionadas à doença.
- `404 Not Found`: Nenhuma análise encontrada.

#### `GET /api/analises/planta/{planta}/usuario/{nome}`
Lista todas as análises relacionadas a uma planta e associadas a um usuário específico.

**Parâmetros do caminho:**
- `planta` (string, obrigatório): Nome da planta.
- `nome` (string, obrigatório): Nome do usuário.

**Respostas:**
- `200 OK`: Retorna uma lista de análises relacionadas à planta e associadas ao usuário.
- `404 Not Found`: Nenhuma análise encontrada.

#### `GET /api/analises`
Lista todas as análises.

**Parâmetros da consulta:**
- `planta` (string, opcional): Nome da planta para filtrar as análises.
- `page` (int, opcional): Número da página a ser retornada. Padrão: 0.
- `size` (int, opcional): Tamanho da página. Padrão: 5.
- `sort` (string, opcional): Ordenação dos resultados. Exemplo: `sort=planta,asc`.

**Respostas:**
- `200 OK`: Retorna uma lista paginada de análises.
- `400 Bad Request`: Parâmetros inválidos.

#### `GET /api/analises/{id}`
Obtém informações de uma análise específica.

**Parâmetros do caminho:**
- `id` (long, obrigatório): ID da análise.

**Respostas:**
- `200 OK`: Retorna as informações da análise.
- `404 Not Found`: Análise não encontrada.

#### `DELETE /api/analises/{id}`
Exclui uma análise.

**Parâmetros do caminho:**
- `id` (long, obrigatório): ID da análise.

**Respostas:**
- `204 No Content`: Análise excluída com sucesso.
- `404 Not Found`: Análise não encontrada.
