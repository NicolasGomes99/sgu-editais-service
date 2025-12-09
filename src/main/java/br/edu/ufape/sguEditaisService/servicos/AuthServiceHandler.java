package br.edu.ufape.sguEditaisService.servicos;


import br.edu.ufape.sguEditaisService.comunicacao.dto.curso.CursoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.usuario.UsuarioResponse;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import br.edu.ufape.sguEditaisService.auth.AuthServiceClient;
import br.edu.ufape.sguEditaisService.comunicacao.dto.unidadeAdministrativa.UnidadeAdministrativaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceHandler implements br.edu.ufape.sguEditaisService.servicos.interfaces.AuthServiceHandler {

    private final AuthServiceClient authServiceClient;

    @Override
    public UnidadeAdministrativaResponse buscarUnidadeAdministrativa(Long id) {
        return authServiceClient.buscarUnidadeAdministrativa(id);
    }

    @Override
    public UnidadeAdministrativaResponse fallbackBuscarUnidadeAdministrativa(Long id, Throwable t) {
        log.warn("AVISO: Não foi possível buscar dados da Unidade Administrativa com ID {}. O serviço de autenticação pode estar indisponível. Erro: {}", id, t.getMessage());
        return new UnidadeAdministrativaResponse();
    }

    @Override
    public Page<UnidadeAdministrativaResponse> listarUnidadesDoFuncionarioPorId(UUID usuarioId) {
        return authServiceClient.listarUnidadesDoFuncionarioPorId(usuarioId);
    }

    @Override
    public Page<UnidadeAdministrativaResponse> fallbackListarUnidadesDoFuncionarioPorId(UUID usuarioId, Throwable t) {
        log.warn("AVISO: Falha ao buscar unidades para o usuário como funcionário com ID {}. Erro: {}", usuarioId, t.getMessage());
        return new PageImpl<>(Collections.emptyList());
    }

    @Override
    public Page<UnidadeAdministrativaResponse> listarUnidadesDoGestorPorId(UUID usuarioId) {
        return authServiceClient.listarUnidadesDoGestorPorId(usuarioId);
    }

    @Override
    public Page<UnidadeAdministrativaResponse> fallbackListarUnidadesDoGestorPorId(UUID usuarioId, Throwable t) {
        log.warn("AVISO: Falha ao buscar unidades para o usuário como gestor com ID {}. Erro: {}", usuarioId, t.getMessage());
        return new PageImpl<>(Collections.emptyList());
    }

    @Override
    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "fallbackBuscarUsuarioPorId")
    public UsuarioResponse buscarUsuarioPorId(UUID userId) {
        return authServiceClient.buscarUsuarioPorId(userId);
    }

    @Override
    public UsuarioResponse fallbackBuscarUsuarioPorId(UUID userId, Throwable t) {
        log.warn("AVISO: Falha ao buscar dados do USUÁRIO com ID {}. O serviço de autenticação pode estar indisponível. Erro: {}", userId, t.getMessage());
        return new UsuarioResponse();
    }

    @Override
    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "fallbackVerificarVinculo")
    public boolean verificarVinculo(Long unidadeId) {
        return authServiceClient.verificarVinculo(unidadeId);
    }

    @Override
    public boolean fallbackVerificarVinculo(Long unidadeId, Throwable t) {
        log.warn("Não foi possível verificar vínculo com a unidade {} no AuthService. Bloqueando acesso por segurança.", unidadeId, t);
        return false;
    }

    @Override
    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "fallbackBuscarCursoPorId")
    public CursoResponse buscarCursoPorId(Long id) {
        return authServiceClient.buscarCursoPorId(id);
    }

    @Override
    public CursoResponse fallbackBuscarCursoPorId(Long id, Throwable t) {
        if (t instanceof FeignException.NotFound) {
            log.warn("Curso com ID {} não encontrado no Auth Service.", id);
            return null;
        }


        log.error("ERRO CRÍTICO: Falha ao validar Curso ID {}. O Auth Service pode estar indisponível. Erro: {}", id, t.getMessage());
        return null; // Retornar null aqui vai bloquear a criação do edital por segurança
    }
}