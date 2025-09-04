package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.auth.AuthServiceClient;
import br.edu.ufape.sguEditaisService.comunicacao.dto.unidadeAdministrativa.UnidadeAdministrativaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        // Retorna um objeto vazio para não quebrar a API do lado do cliente
        return new UnidadeAdministrativaResponse();
    }
}