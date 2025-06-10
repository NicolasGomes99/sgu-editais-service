package br.edu.ufape.sguEditaisService.models;

import br.edu.ufape.sguEditaisService.models.enums.status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Entity
@RequiredArgsConstructor @NoArgsConstructor @Getter @Setter
public class HistoricoEtapaInscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dataAacao;

    @Enumerated(EnumType.STRING)
    private status status;

    private String observacao;

    @ManyToOne
    private Inscricao inscricao;

    @ManyToOne
    private Etapa etapa;


}
