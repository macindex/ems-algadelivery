## Alga Delivery - Protótipo de Microsserviços
Este projeto é um protótipo de um sistema de logística e entregas baseado em uma arquitetura de microsserviços, utilizando princípios de Domain-Driven Design (DDD) para a modelagem do negócio e as tecnologias mais recentes do ecossistema Java/Spring.

## 📌 Domínio do Negócio
Este projeto foi analisado e dividido em três subdomínios principais:

Entregas (Core Domain): O coração do negócio. Focado no conceito de Encomenda (linguagem ubíqua), rastreamento, remetentes e destinatários.

Gestão de Entregadores (Support Subdomain): Gerencia os entregadores e suas disponibilidades. Embora essencial, serve para apoiar o domínio principal.

Suporte ao Cliente (Generic Subdomain): Processos padronizados de tickets de suporte. Por ser genérico, optou-se por utilizar softwares de terceiros nesta etapa.

Mapeamento de Contextos e Microsserviços
Com base nos contextos delimitados (Bounded Contexts), definimos os limites dos nossos microsserviços:

Delivery Tracking: Responsável pelo monitoramento de encomendas.

Courier Management: Responsável pela gestão e atribuição de entregadores.

## 🏗️ Arquitetura e Comunicação
O sistema segue o padrão de um banco de dados por serviço para garantir o isolamento e a escalabilidade.

Comunicação Assíncrona (Preferencial): Utilização do Apache Kafka (Event Broker) para desacoplamento e resiliência em processos como postagem de encomenda e atribuição de entregador.

Comunicação Síncrona: Utilização de HTTP/REST para processos que exigem resposta imediata do usuário.

### 🛠️ Tecnologias Utilizadas
Linguagem: Java 21

Framework: Spring Boot 3.x

Gerenciador de Dependências: Maven

Banco de Dados: PostgreSQL 17.5 (executando via Docker)

Mensageria: Apache Kafka

Bibliotecas Auxiliares:

Spring Data JPA

Spring Web (REST)

Bean Validation (Hibernate Validator)

Lombok (Opcional)

### 📁 Estrutura do Projeto
Plaintext

alga-delivery/
├── microservices/
│   ├── courier-management/   # Microsserviço de gestão de entregadores
│   └── delivery-tracking/    # Microsserviço de rastreamento de entregas
├── docs/                     # Documentações do projeto
├── docker-compose.yml        # Infraestrutura (Postgres, PGAdmin)
└── .gitignore                # Configurações de exclusão do Git

## 🚀 Como Executar o Projeto
1. Pré-requisitos
Docker e Docker Compose instalados.

JDK 21+.

IDE (IntelliJ IDEA recomendada).

2. Subir a Infraestrutura
Na raiz do projeto, execute o comando para iniciar o banco de dados e o administrador:

Bash

docker-compose up -d
Isso iniciará:

PostgreSQL: Porta 5432

PGAdmin: Porta 8083 (Acesse via http://localhost:8083)

Login: dba@algadelivery.com

Senha: algadelivery

3. Configurar os Bancos de Dados
O PostgreSQL não cria múltiplos bancos lógicos automaticamente via string de conexão. Acesse o PGAdmin e crie manualmente os seguintes bancos:

courier-db

delivery-db

4. Executar os Microsserviços
Importe os projetos Maven dentro da pasta microservices na sua IDE e execute a classe principal (Application) de cada um. Nota: Certifique-se de que as propriedades de conexão no application.properties de cada serviço apontam para os respectivos bancos criados.

## 📝 Notas de Implementação

Linguagem Ubíqua: O código utiliza nomes em inglês para os microsserviços e pacotes (delivery-tracking, courier-management), mas respeita os conceitos de negócio validados com os especialistas (ex: "Encomenda").

Tratamento de Erros: O projeto utiliza validações do Hibernate para garantir a integridade dos dados nas APIs.
