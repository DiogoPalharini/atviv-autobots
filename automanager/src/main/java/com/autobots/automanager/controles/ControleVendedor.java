package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Vendedor;
import com.autobots.automanager.repositorios.RepositorioVendedor;

@RestController
@RequestMapping("/vendedores")
public class ControleVendedor {

    @Autowired
    private RepositorioVendedor repositorio;

    @GetMapping("/listar")
    @PreAuthorize("hasRole('MANAGER')") // Apenas MANAGER pode listar vendedores
    public ResponseEntity<List<Vendedor>> listarVendedores() {
        List<Vendedor> vendedores = repositorio.findAll();
        if (vendedores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(vendedores, HttpStatus.OK);
    }

    @PostMapping("/criar")
    @PreAuthorize("hasRole('MANAGER')") // Apenas MANAGER pode criar vendedores
    public ResponseEntity<?> criarVendedor(@RequestBody Vendedor vendedor) {
        repositorio.save(vendedor);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/deletar/{id}")
    @PreAuthorize("hasRole('MANAGER')") // Apenas MANAGER pode deletar vendedores
    public ResponseEntity<?> deletarVendedor(@PathVariable Long id) {
        Optional<Vendedor> vendedor = repositorio.findById(id);
        if (vendedor.isPresent()) {
            repositorio.delete(vendedor.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
