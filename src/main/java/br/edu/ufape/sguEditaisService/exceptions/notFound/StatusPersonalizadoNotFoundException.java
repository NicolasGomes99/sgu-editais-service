package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class StatusPersonalizadoNotFoundException extends NotFoundException {
    public StatusPersonalizadoNotFoundException(Long id) {
        super("Status Personalizado", id);
    }
}
