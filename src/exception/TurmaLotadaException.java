package exception;

/**
 * Exceção lançada quando uma turma atinge sua capacidade máxima de alunos.
 */
public class TurmaLotadaException extends RuntimeException {

    private final String codigoTurma;
    private final int capacidadeMaxima;

    public TurmaLotadaException(String codigoTurma, int capacidadeMaxima) {
        super(String.format("A turma '%s' já atingiu sua capacidade máxima de %d alunos.", codigoTurma, capacidadeMaxima));
        this.codigoTurma = codigoTurma;
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public TurmaLotadaException(String message) {
        super(message);
        this.codigoTurma = null;
        this.capacidadeMaxima = -1;
    }

    public String getCodigoTurma() {
        return codigoTurma;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }
}
