package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class CampoPersonalizadoNotFoundException extends NotFoundException {
    public CampoPersonalizadoNotFoundException(Long id) {
        super("Campo personalizado", id);
    }
}
