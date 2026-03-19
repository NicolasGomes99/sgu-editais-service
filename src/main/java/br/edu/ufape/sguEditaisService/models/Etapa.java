package br.edu.ufape.sguEditaisService.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Etapa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    private Integer ordem;

    // Se pertencer ao Molde (TipoEdital), preenche isto:
    @ManyToOne
    @JoinColumn(name = "tipo_edital_id")
    private TipoEdital tipoEdital;

    // Se for o Snapshot de um Edital real, preenche isto:
    @ManyToOne
    @JoinColumn(name = "edital_id")
    private Edital edital;
}