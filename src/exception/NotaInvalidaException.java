package exception;

/**
 * Exceção lançada quando uma nota informada está fora do intervalo permitido [0.0, 10.0].
 */
public class NotaInvalidaException extends RuntimeException {

    private final double nota;

    public NotaInvalidaException(double nota) {
        super(String.format("Nota inválida: %.2f. A nota deve estar estritamente entre 0.0 e 10.0.", nota));
        this.nota = nota;
    }

    public double getNota() {
        return nota;
    }
}
