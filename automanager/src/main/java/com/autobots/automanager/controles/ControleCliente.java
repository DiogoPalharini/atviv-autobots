package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.repositorios.RepositorioCliente;

@RestController
@RequestMapping("/clientes")
public class ControleCliente {

	@Autowired
	private RepositorioCliente repositorio;

	@GetMapping("/listar")
	@PreAuthorize("hasRole('ADMIN')") // Apenas ADMIN pode acessar
	public ResponseEntity<List<Cliente>> listarClientes() {
		List<Cliente> clientes = repositorio.findAll();
		if (clientes.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(clientes, HttpStatus.OK);
	}

	@PostMapping("/criar")
	@PreAuthorize("hasRole('ADMIN')") // Apenas ADMIN pode criar
	public ResponseEntity<?> criarCliente(@RequestBody Cliente cliente) {
		repositorio.save(cliente);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
