const API_BASE_URL = 'http://localhost:8081';

const form = document.getElementById('enderecoForm');
const cepInput = document.getElementById('cep');
const ruaInput = document.getElementById('rua');
const bairroInput = document.getElementById('bairro');
const cidadeInput = document.getElementById('cidade');
const mensagemContainer = document.getElementById('mensagemContainer');
const resultadoContainer = document.getElementById('resultadoContainer');

function mostrarMensagem(texto, tipo) {
    const cor = tipo === 'erro' ? 'danger' : tipo === 'sucesso' ? 'success' : 'info';
    mensagemContainer.innerHTML = `
        <div class="alert alert-${cor} alert-dismissible fade show" role="alert">
            ${texto}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    `;
}

function setLoading(botao, carregando) {
    botao.disabled = carregando;
    const icon = botao.querySelector('i');
    if (icon) {
        icon.className = carregando ? 'spinner-border spinner-border-sm' : icon.dataset.original;
        if (!carregando) icon.dataset.original = icon.dataset.original || icon.className;
    }
}

function exibirTabela(enderecos) {
    if (!enderecos || enderecos.length === 0) {
        resultadoContainer.innerHTML = '<div class="alert alert-warning">Nenhum endereço encontrado.</div>';
        return;
    }

    const linhas = enderecos.map((e, i) => `
        <tr>
            <td>${i + 1}</td>
            <td>${e.cep}</td>
            <td>${e.rua || '-'}</td>
            <td>${e.bairro || '-'}</td>
            <td>${e.cidade || '-'}</td>
        </tr>
    `).join('');

    resultadoContainer.innerHTML = `
        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>#</th>
                        <th>CEP</th>
                        <th>Rua</th>
                        <th>Bairro</th>
                        <th>Cidade</th>
                    </tr>
                </thead>
                <tbody>${linhas}</tbody>
            </table>
        </div>
    `;
}

async function buscarPorCep() {
    const cep = cepInput.value.trim();
    if (!cep) return mostrarMensagem('Digite um CEP.', 'erro');

    const btn = document.getElementById('btnBuscar');
    setLoading(btn, true);

    try {
        const res = await fetch(`${API_BASE_URL}/adress/${cep}`);
        if (res.ok) {
            const end = await res.json();
            ruaInput.value = end.rua || '';
            bairroInput.value = end.bairro || '';
            cidadeInput.value = end.cidade || '';
            mostrarMensagem('Endereço encontrado!', 'sucesso');
        } else if (res.status === 404) {
            mostrarMensagem('CEP não encontrado.', 'erro');
        }
    } catch {
        mostrarMensagem('Erro ao conectar na API.', 'erro');
    } finally {
        setLoading(btn, false);
    }
}

async function criarEndereco() {
    const cep = cepInput.value.trim();
    const rua = ruaInput.value.trim();
    const bairro = bairroInput.value.trim();
    const cidade = cidadeInput.value.trim();

    if (!cep || !rua || !bairro || !cidade) {
        return mostrarMensagem('Preencha todos os campos.', 'erro');
    }

    const btn = document.getElementById('btnCriar');
    setLoading(btn, true);

    try {
        const res = await fetch(`${API_BASE_URL}/adress`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ cep, rua, bairro, cidade })
        });

        if (res.ok) {
            mostrarMensagem('Endereço criado!', 'sucesso');
            cepInput.value = ruaInput.value = bairroInput.value = cidadeInput.value = '';
            listarTodos();
        }
    } catch {
        mostrarMensagem('Erro ao criar endereço.', 'erro');
    } finally {
        setLoading(btn, false);
    }
}

async function apagarPorCep() {
    const cep = cepInput.value.trim();
    if (!cep) return mostrarMensagem('Digite um CEP para apagar.', 'erro');
    if (!confirm(`Apagar endereço com CEP ${cep}?`)) return;

    const btn = document.getElementById('btnApagar');
    setLoading(btn, true);

    try {
        const res = await fetch(`${API_BASE_URL}/adress/${cep}`, { method: 'DELETE' });
        if (res.ok || res.status === 204) {
            mostrarMensagem('Endereço apagado!', 'sucesso');
            cepInput.value = ruaInput.value = bairroInput.value = cidadeInput.value = '';
            listarTodos();
        } else if (res.status === 404) {
            mostrarMensagem('CEP não encontrado.', 'erro');
        }
    } catch {
        mostrarMensagem('Erro ao apagar.', 'erro');
    } finally {
        setLoading(btn, false);
    }
}

async function listarTodos() {
    const btn = document.getElementById('btnListar');
    setLoading(btn, true);

    try {
        const res = await fetch(`${API_BASE_URL}/adresses`);
        if (res.ok) {
            const enderecos = await res.json();
            exibirTabela(enderecos);
        }
    } catch {
        mostrarMensagem('Erro ao listar.', 'erro');
    } finally {
        setLoading(btn, false);
    }
}

cepInput.addEventListener('input', e => e.target.value = e.target.value.replace(/\D/g, '').slice(0, 8));
cepInput.addEventListener('keypress', e => { if (e.key === 'Enter') { e.preventDefault(); buscarPorCep(); } });
document.getElementById('btnBuscar').addEventListener('click', buscarPorCep);
document.getElementById('btnCriar').addEventListener('click', criarEndereco);
document.getElementById('btnApagar').addEventListener('click', apagarPorCep);
document.getElementById('btnListar').addEventListener('click', listarTodos);

window.addEventListener('DOMContentLoaded', listarTodos);