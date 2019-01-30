package com.thiago.cursomc.repositories;

import com.thiago.cursomc.domain.Pedido;
import com.thiago.cursomc.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

}
