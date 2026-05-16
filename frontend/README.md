# Frontend — VVTS Locação de Ferramentas

Interface web  React + Vite.

## Pré-requisitos

- Node.js 18+
- Back-end da aplicação rodando em `http://localhost:8080`

## Configuração

1. Acesse a pasta do frontend:
   ```bash
   cd frontend
   ```

2. Instale as dependências:
   ```bash
   npm install
   ```

3. Crie um arquivo `.env` na pasta `frontend` com o seguinte conteúdo:
   ```
   VITE_API_URL=http://localhost:8080/api/v1
   ```

   - Se o back-end estiver rodando em uma porta diferente, altere a porta.

## Execute

```bash
npm run dev
```

- A aplicação estará disponível em `http://localhost:5173` (porta padrão do Vite). 
- Se a porta já estiver em uso, o Vite usará outra.
