package model;

/**
 * Enumeração que define o status acadêmico do aluno a partir da sua média ponderada/aritmética.
 * - Aprovado: média >= 7.0
 * - Prova Final: 4.0 <= média < 7.0
 * - Reprovado: média < 4.0
 */
public enum StatusAprovacao {

    APROVADO("Aprovado"),
    PROVA_FINAL("Prova Final"),
    REPROVADO("Reprovado");

    private final String descricao;

    StatusAprovacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    /**
     * Determina o status acadêmico com base na média final do aluno.
     *
     * @param media Média final (entre 0.0 e 10.0)
     * @return StatusAprovacao correspondente
     */
    public static StatusAprovacao aPartirDaMedia(double media) {
        if (media >= 7.0) {
            return APROVADO;
        } else if (media >= 4.0) {
            return PROVA_FINAL;
        } else {
            return REPROVADO;
        }
    }

    @Override
    public String toString() {
        return descricao;
    }
}
