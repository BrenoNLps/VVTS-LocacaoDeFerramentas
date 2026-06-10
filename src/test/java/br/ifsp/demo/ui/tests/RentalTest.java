package br.ifsp.demo.ui.tests;

import br.ifsp.demo.ui.base.BaseUiTest;
import br.ifsp.demo.ui.helpers.UiTestDataFactory;
import br.ifsp.demo.ui.pages.HeaderPage;
import br.ifsp.demo.ui.pages.RentalPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("UiTest")
public class RentalTest extends BaseUiTest {

    @Test
    @Tag("UiTest")
    @DisplayName("Rental page should render the new rental form and active rentals tab")
    public void shouldRenderRentalPageWithNewRentalFormAndActiveRentalsTab() {
        String email = UiTestDataFactory.createEmail();
        String password = UiTestDataFactory.createPassword();
        registerUser("Admin", "User", email, password);
        login(email, password);

        driver.get("http://localhost:5173/rental");
        
        RentalPage rentalPage = new RentalPage(driver);

        assertThat(rentalPage.isHeadingVisible()).isTrue();
        assertThat(rentalPage.isNewRentalTabVisible()).isTrue();
        assertThat(rentalPage.isActiveRentalsTabVisible()).isTrue();
        assertThat(rentalPage.isClientLabelVisible()).isTrue();
        assertThat(rentalPage.isGuaranteeLabelVisible()).isTrue();
        assertThat(rentalPage.isConfirmButtonVisible()).isTrue();
    }

    @Test
    @Tag("UiTest")
    @DisplayName("Valid rental creation should show a success message")
    public void shouldCreateARentalWhenAValidCustomerToolAndGuaranteeAreSelected() {
        String email = UiTestDataFactory.createEmail();
        String password = UiTestDataFactory.createPassword();
        registerUser("Admin", "User", email, password);
        login(email, password);

        createTool("Serra Circular", 20.0, 90.0, 250.0);
        createCustomer("Jose Silva", "jose@test.com");
        
        driver.navigate().refresh();

        HeaderPage headerPage = new HeaderPage(driver);
        headerPage.clickRental();

        RentalPage rentalPage = new RentalPage(driver);

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> rentalPage.isToolTableVisible());
        
        rentalPage.clickFirstToolRow();
        rentalPage.searchCustomer("Jose");
        rentalPage.selectCustomerFromDropdown("Jose Silva");
        rentalPage.selectGuarantee("Dinheiro");

        rentalPage.clickConfirm();

        new WebDriverWait(driver, Duration.ofSeconds(5)).until(d -> rentalPage.isSuccessMessageVisible());

        assertThat(rentalPage.getSuccessMessage()).isEqualTo("Locação registrada com sucesso.");
        assertThat(rentalPage.areToolsSelected()).isFalse();
    }
}
