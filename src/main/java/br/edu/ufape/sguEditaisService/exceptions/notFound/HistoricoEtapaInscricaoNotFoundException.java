package br.edu.ufape.sguEditaisService.exceptions.notFound;

public class HistoricoEtapaInscricaoNotFoundException extends RuntimeException {
    public HistoricoEtapaInscricaoNotFoundException() {
        super("Histórico da etapa de inscrição não encontrado");
    }
}