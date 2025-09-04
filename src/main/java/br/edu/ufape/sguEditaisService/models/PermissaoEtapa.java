package br.edu.ufape.sguEditaisService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor @Getter @Setter
public class PermissaoEtapa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String perfil;

    @ManyToOne
    @JsonIgnore
    private Etapa etapa;
}
