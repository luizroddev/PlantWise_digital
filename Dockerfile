# Use a imagem do OpenJDK 11 Slim como base
FROM openjdk:19-slim as build

# Variável para armazenar o diretório de trabalho
ARG WORK_DIR=/app

# Criação do diretório de trabalho
RUN mkdir -p $WORK_DIR

# Configurando o diretório de trabalho
WORKDIR $WORK_DIR

# Copiando o projeto para o diretório de trabalho
COPY . $WORK_DIR

# Compilando o projeto com Maven
RUN ./mvnw clean package

# Criação de uma nova etapa para reduzir o tamanho da imagem final
FROM openjdk:19-slim

# Variável para armazenar o diretório de trabalho
ARG WORK_DIR=/app

# Criação do diretório de trabalho
RUN mkdir -p $WORK_DIR

# Configurando o diretório de trabalho
WORKDIR $WORK_DIR

# Copiando o arquivo .jar da etapa de compilação para a etapa final
COPY target/PlantWise-0.0.1-SNAPSHOT.jar $WORK_DIR/app.jar

# Expondo a porta 8080 para permitir conexões externas
EXPOSE 8080

# VOLUME para persistência
VOLUME /app-data

ENTRYPOINT [ "java", "-jar", "app.jar" ]

# Comando para iniciar a aplicação Spring Boot