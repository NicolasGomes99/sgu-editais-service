package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class EtapaNotFoundException extends RuntimeException {
    public EtapaNotFoundException() {
        super("Etapa não encontrada.");
    }
}
