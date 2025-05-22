package br.edu.ufape.sguEditaisService.models;

import br.edu.ufape.sguEditaisService.models.enums.status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class inscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private status status;

    private UUID idUsuario;

    @OneToMany(mappedBy = "inscricao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<documento> documentos;

    @OneToMany(mappedBy = "inscricao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<valorCampo> valoresCampo;

    @ManyToOne
    private edital edital;
}
