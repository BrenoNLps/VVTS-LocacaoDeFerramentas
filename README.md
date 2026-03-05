# Sistema de Locação de Ferramentas
Trabalho Prático – Verificação, Validação e Teste de Software  
IFSP São Carlos – Prof. Dr. Lucas Oliveira

## Descrição
Módulo de aluguel de um sistema de locação de ferramentas.  
O sistema aplica preços progressivos: quanto maior o período de locação, menor o custo por dia, com faixas de preço para diária, semanal e mensal.

## Tecnologias

| Tecnologia | Descrição |
|------------|-----------|
| Java | Linguagem principal do projeto |
| Spring Boot | API REST Back-end |
| ****** | Front-end |

## Práticas

| Prática | Descrição |
|---------|-----------|
| DDD | Modelagem do domínio com agregados, entidades e objetos de valor |
| BDD | Especificação de cenários com linguagem ubíqua |
| TDD | Implementação guiada por testes |
| Testes Funcionais | Validação baseada em critérios funcionais |
| Testes Estruturais e de Mutação | Cobertura e qualidade dos testes |
| Testes de Integração e de Sistema | Validação end-to-end com foco em UI |

## Estrutura DDD

**Agregado (Aggregate Root)**
- `Locacao`

**Entidade (Entity)**
- `Equipamento`

**Objetos de Valor (Value Objects)**
- `PrecosProgressivos` — encapsula os valores de diária, semanal e mensal
- `Garantia` — encapsula o valor e as regras da garantia cobrada na locação

**Enum**
- `StatusDaFerramenta`: `DISPONIVEL`, `ALUGADO`, `EM_MANUTENCAO`

**Repositório (Repository)**
- `LocacaoRepository`

**Serviços de Aplicação (Application Services)**
- `AlugarService`
- `RegistrarDevolucaoService`
- `CancelarAluguelService`
- `ConsultarValorAluguelService`
- `EnviarParaManutencaoService`
- `RetornarDaManutencaoService`

## Equipe

| Membro | GitHub |
|--------|--------|
| Breno Nascimento Lopes | [@brenonlps](https://github.com/brenonlps) |
| Lucas Jundi Hikazudani | [@usuario](https://github.com/usuario) |
| Maria Clara Passareli Alves | [@usuario](https://github.com/usuario) |
