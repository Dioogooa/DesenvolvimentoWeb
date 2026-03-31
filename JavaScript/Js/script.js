let titulo = document.getElementById("titulo");
titulo.innerText = "Lista Dinâmica com JavaScript";

let tarefas= [];

function adicionarTarefa () {
    let input = document.querySelector("#inputTarefa");
    
    if (input.value === "") {
        alert("Digite uma tarefa válida");
        return;
    }
    tarefas.push(input.value);
    console.log(tarefas)
    
    renderizarLista();
}

function renderizarLista() {
    let lista = document.getElementById("listaTarefas");
    lista.innerHTML = "";

    for(let i=0; i < tarefas.length ; i++) {
        let item = document.createElement("li");
        item.innerText = tarefas[i];

        item.onclick = function() {
            tarefas.splice (i, 1);
            renderizarLista();
        }
        lista.appendChild(item);
    }
    document.getElementById("contador").innerText = "Tarefas: " + tarefas.length;
}

function limparTela() {
    tarefas = [];
    renderizarLista();
}

