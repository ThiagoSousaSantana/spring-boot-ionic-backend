package com.thiago.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import com.thiago.cursomc.domain.*;
import com.thiago.cursomc.domain.enums.EstadoPagamento;
import com.thiago.cursomc.domain.enums.TipoCliente;
import com.thiago.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursomcApplication  implements CommandLineRunner{

	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}

}

