package br.edu.ufape.sguEditaisService.comunicacao.annotations;

import br.edu.ufape.sguEditaisService.comunicacao.validacoes.DataConsistenteValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = DataConsistenteValidator.class)
@Documented
public @interface DatasConsistentes {
    String message() default "A data de início não pode ser posterior à data de fim";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
