package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class EtapaNotFoundException extends NotFoundException {
    public EtapaNotFoundException(Long id) {
        super("Etapa", id);
    }

    public EtapaNotFoundException(String message) {
        super(message);
    }
}
