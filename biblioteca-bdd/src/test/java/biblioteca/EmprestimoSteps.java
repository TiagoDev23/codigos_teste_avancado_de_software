package biblioteca;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Então;

import static org.junit.jupiter.api.Assertions.*;

public class EmprestimoSteps {

    private Livro livro;
    private boolean resultado;

    @Dado("que existe um exemplar disponível")
    public void criarExemplarDisponivel() {
        livro = new Livro();
    }

    @Dado("que existe um exemplar já emprestado")
    public void criarExemplarEmprestado() {
        livro = new Livro();
        assertTrue(livro.emprestar(),
                "A preparação deve conseguir emprestar o exemplar");
    }

    @Quando("solicito o empréstimo")
    public void solicitarEmprestimo() {
        resultado = livro.emprestar();
    }

    @Então("o empréstimo deve ser aceito")
    public void verificarEmprestimoAceito() {
        assertTrue(resultado);
    }

    @Então("o empréstimo deve ser recusado")
    public void verificarEmprestimoRecusado() {
        assertFalse(resultado);
    }

    @Então("o exemplar deve ficar indisponível")
    public void verificarIndisponibilidade() {
        assertFalse(livro.isDisponivel());
    }
}