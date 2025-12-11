package br.edu.ufape.sguEditaisService.servicos.interfaces;

import br.edu.ufape.sguEditaisService.comunicacao.dto.curso.CursoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.usuario.UsuarioResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.unidadeAdministrativa.UnidadeAdministrativaResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.data.domain.Page;
import java.util.UUID;

public interface AuthServiceHandler {

    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "fallbackBuscarUnidadeAdministrativa")
    UnidadeAdministrativaResponse buscarUnidadeAdministrativa(Long id);

    @SuppressWarnings("unused")
    UnidadeAdministrativaResponse fallbackBuscarUnidadeAdministrativa(Long id, Throwable t);

    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "fallbackListarUnidadesDoFuncionarioPorId")
    Page<UnidadeAdministrativaResponse> listarUnidadesDoFuncionarioPorId(UUID usuarioId);

    @SuppressWarnings("unused")
    Page<UnidadeAdministrativaResponse> fallbackListarUnidadesDoFuncionarioPorId(UUID usuarioId, Throwable t);

    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "fallbackListarUnidadesDoGestorPorId")
    Page<UnidadeAdministrativaResponse> listarUnidadesDoGestorPorId(UUID usuarioId);

    @SuppressWarnings("unused")
    Page<UnidadeAdministrativaResponse> fallbackListarUnidadesDoGestorPorId(UUID usuarioId, Throwable t);

    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "fallbackBuscarUsuarioPorId")
    UsuarioResponse buscarUsuarioPorId(UUID userId);

    @SuppressWarnings("unused")
    UsuarioResponse fallbackBuscarUsuarioPorId(UUID userId, Throwable t);

    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "fallbackVerificarVinculo")
    boolean verificarVinculo(Long unidadeId, UUID userId);

    boolean fallbackVerificarVinculo(Long unidadeId, UUID userId, Throwable t);

    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "fallbackBuscarCursoPorId")
    CursoResponse buscarCursoPorId(Long id);

    CursoResponse fallbackBuscarCursoPorId(Long id, Throwable t);
}