package br.edu.ufape.sguEditaisService.models;

import br.edu.ufape.sguEditaisService.models.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Entity
@AllArgsConstructor
@NoArgsConstructor @Getter @Setter
public class HistoricoEtapaInscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dataAacao;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String observacao;

    @ManyToOne
    private Inscricao inscricao;

    @ManyToOne
    private Etapa etapa;


}
