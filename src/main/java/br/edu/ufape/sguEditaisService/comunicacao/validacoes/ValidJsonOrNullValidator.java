package br.edu.ufape.sguEditaisService.comunicacao.validacoes;

import br.edu.ufape.sguEditaisService.comunicacao.annotations.ValidJsonOrNull;
import br.edu.ufape.sguEditaisService.comunicacao.dto.campoPersonalizado.CampoPersonalizadoRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidJsonOrNullValidator implements ConstraintValidator<ValidJsonOrNull, CampoPersonalizadoRequest> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean isValid(CampoPersonalizadoRequest request, ConstraintValidatorContext context) {
        if (request == null) {
            return true;
        }

        String opcoes = request.getOpcoes();

        if (opcoes == null || opcoes.trim().isEmpty()) {
            return true;
        }

        try {
            objectMapper.readTree(opcoes);
            return true;
        } catch (JsonProcessingException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O campo 'opcoes' não é um JSON válido.")
                    .addPropertyNode("opcoes")
                    .addConstraintViolation();
            return false;
        }
    }
}