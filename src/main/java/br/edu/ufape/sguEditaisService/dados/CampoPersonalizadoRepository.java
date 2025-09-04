package br.edu.ufape.sguEditaisService.dados;

import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CampoPersonalizadoRepository extends JpaRepository<CampoPersonalizado, Long> {

    List<CampoPersonalizado> findByEditalIdAndEtapaIdIsNull(Long editalId);

    List<CampoPersonalizado> findByTipoEditalModeloIdAndEtapaIdIsNull(Long tipoEditalModeloId);

    List<CampoPersonalizado> findByEtapaId(Long etapaId);
}