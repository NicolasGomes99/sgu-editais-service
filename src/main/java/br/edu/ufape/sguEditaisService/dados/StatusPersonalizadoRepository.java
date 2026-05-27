package br.edu.ufape.sguEditaisService.dados;

import br.edu.ufape.sguEditaisService.models.StatusPersonalizado;
import br.edu.ufape.sguEditaisService.models.enums.TipoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StatusPersonalizadoRepository extends JpaRepository<StatusPersonalizado, Long> {
    List<StatusPersonalizado> findByTipoStatus(TipoStatus tipoStatus);
}