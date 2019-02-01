package com.thiago.cursomc.services;

import com.thiago.cursomc.domain.Cliente;
import com.thiago.cursomc.domain.Cliente;
import com.thiago.cursomc.dto.ClienteDTO;
import com.thiago.cursomc.repositories.ClienteRepository;
import com.thiago.cursomc.services.exceptions.DataIntegrityException;
import com.thiago.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> cliente = repository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id:" + id + ", Tipo: " + Cliente.class.getName()));
	}

	public Cliente update(Cliente obj){
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repository.save(newObj);
	}

	public void delete(Integer id) {
		try {
			repository.delete(find(id));
		}catch (DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possivel excluir Cliente que contem pedidos!");
		}
	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO dto){
		return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null);

	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
