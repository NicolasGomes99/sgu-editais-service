package br.edu.ufape.sguEditaisService.dados;

import br.edu.ufape.sguEditaisService.models.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
    List<Inscricao> findByUserId(UUID userId);
    List<Inscricao> findByEditalId(Long editalId);
    Optional<Inscricao> findByNumeroProtocolo(String numeroProtocolo);
    boolean existsByUserIdAndEditalId(UUID userId, Long editalId);
}