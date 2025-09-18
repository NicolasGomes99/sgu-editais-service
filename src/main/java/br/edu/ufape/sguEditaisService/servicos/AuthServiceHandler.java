package br.edu.ufape.sguEditaisService.servicos;

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
}