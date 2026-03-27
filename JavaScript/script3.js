let titulo = document.getElementById("titulo");
titulo.innerText = "Lista Dinâmica com JavaScript";

function adicionarTarefa(){
let input = document.getElementById("inputTarefa");
let lista = document.getElementById("listaTarefas");
let novaTarefa = document.createElement("li");
novaTarefa.innerText = input.value;
lista.appendChild(novaTarefa);
}