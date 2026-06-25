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

import com.baozistore.api.model.Produto;
import com.baozistore.api.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	private final ProdutoRepository produtoRepository;
	
	public ProdutoController(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
	
	//GET - Listar
	@GetMapping
	public List<Produto> listarTodos() {
		return produtoRepository.findAll();
	}
	
	//GET por ID - Listar por ID
	@GetMapping("/{id}")
	public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
		return produtoRepository.findById(id)
				.map(produto -> ResponseEntity.ok().body(produto))
				.orElse(ResponseEntity.notFound().build());
	}
	
	//POST - Criar
	@PostMapping
	public Produto criar(@RequestBody Produto produto) {
		return produtoRepository.save(produto);
	}

	//DELETE - Apagar
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		if (!produtoRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		produtoRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
}
