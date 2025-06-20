package br.edu.ufape.sguEditaisService.dados;

import br.edu.ufape.sguEditaisService.models.CampoPersonalizado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampoPersonalizadoRepository extends JpaRepository<CampoPersonalizado, Long> {
}
