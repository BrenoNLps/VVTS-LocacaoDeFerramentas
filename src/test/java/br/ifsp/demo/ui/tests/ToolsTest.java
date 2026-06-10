package br.ifsp.demo.ui.tests;

import br.ifsp.demo.ui.base.BaseUiTest;
import br.ifsp.demo.ui.helpers.UiTestDataFactory;
import br.ifsp.demo.ui.pages.ToolsPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class ToolsTest extends BaseUiTest {

    @Test
    @Tag("UiTest")
    @DisplayName("Tools page should render the form and table")
    public void shouldRenderToolsFormAndToolsTable() {
        String email = UiTestDataFactory.createEmail();
        String password = UiTestDataFactory.createPassword();
        registerUser("Admin", "User", email, password);
        login(email, password);

        driver.get("http://localhost:5173/tools");
        
        ToolsPage toolsPage = new ToolsPage(driver);

        assertThat(toolsPage.isHeadingVisible()).isTrue();
        assertThat(toolsPage.isNameInputVisible()).isTrue();
        assertThat(toolsPage.isDailyRateInputVisible()).isTrue();
        assertThat(toolsPage.isWeeklyRateInputVisible()).isTrue();
        assertThat(toolsPage.isMonthlyRateInputVisible()).isTrue();
        assertThat(toolsPage.isRegisterButtonVisible()).isTrue();
        assertThat(toolsPage.areTableHeadersVisible()).isTrue();
    }

    @Test
    @Tag("UiTest")
    @DisplayName("Valid tool creation should add the tool to the list")
    public void shouldCreateAValidToolAndShowItInTheList() {
        String email = UiTestDataFactory.createEmail();
        String password = UiTestDataFactory.createPassword();
        registerUser("Admin", "User", email, password);
        login(email, password);

        driver.get("http://localhost:5173/tools");
        ToolsPage toolsPage = new ToolsPage(driver);

        String toolName = "Faker Tool " + System.currentTimeMillis();
        toolsPage.fillForm(toolName, "15.0", "70.0", "200.0");

        ((JavascriptExecutor) driver).executeScript(
            "const toolName = arguments[0];" +
            "const originalFetch = window.fetch; " +
            "window.fetch = function(url, options) { " +
            "  if (url.includes('/tools') && options && options.method === 'POST') { " +
            "    return Promise.resolve(new Response(null, { status: 201 })); " +
            "  } " +
            "  if (url.includes('/tools') && (!options || options.method === 'GET')) { " +
            "    return Promise.resolve(new Response(JSON.stringify([{ " +
            "      id: 'mock-id', name: toolName, status: 'AVAILABLE', dailyRate: 15.0, weeklyDailyRate: 70.0, monthlyDailyRate: 200.0 " +
            "    }]), { " +
            "      status: 200, " +
            "      headers: { 'Content-Type': 'application/json' } " +
            "    })); " +
            "  } " +
            "  return originalFetch(url, options); " +
            "};", 
            toolName
        );

        toolsPage.clickRegister();

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> toolsPage.isToolInTable(toolName));

        assertThat(toolsPage.isToolInTable(toolName)).isTrue();
    }

    @Test
    @Tag("UiTest")
    @DisplayName("Invalid tool creation should show an error message")
    public void shouldShowErrorWhenToolCreationFails() {
        String email = UiTestDataFactory.createEmail();
        String password = UiTestDataFactory.createPassword();
        registerUser("Admin", "User", email, password);
        login(email, password);

        driver.get("http://localhost:5173/tools");
        ToolsPage toolsPage = new ToolsPage(driver);

        String toolName = "Error Tool " + System.currentTimeMillis();
        toolsPage.fillForm(toolName, "15.0", "70.0", "200.0");

        ((JavascriptExecutor) driver).executeScript(
            "const originalFetch = window.fetch; " +
            "window.fetch = function(url, options) { " +
            "  if (url.includes('/tools') && options && options.method === 'POST') { " +
            "    return Promise.reject(new Error('Failed to create tool')); " +
            "  } " +
            "  return originalFetch(url, options); " +
            "};"
        );

        toolsPage.clickRegister();

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> toolsPage.isErrorMessageVisible());

        assertThat(toolsPage.getErrorMessage()).isEqualTo("Erro ao cadastrar ferramenta.");
        assertThat(toolsPage.isToolInTable(toolName)).isFalse();
    }
}
