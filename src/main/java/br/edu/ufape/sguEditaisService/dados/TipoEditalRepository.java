package br.edu.ufape.sguEditaisService.dados;

import br.edu.ufape.sguEditaisService.models.TipoEdital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoEditalRepository extends JpaRepository<TipoEdital, Long> {
}
