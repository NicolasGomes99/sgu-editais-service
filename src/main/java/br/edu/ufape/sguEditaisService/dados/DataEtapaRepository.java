package br.edu.ufape.sguEditaisService.dados;

import br.edu.ufape.sguEditaisService.models.DataEtapa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataEtapaRepository extends JpaRepository<DataEtapa, Long> {
    List<DataEtapa> findAllByEditalId(Long editalId);
}