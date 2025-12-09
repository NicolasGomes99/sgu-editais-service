package br.edu.ufape.sguEditaisService.dados;

import br.edu.ufape.sguEditaisService.models.DataEtapa;
import br.edu.ufape.sguEditaisService.models.Etapa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DataEtapaRepository extends JpaRepository<DataEtapa, Long> {
    List<DataEtapa> findAllByEditalId(Long editalId);
    Optional<DataEtapa> findByEtapa(Etapa etapa);
}