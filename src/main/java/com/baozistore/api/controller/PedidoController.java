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
import com.baozistore.api.model.Pedido;
import com.baozistore.api.model.Produto;
import com.baozistore.api.repository.ClienteRepository;
import com.baozistore.api.repository.PedidoRepository;
import com.baozistore.api.repository.ProdutoRepository;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	private final PedidoRepository pedidoRepository;
	private final ClienteRepository clienteRepository;
	private final ProdutoRepository produtoRepository;
	
	public PedidoController (PedidoRepository pedidoRepository, ClienteRepository clienteRepository, ProdutoRepository produtoRepository) {
		this.pedidoRepository = pedidoRepository;
		this.clienteRepository = clienteRepository;
		this.produtoRepository = produtoRepository;
	}
	
	//GET - Listar
	@GetMapping
	public List<Pedido> listarTodos() {
		return pedidoRepository.findAll();
	}
	
	//GET por ID - Listar por ID
	@GetMapping("/{id}")
	public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
		return pedidoRepository.findById(id)
				.map(pedido -> ResponseEntity.ok().body(pedido))
				.orElse(ResponseEntity.notFound().build());
	}

	//POST - Criar
	@PostMapping
	public ResponseEntity<Pedido> criar(@RequestBody Pedido pedido){
		//Validar Cliente
		Cliente cliente = clienteRepository.findById(pedido.getCliente().getId()).orElse(null);
		//Validar Produto
		Produto produto = produtoRepository.findById(pedido.getProduto().getId()).orElse(null);
		
		if (cliente == null || produto == null) {
			return ResponseEntity.badRequest().build();
		}
		
		pedido.setCliente(cliente);
		pedido.setProduto(produto);
		Pedido novoPedido = pedidoRepository.save(pedido);
		
		return ResponseEntity.ok(novoPedido);
	}
	
	//DELETE - Apagar
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		if (!pedidoRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		pedidoRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
