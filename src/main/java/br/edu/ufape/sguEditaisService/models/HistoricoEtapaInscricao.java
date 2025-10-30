package br.edu.ufape.sguEditaisService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor @Getter @Setter
public class HistoricoEtapaInscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID idUsuario;

    @JoinColumn(nullable = false)
    private LocalDateTime dataAacao;

    @ManyToOne
    @JoinColumn(nullable = false)
    private StatusPersonalizado statusPersonalizado;

    private String observacao;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Inscricao inscricao;

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonIgnore
    private Etapa etapa;


}
