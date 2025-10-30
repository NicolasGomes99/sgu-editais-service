package br.edu.ufape.sguEditaisService.servicos;


import br.edu.ufape.sguEditaisService.comunicacao.dto.usuario.UsuarioResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import br.edu.ufape.sguEditaisService.auth.AuthServiceClient;
import br.edu.ufape.sguEditaisService.comunicacao.dto.unidadeAdministrativa.UnidadeAdministrativaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
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
    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "fallbackBuscarUsuariosPorIds")
    public List<UsuarioResponse> buscarUsuariosPorIds(List<UUID> userIds) {
        if(userIds == null || userIds.isEmpty()) {
            return Collections.emptyList();
        }
        return authServiceClient.buscarUsuariosPorIds(userIds);
    }

// Avaliar isso daqui!!
//    @SuppressWarnings("unused")
//    public List<UsuarioResponse> fallbackBuscarUsuariosPorIds(List<UUID> userIds, Throwable t) {
//        log.warn("AVISO: Falha ao buscar dados em batch para {} usuários. O serviço de autenticação pode estar indisponível. Erro: {}", userIds.size(), t.getMessage());
//        // Retorna uma lista de DTOs parciais para evitar NullPointerException
//        return userIds.stream().map(id -> new UsuarioResponse(id, "Usuário " + id, null, null, null)).toList();
//    }
}