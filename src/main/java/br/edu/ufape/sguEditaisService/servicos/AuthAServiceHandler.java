package br.edu.ufape.sguEditaisService.servicos;

import br.edu.ufape.sguEditaisService.auth.AuthServiceClient;
import br.edu.ufape.sguEditaisService.comunicacao.dto.usuario.AlunoResponse;
import br.edu.ufape.sguEditaisService.comunicacao.dto.usuario.FuncionarioResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthAServiceHandler implements br.edu.ufape.sguEditaisService.servicos.interfaces.AuthServiceHandler{
    private final AuthServiceClient authServiceClient;

    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "fallbackAlunoInfo")
    @Override
    public AlunoResponse getAlunoInfo() {
        return authServiceClient.getAlunoInfo();
    }

    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "fallbackBuscarAlunoPorId")
    @Override
    public AlunoResponse buscarAlunoPorId(UUID userId) {
        return authServiceClient.buscarAlunoPorId(userId);
    }
    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "fallbackBuscarAlunos")
    @Override
    public List<AlunoResponse> buscarAlunos(List<UUID> userIds) {
        return authServiceClient.buscarAlunos(userIds);
    }

    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "fallbackBuscarAlunosPorCurso")
    @Override
    public List<AlunoResponse> buscarAlunosPorCurso(Long idCurso) {
        return authServiceClient.listarAlunosPorCurso(idCurso);
    }


    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "fallbackGetTecnicoInfo")
    @Override
    public FuncionarioResponse getTecnicoInfo() {
        return authServiceClient.getFuncionarioInfo();
    }

    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "fallbackBuscarTecnicoPorId")
    @Override
    public FuncionarioResponse buscarTecnicoPorId(UUID userId) {
        return authServiceClient.buscarFuncionarioPorId(userId);
    }


    @CircuitBreaker(name = "authServiceClient", fallbackMethod = "fallbackBuscarTecnicos")
    @Override
    public List<FuncionarioResponse> buscarTecnicos(List<UUID> userIds) {
        return authServiceClient.buscarFuncionarios(userIds);
    }

    @Override
    public AlunoResponse fallbackAlunoInfo(Throwable t) {
        log.warn("Não foi possível obter info do aluno no AuthService", t);
        return null;
    }

    @Override
    public AlunoResponse fallbackBuscarAlunoPorId(UUID userId, Throwable t) {
        log.warn("Não foi possível buscar aluno com id {} no AuthService", userId, t);
        return null;
    }

    @Override
    public List<AlunoResponse> fallbackBuscarAlunos(List<UUID> userIds, Throwable t) {
        log.warn("Não foi possível buscar alunos com ids {} no AuthService", userIds, t);
        return Collections.emptyList();
    }

    @Override
    public List<AlunoResponse> fallbackBuscarAlunosPorCurso(Long idCurso, Throwable t) {
        log.warn("Não foi possível buscar alunos do curso com id {} no AuthService", idCurso, t);
        return Collections.emptyList();
    }

    @Override
    public FuncionarioResponse fallbackGetTecnicoInfo(Throwable t) {
        log.warn("Não foi possível obter info do técnico no AuthService", t);
        return null;
    }

    @Override
    public FuncionarioResponse fallbackBuscarTecnicoPorId(UUID userId, Throwable t) {
        log.warn("Não foi possível buscar técnico com id {} no AuthService", userId, t);
        return null;
    }

    @Override
    public List<FuncionarioResponse> fallbackBuscarTecnicos(UUID userId, Throwable t) {
        log.warn("Não foi possível buscar técnico com id {} no AuthService", userId, t);
        return Collections.emptyList();
    }
}
