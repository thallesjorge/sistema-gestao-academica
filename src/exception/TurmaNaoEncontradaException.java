package exception;

/**
 * Exceção lançada quando uma turma não é encontrada pelo seu código identificador.
 */
public class TurmaNaoEncontradaException extends RuntimeException {

    private final String codigoTurma;

    public TurmaNaoEncontradaException(String codigoTurma) {
        super(String.format("Turma com código '%s' não foi encontrada.", codigoTurma));
        this.codigoTurma = codigoTurma;
    }

    public String getCodigoTurma() {
        return codigoTurma;
    }
}
