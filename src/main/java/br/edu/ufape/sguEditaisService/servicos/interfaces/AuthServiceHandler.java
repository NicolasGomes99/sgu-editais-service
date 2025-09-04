package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.comunicacao.dto.unidadeAdministrativa.UnidadeAdministrativaResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

public interface AuthServiceHandler {

    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "fallbackBuscarUnidadeAdministrativa")
    UnidadeAdministrativaResponse buscarUnidadeAdministrativa(Long id);

    @SuppressWarnings("unused")
    UnidadeAdministrativaResponse fallbackBuscarUnidadeAdministrativa(Long id, Throwable t);
}