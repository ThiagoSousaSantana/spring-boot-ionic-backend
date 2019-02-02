package com.thiago.cursomc.services.validation;

import com.thiago.cursomc.domain.enums.TipoCliente;
import com.thiago.cursomc.dto.ClienteNewDTO;
import com.thiago.cursomc.repositories.ClienteRepository;
import com.thiago.cursomc.resources.exceptions.FieldMessage;
import com.thiago.cursomc.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;


public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository repository;

    @Override
    public void initialize(ClienteInsert ann) {
    }

    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if(objDto.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj()))
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));

        if(objDto.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj()))
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));

        if (repository.findByEmail(objDto.getEmail()) != null)
            list.add(new FieldMessage("email", "Email ja existente"));

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
