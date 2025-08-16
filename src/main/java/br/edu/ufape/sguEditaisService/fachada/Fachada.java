package br.edu.ufape.sguEditaisService.fachada;

import br.edu.ufape.sguEditaisService.auth.AuthenticatedUserProvider;
import br.edu.ufape.sguEditaisService.exceptions.notFound.StatusPersonalizadoNotFoundException;
import br.edu.ufape.sguEditaisService.models.*;
import br.edu.ufape.sguEditaisService.servicos.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Fachada {

    private final ModelMapper modelMapper;

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
    private final StatusPersonalizadoService statusPersonalizadoService;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    // =================== CampoPersonalizado ===================

    public CampoPersonalizado salvarCampoPersonalizado(CampoPersonalizado campoPersonalizado) {
        if (campoPersonalizado.getEtapa() != null && campoPersonalizado.getEtapa().getId() != null) {
            Etapa etapa = etapaService.buscarPorIdEtapa(campoPersonalizado.getEtapa().getId());
            campoPersonalizado.setEtapa(etapa);
        }

        if (campoPersonalizado.getEdital() != null && campoPersonalizado.getEdital().getId() != null) {
            Edital edital = editalService.buscarPorIdEdital(campoPersonalizado.getEdital().getId());
            campoPersonalizado.setEdital(edital);
        }

        return campoPersonalizadoService.salvarCampoPersonalizado(campoPersonalizado);
    }

    public CampoPersonalizado buscarPorIdCampoPersonalizado(Long id) {
        return campoPersonalizadoService.buscarPorIdCampoPersonalizado(id);
    }

    public List<CampoPersonalizado> listarCampoPersonalizado() {
        return campoPersonalizadoService.listarCampoPersonalizado();
    }

    public CampoPersonalizado editarCampoPersonalizado(Long id, CampoPersonalizado obj) {
        CampoPersonalizado original = campoPersonalizadoService.buscarPorIdCampoPersonalizado(id);
        modelMapper.map(obj, original);

        if (obj.getEtapa() != null && obj.getEtapa().getId() != null) {
            Etapa etapa = etapaService.buscarPorIdEtapa(obj.getEtapa().getId());
            original.setEtapa(etapa);
        }
        if (obj.getEdital() != null && obj.getEdital().getId() != null) {
            Edital edital = editalService.buscarPorIdEdital(obj.getEdital().getId());
            original.setEdital(edital);
        }

        return campoPersonalizadoService.salvarCampoPersonalizado(original);
    }

    public void deletarCampoPersonalizado(Long id) {
        campoPersonalizadoService.deletarCampoPersonalizado(id);
    }

    // =================== Documento ===================

    public Documento salvarDocumento(Documento documento) {
        if (documento.getEtapa() != null && documento.getEtapa().getId() != null) {
            Etapa etapa = etapaService.buscarPorIdEtapa(documento.getEtapa().getId());
            documento.setEtapa(etapa);
        }

        if (documento.getInscricao() != null && documento.getInscricao().getId() != null) {
            Inscricao inscricao = inscricaoService.buscarPorIdInscricao(documento.getInscricao().getId());
            documento.setInscricao(inscricao);
        }

        return documentoService.salvarDocumento(documento);
    }

    public Documento buscarPorIdDocumento(Long id) {
        return documentoService.buscarPorIdDocumento(id);
    }

    public List<Documento> listarDocumento() {
        return documentoService.listarDocumento();
    }

    public Documento editarDocumento(Long id, Documento obj) {
        Documento original = documentoService.buscarPorIdDocumento(id);
        modelMapper.map(obj, original);

        if (obj.getEtapa() != null && obj.getEtapa().getId() != null) {
            Etapa etapa = etapaService.buscarPorIdEtapa(obj.getEtapa().getId());
            original.setEtapa(etapa);
        }
        if (obj.getInscricao() != null && obj.getInscricao().getId() != null) {
            Inscricao inscricao = inscricaoService.buscarPorIdInscricao(obj.getInscricao().getId());
            original.setInscricao(inscricao);
        }

        return documentoService.salvarDocumento(original);
    }

    public void deletarDocumento(Long id) {
        documentoService.deletarDocumento(id);
    }

    // =================== DocumentoEdital ===================

    public DocumentoEdital salvarDocumentoEdital(DocumentoEdital obj) {
        if (obj.getEdital() != null && obj.getEdital().getId() != null) {
            Edital edital = editalService.buscarPorIdEdital(obj.getEdital().getId());
            obj.setEdital(edital);
        }
        return documentoEditalService.salvarDocumentoEdital(obj);
    }

    public DocumentoEdital buscarPorIdDocumentoEdital(Long id) {
        return documentoEditalService.buscarPorIdDocumentoEdital(id);
    }

    public List<DocumentoEdital> listarDocumentoEdital() {
        return documentoEditalService.listarDocumentoEdital();
    }

    public DocumentoEdital editarDocumentoEdital(Long id, DocumentoEdital obj) {
        DocumentoEdital original = documentoEditalService.buscarPorIdDocumentoEdital(id);
        modelMapper.map(obj, original);

        if (obj.getEdital() != null && obj.getEdital().getId() != null) {
            Edital edital = editalService.buscarPorIdEdital(obj.getEdital().getId());
            original.setEdital(edital);
        }

        return documentoEditalService.salvarDocumentoEdital(original);
    }

    public void deletarDocumentoEdital(Long id) {
        documentoEditalService.deletarDocumentoEdital(id);
    }

    // =================== Edital ===================

    public Edital salvarEdital(Edital obj) {
        if (obj.getTipoEdital() != null && obj.getTipoEdital().getId() != null) {
            TipoEdital tipoEdital = tipoEditalService.buscarPorIdTipoEdital(obj.getTipoEdital().getId());
            obj.setTipoEdital(tipoEdital);
        }

        if (obj.getStatusAtual() != null && obj.getStatusAtual().getId() != null) {
            StatusPersonalizado status = statusPersonalizadoService.buscarPorIdStatusPersonalizado(obj.getStatusAtual().getId());
            obj.setStatusAtual(status);
        }

        return editalService.salvarEdital(obj);
    }

    public Edital buscarPorIdEdital(Long id) {
        return editalService.buscarPorIdEdital(id);
    }

    public List<Edital> listarEdital() {
        return editalService.listarEdital();
    }

    public Edital editarEdital(Long id, Edital obj) {
        Edital edital = editalService.buscarPorIdEdital(id);
        modelMapper.map(obj, edital);

        if (obj.getTipoEdital() != null && obj.getTipoEdital().getId() != null) {
            TipoEdital tipoEdital = tipoEditalService.buscarPorIdTipoEdital(obj.getTipoEdital().getId());
            edital.setTipoEdital(tipoEdital);
        }

        if (obj.getStatusAtual() != null && obj.getStatusAtual().getId() != null) {
            StatusPersonalizado status = statusPersonalizadoService.buscarPorIdStatusPersonalizado(obj.getStatusAtual().getId());
            edital.setStatusAtual(status);
        }

        return editalService.salvarEdital(edital);
    }

    public void deletarEdital(Long id) {
        editalService.deletarEdital(id);
    }

    // =================== Etapa ===================

    public Etapa salvarEtapa(Etapa obj) {
        if (obj.getEdital() != null && obj.getEdital().getId() != null) {
            Edital edital = editalService.buscarPorIdEdital(obj.getEdital().getId());
            obj.setEdital(edital);
        }
        if (obj.getStatusAtual() != null && obj.getStatusAtual().getId() != null) {
            StatusPersonalizado status = statusPersonalizadoService.buscarPorIdStatusPersonalizado(obj.getStatusAtual().getId());
            obj.setStatusAtual(status);
        }
        return etapaService.salvarEtapa(obj);
    }

    public Etapa buscarPorIdEtapa(Long id) {
        return etapaService.buscarPorIdEtapa(id);
    }

    public List<Etapa> listarEtapa() {
        return etapaService.listarEtapa();
    }

    public Etapa editarEtapa(Long id, Etapa obj) {
        Etapa original = etapaService.buscarPorIdEtapa(id);
        modelMapper.map(obj, original);

        if (obj.getEdital() != null && obj.getEdital().getId() != null) {
            Edital edital = editalService.buscarPorIdEdital(obj.getEdital().getId());
            original.setEdital(edital);
        }
        if (obj.getStatusAtual() != null && obj.getStatusAtual().getId() != null) {
            StatusPersonalizado status = statusPersonalizadoService.buscarPorIdStatusPersonalizado(obj.getStatusAtual().getId());
            original.setStatusAtual(status);
        }

        return etapaService.salvarEtapa(original);
    }

    public void deletarEtapa(Long id) {
        etapaService.deletarEtapa(id);
    }

    // =================== HistoricoEtapaInscricao ===================

    public HistoricoEtapaInscricao salvarHistoricoEtapaInscricao(HistoricoEtapaInscricao obj) {
        if (obj.getEtapa() != null && obj.getEtapa().getId() != null) {
            Etapa etapa = etapaService.buscarPorIdEtapa(obj.getEtapa().getId());
            obj.setEtapa(etapa);
        }
        if (obj.getInscricao() != null && obj.getInscricao().getId() != null) {
            Inscricao inscricao = inscricaoService.buscarPorIdInscricao(obj.getInscricao().getId());
            obj.setInscricao(inscricao);
        }
        if (obj.getStatusPersonalizado() != null && obj.getStatusPersonalizado().getId() != null) {
            StatusPersonalizado status = statusPersonalizadoService.buscarPorIdStatusPersonalizado(obj.getStatusPersonalizado().getId());
            obj.setStatusPersonalizado(status);
        }
        return historicoEtapaInscricaoService.salvarHistoricoEtapaInscricao(obj);
    }

    public HistoricoEtapaInscricao buscarPorIdHistoricoEtapaInscricao(Long id) {
        return historicoEtapaInscricaoService.buscarPorIdHistoricoEtapaInscricao(id);
    }

    public List<HistoricoEtapaInscricao> listarHistoricoEtapaInscricao() {
        return historicoEtapaInscricaoService.listarHistoricoEtapaInscricao();
    }

    public HistoricoEtapaInscricao editarHistoricoEtapaInscricao(Long id, HistoricoEtapaInscricao obj) {
        HistoricoEtapaInscricao original = historicoEtapaInscricaoService.buscarPorIdHistoricoEtapaInscricao(id);
        modelMapper.map(obj, original);

        if (obj.getEtapa() != null && obj.getEtapa().getId() != null) {
            Etapa etapa = etapaService.buscarPorIdEtapa(obj.getEtapa().getId());
            original.setEtapa(etapa);
        }
        if (obj.getInscricao() != null && obj.getInscricao().getId() != null) {
            Inscricao inscricao = inscricaoService.buscarPorIdInscricao(obj.getInscricao().getId());
            original.setInscricao(inscricao);
        }
        if (obj.getStatusPersonalizado() != null && obj.getStatusPersonalizado().getId() != null) {
            StatusPersonalizado status = statusPersonalizadoService.buscarPorIdStatusPersonalizado(obj.getStatusPersonalizado().getId());
            original.setStatusPersonalizado(status);
        }

        return historicoEtapaInscricaoService.salvarHistoricoEtapaInscricao(original);
    }

    public void deletarHistoricoEtapaInscricao(Long id) {
        historicoEtapaInscricaoService.deletarHistoricoEtapaInscricao(id);
    }

    // =================== Inscricao ===================

    public Inscricao salvarInscricao(Inscricao obj) {
        UUID userId = authenticatedUserProvider.getUserId();
        obj.setIdUsuario(userId);

        if (obj.getEdital() != null && obj.getEdital().getId() != null) {
            Edital edital = editalService.buscarPorIdEdital(obj.getEdital().getId());
            obj.setEdital(edital);
        }
        if (obj.getStatusAtual() != null && obj.getStatusAtual().getId() != null) {
            StatusPersonalizado status = statusPersonalizadoService.buscarPorIdStatusPersonalizado(obj.getStatusAtual().getId());
            obj.setStatusAtual(status);
        }
        return inscricaoService.salvarInscricao(obj);
    }

    public Inscricao buscarPorIdInscricao(Long id) {
        return inscricaoService.buscarPorIdInscricao(id);
    }

    public List<Inscricao> listarInscricao() {
        return inscricaoService.listarInscricao();
    }

    public Inscricao editarInscricao(Long id, Inscricao obj) {
        Inscricao original = inscricaoService.buscarPorIdInscricao(id);
        modelMapper.map(obj, original);

        if (obj.getEdital() != null && obj.getEdital().getId() != null) {
            Edital edital = editalService.buscarPorIdEdital(obj.getEdital().getId());
            original.setEdital(edital);
        }
        if (obj.getStatusAtual() != null && obj.getStatusAtual().getId() != null) {
            StatusPersonalizado status = statusPersonalizadoService.buscarPorIdStatusPersonalizado(obj.getStatusAtual().getId());
            original.setStatusAtual(status);
        }

        return inscricaoService.salvarInscricao(original);
    }

    public void deletarInscricao(Long id) {
        inscricaoService.deletarInscricao(id);
    }

    // =================== PermissaoEtapa ===================

    public PermissaoEtapa salvarPermissaoEtapa(PermissaoEtapa obj) {
        if (obj.getEtapa() != null && obj.getEtapa().getId() != null) {
            Etapa etapa = etapaService.buscarPorIdEtapa(obj.getEtapa().getId());
            obj.setEtapa(etapa);
        }
        return permissaoEtapaService.salvarPermissaoEtapa(obj);
    }

    public PermissaoEtapa buscarPorIdPermissaoEtapa(Long id) {
        return permissaoEtapaService.buscarPorIdPermissaoEtapa(id);
    }

    public List<PermissaoEtapa> listarPermissaoEtapa() {
        return permissaoEtapaService.listarPermissaoEtapa();
    }

    public PermissaoEtapa editarPermissaoEtapa(Long id, PermissaoEtapa obj) {
        PermissaoEtapa original = permissaoEtapaService.buscarPorIdPermissaoEtapa(id);
        modelMapper.map(obj, original);

        if (obj.getEtapa() != null && obj.getEtapa().getId() != null) {
            Etapa etapa = etapaService.buscarPorIdEtapa(obj.getEtapa().getId());
            original.setEtapa(etapa);
        }

        return permissaoEtapaService.salvarPermissaoEtapa(original);
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

    public ValorCampo salvarValorCampo(ValorCampo obj) {
        if (obj.getInscricao() != null && obj.getInscricao().getId() != null) {
            Inscricao inscricao = inscricaoService.buscarPorIdInscricao(obj.getInscricao().getId());
            obj.setInscricao(inscricao);
        }
        if (obj.getCampoPersonalizado() != null && obj.getCampoPersonalizado().getId() != null) {
            CampoPersonalizado campo = campoPersonalizadoService.buscarPorIdCampoPersonalizado(obj.getCampoPersonalizado().getId());
            obj.setCampoPersonalizado(campo);
        }
        return valorCampoService.salvarValorCampo(obj);
    }

    public ValorCampo buscarPorIdValorCampo(Long id) {
        return valorCampoService.buscarPorIdValorCampo(id);
    }

    public List<ValorCampo> listarValorCampo() {
        return valorCampoService.listarValorCampo();
    }

    public ValorCampo editarValorCampo(Long id, ValorCampo obj) {
        ValorCampo original = valorCampoService.buscarPorIdValorCampo(id);
        modelMapper.map(obj, original);

        if (obj.getInscricao() != null && obj.getInscricao().getId() != null) {
            Inscricao inscricao = inscricaoService.buscarPorIdInscricao(obj.getInscricao().getId());
            original.setInscricao(inscricao);
        }
        if (obj.getCampoPersonalizado() != null && obj.getCampoPersonalizado().getId() != null) {
            CampoPersonalizado campo = campoPersonalizadoService.buscarPorIdCampoPersonalizado(obj.getCampoPersonalizado().getId());
            original.setCampoPersonalizado(campo);
        }

        return valorCampoService.salvarValorCampo(original);
    }

    public void deletarValorCampo(Long id) {
        valorCampoService.deletarValorCampo(id);
    }

    // =================== StatusPersonalizado ===================

    public StatusPersonalizado salvarStatusPersonalizado(StatusPersonalizado statusPersonalizado) {
        return statusPersonalizadoService.salvarStatusPersonalizado(statusPersonalizado);
    }

    public StatusPersonalizado buscarPorIdStatusPersonalizado(Long id) throws StatusPersonalizadoNotFoundException {
        return statusPersonalizadoService.buscarPorIdStatusPersonalizado(id);
    }

    public List<StatusPersonalizado> listarStatusPersonalizado() {
        return statusPersonalizadoService.listarStatusPersonalizados();
    }

    public StatusPersonalizado editarStatusPersonalizado(Long id, StatusPersonalizado statusPersonalizado) throws StatusPersonalizadoNotFoundException {
        return statusPersonalizadoService.editarStatusPersonalizado(id, statusPersonalizado);
    }

    public void deletarStatusPersonalizado(Long id) throws StatusPersonalizadoNotFoundException {
        statusPersonalizadoService.deletarStatusPersonalizado(id);
    }
}
