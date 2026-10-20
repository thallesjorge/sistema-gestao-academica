package exception;

/**
 * Exceção lançada quando um aluno não é encontrado no sistema ou na turma.
 */
public class AlunoNaoEncontradoException extends RuntimeException {

    private final String matricula;

    public AlunoNaoEncontradoException(String matricula) {
        super(String.format("Aluno com matrícula '%s' não foi encontrado.", matricula));
        this.matricula = matricula;
    }

    public AlunoNaoEncontradoException(String matricula, String codigoTurma) {
        super(String.format("Aluno com matrícula '%s' não está matriculado na turma '%s'.", matricula, codigoTurma));
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }
}
