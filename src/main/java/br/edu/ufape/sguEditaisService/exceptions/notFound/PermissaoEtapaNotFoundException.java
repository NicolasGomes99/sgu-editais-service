package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class PermissaoEtapaNotFoundException extends NotFoundException {
    public PermissaoEtapaNotFoundException(Long id) {
        super("Permissão da etapa", id);
    }
}
