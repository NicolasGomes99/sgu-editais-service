package br.edu.ufape.sguEditaisService.auth;

import br.edu.ufape.sguEditaisService.comunicacao.dto.curso.CursoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.usuario.UsuarioResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.unidadeAdministrativa.UnidadeAdministrativaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "authServiceClient", url = "${authClient.base-url}")
public interface AuthServiceClient {

    @GetMapping("/unidade-administrativa/{id}")
    UnidadeAdministrativaResponse buscarUnidadeAdministrativa(@PathVariable("id") Long id);

    @GetMapping("/unidade-administrativa/funcionario/{usuarioId}")
    Page<UnidadeAdministrativaResponse> listarUnidadesDoFuncionarioPorId(@PathVariable("usuarioId") UUID usuarioId);

    @GetMapping("/unidade-administrativa/gestor/{usuarioId}")
    Page<UnidadeAdministrativaResponse> listarUnidadesDoGestorPorId(@PathVariable("usuarioId") UUID usuarioId);

    @GetMapping("/usuario/{userId}")
    UsuarioResponse buscarUsuarioPorId(@PathVariable("userId") UUID userId);

    @GetMapping("/unidade-administrativa/{id}/vinculo")
    boolean verificarVinculo(@PathVariable("id") Long id, @RequestParam("userId") UUID userId);

    @GetMapping("curso/{id}")
    CursoResponse buscarCursoPorId(@PathVariable("id") Long id);
}