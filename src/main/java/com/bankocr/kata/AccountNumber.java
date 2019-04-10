package com.bankocr.kata;


/**
 * Created by Abderraouf AYADI on 10/04/2019
 */
public class AccountNumber {

    public static final String ALLOWED_ACCOUNT_NUMBER_REGEX = "[\\d\\?]{9}";
    public static final String VALID_ACCOUNT_NUMBER_REGEX = "\\d{9}";

    private String number;
    private Integer checksum;
    private STATUS status;

    public static enum STATUS {
        VALID, INVALID
    }

    public AccountNumber(final String number) {
        if (number == null) {
            throw new IllegalArgumentException("Numéro de compte ne peut pas être null");
        } else if (!number.matches(ALLOWED_ACCOUNT_NUMBER_REGEX)) {
            throw new IllegalArgumentException("Numéro de compte contient uniquement des chiffres");
        }
        this.number = number;
        this.checksum = calculateChecksum(this.number);
        this.status = this.checksum == 0 ? STATUS.VALID : STATUS.INVALID;
    }

    /**
     *
     * @return Calcule le checkSum pour un account number donné
     */
    public static Integer calculateChecksum(String accountNumber) {

        if (accountNumber != null
                && accountNumber.matches(VALID_ACCOUNT_NUMBER_REGEX)) {

            int total = 0;
            int rank = 1;
            for (char c : new StringBuilder(accountNumber).reverse().toString()
                    .toCharArray()) {
                total += (c - '0') * rank++;
            }

            return total % 11;

        } else {
            return null;
        }

    }

    public String getNumber() {
        return number;
    }

    public Integer getChecksum() {
        return checksum;
    }

    public STATUS getStatus() {
        return status;
    }
}
