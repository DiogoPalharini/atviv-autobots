package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Gerente;
import com.autobots.automanager.repositorios.RepositorioGerente;

@RestController
@RequestMapping("/gerentes")
public class ControleGerente {

    @Autowired
    private RepositorioGerente repositorio;

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN')") // Apenas ADMIN pode listar gerentes
    public ResponseEntity<List<Gerente>> listarGerentes() {
        List<Gerente> gerentes = repositorio.findAll();
        if (gerentes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(gerentes, HttpStatus.OK);
    }

    @PostMapping("/criar")
    @PreAuthorize("hasRole('ADMIN')") // Apenas ADMIN pode criar gerentes
    public ResponseEntity<?> criarGerente(@RequestBody Gerente gerente) {
        repositorio.save(gerente);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/deletar/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Apenas ADMIN pode deletar gerentes
    public ResponseEntity<?> deletarGerente(@PathVariable Long id) {
        Optional<Gerente> gerente = repositorio.findById(id);
        if (gerente.isPresent()) {
            repositorio.delete(gerente.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
