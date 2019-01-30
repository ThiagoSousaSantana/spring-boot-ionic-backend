package com.thiago.cursomc.resources;

import com.thiago.cursomc.domain.Cidade;
import com.thiago.cursomc.domain.Estado;
import com.thiago.cursomc.services.CidadeService;
import com.thiago.cursomc.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/estados")
public class EstadoResource {
	
	@Autowired
	private EstadoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Estado estado = service.buscar(id);
		return ResponseEntity.ok().body(estado);
	}
	
}
