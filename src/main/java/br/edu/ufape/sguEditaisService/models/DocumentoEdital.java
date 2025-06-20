package br.edu.ufape.sguEditaisService.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class DocumentoEdital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String caminho;
    private LocalDateTime dataUpload;

    @ManyToOne
    private Edital edital;
}
