package com.exemplo.bibliotecamobile;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ReservaMobileTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void aceitarQuantidadeValida() {
        preencherEValidar("3");
        verificarMensagem("Solicitação válida.");
    }

    @Test
    public void aceitarLimiteMinimo() {
        preencherEValidar("1");
        verificarMensagem("Solicitação válida.");
    }

    @Test
    public void aceitarLimiteMaximo() {
        preencherEValidar("5");
        verificarMensagem("Solicitação válida.");
    }

    @Test
    public void recusarAbaixoDoMinimo() {
        preencherEValidar("0");
        verificarMensagem("A quantidade deve ser de 1 a 5.");
    }

    @Test
    public void recusarAcimaDoMaximo() {
        preencherEValidar("6");
        verificarMensagem("A quantidade deve ser de 1 a 5.");
    }

    @Test
    public void recusarCampoVazio() {
        preencherEValidar("");
        verificarMensagem("Informe a quantidade.");
    }

    @Test
    public void permitirCorrigirQuantidade() {
        preencherEValidar("6");
        verificarMensagem("A quantidade deve ser de 1 a 5.");

        preencherEValidar("3");
        verificarMensagem("Solicitação válida.");
    }

    @Test
    public void manterDadosAoRecriarTela() {
        preencherEValidar("3");
        verificarMensagem("Solicitação válida.");

        activityRule.getScenario().recreate();

        onView(withId(R.id.quantidade)).perform(scrollTo()).check(matches(withText("3")));
        verificarMensagem("Solicitação válida.");
    }

    private void preencherEValidar(String valor) {
        onView(withId(R.id.quantidade))
                .perform(scrollTo(), replaceText(valor), closeSoftKeyboard());
        onView(withId(R.id.validar)).perform(scrollTo(), click());
    }

    private void verificarMensagem(String mensagemEsperada) {
        onView(withId(R.id.mensagem))
                .perform(scrollTo())
                .check(matches(withText(mensagemEsperada)));
    }
}
