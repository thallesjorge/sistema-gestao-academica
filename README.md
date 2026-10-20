# Sistema de Gestão Acadêmica (Academic Management System)

Núcleo backend de um Sistema de Gestão Acadêmica desenvolvido em **Java puro (Java 17+)**, utilizando boas práticas de **Programação Orientada a Objetos (POO)** e a **Java Collections API**.

O projeto simula regras de negócio reais de uma instituição de ensino superior: cadastro de alunos, professores e disciplinas, abertura de turmas com limite de vagas, lançamento e validação de notas e determinação do status acadêmico final.

---

## 🛠️ Tecnologias e Conceitos Aplicados

- **Linguagem:** Java 17+ (Java puro, sem frameworks externos)
- **Paradigma:** Programação Orientada a Objetos (POO)
  - **Abstração & Herança:** Classe abstrata base `Pessoa` com especialização em `Aluno` e `Professor`.
  - **Polimorfismo:** Método abstrato polimórfico `getPapel()`.
  - **Encapsulamento Estrito:** Atributos privados e métodos defensivos com `Collections.unmodifiableList()`.
  - **Identidade e Igualdade:** Implementação consistente de `equals()` e `hashCode()` baseados nas chaves de domínio (`Disciplina` por código).
- **Estruturas de Dados & Performance:**
  - `HashMap`: Consultas em tempo constante $O(1)$ para alunos, professores, disciplinas e turmas no serviço central.
  - `ArrayList` e `LinkedHashMap`: Preservação de ordem de matrícula e histórico de avaliações.
- **Tratamento de Exceções de Negócio:**
  - `TurmaLotadaException`: Estouro de capacidade de alunos na turma.
  - `NotaInvalidaException`: Lançamento de notas fora da faixa permitida ($0.0$ a $10.0$).
  - `AlunoNaoEncontradoException`: Busca por alunos inexistentes ou alunos não matriculados na turma.
  - `TurmaNaoEncontradaException`: Busca por turmas não cadastradas.

---

## 📁 Estrutura de Pacotes

```
academic-management-system/
├── src/
│   ├── model/
│   │   ├── Pessoa.java               # Classe abstrata base (id, nome, email, getPapel())
│   │   ├── Aluno.java                # Subclasse de Pessoa (matricula, curso)
│   │   ├── Professor.java            # Subclasse de Pessoa (registroFuncional, departamento)
│   │   ├── Disciplina.java           # Entidade disciplina com equals/hashCode por código
│   │   ├── StatusAprovacao.java      # Enum com regras de cálculo de aprovação
│   │   └── Turma.java                # Gestão de capacidade, alunos matriculados e notas
│   ├── exception/
│   │   ├── TurmaLotadaException.java         # Exceção de turma com limite excedido
│   │   ├── AlunoNaoEncontradoException.java   # Exceção de aluno não localizado
│   │   ├── NotaInvalidaException.java        # Exceção de nota fora da faixa [0.0, 10.0]
│   │   └── TurmaNaoEncontradaException.java  # Exceção de código de turma inexistente
│   ├── service/
│   │   └── EscolaService.java        # Orquestrador de negócio com buscas em O(1) via HashMap
│   └── application/
│       └── Main.java                 # Simulação completa do ciclo acadêmico
├── run.ps1                           # Script de compilação e execução automatizada
└── README.md                         # Documentação completa do projeto
```

---

## 📊 Regras de Negócio para Aprovação

O status final do aluno é calculado a partir da média aritmética das notas:

| Média Final | Status Acadêmico |
| :---: | :---: |
| $\ge 7.0$ | **Aprovado** |
| $\ge 4.0$ e $< 7.0$ | **Prova Final** |
| $< 4.0$ | **Reprovado** |

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
- JDK instalado (Java 11, 17, 21 ou superior).

### Opção 1: Via Script PowerShell (Recomendado no Windows)
Na raiz do projeto, execute:
```powershell
.\run.ps1
```

### Opção 2: Compilação e Execução Manual
```powershell
# 1. Compilação com suporte a UTF-8
javac -encoding UTF-8 -d bin -sourcepath src (Get-ChildItem -Path src -Filter *.java -Recurse | ForEach-Object { $_.FullName })

# 2. Execução da classe Main
java "-Dfile.encoding=UTF-8" -cp bin application.Main
```

---

## 🖥️ Exemplo da Saída da Execução

```
========================================================================
     SISTEMA DE GESTÃO ACADÊMICA - SIMULAÇÃO DO NÚCLEO BACKEND         
========================================================================

[ETAPA 1] Cadastrando Professores e Disciplinas...
  ✓ Professor cadastrado: Dr. Alan Turing (RF-1001)
  ✓ Professor cadastrado: Dra. Ada Lovelace (RF-1002)
  ✓ Disciplina cadastrada: Disciplina[CC201 - Programação Orientada a Objetos (60h)]
  ✓ Disciplina cadastrada: Disciplina[CC202 - Estrutura de Dados (80h)]

[ETAPA 2] Cadastrando Alunos...
  ✓ Aluno cadastrado com sucesso: Carlos Silva | Matrícula: MAT-202601
  ✓ Aluno cadastrado com sucesso: Beatriz Santos | Matrícula: MAT-202602
  ✓ Aluno cadastrado com sucesso: Daniel Oliveira | Matrícula: MAT-202603

[ETAPA 3] Abertura de Turmas...
  ✓ Turma aberta: Turma TURMA-POO-01 [Programação Orientada a Objetos] | Prof: Dr. Alan Turing | Ocupação: 0/2
  ✓ Turma aberta: Turma TURMA-ED-01 [Estrutura de Dados] | Prof: Dra. Ada Lovelace | Ocupação: 0/30

[ETAPA 4] Matrícula de Alunos nas Turmas...
  ✓ Carlos Silva matriculado em TURMA-POO-01 (Vagas restantes: 1)
  ✓ Beatriz Santos matriculada em TURMA-POO-01 (Vagas restantes: 0)
  ✓ Daniel Oliveira matriculado em TURMA-ED-01 (Vagas restantes: 29)

[ETAPA 5] Testando estouro de limite de vagas (TurmaLotadaException)...
  -> Tentando matricular Daniel Oliveira em TURMA-POO-01 (Capacidade: 2, Já matriculados: 2)...
  [SUCESSO] Exceção capturada conforme esperado: A turma 'TURMA-POO-01' já atingiu sua capacidade máxima de 2 alunos.

[ETAPA 6] Testando validação de nota fora do intervalo (NotaInvalidaException)...
  -> Tentando lançar nota 11.5 para Carlos Silva em TURMA-POO-01...
  [SUCESSO] Exceção capturada conforme esperado: Nota inválida: 11,50. A nota deve estar estritamente entre 0.0 e 10.0.

[ETAPA 7] Lançamento de Notas Válidas...
  ✓ Notas [8.5, 9.0] lançadas para Carlos Silva
  ✓ Notas [5.0, 6.0] lançadas para Beatriz Santos
  ✓ Notas [2.0, 3.5] lançadas para Daniel Oliveira em TURMA-ED-01

[ETAPA 8] Testando busca por aluno inexistente (AlunoNaoEncontradoException)...
  -> Buscando aluno com matrícula inexistente 'MAT-999999'...
  [SUCESSO] Exceção capturada conforme esperado: Aluno com matrícula 'MAT-999999' não foi encontrado.

[ETAPA 9] Emissão de Boletins Acadêmicos:

=========================================================================================
 BOLETIM ACADÊMICO - TURMA: TURMA-POO-01 | DISCIPLINA: Programação Orientada a Objetos (CC201)
 Professor: Dr. Alan Turing | Ocupação: 2/2 alunos
=========================================================================================
MATRÍCULA    | ALUNO                    | NOTAS              | MÉDIA    | STATUS
-----------------------------------------------------------------------------------------
MAT-202601   | Carlos Silva             | [8.5, 9.0]         | 8,75     | Aprovado
MAT-202602   | Beatriz Santos           | [5.0, 6.0]         | 5,50     | Prova Final
=========================================================================================

=========================================================================================
 BOLETIM ACADÊMICO - TURMA: TURMA-ED-01 | DISCIPLINA: Estrutura de Dados (CC202)
 Professor: Dra. Ada Lovelace | Ocupação: 1/30 alunos
=========================================================================================
MATRÍCULA    | ALUNO                    | NOTAS              | MÉDIA    | STATUS
-----------------------------------------------------------------------------------------
MAT-202603   | Daniel Oliveira          | [2.0, 3.5]         | 2,75     | Reprovado
=========================================================================================
```

---

## 👤 Autor

Desenvolvido por **Thalles Jorge**.  
Projeto focado em demonstrar proficiência em Java, boas práticas de engenharia de software e orientação a objetos para o mercado de desenvolvimento backend.
