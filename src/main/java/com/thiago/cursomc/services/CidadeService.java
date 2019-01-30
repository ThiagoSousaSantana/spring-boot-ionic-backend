package com.thiago.cursomc.services;

import com.thiago.cursomc.domain.Categoria;
import com.thiago.cursomc.domain.Cidade;
import com.thiago.cursomc.repositories.CategoriaRepository;
import com.thiago.cursomc.repositories.CidadeRepository;
import com.thiago.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository repository;
	
	public Cidade buscar(Integer id) {
		Optional<Cidade> cidade = repository.findById(id);
		return cidade.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id:" + id + ", Tipo: " + Cidade.class.getName()));
	}
}
