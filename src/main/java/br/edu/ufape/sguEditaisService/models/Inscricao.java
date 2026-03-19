package br.edu.ufape.sguEditaisService.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // A âncora do candidato (independente de ser aluno UFAPE ou externo via Extra SiSU)
    @Column(nullable = false)
    private UUID userId;

    // Só recebe valor na hora que o candidato clica em "Enviar" definitivamente
    @Column(unique = true)
    private String numeroProtocolo;

    // Data da submissão final
    private LocalDateTime dataInscricao;

//    // Status do motor do sistema (Controla as engrenagens: se é RASCUNHO, não processa)
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private SituacaoInscricao situacao = SituacaoInscricao.RASCUNHO;

    // Status Dinâmico (Para a comissão dar feedback customizado ao candidato, ex: "Falta RG")
    @ManyToOne(optional = false)
    @JoinColumn(name = "status_personalizado_id", nullable = false)
    private StatusPersonalizado statusPersonalizado;

    // A qual edital essa inscrição pertence
    @ManyToOne(optional = false)
    @JoinColumn(name = "edital_id", nullable = false)
    private Edital edital;

    // Respostas do formulário (Blindadas: Apagou inscrição, apaga as respostas)
    @OneToMany(mappedBy = "inscricao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ValorCampo> valoresCampos = new ArrayList<>();

    // Auditoria (Blindada: Apagou inscrição, apaga o rastro dela)
    @OneToMany(mappedBy = "inscricao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistoricoEtapaInscricao> historico = new ArrayList<>();

    // ================== MÉTODOS AUXILIARES ================== //

    public void adicionarValorCampo(ValorCampo valorCampo) {
        valorCampo.setInscricao(this);
        this.valoresCampos.add(valorCampo);
    }

    public void adicionarHistorico(HistoricoEtapaInscricao historicoEtapa) {
        historicoEtapa.setInscricao(this);
        this.historico.add(historicoEtapa);
    }
}