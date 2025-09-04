package br.edu.ufape.sguEditaisService.comunicacao.annotations;

import br.edu.ufape.sguEditaisService.comunicacao.validacoes.ValidJsonOrNullValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidJsonOrNullValidator.class)
@Documented
public @interface ValidJsonOrNull {
    String message() default "Se fornecido, o campo 'opcoes' deve ser uma string JSON válida.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}