package biblioteca;

public class Livro {

    private boolean disponivel = true;

    public boolean emprestar() {
        if(!disponivel){
            return false;
        }
        disponivel = false;
        return true;
    }

    public boolean isDisponivel() {
        return disponivel;
    }
}