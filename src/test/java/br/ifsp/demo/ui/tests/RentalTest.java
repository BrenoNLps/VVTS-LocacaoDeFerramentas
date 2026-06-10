package br.ifsp.demo.ui.tests;

import br.ifsp.demo.ui.base.BaseUiTest;
import br.ifsp.demo.ui.helpers.UiTestDataFactory;
import br.ifsp.demo.ui.pages.RentalPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

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
}
