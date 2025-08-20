package br.edu.ufape.sguEditaisService.dados;

import br.edu.ufape.sguEditaisService.models.Etapa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EtapaRepository extends JpaRepository<Etapa, Long> {

    List<Etapa> findByEditalIdOrderByOrdemAsc(Long editalId);

    List<Etapa> findByTipoEditalModeloIdOrderByOrdemAsc(Long tipoEditalModeloId);
}