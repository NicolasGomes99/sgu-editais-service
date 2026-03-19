package br.edu.ufape.sguEditaisService.dados;

import br.edu.ufape.sguEditaisService.models.TipoEdital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoEditalRepository extends JpaRepository<TipoEdital, Long> {
}