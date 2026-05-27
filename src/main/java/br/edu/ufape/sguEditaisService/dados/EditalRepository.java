package br.edu.ufape.sguEditaisService.dados;

import br.edu.ufape.sguEditaisService.models.Edital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditalRepository extends JpaRepository<Edital, Long> {
}