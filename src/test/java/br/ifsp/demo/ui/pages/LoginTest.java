package br.ifsp.demo.ui.pages;

import br.ifsp.demo.ui.base.BaseUiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class LoginTest extends BaseUiTest {

    @Test
    @DisplayName("Login page should render the authentication form")
    public void shouldRenderLoginForm() {
        driver.get("http://localhost:5173/");

        assertThat(driver.findElement(By.xpath("//h1[text()='Login']")).isDisplayed()).isTrue();
        assertThat(driver.findElement(By.xpath("//input[@placeholder='Email']")).isDisplayed()).isTrue();
        assertThat(driver.findElement(By.xpath("//input[@placeholder='Senha']")).isDisplayed()).isTrue();
        assertThat(driver.findElement(By.xpath("//button[text()='Entrar']")).isDisplayed()).isTrue();
        assertThat(driver.findElement(By.linkText("Cadastre-se")).isDisplayed()).isTrue();
    }
}
