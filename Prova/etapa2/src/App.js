import React, { useState } from 'react';
import './App.css';

function App() {
  
  const [tarefas, setTarefas] = useState([]);
  
  const [textoTarefa, setTextoTarefa] = useState('');

  const adicionarTarefa = () => {
    if (textoTarefa.trim() === '') return;
    
    const novaTarefa = {
      id: Date.now(),
      nome: textoTarefa,
      ok: false
    };
    
    setTarefas([...tarefas, novaTarefa]);
    setTextoTarefa(''); 
  };

  const removerTarefa = (id) => {
    const tarefasFiltradas = tarefas.filter(tarefa => tarefa.id !== id);
    setTarefas(tarefasFiltradas);
  };

  const toggleConcluir = (id) => {
    const tarefasAtualizadas = tarefas.map(tarefa => {
      if (tarefa.id === id) {
        return { ...tarefa, ok: !tarefa.ok };
      }
      return tarefa;
    });
    setTarefas(tarefasAtualizadas);
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      adicionarTarefa();
    }
  };

  return (
    <div className="container py-5">
      <div className="row justify-content-center">
        <div className="col-md-6">
          <div className="card shadow">
            <div className="card-body p-4">
              <h1 className="text-center mb-4">Lista de Tarefas</h1>
              
              <div className="input-group mb-4">
                <input 
                  type="text" 
                  className="form-control" 
                  placeholder="Digite uma tarefa..."
                  value={textoTarefa}
                  onChange={(e) => setTextoTarefa(e.target.value)}
                  onKeyPress={handleKeyPress}
                />
                <button 
                  className="btn btn-primary" 
                  onClick={adicionarTarefa}
                >
                  Adicionar
                </button>
              </div>
              
              <div>
                {tarefas.length === 0 ? (
                  <p className="text-center text-muted">Nenhuma tarefa</p>
                ) : (
                  tarefas.map(tarefa => {
                    const estilo = tarefa.ok 
                      ? 'bg-success bg-opacity-25 text-decoration-line-through' 
                      : '';
                    const botaoTexto = tarefa.ok ? 'Desfazer' : 'Concluir';
                    const corBotao = tarefa.ok ? 'btn-warning' : 'btn-success';
                    
                    return (
                      <div 
                        key={tarefa.id}
                        className={`d-flex justify-content-between align-items-center p-2 mb-2 border rounded ${estilo}`}
                      >
                        <span>{tarefa.nome}</span>
                        <div>
                          <button 
                            className={`btn btn-sm ${corBotao} me-1`}
                            onClick={() => toggleConcluir(tarefa.id)}
                          >
                            {botaoTexto}
                          </button>
                          <button 
                            className="btn btn-sm btn-danger"
                            onClick={() => removerTarefa(tarefa.id)}
                          >
                            Remover
                          </button>
                        </div>
                      </div>
                    );
                  })
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;