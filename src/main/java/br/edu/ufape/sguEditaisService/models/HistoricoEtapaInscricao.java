package br.edu.ufape.sguEditaisService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Entity
@AllArgsConstructor
@NoArgsConstructor @Getter @Setter
public class HistoricoEtapaInscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataAacao;

    @ManyToOne
    private StatusPersonalizado statusPersonalizado;

    private String observacao;

    @ManyToOne
    @JsonIgnore
    private Inscricao inscricao;

    @ManyToOne
    @JsonIgnore
    private Etapa etapa;


}
