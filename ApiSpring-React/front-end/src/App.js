import { useEffect, useState } from "react";
import "./App.css";

const initialForm = {
  cep: " ",
  rua: " ",
  bairro: " ",
  cidade: " ", 
};

function App() {
  const [form, setForm] = useState(initialForm);
  const [adresses, setAdresses] = useState([]);
  const [message, setMessage] = useState("");
  const [count, setCount] = useState(0);
  
  useEffect (() => {
    adressCount();
    listAdresses();
  }, []);

  function handleChange(event) {
    const {name, value} = event.target;

    setForm({
      ...form,
      [name]: value
    });
  }

  async function listAdresses() {
    const response = await fetch("/adresses");
    const data = await response.json();
    setAdresses(data);
  }

  async function adressCount() {
    const response = await fetch("/adress/count")
    const data = await response.json();
    setCount(data);
  }

  async function findByCep() {
    const response = await fetch(`/adresses/${form.cep}`);

    if (response.status === 404) {
      setMessage("Endereço não encontrado.");
      return;
    }
  }

  async function findByCity() {
    const response = await fetch(`/adresses/cidade/${form.cidade}`);

    if (response.status === 404) {
      setMessage("Endereço não encontrado");
      return;
    } 

    const data = await response.json();
    setAdresses(data);
    setMessage("Endereços encontrados")
  }

  async function createAddress() {
    await fetch("/adress", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(form)
    });

    setForm(initialForm);
    setMessage("Endereco cadastrado com sucesso.");
    listAdresses();
  }

  async function updateAdress() {
    await fetch(`/adress/${form.cep}`, {
      method: "PUT" ,
      headers: {
        "Content-Type" : "application/json"
      },
      body: JSON.stringify(form)
    });

    setForm(initialForm);
    setMessage("Endereco atulizado com sucesso!");
    listAdresses();
  }

  async function deleteAdress() {
    await fetch(`/adress/${form.cep}`, {
      method: "DELETE"
    });

    setMessage("Endereco removido com sucesso.");
    setForm(initialForm);
    listAdresses();
  }

  return (
    <main className="container">
      <h1>Integracao React + Spring Boot</h1>

      <label>CEP</label>
      <input
      name="cep"
      value={form.cep}
      onChange={handleChange}
      />

      <label>Rua</label>
      <input
      name="rua"
      value={form.rua}
      onChange={handleChange}
      />

      <label>Cidade</label>
      <input
      name="cidade"
      value={form.cidade}
      onChange={handleChange}
      />

      <button onClick={findByCep}>Buscar por CEP</button>
      <button onClick={createAddress}>Criar Novo</button>
      <button onClick={deleteAdress}>Apagar por CEP</button>
      <button onClick={updateAdress}>Atualizar por CEP</button>
      <button onClick={findByCity}>Buscar por Cidade</button>


      <h3>Total de endereços encontrados: {count}</h3>
      <table>
        <tbody>
          {adresses.map((adress) => (
            <tr key={adress.cep}>
              <td>{adress.cep}</td>
              <td>{adress.rua}</td>
              <td>{adress.cidade}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </main>
  );

}

export default App;