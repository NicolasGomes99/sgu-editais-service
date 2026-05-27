package br.edu.ufape.sguEditaisService.dados;

import br.edu.ufape.sguEditaisService.models.Etapa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EtapaRepository extends JpaRepository<Etapa, Long> {
    List<Etapa> findByTipoEditalIdOrderByOrdemAsc(Long tipoEditalId);
}