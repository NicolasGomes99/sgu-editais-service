package br.edu.ufape.sguEditaisService.fachada;

import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.InscricaoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.usuario.UsuarioResponse;
import br.edu.ufape.sguEditaisService.auth.AuthenticatedUserProvider;
import br.edu.ufape.sguEditaisService.comunicacao.dto.edital.EditalResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.tipoEdital.TipoEditalResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.unidadeAdministrativa.UnidadeAdministrativaResponse;
import br.edu.ufape.sguEditaisService.exceptions.GlobalAccessDeniedException;
import br.edu.ufape.sguEditaisService.exceptions.InscricaoDuplicadaException;
import br.edu.ufape.sguEditaisService.exceptions.notFound.EtapaNotFoundException;
import br.edu.ufape.sguEditaisService.exceptions.notFound.StatusPersonalizadoNotFoundException;
import br.edu.ufape.sguEditaisService.models.*;
import br.edu.ufape.sguEditaisService.servicos.interfaces.*;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class Fachada {

    private final ModelMapper modelMapper;

    private final DataEtapaService dataEtapaService;
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
    private final AuthServiceHandler authServiceHandler;

    // =================== CampoPersonalizado ===================

    public CampoPersonalizado salvarCampoPersonalizado(CampoPersonalizado campoPersonalizado) {
        int parentCount = 0;
        if (campoPersonalizado.getEdital() != null && campoPersonalizado.getEdital().getId() != null) parentCount++;
        if (campoPersonalizado.getEtapa() != null && campoPersonalizado.getEtapa().getId() != null) parentCount++;
        if (campoPersonalizado.getTipoEditalModelo() != null && campoPersonalizado.getTipoEditalModelo().getId() != null)
            parentCount++;

        if (parentCount > 1) {
            throw new IllegalArgumentException("Um CampoPersonalizado só pode pertencer a um Edital, a uma Etapa ou a um TipoEdital (modelo), mas não a múltiplos.");
        }
        if (parentCount == 0) {
            throw new IllegalArgumentException("Um CampoPersonalizado deve pertencer a um Edital, a uma Etapa ou a um TipoEdital (modelo).");
        }

        if (campoPersonalizado.getEtapa() != null && campoPersonalizado.getEtapa().getId() != null) {
            Etapa etapa = etapaService.buscarPorIdEtapa(campoPersonalizado.getEtapa().getId());
            campoPersonalizado.setEtapa(etapa);
        }

        if (campoPersonalizado.getEdital() != null && campoPersonalizado.getEdital().getId() != null) {
            Edital edital = editalService.buscarPorIdEdital(campoPersonalizado.getEdital().getId());
            campoPersonalizado.setEdital(edital);
        }

        if (campoPersonalizado.getTipoEditalModelo() != null && campoPersonalizado.getTipoEditalModelo().getId() != null) {
            TipoEdital tipoEdital = tipoEditalService.buscarPorIdTipoEdital(campoPersonalizado.getTipoEditalModelo().getId());
            campoPersonalizado.setTipoEditalModelo(tipoEdital);
        }

        return campoPersonalizadoService.salvarCampoPersonalizado(campoPersonalizado);
    }

    public CampoPersonalizado buscarPorIdCampoPersonalizado(Long id) {
        return campoPersonalizadoService.buscarPorIdCampoPersonalizado(id);
    }

    public Page<CampoPersonalizado> listarCampoPersonalizado(Pageable pageable) {
        return campoPersonalizadoService.listarCampoPersonalizado(pageable);
    }

    public CampoPersonalizado editarCampoPersonalizado(Long id, CampoPersonalizado obj) {
        CampoPersonalizado original = campoPersonalizadoService.buscarPorIdCampoPersonalizado(id);

        if (obj.getEdital() != null && !obj.getEdital().getId().equals(original.getEdital() != null ? original.getEdital().getId() : null)) {
            throw new IllegalArgumentException("Não é permitido mover um Campo para um Edital diferente.");
        }
        if (obj.getEtapa() != null && !obj.getEtapa().getId().equals(original.getEtapa() != null ? original.getEtapa().getId() : null)) {
            throw new IllegalArgumentException("Não é permitido mover um Campo para uma Etapa diferente.");
        }
        if (obj.getTipoEditalModelo() != null && !obj.getTipoEditalModelo().getId().equals(original.getTipoEditalModelo() != null ? original.getTipoEditalModelo().getId() : null)) {
            throw new IllegalArgumentException("Não é permitido mover um Campo para um Modelo (TipoEdital) diferente.");
        }

        modelMapper.map(obj, original);

        if (original.getEtapa() != null && original.getEtapa().getId() != null) {
            Etapa etapa = etapaService.buscarPorIdEtapa(original.getEtapa().getId());
            original.setEtapa(etapa);
        }
        if (original.getEdital() != null && original.getEdital().getId() != null) {
            Edital edital = editalService.buscarPorIdEdital(original.getEdital().getId());
            original.setEdital(edital);
        }
        if (original.getTipoEditalModelo() != null && original.getTipoEditalModelo().getId() != null) {
            TipoEdital tipoEdital = tipoEditalService.buscarPorIdTipoEdital(original.getTipoEditalModelo().getId());
            original.setTipoEditalModelo(tipoEdital);
        }

        return campoPersonalizadoService.salvarCampoPersonalizado(original);
    }

    public void deletarCampoPersonalizado(Long id) {
        campoPersonalizadoService.deletarCampoPersonalizado(id);
    }

    public List<CampoPersonalizado> listarCamposPorEdital(Long editalId) {
        return campoPersonalizadoService.listarCamposPorEdital(editalId);
    }

    public List<CampoPersonalizado> listarCamposPorTipoEdital(Long tipoEditalId) {
        return campoPersonalizadoService.listarCamposPorTipoEdital(tipoEditalId);
    }

    public List<CampoPersonalizado> listarCamposPorEtapa(Long etapaId) {
        return campoPersonalizadoService.listarCamposPorEtapa(etapaId);
    }

    public CampoPersonalizado alternarObrigatoriedade(Long campoId) {
        return campoPersonalizadoService.alternarObrigatoriedade(campoId);
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

    public Page<DocumentoEdital> listarDocumentoEdital(Pageable pageable) {
        return documentoEditalService.listarDocumentoEdital(pageable);
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

    public EditalResponse salvarEdital(Edital obj) {

        boolean temPermissao = authServiceHandler.verificarVinculo(obj.getIdUnidadeAdministrativa());
        if (!temPermissao) {
            throw new GlobalAccessDeniedException("Você não tem permissão para Criar este edital.");
        }

        if (obj.getTipoEdital() != null && obj.getTipoEdital().getId() != null) {
            obj.setTipoEdital(tipoEditalService.buscarPorIdTipoEdital(obj.getTipoEdital().getId()));
        }
        if (obj.getStatusAtual() != null && obj.getStatusAtual().getId() != null) {
            obj.setStatusAtual(statusPersonalizadoService.buscarPorIdStatusPersonalizado(obj.getStatusAtual().getId()));
        }
        Edital editalSalvo = editalService.salvarEdital(obj);
        return mapToEditalResponse(editalSalvo);
    }

    public EditalResponse criarEditalAPartirDeModelo(Long templateId, Edital editalBase) {
        if (editalBase.getTipoEdital() != null && editalBase.getTipoEdital().getId() != null) {
            editalBase.setTipoEdital(tipoEditalService.buscarPorIdTipoEdital(editalBase.getTipoEdital().getId()));
        }
        if (editalBase.getStatusAtual() != null && editalBase.getStatusAtual().getId() != null) {
            editalBase.setStatusAtual(statusPersonalizadoService.buscarPorIdStatusPersonalizado(editalBase.getStatusAtual().getId()));
        }
        Edital editalSalvo = editalService.criarEditalAPartirDeModelo(templateId, editalBase);
        return mapToEditalResponse(editalSalvo);
    }

    public EditalResponse buscarPorIdEdital(Long id) {
        Edital edital = editalService.buscarPorIdEdital(id);
        return mapToEditalResponse(edital);
    }

    public Page<EditalResponse> listarEdital(Pageable pageable) {
        Page<Edital> paginaDeEditais = editalService.listarEdital(pageable);
        return paginaDeEditais.map(this::mapToEditalResponse);
    }

    public EditalResponse editarEdital(Long id, Edital obj) {
        Edital edital = editalService.buscarPorIdEdital(id);
        modelMapper.map(obj, edital);
        if (obj.getTipoEdital() != null && obj.getTipoEdital().getId() != null) {
            edital.setTipoEdital(tipoEditalService.buscarPorIdTipoEdital(obj.getTipoEdital().getId()));
        }
        if (obj.getStatusAtual() != null && obj.getStatusAtual().getId() != null) {
            edital.setStatusAtual(statusPersonalizadoService.buscarPorIdStatusPersonalizado(obj.getStatusAtual().getId()));
        }
        Edital editalAtualizado = editalService.salvarEdital(edital);
        return mapToEditalResponse(editalAtualizado);
    }

    public Page<EditalResponse> listarEditaisPublicados(Pageable pageable) {
        Page<Edital> editais = editalService.listarEditaisPublicados(pageable);
        return editais.map(this::mapToEditalResponse);
    }

    public EditalResponse atualizarStatusEdital(Long editalId, Long novoStatusId) {
        Edital editalAtualizado = editalService.atualizarStatusEdital(editalId, novoStatusId);
        return mapToEditalResponse(editalAtualizado);
    }

    private EditalResponse mapToEditalResponse(Edital edital) {
        EditalResponse response = new EditalResponse(edital, modelMapper);
        if (response.getTipoEdital() != null && edital.getTipoEdital().getIdUnidadeAdministrativa() != null) {
            UnidadeAdministrativaResponse unidade = authServiceHandler.buscarUnidadeAdministrativa(edital.getTipoEdital().getIdUnidadeAdministrativa());
            response.getTipoEdital().setUnidadeAdministrativa(unidade);
        }
        return response;
    }

    public void deletarEdital(Long id) {
        editalService.deletarEdital(id);
    }

    public TipoEdital transformarEditalEmModelo(Long editalId, String nomeModelo, String descricaoModelo) {
        return editalService.transformarEditalEmModelo(editalId, nomeModelo, descricaoModelo);
    }


    // =================== Etapa ===================

    public Etapa salvarEtapa(Etapa obj) {
        int parentCount = 0;
        if (obj.getEdital() != null && obj.getEdital().getId() != null) parentCount++;
        if (obj.getTipoEditalModelo() != null && obj.getTipoEditalModelo().getId() != null) parentCount++;

        if (parentCount > 1) {
            throw new IllegalArgumentException("Uma Etapa só pode pertencer a um Edital ou a um TipoEdital (modelo), mas não a ambos.");
        }
        if (parentCount == 0) {
            throw new IllegalArgumentException("Uma Etapa deve pertencer a um Edital ou a um TipoEdital (modelo).");
        }

        if (obj.getEdital() != null && obj.getEdital().getId() != null) {
            Edital edital = editalService.buscarPorIdEdital(obj.getEdital().getId());
            obj.setEdital(edital);
        }
        if (obj.getTipoEditalModelo() != null && obj.getTipoEditalModelo().getId() != null) {
            TipoEdital tipoEdital = tipoEditalService.buscarPorIdTipoEdital(obj.getTipoEditalModelo().getId());
            obj.setTipoEditalModelo(tipoEdital);
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

    public Page<Etapa> listarEtapa(Pageable pageable) {
        return etapaService.listarEtapa(pageable);
    }

    public Etapa editarEtapa(Long id, Etapa obj) {
        Etapa original = etapaService.buscarPorIdEtapa(id);

        if (obj.getEdital() != null && !obj.getEdital().getId().equals(original.getEdital() != null ? original.getEdital().getId() : null)) {
            throw new IllegalArgumentException("Não é permitido mover uma Etapa para um Edital diferente.");
        }
        if (obj.getTipoEditalModelo() != null && !obj.getTipoEditalModelo().getId().equals(original.getTipoEditalModelo() != null ? original.getTipoEditalModelo().getId() : null)) {
            throw new IllegalArgumentException("Não é permitido mover uma Etapa para um Modelo (TipoEdital) diferente.");
        }

        modelMapper.map(obj, original);

        if (original.getEdital() != null && original.getEdital().getId() != null) {
            Edital edital = editalService.buscarPorIdEdital(original.getEdital().getId());
            original.setEdital(edital);
        }
        if (original.getTipoEditalModelo() != null && original.getTipoEditalModelo().getId() != null) {
            TipoEdital tipoEdital = tipoEditalService.buscarPorIdTipoEdital(original.getTipoEditalModelo().getId());
            original.setTipoEditalModelo(tipoEdital);
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

    public List<Etapa> listarEtapasPorEdital(Long editalId) {
        return etapaService.listarEtapasPorEdital(editalId);
    }

    public List<Etapa> listarEtapasPorTipoEdital(Long tipoEditalId) {
        return etapaService.listarEtapasPorTipoEdital(tipoEditalId);
    }

    public void atualizarOrdemEtapas(List<Long> idsEtapasEmOrdem) {
        etapaService.atualizarOrdemEtapas(idsEtapasEmOrdem);
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

    @Transactional
    public InscricaoResponse salvarInscricao(Inscricao obj) {
        UUID userId = authenticatedUserProvider.getUserId();

        if (obj.getEdital() == null || obj.getEdital().getId() == null) {
            throw new IllegalArgumentException("O ID do Edital é obrigatório.");
        }

        if (inscricaoService.existeInscricao(userId, obj.getEdital().getId())) {
            throw new InscricaoDuplicadaException();
        }

        Edital edital = editalService.buscarPorIdEdital(obj.getEdital().getId());
        obj.setEdital(edital);
        obj.setIdUsuario(userId);

        // Tenta achar por nome "Inscrição" ou pega a primeira (ordem 1) VERIFICAR!!!!
        Etapa etapaInicial = edital.getEtapas().stream()
                .filter(e -> e.getNome().trim().equalsIgnoreCase("Inscrição"))
                .findFirst()
                .orElseGet(() -> edital.getEtapas().stream()
                        .min(Comparator.comparingInt(Etapa::getOrdem))
                        .orElseThrow(() -> new EtapaNotFoundException("O edital não possui etapas configuradas.")));

        obj.setEtapaAtual(etapaInicial);

        dataEtapaService.validarVigencia(etapaInicial);

        if (obj.getStatusAtual() != null && obj.getStatusAtual().getId() != null) {
            StatusPersonalizado status = statusPersonalizadoService.buscarPorIdStatusPersonalizado(obj.getStatusAtual().getId());
            obj.setStatusAtual(status);
        }

        Inscricao inscricaoSalva = inscricaoService.salvarInscricao(obj);

        HistoricoEtapaInscricao historico = new HistoricoEtapaInscricao();
        historico.setInscricao(inscricaoSalva);
        historico.setEtapa(etapaInicial);
        historico.setDataAacao(LocalDateTime.now());
        historico.setObservacao("Inscrição realizada pelo candidato.");

        historicoEtapaInscricaoService.salvarHistoricoEtapaInscricao(historico);

        return mapToInscricaoResponse(inscricaoSalva);
    }

    public InscricaoResponse buscarPorIdInscricao(Long id) {
        Inscricao inscricao = inscricaoService.buscarPorIdInscricao(id);
        return mapToInscricaoResponse(inscricao);
    }

    public Page<InscricaoResponse> listarInscricao(Pageable pageable) {
        Page<Inscricao> paginaInscricao = inscricaoService.listarInscricao(pageable);
        return paginaInscricao.map(this::mapToInscricaoResponse);
    }

    @Transactional
    public InscricaoResponse editarInscricao(Long id, Inscricao obj) {
        Inscricao original = inscricaoService.buscarPorIdInscricao(id);

        verificarDonoInscricao(original);

        dataEtapaService.validarVigencia(original.getEtapaAtual());

        if (obj.getStatusAtual() != null && obj.getStatusAtual().getId() != null) {
            StatusPersonalizado status = statusPersonalizadoService.buscarPorIdStatusPersonalizado(obj.getStatusAtual().getId());
            original.setStatusAtual(status);
        }

        modelMapper.map(obj, original);

        Inscricao inscricaoSalva = inscricaoService.salvarInscricao(original);

        HistoricoEtapaInscricao historicoLog = new HistoricoEtapaInscricao();
        historicoLog.setInscricao(inscricaoSalva);
        historicoLog.setEtapa(inscricaoSalva.getEtapaAtual());
        historicoLog.setDataAacao(LocalDateTime.now());
        historicoLog.setObservacao("Dados atualizados pelo usuário");
        historicoEtapaInscricaoService.salvarHistoricoEtapaInscricao(historicoLog);

        return mapToInscricaoResponse(inscricaoSalva);
    }

    public void deletarInscricao(Long id) {
        inscricaoService.deletarInscricao(id);
    }

//    private void validarCamposObrigatorios(Inscricao inscricao, List<ValorCampo> valoresEnviados) {
//        List<CampoPersonalizado> camposConfigurados = inscricao.getEdital().getCamposPersonalizados();
//
//        for (CampoPersonalizado campo : camposConfigurados) {
//            if (campo.isObrigatorio()) {
//                // Verifica se existe resposta para este campo na lista enviada
//                boolean preenchido = valoresEnviados.stream()
//                        .anyMatch(valor -> valor.getCampoPersonalizado().getId().equals(campo.getId())
//                                && valor.getValor() != null
//                                && !valor.getValor().isBlank());
//
//                if (!preenchido) {
//                    throw new IllegalArgumentException("O campo obrigatório '" + campo.getNome() + "' não foi preenchido.");
//                }
//            }
//        }
//    }

    private InscricaoResponse mapToInscricaoResponse(Inscricao inscricao) {
        InscricaoResponse response = new InscricaoResponse(inscricao, modelMapper);

        if (inscricao.getIdUsuario() != null) {
            UsuarioResponse usuario = authServiceHandler.buscarUsuarioPorId(inscricao.getIdUsuario());
            response.setUsuario(usuario);
        }

        return response;
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

    public TipoEditalResponse salvarTipoEdital(TipoEdital tipoEdital) {
        UUID userId = authenticatedUserProvider.getUserId();
        Page<UnidadeAdministrativaResponse> unidades;

        try {
            unidades = authServiceHandler.listarUnidadesDoFuncionarioPorId(userId);
        } catch (FeignException.NotFound e) {

            unidades = new PageImpl<>(Collections.emptyList());
        }

        if (unidades.isEmpty()) {
            unidades = authServiceHandler.listarUnidadesDoGestorPorId(userId);
        }

        if (unidades.isEmpty()) {
            throw new IllegalStateException("O usuário logado não está vinculado a nenhuma unidade administrativa (nem como funcionário, nem como gestor) e, portanto, não pode criar um modelo de edital.");
        }
        if (unidades.getTotalElements() > 1) {
            throw new IllegalStateException("O usuário está vinculado a múltiplas unidades administrativas. O sistema não pode determinar automaticamente a unidade de origem.");
        }

        Long idUnidade = unidades.getContent().get(0).getId();
        tipoEdital.setIdUnidadeAdministrativa(idUnidade);
        TipoEdital salvo = tipoEditalService.salvarTipoEdital(tipoEdital);
        return mapToTipoEditalResponse(salvo);
    }


    public TipoEditalResponse buscarPorIdTipoEdital(Long id) {
        TipoEdital tipoEdital = tipoEditalService.buscarPorIdTipoEdital(id);
        return mapToTipoEditalResponse(tipoEdital);
    }

    public Page<TipoEditalResponse> listarTipoEdital(Pageable pageable) {
        Page<TipoEdital> paginaDeEntidades = tipoEditalService.listarTipoEdital(pageable);
        return paginaDeEntidades.map(this::mapToTipoEditalResponse);
    }

    public TipoEditalResponse editarTipoEdital(Long id, TipoEdital tipoEdital) {
        TipoEdital existente = tipoEditalService.buscarPorIdTipoEdital(id);
        tipoEdital.setIdUnidadeAdministrativa(existente.getIdUnidadeAdministrativa());

        TipoEdital atualizado = tipoEditalService.editarTipoEdital(id, tipoEdital);
        return mapToTipoEditalResponse(atualizado);
    }

    public void deletarTipoEdital(Long id) {
        tipoEditalService.deletarTipoEdital(id);
    }

    public TipoEditalResponse duplicarTipoEdital(Long id) {
        TipoEdital duplicado = tipoEditalService.duplicarTipoEdital(id);
        return mapToTipoEditalResponse(duplicado);
    }

    private TipoEditalResponse mapToTipoEditalResponse(TipoEdital tipoEdital) {
        TipoEditalResponse response = new TipoEditalResponse(tipoEdital, modelMapper);
        if (tipoEdital.getIdUnidadeAdministrativa() != null) {
            UnidadeAdministrativaResponse unidade = authServiceHandler.buscarUnidadeAdministrativa(tipoEdital.getIdUnidadeAdministrativa());
            response.setUnidadeAdministrativa(unidade);
        }
        return response;
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

    // =================== Data Etapa ===================
    public DataEtapa salvarDataEtapa(DataEtapa dataEtapa) {
        Etapa etapa = etapaService.buscarPorIdEtapa(dataEtapa.getEtapa().getId());
        Edital edital = editalService.buscarPorIdEdital(dataEtapa.getEdital().getId());
        dataEtapa.setEtapa(etapa);
        dataEtapa.setEdital(edital);
        validarRelacaoEtapaEdital(etapa, edital);
        validarConsistenciaTemporal(dataEtapa, edital);
        return dataEtapaService.salvarDataEtapa(dataEtapa);
    }

    public DataEtapa buscarDataEtapaPorId(Long id) {
        return dataEtapaService.buscarDataEtapaPorId(id);
    }

    public List<DataEtapa> listarDatasEtapas() {
        return dataEtapaService.listarDatasEtapas();
    }

    public DataEtapa editarDataEtapa(Long id, DataEtapa dataEtapa) {

        DataEtapa dataEtapaExistente = dataEtapaService.buscarDataEtapaPorId(id);


        Long etapaIdReq = (dataEtapa.getEtapa() != null && dataEtapa.getEtapa().getId() != null)
                ? dataEtapa.getEtapa().getId()
                : dataEtapaExistente.getEtapa().getId();

        Long editalIdReq = (dataEtapa.getEdital() != null && dataEtapa.getEdital().getId() != null)
                ? dataEtapa.getEdital().getId()
                : dataEtapaExistente.getEdital().getId();

        Etapa etapa = etapaService.buscarPorIdEtapa(etapaIdReq);
        Edital edital = editalService.buscarPorIdEdital(editalIdReq);

        validarRelacaoEtapaEdital(etapa, edital);
        modelMapper.map(dataEtapa, dataEtapaExistente);

        dataEtapaExistente.setEtapa(etapa);
        dataEtapaExistente.setEdital(edital);

        validarConsistenciaTemporal(dataEtapaExistente, edital);

        return dataEtapaService.salvarDataEtapa(dataEtapaExistente);
    }


    public void deletarDataEtapa(Long id) {
        dataEtapaService.deletarDataEtapa(id);
    }

    private void validarRelacaoEtapaEdital(Etapa etapa, Edital edital) {
        boolean editalVinculadoCorretamente = false;

        if (etapa.getEdital() != null && etapa.getEdital().getId().equals(edital.getId())) {
            editalVinculadoCorretamente = true;
        }

        if (etapa.getTipoEditalModelo() != null && edital.getTipoEdital() != null &&
                etapa.getTipoEditalModelo().getId().equals(edital.getTipoEdital().getId())) {
            editalVinculadoCorretamente = true;
        }

        if (!editalVinculadoCorretamente) {
            throw new IllegalArgumentException("A etapa informada não pertence ao edital (seja diretamente ou via modelo).");
        }
    }

    private void validarConsistenciaTemporal(DataEtapa dataEtapaSendoSalva, Edital edital) {
        if (edital.getInicioInscricao() != null && LocalDateTime.now().isAfter(edital.getInicioInscricao())) {
            throw new IllegalStateException("Não é possível modificar as datas das etapas de um edital com inscrições já iniciadas.");
        }

        List<Etapa> etapasOrdenadas = etapaService.listarEtapasPorEdital(edital.getId());
        if (etapasOrdenadas.isEmpty()) {
            return;
        }

        List<DataEtapa> todasAsDatas = dataEtapaService.listarDatasEtapasPorEditalId(edital.getId());

        Map<Long, DataEtapa> mapaDatas = todasAsDatas.stream()
                .collect(Collectors.toMap(
                        d -> d.getEtapa().getId(),
                        d -> d,
                        (existente, novo) -> existente
                ));

        mapaDatas.put(dataEtapaSendoSalva.getEtapa().getId(), dataEtapaSendoSalva);

        DataEtapa dataAnterior = null;
        DataEtapa primeiraData = null;
        DataEtapa ultimaData = null;

        for (Etapa etapa : etapasOrdenadas) {
            DataEtapa dataAtual = mapaDatas.get(etapa.getId());

            if (dataAtual == null) {
                continue;
            }

            dataAtual.setEtapa(etapa);

            if (primeiraData == null) {
                primeiraData = dataAtual;
            }
            ultimaData = dataAtual;

            if (dataAnterior != null && dataAnterior.getDataFim() != null && dataAtual.getDataInicio() != null) {
                if (dataAnterior.getDataFim().isAfter(dataAtual.getDataInicio())) {
                    throw new IllegalStateException(
                            String.format("Inconsistência de datas: A etapa '%s' (ordem %d) não pode começar antes do término da etapa '%s' (ordem %d).",
                                    dataAtual.getEtapa().getNome(), dataAtual.getEtapa().getOrdem(),
                                    dataAnterior.getEtapa().getNome(), dataAnterior.getEtapa().getOrdem())
                    );
                }
            }
            dataAnterior = dataAtual;
        }

        if (primeiraData != null && primeiraData.getDataInicio() != null && edital.getInicioInscricao() != null &&
                primeiraData.getDataInicio().isBefore(edital.getInicioInscricao())) {
            throw new IllegalStateException("A data de início da primeira etapa ('" + primeiraData.getEtapa().getNome() + "') não pode ser anterior ao início das inscrições do edital.");
        }

        if (ultimaData != null && ultimaData.getDataFim() != null && edital.getFimIncricao() != null &&
                ultimaData.getDataFim().isAfter(edital.getFimIncricao())) {
            throw new IllegalStateException("A data de fim da última etapa ('" + ultimaData.getEtapa().getNome() + "') não pode ser posterior ao fim das inscrições do edital.");
        }
    }

    // =================== Auth ===================

    private void verificarDonoInscricao(Inscricao inscricao) {
        UUID userId = authenticatedUserProvider.getUserId();
        if (!inscricao.getIdUsuario().equals(userId)) {
            throw new GlobalAccessDeniedException("Você não tem permissão para alterar esta inscrição.");
        }
    }

}