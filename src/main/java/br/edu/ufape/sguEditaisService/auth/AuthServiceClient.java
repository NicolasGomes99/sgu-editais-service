package br.edu.ufape.sguEditaisService.auth;

import br.edu.ufape.sguEditaisService.comunicacao.dto.unidadeAdministrativa.UnidadeAdministrativaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "authServiceClient", url = "${authClient.base-url}")
public interface AuthServiceClient {

    @GetMapping("/unidade-administrativa/{id}")
    UnidadeAdministrativaResponse buscarUnidadeAdministrativa(@PathVariable("id") Long id);

    @GetMapping("/unidade-administrativa/funcionario/{usuarioId}")
    Page<UnidadeAdministrativaResponse> listarUnidadesDoFuncionarioPorId(@PathVariable("usuarioId") UUID usuarioId);

    @GetMapping("/unidade-administrativa/gestor/{usuarioId}")
    Page<UnidadeAdministrativaResponse> listarUnidadesDoGestorPorId(@PathVariable("usuarioId") UUID usuarioId);

}