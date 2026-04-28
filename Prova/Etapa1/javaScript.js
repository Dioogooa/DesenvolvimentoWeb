let tarefas = [];

function adicionar() {
    let texto = document.getElementById('tarefaInput').value;
    if (texto === '') return;

    tarefas.push({ id: Date.now(), nome: texto, ok: false });
    document.getElementById('tarefaInput').value = '';
    atualizar();
}

function remover(id) {
    let nova = [];
    for (let t of tarefas) if (t.id !== id) nova.push(t);
    tarefas = nova;
    atualizar();
}

function concluir(id) {
    for (let t of tarefas) if (t.id === id) t.ok = !t.ok;
    atualizar();
}

function atualizar() {
    let div = document.getElementById('listaTarefas');

    if (tarefas.length === 0) {
        div.innerHTML = '<p class="text-center text-muted">Nenhuma tarefa</p>';
        return;
    }

    let html = '';
    for (let t of tarefas) {
        let estilo = t.ok ? 'bg-success bg-opacity-25 text-decoration-line-through' : '';
        let botao = t.ok ? 'Desfazer' : 'Concluir';
        let cor = t.ok ? 'btn-warning' : 'btn-success';

        html += `<div class="d-flex justify-content-between align-items-center p-2 mb-2 border rounded ${estilo}">
                    <span>${t.nome}</span>
                    <div>
                        <button class="btn btn-sm ${cor} me-1" onclick="concluir(${t.id})">${botao}</button>
                        <button class="btn btn-sm btn-danger" onclick="remover(${t.id})">Remover</button>
                    </div>
                </div>`;
    }
    div.innerHTML = html;
}

document.getElementById('adicionarBtn').onclick = adicionar;