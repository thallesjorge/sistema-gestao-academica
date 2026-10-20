package model;

import java.util.Objects;

/**
 * Classe abstrata base que representa uma pessoa no ecossistema acadêmico.
 * Demonstra abstração, encapsulamento e polimorfismo.
 */
public abstract class Pessoa {

    private final String id;
    private String nome;
    private String email;

    public Pessoa(String id, String nome, String email) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("O ID da pessoa não pode ser nulo ou vazio.");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da pessoa não pode ser nulo ou vazio.");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("O e-mail da pessoa não pode ser nulo ou vazio.");
        }
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    /**
     * Método abstrato polimórfico que define o papel desempenhado pela pessoa na instituição.
     *
     * @return Descrição do papel (ex: Aluno, Professor).
     */
    public abstract String getPapel();

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome não pode ser nulo ou vazio.");
        }
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("O e-mail não pode ser nulo ou vazio.");
        }
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(id, pessoa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("[%s] ID: %s | Nome: %s | E-mail: %s", getPapel(), id, nome, email);
    }
}
