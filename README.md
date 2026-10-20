# Academic Management System (Sistema de Gestão Acadêmica)

Backend core for an Academic Management System developed in pure Java using Object-Oriented Programming (OOP) principles and the Java Collections Framework.

## Project Structure

```
academic-management-system/
├── src/
│   ├── model/
│   │   ├── Pessoa.java               # Abstract base class (id, nome, email, getPapel())
│   │   ├── Aluno.java                # Subclass of Pessoa (matricula, curso)
│   │   ├── Professor.java            # Subclass of Pessoa (registroFuncional, departamento)
│   │   ├── Disciplina.java           # Course/Subject (equals/hashCode based on code)
│   │   ├── StatusAprovacao.java      # Enum with grade-based status rules
│   │   └── Turma.java                # Class section managing capacity, students and grades
│   ├── exception/
│   │   ├── TurmaLotadaException.java         # Max class capacity exceeded
│   │   ├── AlunoNaoEncontradoException.java   # Student not found in system or class
│   │   ├── NotaInvalidaException.java        # Grade outside [0.0, 10.0]
│   │   └── TurmaNaoEncontradaException.java  # Class section not found
│   ├── service/
│   │   └── EscolaService.java        # Business service with HashMap O(1) lookups
│   └── application/
│       └── Main.java                 # Interactive lifecycle simulation
└── run.ps1                           # Build & execute script
```

## OOP Principles & Collections Used
- **Abstraction & Polymorphism**: Abstract class `Pessoa` with polymorphism through `getPapel()` implemented by `Aluno` and `Professor`.
- **Encapsulation**: Private fields, strict input validation, and defensive views using `Collections.unmodifiableList`.
- **Identity & Equality**: `Disciplina` and `Turma` implement `equals()` and `hashCode()` strictly on business keys.
- **Java Collections API**:
  - `HashMap<String, T>` in `EscolaService` providing $O(1)$ lookup performance.
  - `ArrayList<Aluno>` and `LinkedHashMap<String, List<Double>>` in `Turma` for ordered student rosters and grade histories.
- **Academic Rules**:
  - Average $\ge 7.0$: **Aprovado / Approved**
  - $4.0 \le$ Average $< 7.0$: **Prova Final / Final Exam**
  - Average $< 4.0$: **Reprovado / Failed**

## How to Run

From PowerShell in the project directory:
```powershell
.\run.ps1
```

Or manually:
```powershell
javac -encoding UTF-8 -d bin -sourcepath src (Get-ChildItem -Path src -Filter *.java -Recurse | ForEach-Object { $_.FullName })
java "-Dfile.encoding=UTF-8" -cp bin application.Main
```
