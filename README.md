# Linketinder Acelera ZG

Linketinder é um sistema de contratação de funcionários inspirado no Linkedin e Tinder. Ele permite que empresas encontrem candidatos com as competências desejadas de forma rápida e eficaz.

## Configuração do Projeto

Certifique-se de ter o JDK 17, Groovy 2.4.21+ e Docker Compose instalado em sua máquina. Você também precisará do Gradle para compilar e executar o projeto.

## Executando o Projeto

Para executar o projeto deve-se clonar o [Projeto de Banco de Dados - Linketinder](https://github.com/Alvaro125/Projeto_Introdut-rio_trilha__K1-T9__Banco_de_Dados_PostgreSQL) e siga as instruções

Em seguinte use o comando:

```bash
./gradlew run --console plain
```

Certifique-se de ter todas as dependências corretamente configuradas no seu ambiente. O projeto depende das seguintes bibliotecas:

- Jansi 1.18
- Groovy 4.0.14
- Junit-bom 5.9.1
- Junit-jupiter
- Postgresql 42.7.3
- Spock Framework

Certifique-se de que essas dependências estejam disponíveis no classpath ao executar o projeto.

## Funcionalidades

- CRUD da Tabela de dados de candidatos;
- CRUD da Tabela de dados de empresas;
- CRUD da Tabela de competências;
- CRUD da Tabela de Vagas.

>Futaramente irei aperfeiçoar os testes
---
## Autor
Álvaro Martinez Ferreira

## Licença
Este projeto está licenciado sob a Licença MIT.