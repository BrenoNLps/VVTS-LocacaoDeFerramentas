export default function Register() {
  return (
    <div>
      <div>
        <h1>Cadastro</h1>
        <form>
          <input type="text" placeholder="Nome" />
          <input type="text" placeholder="Sobrenome" />
          <input type="email" placeholder="Email" />
          <input type="password" placeholder="Senha" />
          <button type="submit">Cadastrar</button>
        </form>
      </div>
    </div>
  );
}
