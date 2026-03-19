package br.edu.ufape.sguEditaisService.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HistoricoEtapaInscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataMudanca;

    @ManyToOne
    @JoinColumn(name = "status_anterior_id")
    private StatusPersonalizado statusAnterior;

    @ManyToOne(optional = false)
    @JoinColumn(name = "status_novo_id", nullable = false)
    private StatusPersonalizado statusNovo;

    private UUID responsavelId; // Quem mudou? (Candidato ou Comissão de avaliação)

    @Column(columnDefinition = "TEXT")
    private String parecer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "inscricao_id", nullable = false)
    private Inscricao inscricao;
}