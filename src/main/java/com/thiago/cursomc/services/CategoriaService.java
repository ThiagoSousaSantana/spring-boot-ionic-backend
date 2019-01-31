package com.thiago.cursomc.services;

import java.util.Optional;

import com.thiago.cursomc.services.exceptions.DataIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.thiago.cursomc.domain.Categoria;
import com.thiago.cursomc.repositories.CategoriaRepository;
import com.thiago.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;
	
	public Categoria find(Integer id) {
		Optional<Categoria> categoria = repository.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id:" + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria insert(Categoria obj){
		obj.setId(null);
		return repository.save(obj);
	}

	public Categoria update(Categoria obj){
		find(obj.getId());
		return repository.save(obj);
	}

	public void delete(Integer id) {
		try {
			repository.delete(find(id));
		}catch (DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possivel excluir categoria que contem produtos!");
		}
	}
}
