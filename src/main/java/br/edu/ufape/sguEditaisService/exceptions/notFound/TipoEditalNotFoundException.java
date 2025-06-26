package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class TipoEditalNotFoundException extends RuntimeException {
    public TipoEditalNotFoundException() {
        super("Tipo de edital não encontrado");
    }
}