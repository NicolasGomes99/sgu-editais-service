package br.edu.ufape.sguEditaisService.dados;

import br.edu.ufape.sguEditaisService.models.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
    boolean existsByIdUsuarioAndEditalId(UUID idUsuario, Long editalId);
    boolean existsByEditalIdAndIdUsuario(Long editalId, UUID idUsuario);
    Inscricao findByEditalId(Long editalId);
    Inscricao findByIdUsuario(UUID idUsuario);
}
