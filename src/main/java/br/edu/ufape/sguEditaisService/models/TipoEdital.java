package br.edu.ufape.sguEditaisService.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TipoEdital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    private String descricao;

    // As etapas canônicas que todo edital desse tipo DEVE ter
    @OneToMany(mappedBy = "tipoEdital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Etapa> etapas = new ArrayList<>();

    // O formulário padrão para esse tipo de edital
    @OneToMany(mappedBy = "tipoEdital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CampoPersonalizado> campos = new ArrayList<>();
}