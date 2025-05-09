# 🏆 Plataforma de Torneios de Desafios

## 📌 Visão Geral

A **Plataforma de Torneios de Desafios** é uma aplicação backend robusta para gerenciar torneios de desafios de programação. Construída com **Kotlin e Spring Boot** usando Arquitetura Hexagonal, oferece:

- 👥 Gerenciamento de jogadores
- 🏅 Organização de torneios
- 🎯 Execução de desafios técnicos
- 📊 Sistema automatizado de classificação

---

## ✨ Funcionalidades Principais

### 👤 Jogadores
- ✅ Criar, atualizar e deletar jogadores
- 🔍 Buscar por ID ou nome
- 🔄 Validação de nomes únicos

### 🏟️ Torneios
- 🗓️ Criar com nome e data
- ➕/➖ Adicionar/remover jogadores
- 📋 Listar participantes
- 🔒 Finalizar (bloqueia modificações)

### 🧩 Desafios Técnicos

| Desafio         | Peso  | Descrição                                |
|-----------------|-------|------------------------------------------|
| **Fibonacci**   | 10pts | Cálculo eficiente do enésimo termo       |
| **Palíndromo**  | 5pts  | Verificação insensível a caso e pontuação|
| **Ordenação**   | 8pts  | Implementação customizada (sem `.sorted()`)|

### 📈 Sistema de Classificação
- 🌍 **Global** (pontuações acumuladas)
- 🏟️ **Por Torneio**
- ⏫ Atualizações automáticas

---

## 🏗️ Arquitetura (Hexagonal)

### 🧱 Estrutura de Camadas
```plaintext
src/
├── main/
│   ├── kotlin/
│   │   └── br/com/weconcept/challenge/
│   │       ├── application/ # 🏗️ Camada de aplicação (casos de uso)
│   │       ├── domain/ # 🎯 Camada de domínio (lógica central de negócio)
│   │       └── infrastructure/ # 🔌 Camada de infraestrutura (adapters)
│   └── resources/ # Arquivos de configuração
```

### 🔌 Portas Principais (Interfaces)
- `PlayerRepositoryPort` - Operações de dados de jogadores
- `TournamentRepositoryPort` - Gerenciamento de torneios
- `ChallengeRepositoryPort` - Rastreamento de execuções de desafios
- `RankingRepositoryPort` - Cálculos de pontuação

### 📚 Modelos de Domínio
- `Player`: Representa participantes do torneio
- `Tournament`: Competição com datas de início/fim
- `Challenge`: Definições de problemas (Fibonacci, Palíndromo, Ordenação)
- `ChallengeExecution`: Registro de tentativas dos jogadores
- `Ranking`: Sistema de rastreamento de pontuações

---

## 🚀 Começando

### 📋 Pré-requisitos
- 🐳 Docker (para implantação em produção)
- ☕ Java 21+
- 🛠️ Gradle 7.4+

### ⚙️ Configuração

#### Modo de Desenvolvimento (banco de dados H2 em memória)
```bash
./gradlew bootRun
```

#### Implantação em Produção (PostgreSQL)
```bash
docker-compose up -d
./gradlew build && java -jar build/libs/*.jar
```

---

## 🧪 Testes

```bash
./gradlew test          # Testes unitários
./gradlew integrationTest # Testes de integração
./gradlew check         # Suite completa de testes com cobertura
```

---

## 📚 Documentação

### 🔍 Documentação da API
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- OpenAPI Spec: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 🛡️ Atributos de Qualidade

### 🔒 Segurança
- Validação de entrada em todas as camadas
- Consultas parametrizadas para prevenir SQL injection
- Controle de acesso baseado em papéis (RBAC) pronto

### ⚡ Performance
- Algoritmos otimizados para desafios
- Processamento em lote para cálculos de pontuação
- Camada de cache preparada para rankings

### 🔄 Manutenibilidade
- Separação clara de responsabilidades
- Cobertura de testes superior a 85%
- Logging abrangente

---

## 📦 Implantação

### Configuração via Docker

```yaml
version: '3.8'
services:
  app:
    image: challenge-platform:latest
    ports:
      - "8080:8080"
    depends_on:
      - postgres
    environment:
      - SPRING_PROFILES_ACTIVE=prod

  postgres:
    image: postgres:13
    environment:
      - POSTGRES_PASSWORD=secret
      - POSTGRES_USER=admin
      - POSTGRES_DB=tournament
    ports:
      - "5432:5432"
```

### Implantação via Script

O projeto inclui um script `run.sh` para facilitar a implantação e configuração do ambiente. Antes de usar o script, garanta que ele tenha permissões de execução:

```bash
chmod +x run.sh
```

#### Comandos de Implantação
- **Configuração do Banco de Dados**: Implante o container do banco de dados:
  ```bash
  ./run.sh database
  ```
- **Modo de Desenvolvimento**: Inicie a aplicação em modo desenvolvimento (requer o banco de dados em execução):
  ```bash
  ./run.sh dev
  ```
- **Modo de Produção**: Containerize e inicie a aplicação em modo produção:
  ```bash
  ./run.sh prod
  ```

Use o comando apropriado para o ambiente desejado.

---

## 📈 Cobertura de Testes

### Percentual de Cobertura

Com a implementação desses testes, a cobertura de testes da aplicação deve atingir aproximadamente **90-95%**. Os principais pontos que ainda podem não estar totalmente cobertos incluem:

- Validações específicas dentro dos serviços
- Casos de borda em certos métodos
- Testes de integração mais complexos

### Passos para Atingir 100% de Cobertura

Para atingir a cobertura completa, os seguintes passos adicionais são necessários:

- Implementar testes para todos os casos de exceção
- Testar todos os cenários de borda
- Adicionar testes de integração que cubram o fluxo completo entre controllers, serviços e repositórios
- Testar todos os métodos de todos os componentes

Os testes fornecidos, no entanto, cobrem a grande maioria das funcionalidades e casos de uso principais da aplicação.

---

## 🤝 Contribuindo

1. Faça um fork do repositório
2. Crie sua branch de funcionalidade (`git checkout -b feature/feature-name`)
3. Faça commit das suas alterações (`git commit -m 'Add some feature'`)
4. Faça push para a branch (`git push origin feature/feature-name`)
5. Abra um Pull Request

---

## 📜 Licença

Licença MIT - Consulte LICENSE para mais detalhes.

---

*Última atualização: 10 de maio de 2025*