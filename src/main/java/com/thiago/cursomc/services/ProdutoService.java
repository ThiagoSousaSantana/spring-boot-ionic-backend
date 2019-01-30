package com.thiago.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiago.cursomc.domain.Produto;
import com.thiago.cursomc.repositories.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repository;
	
	public Produto buscar(Integer id) {
		Optional<Produto> pOptional = repository.findById(id);
		return pOptional.orElse(null);
	}
}
