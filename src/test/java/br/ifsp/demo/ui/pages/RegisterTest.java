package br.ifsp.demo.ui.pages;

import br.ifsp.demo.ui.base.BaseUiTest;
import br.ifsp.demo.ui.helpers.UiTestDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

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

    @Test
    @DisplayName("Valid registration should redirect the user to login")
    public void shouldRegisterAndRedirectToLoginWhenDataIsValid() {
        driver.get("http://localhost:5173/register");

        driver.findElement(By.xpath("//input[@placeholder='Nome']")).sendKeys(UiTestDataFactory.createName());
        driver.findElement(By.xpath("//input[@placeholder='Sobrenome']")).sendKeys(UiTestDataFactory.createLastName());
        driver.findElement(By.xpath("//input[@placeholder='Email']")).sendKeys(UiTestDataFactory.createEmail());
        driver.findElement(By.xpath("//input[@placeholder='Senha']")).sendKeys(UiTestDataFactory.createPassword());
        driver.findElement(By.xpath("//button[text()='Cadastrar']")).click();

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> d.getCurrentUrl().equals("http://localhost:5173/"));

        assertThat(driver.getCurrentUrl()).isEqualTo("http://localhost:5173/");
        assertThat(driver.findElements(By.xpath("//h1[text()='Cadastro']"))).isEmpty();
    }

    @Test
    @DisplayName("Invalid registration should show an error message")
    public void shouldShowErrorWhenRegistrationFails() {
        driver.get("http://localhost:5173/register");

        ((JavascriptExecutor) driver).executeScript(
            "window.fetch = function() { return Promise.resolve({ ok: false, status: 400 }); };"
        );

        driver.findElement(By.xpath("//input[@placeholder='Nome']")).sendKeys(UiTestDataFactory.createName());
        driver.findElement(By.xpath("//input[@placeholder='Sobrenome']")).sendKeys(UiTestDataFactory.createLastName());
        driver.findElement(By.xpath("//input[@placeholder='Email']")).sendKeys(UiTestDataFactory.createEmail());
        driver.findElement(By.xpath("//input[@placeholder='Senha']")).sendKeys(UiTestDataFactory.createPassword());
        driver.findElement(By.xpath("//button[text()='Cadastrar']")).click();

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> d.findElement(By.xpath("//p[text()='Erro ao cadastrar. Verifique os dados e tente novamente.']")).isDisplayed());

        assertThat(driver.findElement(By.xpath("//p[text()='Erro ao cadastrar. Verifique os dados e tente novamente.']")).isDisplayed()).isTrue();
        assertThat(driver.getCurrentUrl()).isEqualTo("http://localhost:5173/register");
    }
}
