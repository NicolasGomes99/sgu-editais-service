package br.edu.ufape.sguEditaisService.comunicacao.validacoes;

import br.edu.ufape.sguEditaisService.comunicacao.annotations.DatasConsistentes;
import br.edu.ufape.sguEditaisService.comunicacao.dto.dataEtapa.DataEtapaRequest;
import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalRequest;
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
        } else if (value instanceof DataEtapaRequest request) {
            inicio = request.getDataInicio();
            fim = request.getDataFim();
        }

        if (inicio == null || fim == null) {
            return true;
        }

        return !inicio.isAfter(fim);
    }
}