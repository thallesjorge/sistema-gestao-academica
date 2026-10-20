package model;

import exception.AlunoNaoEncontradoException;
import exception.NotaInvalidaException;
import exception.TurmaLotadaException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Representa uma Turma de uma Disciplina com professor responsável, limite de vagas,
 * lista de alunos matriculados e controle de notas por aluno.
 */
public class Turma {

    private final String codigo;
    private final Disciplina disciplina;
    private Professor professor;
    private final int capacidadeMaxima;

    // Coleções em memória para gestão de alunos e notas
    private final List<Aluno> alunos;
    private final Map<String, List<Double>> notasPorMatricula;

    public Turma(String codigo, Disciplina disciplina, Professor professor, int capacidadeMaxima) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("O código da turma não pode ser nulo ou vazio.");
        }
        if (disciplina == null) {
            throw new IllegalArgumentException("A disciplina não pode ser nula.");
        }
        if (capacidadeMaxima <= 0) {
            throw new IllegalArgumentException("A capacidade máxima deve ser maior que zero.");
        }
        this.codigo = codigo.trim().toUpperCase();
        this.disciplina = disciplina;
        this.professor = professor;
        this.capacidadeMaxima = capacidadeMaxima;
        this.alunos = new ArrayList<>();
        this.notasPorMatricula = new LinkedHashMap<>();
    }

    /**
     * Matricula um aluno na turma respeitando a capacidade máxima.
     *
     * @param aluno Aluno a ser matriculado
     * @throws TurmaLotadaException se a turma já tiver atingido a capacidade máxima
     */
    public synchronized void matricularAluno(Aluno aluno) {
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno não pode ser nulo.");
        }

        if (alunos.size() >= capacidadeMaxima) {
            throw new TurmaLotadaException(codigo, capacidadeMaxima);
        }

        if (estaMatriculado(aluno.getMatricula())) {
            throw new IllegalStateException(String.format("Aluno com matrícula '%s' já está matriculado nesta turma.", aluno.getMatricula()));
        }

        alunos.add(aluno);
        notasPorMatricula.put(aluno.getMatricula(), new ArrayList<>());
    }

    /**
     * Lança uma nova nota para um aluno matriculado.
     *
     * @param matricula Matrícula do aluno
     * @param nota Valor da nota entre 0.0 e 10.0
     * @throws AlunoNaoEncontradoException se o aluno não estiver matriculado nesta turma
     * @throws NotaInvalidaException se a nota estiver fora do intervalo [0.0, 10.0]
     */
    public synchronized void adicionarNota(String matricula, double nota) {
        if (matricula == null || matricula.isBlank()) {
            throw new IllegalArgumentException("A matrícula não pode ser nula ou vazia.");
        }

        if (!estaMatriculado(matricula)) {
            throw new AlunoNaoEncontradoException(matricula, codigo);
        }

        if (nota < 0.0 || nota > 10.0) {
            throw new NotaInvalidaException(nota);
        }

        notasPorMatricula.get(matricula).add(nota);
    }

    /**
     * Calcula a média aritmética das notas de um aluno.
     *
     * @param matricula Matrícula do aluno
     * @return Média das notas, ou 0.0 se nenhuma nota foi lançada
     * @throws AlunoNaoEncontradoException se o aluno não estiver matriculado
     */
    public double calcularMedia(String matricula) {
        if (!estaMatriculado(matricula)) {
            throw new AlunoNaoEncontradoException(matricula, codigo);
        }

        List<Double> notas = notasPorMatricula.get(matricula);
        if (notas == null || notas.isEmpty()) {
            return 0.0;
        }

        double soma = 0.0;
        for (double n : notas) {
            soma += n;
        }
        return soma / notas.size();
    }

    /**
     * Determina o status de aprovação de um aluno na turma.
     *
     * @param matricula Matrícula do aluno
     * @return StatusAprovacao correspondente à média
     */
    public StatusAprovacao obterStatusAluno(String matricula) {
        double media = calcularMedia(matricula);
        return StatusAprovacao.aPartirDaMedia(media);
    }

    /**
     * Verifica se o aluno está matriculado nesta turma.
     *
     * @param matricula Matrícula do aluno
     * @return true se estiver matriculado, false caso contrário
     */
    public boolean estaMatriculado(String matricula) {
        return notasPorMatricula.containsKey(matricula);
    }

    public int getVagasDisponiveis() {
        return capacidadeMaxima - alunos.size();
    }

    public int getQuantidadeAlunos() {
        return alunos.size();
    }

    public String getCodigo() {
        return codigo;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    /**
     * Retorna uma visualização não-modificável da lista de alunos para preservar o encapsulamento.
     */
    public List<Aluno> getAlunos() {
        return Collections.unmodifiableList(alunos);
    }

    /**
     * Retorna uma cópia não-modificável das notas de um aluno.
     */
    public List<Double> getNotasAluno(String matricula) {
        if (!estaMatriculado(matricula)) {
            throw new AlunoNaoEncontradoException(matricula, codigo);
        }
        return Collections.unmodifiableList(new ArrayList<>(notasPorMatricula.get(matricula)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turma turma = (Turma) o;
        return Objects.equals(codigo, turma.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        String profNome = (professor != null) ? professor.getNome() : "Sem Professor Atribuído";
        return String.format("Turma %s [%s] | Prof: %s | Ocupação: %d/%d",
                codigo, disciplina.getNome(), profNome, alunos.size(), capacidadeMaxima);
    }
}
