package model;

import java.util.Objects;

/**
 * Representa um Aluno matriculado na instituição de ensino.
 * Subclasse de Pessoa que adiciona matrícula e curso de graduação.
 */
public class Aluno extends Pessoa {

    private final String matricula;
    private String curso;

    public Aluno(String id, String nome, String email, String matricula, String curso) {
        super(id, nome, email);
        if (matricula == null || matricula.isBlank()) {
            throw new IllegalArgumentException("A matrícula não pode ser nula ou vazia.");
        }
        if (curso == null || curso.isBlank()) {
            throw new IllegalArgumentException("O curso não pode ser nulo ou vazio.");
        }
        this.matricula = matricula;
        this.curso = curso;
    }

    @Override
    public String getPapel() {
        return "Aluno";
    }

    public String getMatricula() {
        return matricula;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        if (curso == null || curso.isBlank()) {
            throw new IllegalArgumentException("O curso não pode ser nulo ou vazio.");
        }
        this.curso = curso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Aluno aluno = (Aluno) o;
        return Objects.equals(matricula, aluno.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), matricula);
    }

    @Override
    public String toString() {
        return String.format("%s | Matrícula: %s | Curso: %s", super.toString(), matricula, curso);
    }
}
