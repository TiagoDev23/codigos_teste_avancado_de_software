# Aula prática de ATDD e BDD

O projeto usa Maven e mantém dois módulos independentes:

- [Biblioteca BDD](biblioteca-bdd/README.md): cenários de empréstimo com Cucumber.
- [Biblioteca Web](biblioteca-web/README.md): formulário de reserva e testes com
  Selenium, com instruções de execução e solução de problemas.

```text
pom.xml
biblioteca-bdd/
  pom.xml
  src/main/java/biblioteca/Livro.java
  src/test/java/biblioteca/EmprestimoSteps.java
  src/test/java/biblioteca/RunCucumberTest.java
  src/test/resources/features/emprestimo.feature
biblioteca-web/
  pom.xml
  src/main/java/biblioteca/AplicacaoWeb.java
  src/test/java/biblioteca/ReservaWebTest.java
```

Os arquivos Java e os cenários existentes foram preservados. A cópia idêntica
de `biblioteca-bdd` que ficava em `codigos_teste_avancado_de_software` foi
removida. O histórico e a configuração Git dessa cópia foram preservados em
`.historico/codigos_teste_avancado_de_software.git.zip`.

## Executar

Use JDK 21 ou superior e Maven no PATH. Na raiz do projeto:

```powershell
mvn test
```

Os testes de `biblioteca-web` usam Selenium e precisam do Google Chrome
instalado. O Selenium Manager resolve o ChromeDriver automaticamente e pode
precisar de acesso à internet na primeira execução.

Para executar cada módulo separadamente:

```powershell
mvn -pl biblioteca-bdd test
mvn -pl biblioteca-web test
```

Para iniciar a aplicação web em `http://localhost:8080`:

```powershell
mvn -pl biblioteca-web compile exec:java
```

## VS Code

Abra a pasta raiz `atdd_bdd_aula_pratica` com o suporte Java e Maven habilitado.
O `pom.xml` da raiz reúne os dois módulos para importação pelo editor.
O pacote dos arquivos Java continua sendo `biblioteca`.

Se o editor ainda mostrar os diagnósticos antigos, execute **Java: Clean Java
Language Server Workspace** na paleta (`Ctrl+Shift+P`), confirme a
reinicialização e aguarde a importação Maven. Reabra o teste web no novo caminho
`biblioteca-web/src/test/java/biblioteca/ReservaWebTest.java`.

As pastas `target` são geradas pelo Maven e estão ignoradas pelo Git.
