package com.bankocr.kata;


/**
 * Created by Abderraouf AYADI on 10/04/2019
 */
public class AccountNumber {

    public static final String ALLOWED_ACCOUNT_NUMBER_REGEX = "[\\d\\?]{9}";
    private String number = null;

    public AccountNumber(final String number) {
        if (number == null) {
            throw new IllegalArgumentException("Numéro de compte ne peut pas être null");
        } else if (!number.matches(ALLOWED_ACCOUNT_NUMBER_REGEX)) {
            throw new IllegalArgumentException("Numéro de compte contient uniquement des chiffres");
        }
        this.number = number;
    }

    public String getNumber() {
        return number;
    }
}
