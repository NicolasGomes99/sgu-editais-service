package br.edu.ufape.sguEditaisService.dados;

import br.edu.ufape.sguEditaisService.models.DocumentoEdital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoEditalRepository extends JpaRepository<DocumentoEdital, Long> {
}
