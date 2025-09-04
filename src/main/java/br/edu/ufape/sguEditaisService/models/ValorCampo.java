package br.edu.ufape.sguEditaisService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ValorCampo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String valor;


    @ManyToOne
    @JsonIgnore
    private Inscricao inscricao;

    @ManyToOne
    @JsonIgnore
    private CampoPersonalizado campoPersonalizado;

}
