package biblioteca;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class AplicacaoWeb {

    public static HttpServer iniciar(int porta) throws IOException {
        HttpServer servidor = HttpServer.create(
                new InetSocketAddress("127.0.0.1", porta), 0
        );

        servidor.createContext("/", troca -> {
            String consulta = troca.getRequestURI().getRawQuery();
            String mensagem = "";

            if (consulta != null) {
                String quantidade = "";

                for (String parametro : consulta.split("&")) {
                    String[] partes = parametro.split("=", 2);

                    if (partes[0].equals("quantidade")) {
                        quantidade = partes.length == 2
                                ? URLDecoder.decode(
                                        partes[1], StandardCharsets.UTF_8)
                                : "";
                    }
                }

                mensagem = validarQuantidade(quantidade);
            }

            String pagina = """
                    <!DOCTYPE html>
                    <html lang="pt-BR">
                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport"
                              content="width=device-width, initial-scale=1">
                        <title>Biblioteca Web</title>
                        <style>
                            body {
                                font-family: Arial, sans-serif;
                                background: #f3f5f7;
                                padding: 24px;
                            }
                            main {
                                max-width: 480px;
                                margin: 40px auto;
                                padding: 28px;
                                background: white;
                                border-radius: 12px;
                            }
                            input, button {
                                box-sizing: border-box;
                                width: 100%%;
                                padding: 12px;
                                margin-top: 12px;
                            }
                            button {
                                background: #205eaa;
                                color: white;
                                border: none;
                                cursor: pointer;
                            }
                        </style>
                    </head>
                    <body>
                        <main>
                            <h1>Reserva de livros</h1>
                            <p>Informe uma quantidade de 1 a 5 livros.</p>

                            <form method="get" action="/">
                                <label for="quantidade">
                                    Quantidade de livros
                                </label>
                                <input id="quantidade"
                                       name="quantidade"
                                       type="text">

                                <button id="validar" type="submit">
                                    Validar solicitação
                                </button>
                            </form>

                            <p id="mensagem" role="status">%s</p>
                        </main>
                    </body>
                    </html>
                    """.formatted(mensagem);

            byte[] resposta = pagina.getBytes(StandardCharsets.UTF_8);

            troca.getResponseHeaders().set(
                    "Content-Type", "text/html; charset=UTF-8"
            );
            troca.sendResponseHeaders(200, resposta.length);

            try (var corpo = troca.getResponseBody()) {
                corpo.write(resposta);
            }
        });

        servidor.start();
        return servidor;
    }

    private static String validarQuantidade(String entrada) {
        if (entrada.isBlank()) {
            return "Informe a quantidade.";
        }

        try {
            int quantidade = Integer.parseInt(entrada.trim());

            if (quantidade < 1 || quantidade > 5) {
                return "A quantidade deve ser de 1 a 5.";
            }

            return "Solicitação válida.";
        } catch (NumberFormatException erro) {
            return "Digite um número inteiro.";
        }
    }

    public static void main(String[] args) throws IOException {
        iniciar(8080);
        System.out.println("Abra http://localhost:8080");
    }
}
