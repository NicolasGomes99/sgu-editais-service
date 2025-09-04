package br.edu.ufape.sguEditaisService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Edital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private LocalDateTime dataPublicacao;
    private LocalDateTime inicioInscricao;
    private LocalDateTime fimIncricao;

    @ManyToOne
    private StatusPersonalizado statusAtual;

    @ManyToOne
    private TipoEdital tipoEdital;

    private Long idUnidadeAdministrativa;

    @OneToMany(mappedBy = "edital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Etapa> etapas;

    @OneToMany(mappedBy = "edital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inscricao> inscricoes;

    @OneToMany(mappedBy = "edital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentoEdital> documentosEdital;

    @OneToMany(mappedBy = "edital", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CampoPersonalizado> camposPersonalizados;
}