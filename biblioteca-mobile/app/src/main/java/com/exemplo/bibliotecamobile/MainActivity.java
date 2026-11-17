package com.exemplo.bibliotecamobile;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends Activity {

    private static final String ESTADO_QUANTIDADE = "estado_quantidade";
    private static final String ESTADO_MENSAGEM = "estado_mensagem";

    private EditText quantidade;
    private TextView mensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            // Android 6/7 não permitem ícones escuros na barra de navegação.
            getWindow().setNavigationBarColor(Color.BLACK);
        }
        setContentView(R.layout.activity_main);

        quantidade = findViewById(R.id.quantidade);
        mensagem = findViewById(R.id.mensagem);
        findViewById(R.id.validar).setOnClickListener(view -> validarQuantidade());
        configurarEspacosDoSistema();
    }

    private void configurarEspacosDoSistema() {
        View tela = findViewById(R.id.tela);
        WindowCompat.getInsetsController(getWindow(), tela)
                .setAppearanceLightStatusBars(true);
        WindowCompat.getInsetsController(getWindow(), tela)
                .setAppearanceLightNavigationBars(true);

        ViewCompat.setOnApplyWindowInsetsListener(tela, (view, windowInsets) -> {
            // A união usa o maior espaço em cada lado, sem somar teclado e navegação.
            Insets espacos = windowInsets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                            | WindowInsetsCompat.Type.displayCutout()
                            | WindowInsetsCompat.Type.ime());
            view.setPadding(espacos.left, espacos.top, espacos.right, espacos.bottom);
            return windowInsets;
        });
        ViewCompat.requestApplyInsets(tela);
    }

    private void validarQuantidade() {
        String entrada = quantidade.getText().toString().trim();
        if (entrada.isEmpty()) {
            mensagem.setText(R.string.informe_quantidade);
            return;
        }

        final int valor;
        try {
            valor = Integer.parseInt(entrada);
        } catch (NumberFormatException exception) {
            mensagem.setText(R.string.digite_numero_inteiro);
            return;
        }

        if (valor < 1 || valor > 5) {
            mensagem.setText(R.string.quantidade_fora_do_intervalo);
        } else {
            mensagem.setText(R.string.solicitacao_valida);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(ESTADO_QUANTIDADE, quantidade.getText().toString());
        outState.putString(ESTADO_MENSAGEM, mensagem.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        quantidade.setText(savedInstanceState.getString(ESTADO_QUANTIDADE, ""));
        mensagem.setText(savedInstanceState.getString(ESTADO_MENSAGEM, ""));
    }
}
