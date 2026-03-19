package br.edu.ufape.sguEditaisService.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Edital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private Long cursoId;

    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private boolean ativo = true;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "documento_pdf_id")
    private Documento documentoEdital;

    @ManyToOne(optional = false)
    @JoinColumn(name = "status_personalizado_id", nullable = false)
    private StatusPersonalizado status;

    @ManyToOne(optional = false) // Mantemos para saber de qual modelo este edital se originou
    @JoinColumn(name = "tipo_edital_id", nullable = false)
    private TipoEdital tipoEdital;

    @OneToMany(mappedBy = "edital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DataEtapa> cronograma = new ArrayList<>();

    // O Snapshot das Etapas e Campos
    @OneToMany(mappedBy = "edital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Etapa> etapas = new ArrayList<>();

    @OneToMany(mappedBy = "edital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CampoPersonalizado> campos = new ArrayList<>();
}