package br.edu.ufape.sguEditaisService.dados;

import br.edu.ufape.sguEditaisService.models.HistoricoEtapaInscricao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoEtapaInscricaoRepository extends JpaRepository<HistoricoEtapaInscricao, Long> {
}
