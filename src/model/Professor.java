package model;

import java.util.Objects;

/**
 * Representa um Professor da instituição de ensino.
 * Subclasse de Pessoa que adiciona registro funcional e departamento acadêmico.
 */
public class Professor extends Pessoa {

    private final String registroFuncional;
    private String departamento;

    public Professor(String id, String nome, String email, String registroFuncional, String departamento) {
        super(id, nome, email);
        if (registroFuncional == null || registroFuncional.isBlank()) {
            throw new IllegalArgumentException("O registro funcional não pode ser nulo ou vazio.");
        }
        if (departamento == null || departamento.isBlank()) {
            throw new IllegalArgumentException("O departamento não pode ser nulo ou vazio.");
        }
        this.registroFuncional = registroFuncional;
        this.departamento = departamento;
    }

    @Override
    public String getPapel() {
        return "Professor";
    }

    public String getRegistroFuncional() {
        return registroFuncional;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        if (departamento == null || departamento.isBlank()) {
            throw new IllegalArgumentException("O departamento não pode ser nulo ou vazio.");
        }
        this.departamento = departamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Professor professor = (Professor) o;
        return Objects.equals(registroFuncional, professor.registroFuncional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), registroFuncional);
    }

    @Override
    public String toString() {
        return String.format("%s | Reg. Funcional: %s | Depto: %s", super.toString(), registroFuncional, departamento);
    }
}
