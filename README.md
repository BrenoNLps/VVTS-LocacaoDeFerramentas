# Sistema de Locação de Ferramentas
Trabalho Prático – Verificação, Validação e Teste de Software
IFSP São Carlos – Prof. Dr. Lucas Oliveira

## Descrição
Módulo de aluguel de um sistema de locação de ferramentas.
O sistema aplica preços progressivos: quanto maior o período de locação, menor o custo por dia, com faixas de preço para diária, semanal e mensal.

## Procedimentos
- Cliente previamente cadastrado escolhe ferramenta disponível
- Pode consultar o valor estimado da locação informando as ferramentas e o período desejado
- Ao registrar a locação, o cliente escolhe o tipo de garantia (promissória, reserva em cartão ou depósito em dinheiro)
- A locação é registrada com a data atual como data de início
- A data de devolução é registrada apenas na finalização da locação, que também calcula o valor total a pagar
- A locação pode ser cancelada sem registrar data de devolução
- Ferramenta pode ser enviada para manutenção (fica indisponível durante o processo)
- Quando a ferramenta retorna da manutenção, o retorno é registrado e ela volta a ficar disponível

## Como executar

### Pré-requisitos
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) instalado

### Passos

1. Clone o repositório:
```bash
git clone https://github.com/brenonlps/VVTS-LocacaoDeFerramentas.git
cd VVTS-LocacaoDeFerramentas
```

2. Suba os containers:
```bash
docker-compose up --build
```
> Na primeira execução pode demorar alguns minutos enquanto baixa as dependências.

3. Acesse no navegador:
- **Frontend:** http://localhost:3000
- **API:** http://localhost:8080

Para parar: `Ctrl+C` ou `docker-compose down`

> O banco de dados é criado automaticamente na primeira execução e persiste entre reinicializações.

## Tecnologias

| Tecnologia | Descrição |
|------------|-----------|
| Java 21 | Linguagem principal do back-end |
| Spring Boot | API REST |
| SQLite | Banco de dados |
| React + Vite | Front-end |
| Nginx | Servidor do front-end em produção |
| Docker | Containerização e execução local |

## Práticas

| Prática | Descrição |
|---------|-----------|
| DDD | Modelagem do domínio com agregados, entidades e objetos de valor |
| BDD | Especificação de cenários com linguagem ubíqua |
| TDD | Implementação guiada por testes |
| Testes Funcionais | Validação baseada em critérios funcionais |
| Testes Estruturais e de Mutação | Cobertura e qualidade dos testes |
| Testes de Integração e de Sistema | Validação end-to-end com foco em UI |

## Equipe

| Membro | GitHub |
|--------|--------|
| Breno Nascimento Lopes | [@brenonlps](https://github.com/brenonlps) |


## Ex Colaboradores

| Membro | GitHub |
|--------|--------|
| Lucas Jundi Hikazudani | [@hikazudani](https://github.com/hikazudani) |
| Maria Clara Passareli Alves | [@passareliscoding](https://github.com/passareliscoding) |