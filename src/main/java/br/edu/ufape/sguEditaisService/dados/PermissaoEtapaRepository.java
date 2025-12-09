package br.edu.ufape.sguEditaisService.dados;

import br.edu.ufape.sguEditaisService.models.PermissaoEtapa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermissaoEtapaRepository extends JpaRepository<PermissaoEtapa, Long> {

    List<PermissaoEtapa> findByEtapaId(Long etapaId);

    Optional<PermissaoEtapa> findByEtapaIdAndPerfil(Long etapaId, String perfil);

}
