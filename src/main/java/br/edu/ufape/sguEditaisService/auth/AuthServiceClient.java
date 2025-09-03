package br.edu.ufape.sguEditaisService.auth;

import br.edu.ufape.sguEditaisService.comunicacao.dto.unidadeAdministrativa.UnidadeAdministrativaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "authServiceClient", url = "${authClient.base-url}")
public interface AuthServiceClient {

    @GetMapping("/unidade-administrativa/{id}")
    UnidadeAdministrativaResponse buscarUnidadeAdministrativa(@PathVariable("id") Long id);

}