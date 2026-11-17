# Biblioteca Mobile

Aula prática de 10 a 15 minutos: validar de 1 a 5 livros e demonstrar testes de interface, limites, correção de entrada e recriação de uma Activity. Aplicação e testes em Java, com Views/XML, sem banco, login, API, acesso à internet em execução ou armazenamento permanente.

## Tecnologias e pré-requisitos

- JDK **21** para executar o Gradle (encontrado neste ambiente: **21.0.2**); código Java configurado para nível **17**.
- Gradle **8.13**, com Wrapper completo; Android Gradle Plugin **8.13.2**.
- Android SDK Platform **36**, SDK Build-Tools **35.0.0** (padrão do AGP) e Platform-Tools.
- `compileSdk` e `targetSdk` **36**; Android mínimo **6.0 / API 23**.
- AndroidX Core **1.17.0** para tratar barras do sistema, recortes e teclado.
- JUnit **4.13.2**, Espresso **3.7.0**, AndroidX Test Core/Runner/Rules **1.7.0** e Ext JUnit **1.3.0**. Runner: `AndroidJUnitRunner`.
- Android Studio compatível com AGP 8.13 (por exemplo, **Otter 2 Feature Drop 2025.2.2**) e emulador ou aparelho API 23+. Para exercitar o comportamento do target SDK, prefira um emulador API 36.

As versões estão fixadas. A compatibilidade está documentada nas [notas do AGP 8.13](https://developer.android.com/build/releases/agp-8-13-0-release-notes), na [matriz Java/Gradle](https://docs.gradle.org/current/userguide/compatibility.html) e nas [versões estáveis do AndroidX Test](https://developer.android.com/jetpack/androidx/releases/test).

O Wrapper baixa o Gradle na primeira execução; **não é necessário Gradle global**. A configuração inicial precisa de internet para baixar Gradle e dependências. Depois de instalado, o aplicativo funciona offline. O JAR oficial do Wrapper foi obtido do repositório Gradle, tag `v8.13.0`, e seu SHA-256 foi conferido na [referência oficial de checksums](https://gradle.org/release-checksums/): `81a82aaea5abcc8ff68b3dfcb58b3c3c429378efd98e7433460610fecd7ae45f`. A distribuição também tem checksum configurado no Wrapper.

## Abrir e executar no Android Studio

1. Use **File > Open** e selecione a pasta **biblioteca-mobile**, não a raiz Maven da aula. Aguarde o Gradle Sync.
2. Em **Settings > Build, Execution, Deployment > Build Tools > Gradle**, escolha o JDK 21 e o Wrapper do projeto.
3. No **SDK Manager**, instale Platform 36, Build-Tools 35.0.0 e Platform-Tools. O Android Studio gera `local.properties` com o caminho local do SDK; esse arquivo não deve ser versionado.
4. No **Device Manager**, crie manualmente um dispositivo virtual, escolha uma imagem API 36 compatível com o computador e inicie-o pelo botão de reprodução. Alternativamente, conecte um aparelho com depuração USB autorizada.
5. Selecione o dispositivo e a configuração **app** e clique em **Run**. Informe `3` e toque em **Validar solicitação**.

Para os **oito testes**, abra `app/src/androidTest/java/com/exemplo/bibliotecamobile/ReservaMobileTest.java`, clique no ícone de execução da classe e escolha **Run 'ReservaMobileTest'**. Cada teste inicia sua própria Activity pela `ActivityScenarioRule`; o resultado aparece na janela **Run**. Os auxiliares localizam por ID, fazem rolagem e fecham o teclado antes do clique.

## Comandos PowerShell

Após instalar o SDK, ajuste `ANDROID_HOME` ao caminho mostrado no SDK Manager. Exemplo com o local padrão do Windows:

```powershell
Set-Location C:\atdd_bdd_aula_pratica\biblioteca-mobile
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-21'
$env:ANDROID_HOME = "$env:LOCALAPPDATA\Android\Sdk"

.\gradlew.bat --version
# Compila o aplicativo e o APK dos testes; não executa testes.
.\gradlew.bat :app:assembleDebug :app:assembleDebugAndroidTest

# Com emulador iniciado ou aparelho autorizado:
& "$env:ANDROID_HOME\platform-tools\adb.exe" devices
.\gradlew.bat :app:installDebug
& "$env:ANDROID_HOME\platform-tools\adb.exe" shell am start -n com.exemplo.bibliotecamobile/.MainActivity
.\gradlew.bat :app:connectedDebugAndroidTest
```

Não se usa **`mvn test`** neste projeto: ele é independente dos projetos Maven vizinhos. Compilar APKs com sucesso não significa que os testes foram executados.

Arquivos produzidos após os comandos correspondentes:

- APK do app: `app/build/outputs/apk/debug/app-debug.apk`.
- APK de testes: `app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk`.
- Relatório HTML dos testes executados pelo Gradle: `app/build/reports/androidTests/connected/debug/index.html`.
- Resultados XML por dispositivo: `app/build/outputs/androidTest-results/connected/debug/`.

## Roteiro da aula

1. Apresente a tela e `MainActivity.validarQuantidade`: vazio/espaços → “Informe a quantidade.”; inteiro fora de 1 a 5 → “A quantidade deve ser de 1 a 5.”; inteiro permitido → “Solicitação válida.”; falha na conversão → “Digite um número inteiro.”. O teclado é numérico; um valor que exceda o intervalo de `int`, como `2147483648`, demonstra a falha de conversão. O código também trata entrada não numérica, embora o campo restrinja a digitação.
2. Execute a classe e explique os oito casos: `3`, limites `1` e `5`, rejeições `0`, `6` e vazio, correção de `6` para `3`, e manutenção dos dados após recriação. Não há testes de exemplo adicionais.
3. Demonstre um defeito: em `MainActivity.java`, altere temporariamente **`valor > 5`** para **`valor >= 5`**. Execute novamente: **`aceitarLimiteMaximo` deve falhar**, pois `5` será rejeitado. Restaure `valor > 5` e repita os testes. A regra entregue está correta.
4. Para complementar, informe `3`, valide e gire manualmente o emulador/aparelho para a horizontal e de volta. Confira campo, mensagem, rolagem e acesso aos controles com teclado aberto. A orientação fica livre; não há `configChanges` para evitar recriação.

`ActivityScenario.recreate()` testa **recriação da Activity**, não rotação física nem encerramento do processo. `onSaveInstanceState` e `onRestoreInstanceState` salvam/restauram temporariamente quantidade e mensagem. Isso **não é persistência permanente** e não garante recuperação após qualquer encerramento do aplicativo. O resultado usa uma região acessível `polite`, e os espaços das barras/teclado são aplicados à área rolável, inclusive com o edge-to-edge do target 36.

## Validação neste workspace

Foi encontrado JDK 21.0.2, e o usuário executou o Wrapper com sucesso, confirmando Gradle 8.13. Após a instalação manual do Android Studio e dos componentes necessários, foram confirmados Platform 36, Build-Tools 35.0.0 e Platform-Tools no SDK em `C:\Users\tiago\AppData\Local\Android\Sdk`. O caminho foi configurado no `local.properties` local, ignorado pelo Git. Não foram instaladas ferramentas grandes nem criado emulador automaticamente.

Foram conferidos estaticamente os caminhos, packages, imports, IDs, recursos XML, a sintaxe Java, os oito métodos de teste e o JAR oficial do Wrapper. O usuário executou `:app:assembleDebug :app:assembleDebugAndroidTest` com **BUILD SUCCESSFUL**, e os dois APKs foram encontrados nos caminhos documentados acima. **Os testes instrumentados ainda não foram executados.** A tentativa de instalação retornou `No connected devices`; é necessário concluir a inicialização de um emulador ou conectar um aparelho autorizado antes de executar `:app:installDebug` e `:app:connectedDebugAndroidTest`.
