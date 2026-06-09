package br.ifsp.demo.ui.components;

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

@Tag("UiTest")public class HeaderTest extends BaseUiTest {

    @BeforeEach
    public void setup() {
        registerUser("Admin", "User", "header@test.com", "1234");
        driver.get("http://localhost:5173/");
        driver.findElement(By.xpath("//input[@placeholder='Email']")).sendKeys("header@test.com");
        driver.findElement(By.xpath("//input[@placeholder='Senha']")).sendKeys("1234");
        driver.findElement(By.xpath("//button[text()='Entrar']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> d.getCurrentUrl().contains("/home"));
    }

    @Test
    @Tag("UiTest")
    @DisplayName("Header should render navigation links and logout button")
    public void shouldRenderHeaderNavigationAndLogoutButton() {
        assertThat(driver.findElement(By.linkText("Home")).isDisplayed()).isTrue();
        assertThat(driver.findElement(By.linkText("Ferramentas")).isDisplayed()).isTrue();
        assertThat(driver.findElement(By.linkText("Locações")).isDisplayed()).isTrue();
        assertThat(driver.findElement(By.linkText("Manutenções")).isDisplayed()).isTrue();
        assertThat(driver.findElement(By.linkText("Histórico")).isDisplayed()).isTrue();
        assertThat(driver.findElement(By.linkText("Clientes")).isDisplayed()).isTrue();
        assertThat(driver.findElement(By.xpath("//button[text()='Sair']")).isDisplayed()).isTrue();
    }
}
