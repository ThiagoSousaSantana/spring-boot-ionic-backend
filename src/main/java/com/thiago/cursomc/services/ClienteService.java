package com.thiago.cursomc.services;

import com.thiago.cursomc.domain.Categoria;
import com.thiago.cursomc.domain.Cidade;
import com.thiago.cursomc.domain.Cliente;
import com.thiago.cursomc.domain.Endereco;
import com.thiago.cursomc.domain.enums.TipoCliente;
import com.thiago.cursomc.dto.ClienteDTO;
import com.thiago.cursomc.dto.ClienteNewDTO;
import com.thiago.cursomc.repositories.CidadeRepository;
import com.thiago.cursomc.repositories.ClienteRepository;
import com.thiago.cursomc.repositories.EnderecoRepository;
import com.thiago.cursomc.services.exceptions.DataIntegrityException;
import com.thiago.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> cliente = repository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id:" + id + ", Tipo: " + Cliente.class.getName()));
	}

	public Cliente insert(Cliente obj){
		obj.setId(null);
		obj = repository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
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

	public Cliente fromDTO(ClienteNewDTO dto){
		Cliente cliente = new Cliente(null, dto.getNome(), dto.getEmail(), dto.getCpfOuCnpj(),
				TipoCliente.toEnum(dto.getTipoCliente()));

		Cidade cidade = new Cidade(dto.getCidade(), null, null);

		cliente.getEnderecos().add(new Endereco(null, dto.getLogradouro(), dto.getNumero(),
				dto.getComplemento(), dto.getBairro(), dto.getCep(), cidade, cliente));

		cliente.getTelefones().add(dto.getTelefone1());
		if (dto.getTelefone2() != null)
			cliente.getTelefones().add(dto.getTelefone2());
		if (dto.getTelefone3() != null)
			cliente.getTelefones().add(dto.getTelefone3());

		return cliente;
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
