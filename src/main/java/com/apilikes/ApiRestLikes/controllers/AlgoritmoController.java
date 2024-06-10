package com.apilikes.ApiRestLikes.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.apilikes.ApiRestLikes.Services.AlgoritmoService;

@RestController
@RequestMapping("/api/algoritmo")
public class AlgoritmoController {

    @Autowired
    private AlgoritmoService algoritmoService;

    @PostMapping("/iniciar")
    public ResponseEntity<String> iniciarAlgoritmo() {
        algoritmoService.iniciarAlgoritmo();
        return ResponseEntity.ok("Algoritmo iniciado");
    }

    @PostMapping("/recibirMarcador")
    public ResponseEntity<String> recibirMarcador(@RequestParam String from) {
        algoritmoService.recibirMarcador(from);
        return ResponseEntity.ok("Marcador recibido");
    }

    @PostMapping("/recibirMensaje")
    public ResponseEntity<String> recibirMensaje(@RequestParam String mensaje, @RequestParam String from) {
        algoritmoService.recibirMensaje(mensaje, from);
        return ResponseEntity.ok("Mensaje recibido");
    }
}