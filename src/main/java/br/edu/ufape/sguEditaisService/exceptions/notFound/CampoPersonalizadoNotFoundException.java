package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class CampoPersonalizadoNotFoundException extends RuntimeException {
    public CampoPersonalizadoNotFoundException() {
        super("Campo personalizado não encontrado");
    }
}
