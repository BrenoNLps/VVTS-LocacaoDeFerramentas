package br.ifsp.demo.application.service;

import br.ifsp.demo.annotation.Structural;
import br.ifsp.demo.annotation.UnitTest;
import br.ifsp.demo.domain.exception.InvalidArgumentException;
import br.ifsp.demo.domain.model.Customer;
import br.ifsp.demo.domain.repository.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateCustomer")
class CreateCustomerTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CreateCustomer createCustomer;

    @Test
    @UnitTest
    @Structural
    @DisplayName("Should create and return customer when name is valid")
    void shouldCreateAndReturnCustomerWhenNameIsValid() {
        Customer result = createCustomer.execute("Joao");

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Joao");
        verify(customerRepository).save(any());
    }

}