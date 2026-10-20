package service;

import exception.AlunoNaoEncontradoException;
import exception.NotaInvalidaException;
import exception.TurmaLotadaException;
import exception.TurmaNaoEncontradaException;
import model.Aluno;
import model.Disciplina;
import model.Professor;
import model.StatusAprovacao;
import model.Turma;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Serviço central de gerenciamento escolar.
 * Utiliza tabelas hash (HashMap) para garantir buscas em tempo constante O(1) por chave primária,
 * orquestrando as regras de negócio para matrículas, atribuições e lançamentos de notas.
 */
public class EscolaService {

    // Estruturas de dados em memória baseadas em HashMap para acesso O(1)
    private final Map<String, Aluno> alunosPorMatricula;
    private final Map<String, Professor> professoresPorRegistro;
    private final Map<String, Disciplina> disciplinasPorCodigo;
    private final Map<String, Turma> turmasPorCodigo;

    public EscolaService() {
        this.alunosPorMatricula = new HashMap<>();
        this.professoresPorRegistro = new HashMap<>();
        this.disciplinasPorCodigo = new HashMap<>();
        this.turmasPorCodigo = new HashMap<>();
    }

    // ==========================================
    // Gestão de Alunos
    // ==========================================

    public void cadastrarAluno(Aluno aluno) {
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno não pode ser nulo.");
        }
        if (alunosPorMatricula.containsKey(aluno.getMatricula())) {
            throw new IllegalArgumentException(String.format("Já existe aluno cadastrado com a matrícula '%s'.", aluno.getMatricula()));
        }
        alunosPorMatricula.put(aluno.getMatricula(), aluno);
    }

    /**
     * Busca aluno por matrícula em tempo O(1).
     */
    public Aluno buscarAlunoPorMatricula(String matricula) {
        if (matricula == null || matricula.isBlank()) {
            throw new IllegalArgumentException("Matrícula de busca não pode ser nula ou vazia.");
        }
        Aluno aluno = alunosPorMatricula.get(matricula);
        if (aluno == null) {
            throw new AlunoNaoEncontradoException(matricula);
        }
        return aluno;
    }

    public List<Aluno> listarTodosAlunos() {
        return Collections.unmodifiableList(new ArrayList<>(alunosPorMatricula.values()));
    }

    // ==========================================
    // Gestão de Professores
    // ==========================================

    public void cadastrarProfessor(Professor professor) {
        if (professor == null) {
            throw new IllegalArgumentException("Professor não pode ser nulo.");
        }
        if (professoresPorRegistro.containsKey(professor.getRegistroFuncional())) {
            throw new IllegalArgumentException(String.format("Já existe professor com registro funcional '%s'.", professor.getRegistroFuncional()));
        }
        professoresPorRegistro.put(professor.getRegistroFuncional(), professor);
    }

    /**
     * Busca professor por registro funcional em tempo O(1).
     */
    public Professor buscarProfessorPorRegistro(String registroFuncional) {
        if (registroFuncional == null || registroFuncional.isBlank()) {
            throw new IllegalArgumentException("Registro funcional não pode ser nulo ou vazio.");
        }
        Professor prof = professoresPorRegistro.get(registroFuncional);
        if (prof == null) {
            throw new IllegalArgumentException(String.format("Professor com registro '%s' não encontrado.", registroFuncional));
        }
        return prof;
    }

    public List<Professor> listarTodosProfessores() {
        return Collections.unmodifiableList(new ArrayList<>(professoresPorRegistro.values()));
    }

    // ==========================================
    // Gestão de Disciplinas
    // ==========================================

    public void cadastrarDisciplina(Disciplina disciplina) {
        if (disciplina == null) {
            throw new IllegalArgumentException("Disciplina não pode ser nula.");
        }
        if (disciplinasPorCodigo.containsKey(disciplina.getCodigo())) {
            throw new IllegalArgumentException(String.format("Disciplina com código '%s' já cadastrada.", disciplina.getCodigo()));
        }
        disciplinasPorCodigo.put(disciplina.getCodigo(), disciplina);
    }

    /**
     * Busca disciplina por código em tempo O(1).
     */
    public Disciplina buscarDisciplinaPorCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("Código de disciplina não pode ser nulo ou vazio.");
        }
        Disciplina disc = disciplinasPorCodigo.get(codigo.trim().toUpperCase());
        if (disc == null) {
            throw new IllegalArgumentException(String.format("Disciplina com código '%s' não encontrada.", codigo));
        }
        return disc;
    }

    public List<Disciplina> listarTodasDisciplinas() {
        return Collections.unmodifiableList(new ArrayList<>(disciplinasPorCodigo.values()));
    }

    // ==========================================
    // Gestão de Turmas
    // ==========================================

    /**
     * Cria e cadastra uma nova Turma, associando a Disciplina e o Professor correspondentes.
     */
    public Turma abrirTurma(String codigoTurma, String codigoDisciplina, String registroProfessor, int capacidadeMaxima) {
        if (codigoTurma == null || codigoTurma.isBlank()) {
            throw new IllegalArgumentException("Código da turma não pode ser nulo ou vazio.");
        }
        String codigoNormalizado = codigoTurma.trim().toUpperCase();
        if (turmasPorCodigo.containsKey(codigoNormalizado)) {
            throw new IllegalArgumentException(String.format("Turma com código '%s' já existe.", codigoTurma));
        }

        Disciplina disciplina = buscarDisciplinaPorCodigo(codigoDisciplina);
        Professor professor = (registroProfessor != null && !registroProfessor.isBlank())
                ? buscarProfessorPorRegistro(registroProfessor)
                : null;

        Turma novaTurma = new Turma(codigoNormalizado, disciplina, professor, capacidadeMaxima);
        turmasPorCodigo.put(codigoNormalizado, novaTurma);
        return novaTurma;
    }

    /**
     * Busca turma por código em tempo O(1).
     */
    public Turma buscarTurmaPorCodigo(String codigoTurma) {
        if (codigoTurma == null || codigoTurma.isBlank()) {
            throw new IllegalArgumentException("Código da turma não pode ser nulo ou vazio.");
        }
        Turma turma = turmasPorCodigo.get(codigoTurma.trim().toUpperCase());
        if (turma == null) {
            throw new TurmaNaoEncontradaException(codigoTurma);
        }
        return turma;
    }

    public List<Turma> listarTodasTurmas() {
        return Collections.unmodifiableList(new ArrayList<>(turmasPorCodigo.values()));
    }

    // ==========================================
    // Regras de Negócio: Matrícula & Notas
    // ==========================================

    /**
     * Realiza a matrícula de um aluno em uma turma específica.
     * Busca aluno e turma em O(1) e delega a inclusão com validação de limite.
     *
     * @throws TurmaLotadaException se a turma estiver cheia
     * @throws AlunoNaoEncontradoException se o aluno não existir
     * @throws TurmaNaoEncontradaException se a turma não existir
     */
    public void matricularAlunoEmTurma(String codigoTurma, String matriculaAluno) {
        Turma turma = buscarTurmaPorCodigo(codigoTurma);
        Aluno aluno = buscarAlunoPorMatricula(matriculaAluno);
        turma.matricularAluno(aluno);
    }

    /**
     * Lança nota para um aluno em uma turma.
     *
     * @throws NotaInvalidaException se a nota for menor que 0.0 ou maior que 10.0
     * @throws AlunoNaoEncontradoException se o aluno não estiver matriculado na turma
     * @throws TurmaNaoEncontradaException se a turma não existir
     */
    public void lancarNota(String codigoTurma, String matriculaAluno, double nota) {
        Turma turma = buscarTurmaPorCodigo(codigoTurma);
        // Garante que o aluno existe no sistema global
        buscarAlunoPorMatricula(matriculaAluno);
        turma.adicionarNota(matriculaAluno, nota);
    }

    /**
     * Gera relatório detalhado (boletim) de uma turma com todos os alunos, notas, médias e status.
     */
    public String gerarRelatorioTurma(String codigoTurma) {
        Turma turma = buscarTurmaPorCodigo(codigoTurma);
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================================================================\n");
        sb.append(String.format(" BOLETIM ACADÊMICO - TURMA: %s | DISCIPLINA: %s (%s)\n",
                turma.getCodigo(), turma.getDisciplina().getNome(), turma.getDisciplina().getCodigo()));
        sb.append(String.format(" Professor: %s | Ocupação: %d/%d alunos\n",
                turma.getProfessor() != null ? turma.getProfessor().getNome() : "Não atribuído",
                turma.getQuantidadeAlunos(), turma.getCapacidadeMaxima()));
        sb.append("=========================================================================================\n");
        sb.append(String.format("%-12s | %-24s | %-18s | %-8s | %s\n",
                "MATRÍCULA", "ALUNO", "NOTAS", "MÉDIA", "STATUS"));
        sb.append("-----------------------------------------------------------------------------------------\n");

        if (turma.getAlunos().isEmpty()) {
            sb.append(" Nenhum aluno matriculado nesta turma.\n");
        } else {
            for (Aluno aluno : turma.getAlunos()) {
                String mat = aluno.getMatricula();
                List<Double> notas = turma.getNotasAluno(mat);
                double media = turma.calcularMedia(mat);
                StatusAprovacao status = turma.obterStatusAluno(mat);

                sb.append(String.format("%-12s | %-24s | %-18s | %-8.2f | %s\n",
                        mat,
                        aluno.getNome(),
                        notas.toString(),
                        media,
                        status.getDescricao()));
            }
        }
        sb.append("=========================================================================================\n");
        return sb.toString();
    }
}
