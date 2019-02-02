package com.thiago.cursomc.services.validation;

import com.thiago.cursomc.domain.Cliente;
import com.thiago.cursomc.dto.ClienteDTO;
import com.thiago.cursomc.repositories.ClienteRepository;
import com.thiago.cursomc.resources.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

    @Autowired
    private HttpServletRequest servletRequest;

    @Autowired
    private ClienteRepository repository;

    @Override
    public void initialize(ClienteUpdate ann) {
    }

    @Override
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

        Map<String, String> map =
                (Map<String, String>) servletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        Integer id = Integer.parseInt(map.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        Cliente aux = repository.findByEmail(objDto.getEmail());
        if (aux != null && !aux.getId().equals(id))
            list.add(new FieldMessage("email", "Email ja existente"));

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
