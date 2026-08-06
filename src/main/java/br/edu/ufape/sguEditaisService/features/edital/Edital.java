package br.edu.ufape.sguEditaisService.features.edital;

import br.edu.ufape.sguEditaisService.exceptions.business.RegraNegocioException;
import br.edu.ufape.sguEditaisService.features.edital.campoedital.CampoEdital;
import br.edu.ufape.sguEditaisService.features.edital.etapainstancia.EtapaInstancia;
import br.edu.ufape.sguEditaisService.features.tipoedital.TipoEdital;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE edital SET ativo = false WHERE id = ?")
@SQLRestriction("ativo = true")
public class Edital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String titulo;

    @Column(name = "curso_id")
    private Long cursoId;

    @NotBlank
    @Column(nullable = false)
    private String moduloOrigem;

    @Column(nullable = false)
    private boolean ativo = true;

    // Controla a visibilidade/andamento do edital instanciado para o Motor
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoEdital situacao = SituacaoEdital.PLANEJAMENTO;

    // O Molde do qual este Edital foi originado
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_edital_id", nullable = false, updatable = false)
    private TipoEdital tipoEditalOrigem;

    @OneToMany(mappedBy = "edital", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<EtapaInstancia> etapas = new ArrayList<>();

    @OneToMany(mappedBy = "edital", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CampoEdital> camposGlobais = new ArrayList<>();

    // Mutações e Comportamentos (DDD)
    public static Edital instanciarDe(TipoEdital tipoEdital, String titulo, Long cursoId) {
        Edital edital = new Edital();
        edital.titulo = titulo;
        edital.moduloOrigem = tipoEdital.getModuloOrigem();
        edital.tipoEditalOrigem = tipoEdital;
        edital.cursoId = cursoId;
        return edital;
    }

    public void adicionarEtapa(EtapaInstancia etapa) {
        this.etapas.add(etapa);
        etapa.vincularAoEdital(this);
    }

    public void adicionarCampoGlobal(CampoEdital campo) {
        this.camposGlobais.add(campo);
        campo.vincularAoEdital(this);
    }

    public void checarPermissaoEdicao() {
        if (this.situacao != SituacaoEdital.PLANEJAMENTO) {
            throw new RegraNegocioException("Editais só podem ter sua estrutura alterada enquanto estiverem em PLANEJAMENTO.");
        }
    }
}