package com.baozistore.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baozistore.api.model.Cliente;
import com.baozistore.api.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	private final ClienteRepository clienteRepository;
	
	public ClienteController(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	//GET - Listar
	@GetMapping
	public List<Cliente> listarTodos() {
		return clienteRepository.findAll();
	}
	
	//GET por ID - Listar por ID
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
		return clienteRepository.findById(id)
				.map(cliente -> ResponseEntity.ok().body(cliente))
				.orElse(ResponseEntity.notFound().build());
	}
	
	//POST - Criar
	@PostMapping
	public Cliente criar(@RequestBody Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	//DELETE - Apagar
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		if (!clienteRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		clienteRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
}
