# Biblioteca BDD

Exemplo de BDD com Java 17, Maven, Cucumber e JUnit Platform.

## Estrutura

```text
pom.xml
src/
  main/java/biblioteca/
    Livro.java
  test/
    java/biblioteca/
      EmprestimoSteps.java
      RunCucumberTest.java
    resources/features/
      emprestimo.feature
```

## Executar

Com JDK 17 ou superior e Maven no PATH, execute dentro de `biblioteca-bdd`:

```powershell
mvn test
```

O projeto está na etapa vermelha da demonstração: `Livro.emprestar()` ainda
retorna `false`. O cenário deve ser encontrado e falhar no passo
“Então o empréstimo deve ser aceito”, com `expected: <true> but was: <false>`.
Essa falha é intencional; o próximo passo do exercício é implementar o empréstimo.

O relatório HTML é gerado em `target/relatorio-cucumber.html`. Os resultados
do Maven ficam em `target/surefire-reports/`. A pasta `target` contém arquivos
gerados e não deve ser editada manualmente.

## VS Code

Abra a pasta `biblioteca-bdd` e salve os arquivos em UTF-8. Se o editor continuar
marcando erros apesar de o Maven compilar, execute **Java: Clean Java Language
Server Workspace** na paleta de comandos (`Ctrl+Shift+P`), confirme a
reinicialização e aguarde a importação do projeto.

Mantenha `package biblioteca;` e os imports em português do Cucumber.
