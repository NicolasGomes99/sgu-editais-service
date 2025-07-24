package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class StatusPersonalizadoNotFoundException extends RuntimeException {
    public StatusPersonalizadoNotFoundException() {
        super("Status Personalizado não encontrado");
    }
}
