package model;

import java.util.Objects;

/**
 * Representa uma Disciplina curricular oferecida pela instituição.
 * Define igualdade estritamente com base no seu código único (equals e hashCode).
 */
public class Disciplina {

    private final String codigo;
    private String nome;
    private int cargaHoraria;

    public Disciplina(String codigo, String nome, int cargaHoraria) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("O código da disciplina não pode ser nulo ou vazio.");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da disciplina não pode ser nulo ou vazio.");
        }
        if (cargaHoraria <= 0) {
            throw new IllegalArgumentException("A carga horária deve ser maior que zero.");
        }
        this.codigo = codigo.trim().toUpperCase();
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da disciplina não pode ser nulo ou vazio.");
        }
        this.nome = nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        if (cargaHoraria <= 0) {
            throw new IllegalArgumentException("A carga horária deve ser maior que zero.");
        }
        this.cargaHoraria = cargaHoraria;
    }

    /**
     * Igualdade baseada unicamente no código da disciplina.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disciplina that = (Disciplina) o;
        return Objects.equals(codigo, that.codigo);
    }

    /**
     * Hash code baseado unicamente no código da disciplina.
     */
    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return String.format("Disciplina[%s - %s (%dh)]", codigo, nome, cargaHoraria);
    }
}
