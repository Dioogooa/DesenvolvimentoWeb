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
                    new Adress("74805300", "Rua 32", "Jardim goias", "Goiânia"),
                    new Adress("74800350", "Rua 34", "Jardim Goias", "Goiânia"),
                    new Adress("74830370", "Rua 28", "Jardim Goias", "Goiânia")
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
