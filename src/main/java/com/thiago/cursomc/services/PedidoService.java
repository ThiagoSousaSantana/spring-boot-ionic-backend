package com.thiago.cursomc.services;

import com.thiago.cursomc.domain.ItemPedido;
import com.thiago.cursomc.domain.PagamentoComBoleto;
import com.thiago.cursomc.domain.PagamentoComCartao;
import com.thiago.cursomc.domain.Pedido;
import com.thiago.cursomc.domain.enums.EstadoPagamento;
import com.thiago.cursomc.repositories.ItemPedidoRepository;
import com.thiago.cursomc.repositories.PagamentoRepository;
import com.thiago.cursomc.repositories.PedidoRepository;
import com.thiago.cursomc.repositories.ProdutoRepository;
import com.thiago.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repository;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> pedido = repository.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id:" + id + ", Tipo: " + Pedido.class.getName()));
	}

	public Pedido insert(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		pedido.setCliente(clienteService.find(pedido.getCliente().getId()));

		if (pedido.getPagamento() instanceof PagamentoComBoleto){
			PagamentoComBoleto comBoleto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(comBoleto, pedido.getInstante());
		}
		pedido = repository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());

		for (ItemPedido ip: pedido.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(pedido);
		}
		itemPedidoRepository.saveAll(pedido.getItens());
		emailService.sendOrderConfirmationEmail(pedido);
		return pedido;
	}
}
