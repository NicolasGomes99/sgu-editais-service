package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class EditalNotFoundException extends RuntimeException {
    public EditalNotFoundException() {
        super("Edital não encontrado");
    }
}
