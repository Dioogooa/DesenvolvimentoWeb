console.log("INICIOU O ARQUIVO");

const express = require("express");
const cors = require("cors");

const app = express();
const PORT = 3000;

app.use(cors());
app.use(express.json());

let pets = [
    {
        id: 1,
        nome: "Rex",
        especie: "Cachorro",
        raca: "Labrador",
        subraca: "",
        idade: 4,
        corPelo: "Amarelo",
        tipoSanguineo: "DEA 1.1+",
        detalhesNascimento: "Sem complicações registradas",
        tutor: "Carlos Silva"
    },
    {
        id: 2,
        nome: "Mimi",
        especie: "Gato",
        raca: "Siamês",
        subraca: "",
        idade: 2,
        corPelo: "Branco e cinza",
        tipoSanguineo: "Tipo A",
        detalhesNascimento: "Resgatada ainda filhote",
        tutor: "Ana Souza"
    }
];

let proximoId = 3;

// Rota inicial
app.get("/", (req, res) => {
    res.json({
        mensagem: "API Petshop em execução",
        endpoints: [
            "GET /pets",
            "GET /pets/:id",
            "POST /pets",
            "PUT /pets/:id",
            "DELETE /pets/:id"
        ]
    });
});

// Listar todos os pets
app.get("/pets", (req, res) => {
    res.status(200).json(pets);
});

// Buscar pet por ID
app.get("/pets/:id", (req, res) => {
    const id = Number(req.params.id);
    const pet = pets.find(p => p.id === id);

    if (!pet) {
        return res.status(404).json({
            erro: "Pet não encontrado"
        });
    }

    res.status(200).json(pet);
});

// Criar novo pet
app.post("/pets", (req, res) => {
    const {
        nome,
        especie,
        raca,
        subraca,
        idade,
        corPelo,
        tipoSanguineo,
        detalhesNascimento,
        tutor
    } = req.body;

    if (!nome || !especie || !raca || !tutor) {
        return res.status(400).json({
            erro: "Campos obrigatórios: nome, especie, raca e tutor"
        });
    }

    const novoPet = {
        id: proximoId++,
        nome,
        especie,
        raca,
        subraca: subraca || "",
        idade: idade || null,
        corPelo: corPelo || "",
        tipoSanguineo: tipoSanguineo || "",
        detalhesNascimento: detalhesNascimento || "",
        tutor
    };

    pets.push(novoPet);

    res.status(201).json(novoPet);
});

// Atualizar pet inteiro
app.put("/pets/:id", (req, res) => {
    const id = Number(req.params.id);
    const indice = pets.findIndex(p => p.id === id);

    if (indice === -1) {
        return res.status(404).json({
            erro: "Pet não encontrado"
        });
    }

    const petAtualizado = {
        id,
        nome: req.body.nome,
        especie: req.body.especie,
        raca: req.body.raca,
        subraca: req.body.subraca || "",
        idade: req.body.idade || null,
        corPelo: req.body.corPelo || "",
        tipoSanguineo: req.body.tipoSanguineo || "",
        detalhesNascimento: req.body.detalhesNascimento || "",
        tutor: req.body.tutor
    };

    pets[indice] = petAtualizado;

    res.status(200).json(petAtualizado);
});

// Atualizar parcialmente um pet
app.patch("/pets/:id", (req, res) => {
    const id = Number(req.params.id);
    const pet = pets.find(p => p.id === id);

    if (!pet) {
        return res.status(404).json({
            erro: "Pet não encontrado"
        });
    }

    Object.assign(pet, req.body);

    res.status(200).json(pet);
});

// Remover pet
app.delete("/pets/:id", (req, res) => {
    const id = Number(req.params.id);
    const indice = pets.findIndex(p => p.id === id);

    if (indice === -1) {
        return res.status(404).json({
            erro: "Pet não encontrado"
        });
    }

    const petRemovido = pets.splice(indice, 1);

    res.status(200).json({
        mensagem: "Pet removido com sucesso",
        pet: petRemovido[0]
    });
});

app.listen(PORT, () => {
    console.log(`API Petshop rodando em http://localhost:${PORT}`);
});