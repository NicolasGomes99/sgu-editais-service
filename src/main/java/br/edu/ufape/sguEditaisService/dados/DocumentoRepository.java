package br.edu.ufape.sguEditaisService.dados;

import br.edu.ufape.sguEditaisService.models.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
}
