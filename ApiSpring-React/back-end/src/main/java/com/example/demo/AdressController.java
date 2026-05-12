package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class AdressController {

    private final List<Adress> adresses = new ArrayList<>(
            Arrays.asList(
                    new Adress("74805300", "Rua 32", "Jardim goias", "Goiania"),
                    new Adress("74800350", "Rua 34", "Jardim Goias", "Beaga"),
                    new Adress("74830370", "Rua 28", "Jardim Goias", "Minas")
            )
    );

    @GetMapping ("/hello")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/adresses")
    public List<Adress> getAdress() {
        return this.adresses;
    }

    @GetMapping("/adress/{cep}")
    public ResponseEntity<Adress> getAdress(@PathVariable String cep) {
        for (Adress adress : this.adresses) {
            if (adress.getCep().equals(cep)) {
                return ResponseEntity.ok(adress);
            }
        }
        return ResponseEntity.notFound().build();
    };

    @GetMapping("adress/count")
    public int getAdressCount() {
        return this.adresses.size();
    }

    @GetMapping("/adresses/cidade/{cidade}")
    public ResponseEntity<List<Adress>> getCityList(@PathVariable String cidade){
        List<Adress> Adresses = new ArrayList<>();
        for (Adress adress : this.adresses) {
            if (adress.getCidade().equals(cidade)) {
                Adresses.add(adress);
            }
        }

        if (!Adresses.isEmpty()) {
            return ResponseEntity.ok(Adresses);
        }
        return ResponseEntity.notFound().build();
    };

    @PutMapping("/adress/{cep}")
    public ResponseEntity<Adress> putAdress(@PathVariable String cep, @RequestBody Adress adress) {
        for (Adress adress1 : this.adresses) {
            if (adress1.getCep().equals(cep)) {
                adress1.setRua(adress.getRua());
                adress.setBairro(adress.getBairro());
                adress.setCidade(adress.getCidade());
                return ResponseEntity.ok(adress);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/adress/{cep}")
    public ResponseEntity<Void> deleteAdress(@PathVariable String cep) {
        for (Adress adress : this.adresses) {
            if (adress.getCep().equals(cep)) {
                this.adresses.remove(adress);
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.notFound().build();
    };

    @PostMapping("/adress")
    public void addAdress(@RequestBody Adress adress) {
        this.adresses.add(adress);
    }
}
