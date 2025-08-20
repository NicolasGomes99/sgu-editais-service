package br.edu.ufape.sguEditaisService.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class TipoEdital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;

    @OneToMany(mappedBy = "tipoEditalModelo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Etapa> etapasModelo;

    @OneToMany(mappedBy = "tipoEditalModelo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CampoPersonalizado> camposPersonalizadosModelo;
}