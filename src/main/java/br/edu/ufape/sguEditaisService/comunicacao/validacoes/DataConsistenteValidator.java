package br.edu.ufape.sguEditaisService.comunicacao.validacoes;

import br.edu.ufape.sguEditaisService.comunicacao.annotations.DatasConsistentes;
import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.etapa.EtapaRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class DataConsistenteValidator implements ConstraintValidator<DatasConsistentes, Object> {

    @Override
    public void initialize(DatasConsistentes constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        LocalDateTime inicio = null;
        LocalDateTime fim = null;

        if (value instanceof EditalRequest request) {
            inicio = request.getInicioInscricao();
            fim = request.getFimIncricao();
        } else if (value instanceof EtapaRequest request) {
            inicio = request.getDataInicio();
            fim = request.getDataFim();
        }

        return !inicio.isAfter(fim);
    }
}