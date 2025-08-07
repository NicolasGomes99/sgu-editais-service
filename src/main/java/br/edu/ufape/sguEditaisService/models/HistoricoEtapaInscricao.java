package br.edu.ufape.sguEditaisService.models;

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

    @ManyToOne
    private StatusPersonalizado statusPersonalizado;

    private String observacao;

    @ManyToOne
    private Inscricao inscricao;

    @ManyToOne
    private Etapa etapa;


}
