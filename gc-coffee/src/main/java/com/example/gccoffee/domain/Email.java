package com.example.gccoffee.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.val;

import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Getter
@EqualsAndHashCode
public class Email{
    private final String address;

    public Email(String email) {
        checkNotNull(email, "email should not be null");
        checkArgument(email.length() >= 4 && email.length() <= 50, "email length must be between 4 and 50 characters.");
        checkArgument(checkEmail(email), "Invalid email address");

        this.address = email;
    }

    private static boolean checkEmail(String email){
        val pattern = Pattern.compile("\\b[\\w.-]+@[\\w\\.-]+\\.\\w{2,4}\\b");

        val m = pattern.matcher(email);
        return m.matches();
    }


}
