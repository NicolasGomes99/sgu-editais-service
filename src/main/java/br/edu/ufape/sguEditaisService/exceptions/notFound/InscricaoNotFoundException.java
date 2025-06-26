package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class InscricaoNotFoundException extends RuntimeException {
    public InscricaoNotFoundException() {
        super("Inscrição não encontrada");
    }
}
