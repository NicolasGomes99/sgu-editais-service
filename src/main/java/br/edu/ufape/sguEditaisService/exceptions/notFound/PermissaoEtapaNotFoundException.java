package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class PermissaoEtapaNotFoundException extends RuntimeException {
    public PermissaoEtapaNotFoundException() {
        super("Permissão de etapa não encontrada");
    }
}
