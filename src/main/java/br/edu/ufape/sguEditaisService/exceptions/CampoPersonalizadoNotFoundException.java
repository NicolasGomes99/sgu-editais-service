package br.edu.ufape.sguEditaisService.exceptions;

public class CampoPersonalizadoNotFoundException extends RuntimeException {
    public CampoPersonalizadoNotFoundException() {
        super("Campo personalizado não encontrado");
    }
}
