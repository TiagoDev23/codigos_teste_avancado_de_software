package biblioteca;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.StaleElementReferenceException;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservaWebTest {

    private static HttpServer servidor;
    private static String endereco;
    private WebDriver navegador;

    @BeforeAll
    static void iniciarAplicacao() throws Exception {
        // Porta 0: o sistema escolhe uma porta livre.
        servidor = AplicacaoWeb.iniciar(0);
        endereco = "http://127.0.0.1:"
                + servidor.getAddress().getPort();
    }

    @BeforeEach
    void abrirNavegador() {
        navegador = new ChromeDriver();
        navegador.get(endereco);
    }

    @AfterEach
    void fecharNavegador() {
        if (navegador != null) {
            navegador.quit();
        }
    }

    @AfterAll
    static void pararAplicacao() {
        if (servidor != null) {
            servidor.stop(0);
        }
    }

    @ParameterizedTest(name = "Quantidade {0}: {1}")
    @CsvSource({
            "3, Solicitação válida.",
            "0, A quantidade deve ser de 1 a 5.",
            "1, Solicitação válida.",
            "5, Solicitação válida.",
            "6, A quantidade deve ser de 1 a 5.",
            "abc, Digite um número inteiro."
    })
    void validarQuantidades(String entrada, String esperado) {
        preencherEEnviar(entrada);

        assertEquals(esperado, mensagemExibida());
    }

    @Test
    void recusarCampoVazio() {
        preencherEEnviar("");

        assertEquals("Informe a quantidade.", mensagemExibida());
    }

    private void preencherEEnviar(String entrada) {
        navegador.findElement(By.id("quantidade")).sendKeys(entrada);
        navegador.findElement(By.id("validar")).click();
    }

private String mensagemExibida() {
    WebDriverWait espera = new WebDriverWait(
            navegador, Duration.ofSeconds(5)
    );

    espera.ignoring(StaleElementReferenceException.class);

    return espera.until(driver -> {
        String mensagem = driver
                .findElement(By.id("mensagem"))
                .getText();

        return mensagem.isBlank() ? null : mensagem;
    });
}
}