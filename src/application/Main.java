package application;

import exception.AlunoNaoEncontradoException;
import exception.NotaInvalidaException;
import exception.TurmaLotadaException;
import model.Aluno;
import model.Disciplina;
import model.Professor;
import service.EscolaService;

/**
 * Ponto de entrada da aplicação.
 * Simula o ciclo de vida acadêmico: cadastros, atribuição de turmas, matrículas,
 * lançamentos de notas, cálculo de status (Aprovado/Prova Final/Reprovado)
 * e captura robusta de exceções de negócio (capacidade estourada e notas inválidas).
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("     SISTEMA DE GESTÃO ACADÊMICA - SIMULAÇÃO DO NÚCLEO BACKEND         ");
        System.out.println("========================================================================\n");

        EscolaService escolaService = new EscolaService();

        // ---------------------------------------------------------------------------------
        // 1. CADASTRO DE PROFESSORES E DISCIPLINAS
        // ---------------------------------------------------------------------------------
        System.out.println("[ETAPA 1] Cadastrando Professores e Disciplinas...");
        Professor profAlan = new Professor(
                "P01", "Dr. Alan Turing", "alan.turing@universidade.edu", "RF-1001", "Ciência da Computação");
        Professor profAda = new Professor(
                "P02", "Dra. Ada Lovelace", "ada.lovelace@universidade.edu", "RF-1002", "Engenharia de Software");

        escolaService.cadastrarProfessor(profAlan);
        escolaService.cadastrarProfessor(profAda);
        System.out.println("  ✓ Professor cadastrado: " + profAlan.getNome() + " (" + profAlan.getRegistroFuncional() + ")");
        System.out.println("  ✓ Professor cadastrado: " + profAda.getNome() + " (" + profAda.getRegistroFuncional() + ")");

        Disciplina discPOO = new Disciplina("CC201", "Programação Orientada a Objetos", 60);
        Disciplina discED = new Disciplina("CC202", "Estrutura de Dados", 80);

        escolaService.cadastrarDisciplina(discPOO);
        escolaService.cadastrarDisciplina(discED);
        System.out.println("  ✓ Disciplina cadastrada: " + discPOO);
        System.out.println("  ✓ Disciplina cadastrada: " + discED);
        System.out.println();

        // ---------------------------------------------------------------------------------
        // 2. CADASTRO DE ALUNOS
        // ---------------------------------------------------------------------------------
        System.out.println("[ETAPA 2] Cadastrando Alunos...");
        Aluno aluno1 = new Aluno("A01", "Carlos Silva", "carlos.silva@aluno.edu", "MAT-202601", "Ciência da Computação");
        Aluno aluno2 = new Aluno("A02", "Beatriz Santos", "beatriz.santos@aluno.edu", "MAT-202602", "Sistemas de Informação");
        Aluno aluno3 = new Aluno("A03", "Daniel Oliveira", "daniel.oliveira@aluno.edu", "MAT-202603", "Engenharia de Software");

        escolaService.cadastrarAluno(aluno1);
        escolaService.cadastrarAluno(aluno2);
        escolaService.cadastrarAluno(aluno3);

        System.out.println("  ✓ Aluno cadastrado com sucesso: " + aluno1.getNome() + " | Matrícula: " + aluno1.getMatricula());
        System.out.println("  ✓ Aluno cadastrado com sucesso: " + aluno2.getNome() + " | Matrícula: " + aluno2.getMatricula());
        System.out.println("  ✓ Aluno cadastrado com sucesso: " + aluno3.getNome() + " | Matrícula: " + aluno3.getMatricula());
        System.out.println();

        // ---------------------------------------------------------------------------------
        // 3. ABERTURA DE TURMAS E ATRIBUIÇÃO DE PROFESSORES
        // ---------------------------------------------------------------------------------
        System.out.println("[ETAPA 3] Abertura de Turmas...");
        // Turma TURMA-POO com capacidade reduzida de 2 vagas para testar transbordamento (overflow)
        escolaService.abrirTurma("TURMA-POO-01", "CC201", "RF-1001", 2);
        // Turma TURMA-ED com capacidade para 30 vagas
        escolaService.abrirTurma("TURMA-ED-01", "CC202", "RF-1002", 30);

        System.out.println("  ✓ Turma aberta: " + escolaService.buscarTurmaPorCodigo("TURMA-POO-01"));
        System.out.println("  ✓ Turma aberta: " + escolaService.buscarTurmaPorCodigo("TURMA-ED-01"));
        System.out.println();

        // ---------------------------------------------------------------------------------
        // 4. MATRÍCULA DE ALUNOS EM TURMAS
        // ---------------------------------------------------------------------------------
        System.out.println("[ETAPA 4] Matrícula de Alunos nas Turmas...");
        escolaService.matricularAlunoEmTurma("TURMA-POO-01", "MAT-202601");
        System.out.println("  ✓ Carlos Silva matriculado em TURMA-POO-01 (Vagas restantes: "
                + escolaService.buscarTurmaPorCodigo("TURMA-POO-01").getVagasDisponiveis() + ")");

        escolaService.matricularAlunoEmTurma("TURMA-POO-01", "MAT-202602");
        System.out.println("  ✓ Beatriz Santos matriculada em TURMA-POO-01 (Vagas restantes: "
                + escolaService.buscarTurmaPorCodigo("TURMA-POO-01").getVagasDisponiveis() + ")");

        escolaService.matricularAlunoEmTurma("TURMA-ED-01", "MAT-202603");
        System.out.println("  ✓ Daniel Oliveira matriculado em TURMA-ED-01 (Vagas restantes: "
                + escolaService.buscarTurmaPorCodigo("TURMA-ED-01").getVagasDisponiveis() + ")");
        System.out.println();

        // ---------------------------------------------------------------------------------
        // 5. TESTE DE ERRO CONTROLADO: TURMA LOTADA (CAPACIDADE MÁXIMA)
        // ---------------------------------------------------------------------------------
        System.out.println("[ETAPA 5] Testando estouro de limite de vagas (TurmaLotadaException)...");
        try {
            System.out.println("  -> Tentando matricular Daniel Oliveira em TURMA-POO-01 (Capacidade: 2, Já matriculados: 2)...");
            escolaService.matricularAlunoEmTurma("TURMA-POO-01", "MAT-202603");
            System.err.println("  ERRO: Deveria ter lançado TurmaLotadaException!");
        } catch (TurmaLotadaException e) {
            System.out.println("  [SUCESSO] Exceção capturada conforme esperado: " + e.getMessage());
            System.out.println("            Código da turma: " + e.getCodigoTurma() + " | Limite: " + e.getCapacidadeMaxima());
        }
        System.out.println();

        // ---------------------------------------------------------------------------------
        // 6. TESTE DE ERRO CONTROLADO: NOTA INVÁLIDA (< 0.0 ou > 10.0)
        // ---------------------------------------------------------------------------------
        System.out.println("[ETAPA 6] Testando validação de nota fora do intervalo (NotaInvalidaException)...");
        try {
            System.out.println("  -> Tentando lançar nota 11.5 para Carlos Silva em TURMA-POO-01...");
            escolaService.lancarNota("TURMA-POO-01", "MAT-202601", 11.5);
            System.err.println("  ERRO: Deveria ter lançado NotaInvalidaException!");
        } catch (NotaInvalidaException e) {
            System.out.println("  [SUCESSO] Exceção capturada conforme esperado: " + e.getMessage());
        }

        try {
            System.out.println("  -> Tentando lançar nota -1.0 para Carlos Silva em TURMA-POO-01...");
            escolaService.lancarNota("TURMA-POO-01", "MAT-202601", -1.0);
            System.err.println("  ERRO: Deveria ter lançado NotaInvalidaException!");
        } catch (NotaInvalidaException e) {
            System.out.println("  [SUCESSO] Exceção capturada conforme esperado: " + e.getMessage());
        }
        System.out.println();

        // ---------------------------------------------------------------------------------
        // 7. LANÇAMENTO DE NOTAS VÁLIDAS E CÁLCULO DE STATUS ACADÊMICOS
        // ---------------------------------------------------------------------------------
        System.out.println("[ETAPA 7] Lançamento de Notas Válidas...");
        // Aluno 1: Carlos Silva (esperado: APROVADO, média >= 7.0)
        escolaService.lancarNota("TURMA-POO-01", "MAT-202601", 8.5);
        escolaService.lancarNota("TURMA-POO-01", "MAT-202601", 9.0);
        System.out.println("  ✓ Notas [8.5, 9.0] lançadas para Carlos Silva");

        // Aluno 2: Beatriz Santos (esperado: PROVA FINAL, 4.0 <= média < 7.0)
        escolaService.lancarNota("TURMA-POO-01", "MAT-202602", 5.0);
        escolaService.lancarNota("TURMA-POO-01", "MAT-202602", 6.0);
        System.out.println("  ✓ Notas [5.0, 6.0] lançadas para Beatriz Santos");

        // Aluno 3: Daniel Oliveira (esperado: REPROVADO, média < 4.0)
        escolaService.lancarNota("TURMA-ED-01", "MAT-202603", 2.0);
        escolaService.lancarNota("TURMA-ED-01", "MAT-202603", 3.5);
        System.out.println("  ✓ Notas [2.0, 3.5] lançadas para Daniel Oliveira em TURMA-ED-01");
        System.out.println();

        // ---------------------------------------------------------------------------------
        // 8. TESTE DE ERRO CONTROLADO: ALUNO NÃO ENCONTRADO
        // ---------------------------------------------------------------------------------
        System.out.println("[ETAPA 8] Testando busca por aluno inexistente (AlunoNaoEncontradoException)...");
        try {
            System.out.println("  -> Buscando aluno com matrícula inexistente 'MAT-999999'...");
            escolaService.buscarAlunoPorMatricula("MAT-999999");
            System.err.println("  ERRO: Deveria ter lançado AlunoNaoEncontradoException!");
        } catch (AlunoNaoEncontradoException e) {
            System.out.println("  [SUCESSO] Exceção capturada conforme esperado: " + e.getMessage());
        }
        System.out.println();

        // ---------------------------------------------------------------------------------
        // 9. EMISSÃO DOS BOLETINS E VERIFICAÇÃO DOS STATUS ACADÊMICOS
        // ---------------------------------------------------------------------------------
        System.out.println("[ETAPA 9] Emissão de Boletins Acadêmicos:\n");
        System.out.print(escolaService.gerarRelatorioTurma("TURMA-POO-01"));
        System.out.println();
        System.out.print(escolaService.gerarRelatorioTurma("TURMA-ED-01"));
        System.out.println();

        System.out.println("========================================================================");
        System.out.println("     SIMULAÇÃO CONCLUÍDA COM SUCESSO - TODAS AS REGRAS VALIDADAS       ");
        System.out.println("========================================================================");
    }
}
