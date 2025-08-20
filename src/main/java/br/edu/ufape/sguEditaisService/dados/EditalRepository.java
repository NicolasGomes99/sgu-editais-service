package br.edu.ufape.sguEditaisService.dados;

import br.edu.ufape.sguEditaisService.models.Edital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface EditalRepository extends JpaRepository<Edital, Long> {

    @Query("SELECT e FROM Edital e WHERE e.dataPublicacao IS NOT NULL AND e.dataPublicacao <= :now AND e.inicioInscricao <= :now AND e.fimIncricao >= :now")
    Page<Edital> findByInscricoesAbertas(LocalDateTime now, Pageable pageable);
}