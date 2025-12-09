package br.edu.ufape.sguEditaisService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Inscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private UUID idUsuario;

    private LocalDateTime dataInscricao;

    @ManyToOne
    //@JsonIgnore
    private StatusPersonalizado statusAtual;

    @OneToMany(mappedBy = "inscricao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentos;

    @OneToMany(mappedBy = "inscricao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ValorCampo> valoresCampo;

    @ManyToOne
    @JsonIgnore
    private Edital edital;

    @ManyToOne
    @JoinColumn(name = "etapa_atual_id")
    private Etapa etapaAtual;

    @OneToMany(mappedBy = "inscricao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricoEtapaInscricao> historicoEtapaInscricao;
}
