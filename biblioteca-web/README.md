# Biblioteca Web

Exemplo de testes de uma aplicação web com Java 21, Maven, Selenium WebDriver
e JUnit 5. O formulário valida a quantidade de livros solicitada para reserva.
A página é servida pelo `HttpServer` do próprio JDK.

## Pré-requisitos

- JDK 21 ou superior, com `JAVA_HOME` configurado.
- Maven no `PATH`.
- Google Chrome instalado para executar os testes automatizados.
- Acesso à internet para baixar as dependências Maven na primeira execução.
  O Selenium Manager resolve o ChromeDriver automaticamente e também pode
  precisar de internet para baixar um driver compatível com o Chrome.

Confira o ambiente no terminal:

```powershell
java -version
mvn -version
```

O Java mostrado por `mvn -version` deve ser 21 ou superior.

## Estrutura

```text
biblioteca-web/
  pom.xml
  README.md
  src/
    main/java/biblioteca/
      AplicacaoWeb.java
    test/java/biblioteca/
      ReservaWebTest.java
```

- `AplicacaoWeb.java`: inicia o servidor HTTP, exibe o formulário e valida a entrada.
- `ReservaWebTest.java`: abre o Chrome com Selenium, preenche o formulário e
  verifica as mensagens com JUnit 5.
- `pom.xml`: define as dependências e os plugins para compilar, testar e iniciar
  a aplicação.

## Executar a aplicação

Dentro de `biblioteca-web`, execute:

```powershell
mvn compile exec:java
```

Se o terminal estiver na raiz `atdd_bdd_aula_pratica`, use:

```powershell
mvn -pl biblioteca-web compile exec:java
```

Abra <http://localhost:8080>, preencha **Quantidade de livros** e clique em
**Validar solicitação**. O servidor fica em execução no terminal; use `Ctrl+C`
para encerrá-lo.

### Regras de validação

| Entrada | Mensagem esperada |
| --- | --- |
| Número inteiro de 1 a 5, inclusive | `Solicitação válida.` |
| Número inteiro menor que 1 ou maior que 5, como 0 ou 6 | `A quantidade deve ser de 1 a 5.` |
| Texto ou número decimal, como `abc` ou `2.5` | `Digite um número inteiro.` |
| Campo vazio ou somente espaços | `Informe a quantidade.` |

Este exemplo valida a quantidade informada; ele não grava reservas nem mantém
um catálogo ou estoque de livros.

## Executar os testes

Dentro de `biblioteca-web`, execute:

```powershell
mvn test
```

Na raiz do repositório, execute apenas este módulo com:

```powershell
mvn -pl biblioteca-web test
```

Para executar somente a classe de teste a partir do módulo:

```powershell
mvn "-Dtest=ReservaWebTest" test
```

Não é necessário iniciar a aplicação manualmente antes dos testes. A própria
classe inicia um servidor em uma porta livre, abre uma janela do Chrome para
cada caso e encerra o navegador e o servidor ao final. Os testes atuais usam
o Chrome com interface gráfica.

São sete casos automatizados: quantidades `3`, `0`, `1`, `5`, `6`, texto `abc`
e campo vazio. Eles cobrem entradas válidas, limites da faixa e entradas
inválidas. Ao concluir com sucesso, o Maven mostra `BUILD SUCCESS` e o resumo
dos sete testes sem falhas.

Os resultados ficam em `target/surefire-reports/`, em arquivos de texto e XML.
Este módulo não gera o relatório HTML do Cucumber usado na `biblioteca-bdd`.
A pasta `target` contém arquivos gerados e não deve ser editada manualmente.

Para remover os resultados anteriores e executar novamente:

```powershell
mvn clean test
```

## Relação com Biblioteca BDD

O módulo [biblioteca-bdd](../biblioteca-bdd/README.md) descreve cenários de
empréstimo em Gherkin e os executa com Cucumber. Este módulo verifica o
formulário de reserva por meio de um navegador real. Os exemplos são
independentes e não compartilham o estado dos livros.

Para executar os dois módulos, use na raiz do repositório:

```powershell
mvn test
```

Esse comando exige JDK 21 ou superior por incluir a `biblioteca-web`.

## Roteiro da aula prática

1. Inicie a aplicação e experimente as entradas da tabela de validação.
2. Encerre a aplicação e execute `mvn test` dentro do módulo.
3. Compare cada caso de `ReservaWebTest.java` com a mensagem exibida na página.
4. Como exercício, adicione casos para números decimais e entradas com espaços.
5. Ao alterar uma regra, ajuste a expectativa do teste, observe a falha,
   implemente o comportamento e execute os testes novamente.

## VS Code

Abra a pasta raiz `atdd_bdd_aula_pratica` com suporte a Java e Maven habilitado.
O `pom.xml` da raiz reúne os dois módulos para importação pelo editor.
Mantenha os arquivos em UTF-8 e a declaração `package biblioteca;`.

Se o editor continuar marcando erros após o Maven compilar, execute
**Java: Clean Java Language Server Workspace** na paleta (`Ctrl+Shift+P`),
confirme a reinicialização e aguarde a importação Maven.

## Problemas comuns

| Problema | O que verificar |
| --- | --- |
| `mvn` não é reconhecido | Instale o Maven, inclua sua pasta `bin` no `PATH` e reabra o terminal. |
| `release version 21 not supported` | Confira `JAVA_HOME` e o Java usado em `mvn -version`; selecione um JDK 21 ou superior. |
| Chrome ou ChromeDriver não inicia | Confira se o Chrome está instalado e se a rede permite o download pelo Selenium Manager. Em redes com proxy, verifique a configuração de acesso. |
| `Address already in use` ao iniciar a aplicação | Encerre a instância anterior ou libere a porta 8080. Os testes escolhem uma porta livre automaticamente. |
| Maven não encontra um projeto | Execute o comando na pasta que contém o `pom.xml` correspondente; na raiz, use `-pl biblioteca-web` para selecionar este módulo. |
| Testes falham ao verificar mensagens | Consulte `target/surefire-reports/` e compare o resultado com as regras em `AplicacaoWeb.java`. |
