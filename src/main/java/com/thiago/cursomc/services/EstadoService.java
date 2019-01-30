package com.thiago.cursomc.services;

import com.thiago.cursomc.domain.Estado;
import com.thiago.cursomc.repositories.EstadoRepository;
import com.thiago.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository repository;
	
	public Estado buscar(Integer id) {
		Optional<Estado> estado = repository.findById(id);
		return estado.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id:" + id + ", Tipo: " + Estado.class.getName()));
	}
}
