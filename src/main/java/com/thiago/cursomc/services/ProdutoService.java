package com.thiago.cursomc.services;

import com.thiago.cursomc.domain.Categoria;
import com.thiago.cursomc.domain.Produto;
import com.thiago.cursomc.repositories.CategoriaRepository;
import com.thiago.cursomc.repositories.ProdutoRepository;
import com.thiago.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repository;

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto find(Integer id) {
		Optional<Produto> produto = repository.findById(id);
		return produto.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id:" + id + ", Tipo: " + Produto.class.getName()));
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

		List<Categoria> categorias = categoriaRepository.findAllById(ids);

		return repository.search(nome, categorias, pageRequest);
	}

	public List<Produto> findAll(){
		return repository.findAll();
	}

}
