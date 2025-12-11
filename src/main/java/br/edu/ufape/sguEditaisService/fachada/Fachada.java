package br.edu.ufape.sguEditaisService.fachada;

import br.edu.ufape.sguEditaisService.comunicacao.dto.curso.CursoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.inscricao.InscricaoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.permissaoEtapa.PermissaoEtapaResponse;
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


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    @Transactional
    public Documento salvarDocumento(Documento documento) {
        if (documento.getInscricao() == null || documento.getInscricao().getId() == null) {
            throw new IllegalArgumentException("O documento deve estar vinculado a uma inscrição.");
        }
        Inscricao inscricao = inscricaoService.buscarPorIdInscricao(documento.getInscricao().getId());
        documento.setInscricao(inscricao);

        verificarDonoInscricao(inscricao);

        if (documento.getEtapa() != null && documento.getEtapa().getId() != null) {
            Etapa etapaSolicitada = etapaService.buscarPorIdEtapa(documento.getEtapa().getId());
            // Opcional: Validar se a etapaSolicitada pertence ao Edital da inscrição
            if (!etapaSolicitada.getEdital().getId().equals(inscricao.getEdital().getId()) &&
                    (etapaSolicitada.getTipoEditalModelo() == null)) {
            }
            documento.setEtapa(etapaSolicitada);
        } else {
            // AUTO-BINDING: Vincula automaticamente à etapa onde o usuário está
            documento.setEtapa(inscricao.getEtapaAtual());
        }

        // 4. Valida se a etapa está vigente (opcional, mas recomendado)
        dataEtapaService.validarVigencia(documento.getEtapa());

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

        UUID userID = authenticatedUserProvider.getUserId();

        boolean temPermissao = authServiceHandler.verificarVinculo(obj.getIdUnidadeAdministrativa(), userID);
        if (!temPermissao) {
            throw new GlobalAccessDeniedException("Você não tem permissão para Criar este edital.");
        }

        validarCurso( obj.getCursoId());

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

        validarCurso( obj.getCursoId());

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

        // 1. Define a Etapa Inicial (menor ordem)
        Etapa etapaInicial = edital.getEtapas().stream()
                .min(Comparator.comparingInt(Etapa::getOrdem))
                .orElseThrow(() -> new EtapaNotFoundException("O edital não possui etapas configuradas."));

        obj.setEtapaAtual(etapaInicial);
        dataEtapaService.validarVigencia(etapaInicial);

        // 2. Define o Status Inicial Padrão ("Em Análise" ou similar)
        // Buscamos um status que NÃO conclua etapa por padrão para iniciar
        StatusPersonalizado statusInicial = statusPersonalizadoService.listarStatusPersonalizados().stream()
                .filter(s -> s.getNome().equalsIgnoreCase("Em Análise")) // Nome canônico do Seeder
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Status base 'Em Análise' não encontrado. Verifique o Seeder."));

        obj.setStatusAtual(statusInicial);
        obj.setDataInscricao(LocalDateTime.now());

        Inscricao inscricaoSalva = inscricaoService.salvarInscricao(obj);

        // 3. Gera Histórico Inicial
        registrarHistorico(inscricaoSalva, etapaInicial, statusInicial, "Inscrição realizada pelo candidato.");

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

    public List<InscricaoResponse> listarInscricoesUsuarioLogado() {
        UUID userId = authenticatedUserProvider.getUserId();
        List<Inscricao> inscricoes = inscricaoService.listarInscricoesPorUsuario(userId);

        return inscricoes.stream()
                .map(this::mapToInscricaoResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public InscricaoResponse editarInscricao(Long id, Inscricao obj) {
        Inscricao original = inscricaoService.buscarPorIdInscricao(id);

        verificarDonoInscricao(original);
        dataEtapaService.validarVigencia(original.getEtapaAtual());

        // A edição de dados não muda o status automaticamente, mantém o atual
        // a menos que seja explicitamente enviado um novo status (mas o ideal é usar a rota de status)
        modelMapper.map(obj, original);

        Inscricao inscricaoSalva = inscricaoService.salvarInscricao(original);

        registrarHistorico(inscricaoSalva, original.getEtapaAtual(), original.getStatusAtual(), "Dados da inscrição atualizados pelo candidato.");

        return mapToInscricaoResponse(inscricaoSalva);
    }

    public void deletarInscricao(Long id) {
        inscricaoService.deletarInscricao(id);
    }

    @Transactional
    public InscricaoResponse atualizarStatusInscricao(Long id, Long novoStatusId, String observacao) {
        Inscricao inscricao = inscricaoService.buscarPorIdInscricao(id);
        StatusPersonalizado novoStatus = statusPersonalizadoService.buscarPorIdStatusPersonalizado(novoStatusId);

        // 1. Atualiza Status e Salva
        inscricao.setStatusAtual(novoStatus);
        Inscricao inscricaoSalva = inscricaoService.salvarInscricao(inscricao);

        // 2. Log de Auditoria (Historico)
        HistoricoEtapaInscricao historico = new HistoricoEtapaInscricao();
        historico.setInscricao(inscricaoSalva);
        historico.setEtapa(inscricaoSalva.getEtapaAtual());
        historico.setStatusPersonalizado(novoStatus);
        historico.setDataAacao(LocalDateTime.now());
        historico.setObservacao(observacao != null ? observacao : "Atualização de status manual.");
        historicoEtapaInscricaoService.salvarHistoricoEtapaInscricao(historico);

        // 3. O Motor de Decisão: Baseado na flag do banco!
        if (novoStatus.isConcluiEtapa()) {
            avancarParaProximaEtapa(inscricaoSalva);
        }

        return mapToInscricaoResponse(inscricaoSalva);
    }

    private void avancarParaProximaEtapa(Inscricao inscricao) {
        Long editalId = inscricao.getEdital().getId();
        int ordemAtual = inscricao.getEtapaAtual().getOrdem();

        // Busca a próxima etapa
        Optional<Etapa> proximaEtapa = etapaService.buscarProximaEtapa(editalId, ordemAtual);

        if (proximaEtapa.isPresent()) {
            Etapa novaEtapa = proximaEtapa.get();
            inscricao.setEtapaAtual(novaEtapa);

            // Busca o status "Em Análise" para ser o padrão da nova etapa
            // Se não achar, mantém o status atual como fallback
            StatusPersonalizado statusInicialNovaEtapa = statusPersonalizadoService.listarStatusPersonalizados().stream()
                    .filter(s -> s.getNome().equalsIgnoreCase("Em Análise"))
                    .findFirst()
                    .orElse(inscricao.getStatusAtual());

            inscricao.setStatusAtual(statusInicialNovaEtapa);
            inscricaoService.salvarInscricao(inscricao);

            // Log automático de entrada na nova etapa
            HistoricoEtapaInscricao logAvanco = new HistoricoEtapaInscricao();
            logAvanco.setInscricao(inscricao);
            logAvanco.setEtapa(novaEtapa);
            logAvanco.setStatusPersonalizado(statusInicialNovaEtapa);
            logAvanco.setDataAacao(LocalDateTime.now());
            logAvanco.setObservacao("Sistema: Avanço automático para a etapa '" + novaEtapa.getNome() + "' após aprovação.");
            historicoEtapaInscricaoService.salvarHistoricoEtapaInscricao(logAvanco);
        } else {
            StatusPersonalizado statusFinal = statusPersonalizadoService.listarStatusPersonalizados().stream()
                    .filter(s -> s.getNome().equalsIgnoreCase("Aprovado"))
                    .findFirst()
                    .orElse(inscricao.getStatusAtual());

            // Só atualiza se o status atual já não for o final (para evitar loops ou redundância)
            if (!inscricao.getStatusAtual().getId().equals(statusFinal.getId())) {
                inscricao.setStatusAtual(statusFinal);
                inscricaoService.salvarInscricao(inscricao);

                registrarHistorico(inscricao, inscricao.getEtapaAtual(), statusFinal,
                        "Sistema: Processo seletivo concluído com sucesso. Candidato Aprovado.");
            }
        }
    }

    private void registrarHistorico(Inscricao inscricao, Etapa etapa, StatusPersonalizado status, String obs) {
        HistoricoEtapaInscricao historico = new HistoricoEtapaInscricao();
        historico.setInscricao(inscricao);
        historico.setEtapa(etapa);
        historico.setStatusPersonalizado(status);
        historico.setDataAacao(LocalDateTime.now());
        historico.setObservacao(obs);
        historicoEtapaInscricaoService.salvarHistoricoEtapaInscricao(historico);
    }

    private InscricaoResponse mapToInscricaoResponse(Inscricao inscricao) {
        InscricaoResponse response = new InscricaoResponse(inscricao, modelMapper);

        if (inscricao.getIdUsuario() != null) {
            UsuarioResponse usuario = authServiceHandler.buscarUsuarioPorId(inscricao.getIdUsuario());
            response.setUsuario(usuario);
        }

        return response;
    }

    // =================== PermissaoEtapa ===================

//    public PermissaoEtapa salvarPermissaoEtapa(PermissaoEtapa obj) {
//        if (obj.getEtapa() != null && obj.getEtapa().getId() != null) {
//            Etapa etapa = etapaService.buscarPorIdEtapa(obj.getEtapa().getId());
//            obj.setEtapa(etapa);
//        }
//        return permissaoEtapaService.salvarPermissaoEtapa(obj);
//    }
//
//    public PermissaoEtapa buscarPorIdPermissaoEtapa(Long id) {
//        return permissaoEtapaService.buscarPorIdPermissaoEtapa(id);
//    }
//
//    public List<PermissaoEtapa> listarPermissaoEtapa() {
//        return permissaoEtapaService.listarPermissaoEtapa();
//    }
//
//    public PermissaoEtapa editarPermissaoEtapa(Long id, PermissaoEtapa obj) {
//        PermissaoEtapa original = permissaoEtapaService.buscarPorIdPermissaoEtapa(id);
//        modelMapper.map(obj, original);
//
//        if (obj.getEtapa() != null && obj.getEtapa().getId() != null) {
//            Etapa etapa = etapaService.buscarPorIdEtapa(obj.getEtapa().getId());
//            original.setEtapa(etapa);
//        }
//
//        return permissaoEtapaService.salvarPermissaoEtapa(original);
//    }
//
//    public void deletarPermissaoEtapa(Long id) {
//        permissaoEtapaService.deletarPermissaoEtapa(id);
//    }

    public PermissaoEtapaResponse adicionarPermissaoEtapa(Long etapaId, String perfil) {
        Etapa etapa = etapaService.buscarPorIdEtapa(etapaId);

        if (permissaoEtapaService.buscarPorEtapaEPerfil(etapaId, perfil).isPresent()) {
            throw new IllegalArgumentException("O perfil '" + perfil + "' já possui permissão nesta etapa.");
        }

        PermissaoEtapa novaPermissao = new PermissaoEtapa();
        novaPermissao.setEtapa(etapa);
        novaPermissao.setPerfil(perfil.toUpperCase());

        PermissaoEtapa salvo = permissaoEtapaService.salvarPermissaoEtapa(novaPermissao);
        return new PermissaoEtapaResponse(salvo, modelMapper);
    }

    public void removerPermissaoEtapa(Long etapaId, String perfil) {
        PermissaoEtapa permissao = permissaoEtapaService.buscarPorEtapaEPerfil(etapaId, perfil)
                .orElseThrow(() -> new IllegalArgumentException("Permissão não encontrada para o perfil '" + perfil + "' nesta etapa."));


        permissaoEtapaService.deletarPermissaoEtapa(permissao.getId());
    }

    public List<PermissaoEtapaResponse> listarPermissoesDaEtapa(Long etapaId) {
        etapaService.buscarPorIdEtapa(etapaId);

        return permissaoEtapaService.listarPermissoesPorEtapa(etapaId).stream()
                .map(p -> new PermissaoEtapaResponse(p, modelMapper))
                .collect(Collectors.toList());
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

            verificarDonoInscricao(inscricao);
        }
        if (obj.getCampoPersonalizado() != null && obj.getCampoPersonalizado().getId() != null) {
            CampoPersonalizado campo = campoPersonalizadoService.buscarPorIdCampoPersonalizado(obj.getCampoPersonalizado().getId());
            obj.setCampoPersonalizado(campo);
        }

        validarConteudoDoCampo(obj);
        // -------------------------

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

        if (original.getInscricao() != null) {
            verificarDonoInscricao(original.getInscricao());
        }

        modelMapper.map(obj, original);

        if (obj.getInscricao() != null && obj.getInscricao().getId() != null) {
            Inscricao inscricao = inscricaoService.buscarPorIdInscricao(obj.getInscricao().getId());
            original.setInscricao(inscricao);
        }
        if (obj.getCampoPersonalizado() != null && obj.getCampoPersonalizado().getId() != null) {
            CampoPersonalizado campo = campoPersonalizadoService.buscarPorIdCampoPersonalizado(obj.getCampoPersonalizado().getId());
            original.setCampoPersonalizado(campo);
        }

        validarConteudoDoCampo(original);

        return valorCampoService.salvarValorCampo(original);
    }

    public void deletarValorCampo(Long id) {
        valorCampoService.deletarValorCampo(id);
    }

    private void validarConteudoDoCampo(ValorCampo valorCampo) {
        CampoPersonalizado campo = valorCampo.getCampoPersonalizado();

        if (campo == null) {
            throw new IllegalArgumentException("O ValorCampo deve estar associado a um CampoPersonalizado.");
        }

        String valor = valorCampo.getValor();
        boolean isVazio = valor == null || valor.trim().isEmpty();

        // 1. Validação de Obrigatoriedade
        if (isVazio) {
            if (campo.isObrigatorio()) {
                throw new IllegalArgumentException("O campo '" + campo.getNome() + "' (" + campo.getRotulo() + ") é obrigatório.");
            }
            return; // Se está vazio e NÃO é obrigatório, está válido. Não validamos tipo de string vazia.
        }

        // 2. Validação de Tipo de Dado (Só executa se tiver valor)
        try {
            switch (campo.getTipoCampo()) {
                case NUMERO_INTEIRO:
                    Long.parseLong(valor);
                    break;
                case NUMERO_DECIMAL:
                    // Substitui vírgula por ponto para aceitar formato PT-BR
                    new BigDecimal(valor.replace(",", "."));
                    break;
                case BOOLEAN:
                    if (!valor.equalsIgnoreCase("true") && !valor.equalsIgnoreCase("false")) {
                        throw new IllegalArgumentException();
                    }
                    break;
                case DATA:
                    try {
                        LocalDate.parse(valor); // Tenta ISO (yyyy-MM-dd)
                    } catch (DateTimeParseException e) {
                        LocalDate.parse(valor, DateTimeFormatter.ofPattern("dd/MM/yyyy")); // Tenta BR
                    }
                    break;
                case DATA_HORA:
                    try {
                        LocalDateTime.parse(valor);
                    } catch (DateTimeParseException e) {
                        LocalDateTime.parse(valor, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                    }
                    break;
                case TEXTO_CURTO:
                    if (valor.length() > 255) {
                        throw new IllegalArgumentException("O texto excede o limite de 255 caracteres.");
                    }
                    break;
                default:
                    break; // TEXTO_LONGO aceita qualquer coisa
            }
        } catch (Exception e) {
            // Captura erros de parse (NumberFormat, DateTimeParse) e lança msg amigável
            throw new IllegalArgumentException("O valor informado '" + valor + "' não é válido para o campo '" +
                    campo.getNome() + "' (Tipo: " + campo.getTipoCampo() + ").");
        }
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
//        if (edital.getInicioInscricao() != null && LocalDateTime.now().isAfter(edital.getInicioInscricao())) {
//            throw new IllegalStateException("Não é possível modificar as datas das etapas de um edital com inscrições já iniciadas.");
//        }

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

    private void validarCurso(Long cursoId) {
        if (cursoId != null) {
            CursoResponse curso = authServiceHandler.buscarCursoPorId(cursoId);
            if (curso == null) {
                throw new IllegalArgumentException("O Curso informado (ID " + cursoId + ") não foi encontrado ou o serviço de validação está indisponível.");
            }
        }
    }

}