package com.example.gccoffee.domain;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class EmailTest {

    @Test
    void testInvalidEmail() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Email("acccc"));
    }

    @Test
    void testValidEmail() {
        val email = new Email("hello@gmail.com");

        assertThat(email.getAddress()).isEqualTo("hello@gmail.com");
    }

    @Test
    void testEqEmail() {
        val email = new Email("hello@gmail.com");
        val email2 = new Email("hello@gmail.com");

        assertThat(email).isEqualTo(email2);
    }
}