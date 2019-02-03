package com.thiago.cursomc.services;

import com.thiago.cursomc.domain.PagamentoComBoleto;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BoletoService {

    public void preencherPagamentoComBoleto(PagamentoComBoleto comBoleto, Date dataPedido){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataPedido);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        comBoleto.setDataVencimento(dataPedido);
    }
}
