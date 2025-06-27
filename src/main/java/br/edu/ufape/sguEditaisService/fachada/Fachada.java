package br.edu.ufape.sguEditaisService.fachada;

import br.edu.ufape.sguEditaisService.models.*;
import br.edu.ufape.sguEditaisService.servicos.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Fachada {

    private final CampoPersonalizadoService campoPersonalizadoService;
    private final DocumentoService documentoService;
    private final DocumentoEditalService documentoEditalService;
    private final EditalService editalService;
    private final EtapaService etapaService;
    private final HistoricoEtapaInscricaoService historicoEtapaInscricaoService;
    private final InscricaoService inscricaoService;
    private final PermissaoEtapaService permissaoEtapaService;
    private final TipoEditalService tipoEditalService;
    private final ValorCampoService valorCampoService;


    // =================== CampoPersonalizado ===================

    public CampoPersonalizado salvarCampoPersonalizado(CampoPersonalizado campoPersonalizado) {
        return campoPersonalizadoService.salvarCampoPersonalizado(campoPersonalizado);
    }

    public CampoPersonalizado buscarPorIdCampoPersonalizado(Long id) {
        return campoPersonalizadoService.buscarPorIdCampoPersonalizado(id);
    }

    public List<CampoPersonalizado> listarCampoPersonalizado() {
        return campoPersonalizadoService.listarCampoPersonalizado();
    }

    public CampoPersonalizado editarCampoPersonalizado(Long id, CampoPersonalizado campoPersonalizado) {
        return campoPersonalizadoService.editarCampoPersonalizado(id, campoPersonalizado);
    }

    public void deletarCampoPersonalizado(Long id) {
        campoPersonalizadoService.deletarCampoPersonalizado(id);
    }

    // =================== Documento ===================

    public Documento salvarDocumento(Documento documento) {
        return documentoService.salvarDocumento(documento);
    }

    public Documento buscarPorIdDocumento(Long id) {
        return documentoService.buscarPorIdDocumento(id);
    }

    public List<Documento> listarDocumento() {
        return documentoService.listarDocumento();
    }

    public Documento editarDocumento(Long id, Documento documento) {
        return documentoService.editarDocumento(id, documento);
    }

    public void deletarDocumento(Long id) {
        documentoService.deletarDocumento(id);
    }

    // =================== DocumentoEdital ===================

    public DocumentoEdital salvarDocumentoEdital(DocumentoEdital documentoEdital) {
        return documentoEditalService.salvarDocumetoEdial(documentoEdital);
    }

    public DocumentoEdital buscarPorIdDocumentoEdital(Long id) {
        return documentoEditalService.buscarPorIdDocumentoEdital(id);
    }

    public List<DocumentoEdital> listarDocumentoEdital() {
        return documentoEditalService.listarDocumentoEdital();
    }

    public DocumentoEdital editarDocumentoEdital(Long id, DocumentoEdital documentoEdital) {
        return documentoEditalService.editarDocumentoEdital(id, documentoEdital);
    }

    public void deletarDocumentoEdital(Long id) {
        documentoEditalService.deletarDocumentoEdital(id);
    }

    // =================== Edital ===================

    public Edital salvarEdital(Edital edital) {
        return editalService.salvarEdital(edital);
    }

    public Edital buscarPorIdEdital(Long id) {
        return editalService.buscarPorIdEdital(id);
    }

    public List<Edital> listarEdital() {
        return editalService.listarEdital();
    }

    public Edital editarEdital(Long id, Edital edital) {
        return editalService.editarEdital(id, edital);
    }

    public void deletarEdital(Long id) {
        editalService.deletarEdital(id);
    }

    // =================== Etapa ===================

    public Etapa salvarEtapa(Etapa etapa) {
        return etapaService.salvarEtapa(etapa);
    }

    public Etapa buscarPorIdEtapa(Long id) {
        return etapaService.buscarPorIdEtapa(id);
    }

    public List<Etapa> listarEtapa() {
        return etapaService.listarEtapa();
    }

    public Etapa editarEtapa(Long id, Etapa etapa) {
        return etapaService.editarEtapa(id, etapa);
    }

    public void deletarEtapa(Long id) {
        etapaService.deletarEtapa(id);
    }

    // =================== HistoricoEtapaInscricao ===================

    public HistoricoEtapaInscricao salvarHistoricoEtapaInscricao(HistoricoEtapaInscricao historicoEtapaInscricao) {
        return historicoEtapaInscricaoService.salvarHistoricoEtapaInscricao(historicoEtapaInscricao);
    }

    public HistoricoEtapaInscricao buscarPorIdHistoricoEtapaInscricao(Long id) {
        return historicoEtapaInscricaoService.buscarPorIdHistoricoEtapaInscricao(id);
    }

    public List<HistoricoEtapaInscricao> listarHistoricoEtapaInscricao() {
        return historicoEtapaInscricaoService.listarHistoricoEtapaInscricao();
    }

    public HistoricoEtapaInscricao editarHistoricoEtapaInscricao(Long id, HistoricoEtapaInscricao historicoEtapaInscricao) {
        return historicoEtapaInscricaoService.editarHistoricoEtapaInscricao(id, historicoEtapaInscricao);
    }

    public void deletarHistoricoEtapaInscricao(Long id) {
        historicoEtapaInscricaoService.deletarHistoricoEtapaInscricao(id);
    }

    // =================== Inscricao ===================

    public Inscricao salvarInscricao(Inscricao inscricao) {
        return inscricaoService.salvarInscricao(inscricao);
    }

    public Inscricao buscarPorIdInscricao(Long id) {
        return inscricaoService.buscarPorIdInscricao(id);
    }

    public List<Inscricao> listarInscricao() {
        return inscricaoService.listarInscricao();
    }

    public Inscricao editarInscricao(Long id, Inscricao inscricao) {
        return inscricaoService.editarInscricao(id, inscricao);
    }

    public void deletarInscricao(Long id) {
        inscricaoService.deletarInscricao(id);
    }

    // =================== PermissaoEtapa ===================

    public PermissaoEtapa salvarPermissaoEtapa(PermissaoEtapa permissaoEtapa) {
        return permissaoEtapaService.salvarPermissaoEtapa(permissaoEtapa);
    }

    public PermissaoEtapa buscarPorIdPermissaoEtapa(Long id) {
        return permissaoEtapaService.buscarPorIdPermissaoEtapa(id);
    }

    public List<PermissaoEtapa> listarPermissaoEtapa() {
        return permissaoEtapaService.listarPermissaoEtapa();
    }

    public PermissaoEtapa editarPermissaoEtapa(Long id, PermissaoEtapa permissaoEtapa) {
        return permissaoEtapaService.editarPermissaoEtapa(id, permissaoEtapa);
    }

    public void deletarPermissaoEtapa(Long id) {
        permissaoEtapaService.deletarPermissaoEtapa(id);
    }

    // =================== TipoEdital ===================

    public TipoEdital salvarTipoEdital(TipoEdital tipoEdital) {
        return tipoEditalService.salvarTipoEdital(tipoEdital);
    }

    public TipoEdital buscarPorIdTipoEdital(Long id) {
        return tipoEditalService.buscarPorIdTipoEdital(id);
    }

    public List<TipoEdital> listarTipoEdital() {
        return tipoEditalService.listarTipoEdital();
    }

    public TipoEdital editarTipoEdital(Long id, TipoEdital tipoEdital) {
        return tipoEditalService.editarTipoEdital(id, tipoEdital);
    }

    public void deletarTipoEdital(Long id) {
        tipoEditalService.deletarTipoEdital(id);
    }

    // =================== ValorCampo ===================

    public ValorCampo salvarValorCampo(ValorCampo valorCampo) {
        return valorCampoService.salvarValorCampo(valorCampo);
    }

    public ValorCampo buscarPorIdValorCampo(Long id) {
        return valorCampoService.buscarPorIdValorCampo(id);
    }

    public List<ValorCampo> listarValorCampo() {
        return valorCampoService.listarValorCampo();
    }

    public ValorCampo editarValorCampo(Long id, ValorCampo valorCampo) {
        return valorCampoService.editarValorCampo(id, valorCampo);
    }

    public void deletarValorCampo(Long id) {
        valorCampoService.deletarValorCampo(id);
    }
}
