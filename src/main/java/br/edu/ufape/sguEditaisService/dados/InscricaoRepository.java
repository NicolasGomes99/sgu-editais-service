package br.edu.ufape.sguEditaisService.dados;

import br.edu.ufape.sguEditaisService.models.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
}
