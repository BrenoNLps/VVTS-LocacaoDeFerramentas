package br.ifsp.demo.ui.pages;

import br.ifsp.demo.ui.base.BaseUiTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class RegisterTest extends BaseUiTest {

    @Test
    @DisplayName("Register page should render the registration form")
    public void shouldRenderRegisterForm() {
        driver.get("http://localhost:5173/register");

        assertThat(driver.findElement(By.xpath("//h1[text()='Cadastro']")).isDisplayed()).isTrue();
        assertThat(driver.findElement(By.xpath("//input[@placeholder='Nome']")).isDisplayed()).isTrue();
        assertThat(driver.findElement(By.xpath("//input[@placeholder='Sobrenome']")).isDisplayed()).isTrue();
        assertThat(driver.findElement(By.xpath("//input[@placeholder='Email']")).isDisplayed()).isTrue();
        assertThat(driver.findElement(By.xpath("//input[@placeholder='Senha']")).isDisplayed()).isTrue();
        assertThat(driver.findElement(By.xpath("//button[text()='Cadastrar']")).isDisplayed()).isTrue();
        assertThat(driver.findElement(By.linkText("Entrar")).isDisplayed()).isTrue();
    }
}
