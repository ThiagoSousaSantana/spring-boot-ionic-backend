package com.thiago.cursomc.repositories;

import com.thiago.cursomc.domain.Cliente;
import com.thiago.cursomc.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
