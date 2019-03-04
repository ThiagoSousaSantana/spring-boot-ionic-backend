package com.thiago.cursomc.resources;

import com.thiago.cursomc.domain.Produto;
import com.thiago.cursomc.dto.CategoriaDTO;
import com.thiago.cursomc.dto.ProdutoDTO;
import com.thiago.cursomc.resources.utils.URL;
import com.thiago.cursomc.services.ProdutoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		Produto produto = service.find(id);
		return ResponseEntity.ok().body(produto);
	}

	@ApiOperation(value = "Retorna uma pagina de Produtos")
	@RequestMapping(method=RequestMethod.GET, value = "/page")
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> categoriaList = URL.decodeIntList(categorias);

		Page<ProdutoDTO> page1 =
				service.search(nomeDecoded, categoriaList, page, linesPerPage, orderBy, direction).map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(page1);
	}

	@ApiOperation(value = "Retorna uma lista de produtos")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Produto>> findAll() {
		List<Produto> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
}
