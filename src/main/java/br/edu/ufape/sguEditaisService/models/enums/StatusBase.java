package br.edu.ufape.sguEditaisService.models.enums;

import lombok.Getter;

@Getter
public enum StatusBase {
    // Status de EDITAL
    EDITAL_CRIADO("Criado", TipoStatus.EDITAL),
    EDITAL_PUBLICADO("Publicado", TipoStatus.EDITAL),
    EDITAL_EM_ANDAMENTO("Em Andamento", TipoStatus.EDITAL),
    EDITAL_FINALIZADO("Finalizado", TipoStatus.EDITAL),
    EDITAL_CANCELADO("Cancelado", TipoStatus.EDITAL),

    // Status de INSCRIÇÃO (Geral)
    INSCRICAO_INICIADA("Inscrição Iniciada", TipoStatus.INSCRICAO),
    INSCRICAO_ENVIADA("Inscrição Enviada", TipoStatus.INSCRICAO),
    AGUARDANDO_PAGAMENTO("Aguardando Pagamento", TipoStatus.INSCRICAO),
    PAGO("Pago", TipoStatus.INSCRICAO),
    ISENTO("Isento", TipoStatus.INSCRICAO),
    EM_ANALISE("Em Análise", TipoStatus.INSCRICAO),
    DEFERIDO("Deferido", TipoStatus.INSCRICAO),
    INDEFERIDO("Indeferido", TipoStatus.INSCRICAO),

    // Status de ETAPA (Refatorados conforme solicitado)
    ETAPA_EM_ANALISE("Em Análise", TipoStatus.ETAPA),
    ETAPA_APROVADA("Aprovada", TipoStatus.ETAPA),
    ETAPA_REPROVADA("Reprovada", TipoStatus.ETAPA);

    private final String nome;
    private final TipoStatus tipo;

    StatusBase(String nome, TipoStatus tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }
}