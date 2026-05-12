# Mutantes Equivalentes

| Classe | Linha | ID do Mutante | Tipo de Mutação | Justificativa de Equivalência |
|--------|-------|---------------|-----------------|-------------------------------|
| Customer | 14 | 1 | `replaced return value with "" for getName()` | O nome do cliente não é usado em nenhuma regra de negócio dos serviços testados, então retornar `""` produz o mesmo comportamento observável. |
| Customer | 18 | 1 | `replaced return value with "" for getId()` | O `id` do cliente não é acessado via getter em nenhum serviço testado, portanto o mutante não altera nenhum resultado dos testes. |
| Rental | 86 | 1 | `replaced return value with Collections.emptyList for getTools()` | Os métodos `finalize()` e `cancel()` iteram diretamente sobre o campo privado `tools`, sem passar pelo `getTools()`. Como nenhum teste chama esse getter, o mutante é indistinguível do original. |
| Rental | 90 | 1 | `replaced return value with null for getStartDate()` | A data de início é usada internamente pelos métodos da própria classe via campo privado. O getter `getStartDate()` não é invocado por nenhum serviço testado. |
| Tool | 46 | 1 | `replaced return value with Collections.emptyList for getMaintenanceHistory()` | Nenhum teste verifica o conteúdo do histórico de manutenção. Os testes dos serviços relacionados verificam apenas o status da ferramenta, não as entradas do histórico. |
| MaintenanceRecord | 25 | 1 | `negated conditional` em `isOpen()` | O método `isOpen()` não é invocado em nenhum dos testes existentes, tornando essa mutação equivalente ao original dentro do escopo da suíte atual. |
| MaintenanceRecord | 25 | 2 | `replaced boolean return with true for isOpen()` | O método `isOpen()` não é chamado por nenhum teste, então retornar `true` sempre não altera nenhum resultado observável da suíte. |
| MaintenanceRecord | 26 | 1 | `replaced boolean return with true for isClosed()` | O método `isClosed()` não é invocado por nenhum teste existente, portanto qualquer mutação nele não altera os resultados observáveis. |
| MaintenanceRecord | 26 | 2 | `replaced boolean return with false for isClosed()` | O método `isClosed()` não é chamado por nenhum teste, então retornar `false` sempre não muda nenhum comportamento observável da suíte. |
