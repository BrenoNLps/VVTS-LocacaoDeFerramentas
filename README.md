# Sistema de Locação de Ferramentas
Trabalho Prático – Verificação, Validação e Teste de Software  
IFSP São Carlos – Prof. Dr. Lucas Oliveira

## Descrição
Módulo de aluguel de um sistema de locação de ferramentas.  
O sistema aplica preços progressivos: quanto maior o período de locação, menor o custo por dia, com faixas de preço para diária, semanal e mensal.

## Procedimento de locação de ferramenta

Cada equipamento disponível para locação possui uma placa de identificação fixada, permitindo que ele seja corretamente gerenciado no sistema.

Para realizar a locação de uma ferramenta, o atendente conversa com o cliente para verificar se o equipamento que ele deseja alugar é compatível com o uso pretendido. Caso seja compatível e o cliente tenha interesse em alugá-lo, uma garantia é fornecida. Essa garantia pode ser uma nota promissória, caso o cliente possua score positivo. Caso contrário, ele pode optar por realizar uma reserva no cartão de crédito com o valor do equipamento a ser locado ou deixar o valor do equipamento em dinheiro como garantia.

Para efetivar a locação, o atendente testa o equipamento antes de alugá-lo, realiza o cadastro do cliente, coleta a garantia, registra a locação no sistema com a data atual, solicita que o cliente assine o contrato de locação e, então, a ferramenta é liberada para o cliente.

Quando o cliente retorna para devolver o equipamento, a ferramenta alugada é testada. Em seguida, o atendente informa o valor total da locação, o cliente realiza o pagamento do aluguel e o atendente registra a devolução no sistema, para que o equipamento fique disponível para uma nova locação. Após isso, a garantia fornecida pelo cliente é devolvida.

Caso, logo após a locação da ferramenta, o cliente desista de alugar o equipamento, o atendente registra o cancelamento da locação para que o valor não seja cobrado e a ferramenta volte a ficar disponível para locação.

Quando o atendente testa o equipamento e verifica que ele não está em boas condições, entra em contato com uma empresa de manutenção solicitando sua retirada, registra no sistema que a ferramenta foi enviada para manutenção e, assim, ela fica indisponível para locação.

Quando a ferramenta retorna da manutenção, o atendente testa novamente o equipamento. Ao verificar que está em boas condições, registra no sistema o retorno da manutenção para que o equipamento volte a estar disponível para locação.

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

**Repositório (Repository)**
- `LocacaoRepository`

**Serviços de Aplicação (Application Services)**
- `AlugarService`
- `RegistrarDevolucaoService`
- `CancelarAluguelService`
- `ConsultarValorAluguelService`
- `EnviarParaManutencaoService`
- `RetornarDaManutencaoService`

**Enum**
- `StatusDaFerramenta`: `DISPONIVEL`, `ALUGADO`, `EM_MANUTENCAO`
- `StatusDaLocacao`: `DISPONIVEL`, `CANCELADA`, `FINALIZADA`

## Equipe

| Membro | GitHub |
|--------|--------|
| Breno Nascimento Lopes | [@brenonlps](https://github.com/brenonlps) |
| Lucas Jundi Hikazudani | [@hikazudani](https://github.com/hikazudani) |
| Maria Clara Passareli Alves | [@passareliscoding](https://github.com/passareliscoding) |
