package br.ifsp.demo.ui.pages;

import br.ifsp.demo.ui.base.BaseUiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class LoginTest extends BaseUiTest {

    @BeforeEach
    public void setup() {
        registerUser("Admin", "User", "admin@gmail.com", "1234");
    }

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

    @Test
    @DisplayName("Valid login should redirect the user to home")
    public void shouldLoginAndRedirectToHomeWhenCredentialsAreValid() {
        driver.get("http://localhost:5173/");

        driver.findElement(By.xpath("//input[@placeholder='Email']")).sendKeys("admin@gmail.com");
        driver.findElement(By.xpath("//input[@placeholder='Senha']")).sendKeys("1234");
        driver.findElement(By.xpath("//button[text()='Entrar']")).click();

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> d.getCurrentUrl().contains("/home"));

        assertThat(driver.getCurrentUrl()).contains("/home");
        assertThat(driver.findElements(By.xpath("//h1[text()='Login']"))).isEmpty();
    }
}
