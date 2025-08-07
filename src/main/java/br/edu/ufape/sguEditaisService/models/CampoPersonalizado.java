package br.edu.ufape.sguEditaisService.models;

import br.edu.ufape.sguEditaisService.models.enums.TipoCampo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CampoPersonalizado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String rotulo;
    private boolean obrigatorio;

    @Enumerated(EnumType.STRING)
    private TipoCampo tipoCampo;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String opcoes;

    @OneToMany(mappedBy = "campoPersonalizado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ValorCampo> valoresCampo;

    @ManyToOne
    private Etapa etapa;

    @ManyToOne
    private Edital edital;

}
