package br.ifsp.demo.ui.tests;

import br.ifsp.demo.ui.base.BaseUiTest;
import br.ifsp.demo.ui.helpers.UiTestDataFactory;
import br.ifsp.demo.ui.pages.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest extends BaseUiTest {
    private LoginPage loginPage;

    @BeforeEach
    public void setup() {
        registerUser("Admin", "User", "admin@gmail.com", "1234");
        loginPage = new LoginPage(driver);
    }

    @Test
    @Tag("UiTest")
    @DisplayName("Login page should render the authentication form")
    public void shouldRenderLoginForm() {
        driver.get("http://localhost:5173/");

        assertThat(driver.findElement(By.xpath("//h1[text()='Login']")).isDisplayed()).isTrue();
        assertThat(driver.findElement(By.xpath("//input[@placeholder='Email']")).isDisplayed()).isTrue();
        assertThat(driver.findElement(By.xpath("//input[@placeholder='Senha']")).isDisplayed()).isTrue();
        assertThat(driver.findElement(By.xpath("//button[text()='Entrar']")).isDisplayed()).isTrue();
        assertThat(driver.findElement(By.linkText("Cadastre-se")).isDisplayed()).isTrue();
    }

    @Test
    @Tag("UiTest")
    @DisplayName("Valid login should redirect the user to home")
    public void shouldLoginAndRedirectToHomeWhenCredentialsAreValid() {
        driver.get("http://localhost:5173/");

        loginPage.fillEmail("admin@gmail.com");
        loginPage.fillPassword("1234");
        loginPage.clickLogin();

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> d.getCurrentUrl().contains("/home"));

        assertThat(driver.getCurrentUrl()).contains("/home");
        assertThat(driver.findElements(By.xpath("//h1[text()='Login']"))).isEmpty();
    }

    @Test
    @Tag("UiTest")
    @DisplayName("Invalid login should show error message")
    public void shouldShowErrorWhenLoginFails() {
        driver.get("http://localhost:5173/");

        loginPage.fillEmail(UiTestDataFactory.createEmail());
        loginPage.fillPassword(UiTestDataFactory.createPassword());
        loginPage.clickLogin();

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> loginPage.isErrorMessageVisible());

        assertThat(loginPage.isErrorMessageVisible()).isTrue();
        assertThat(driver.getCurrentUrl()).isEqualTo("http://localhost:5173/");
    }
}
