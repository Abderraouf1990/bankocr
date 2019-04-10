package com.bankocr.kata;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Abderraouf AYADI on 10/04/2019
 */
public class AccountNumberTest {

    /**
     * Vérifie le calcul de checksum du numéro de compte
     */
    @Test
    public void testChecksumCalculation() {

        Assert.assertTrue(AccountNumber.calculateChecksum("711111111") == 0);
        Assert.assertTrue(AccountNumber.calculateChecksum("123456789") == 0);
        Assert.assertTrue(AccountNumber.calculateChecksum("490867715") == 0);

        Assert.assertFalse(AccountNumber.calculateChecksum("888888888") == 0);
        Assert.assertFalse(AccountNumber.calculateChecksum("490067715") == 0);
        Assert.assertFalse(AccountNumber.calculateChecksum("012345678") == 0);

    }

    /**
     * Vérifie le status du numéro de compte
     */
    @Test
    public void testAccountNumberStatus() {

        AccountNumber accountNumber = new AccountNumber("711111111");
        Assert.assertTrue(accountNumber.getStatus() == AccountNumber.STATUS.VALID);

        accountNumber = new AccountNumber("123456789");
        Assert.assertTrue(accountNumber.getStatus() == AccountNumber.STATUS.VALID);

        accountNumber = new AccountNumber("490867715");
        Assert.assertTrue(accountNumber.getStatus() == AccountNumber.STATUS.VALID);

        accountNumber = new AccountNumber("888888888");
        Assert.assertTrue(accountNumber.getStatus() == AccountNumber.STATUS.INVALID);

        accountNumber = new AccountNumber("490067715");
        Assert.assertTrue(accountNumber.getStatus() == AccountNumber.STATUS.INVALID);

        accountNumber = new AccountNumber("012345678");
        Assert.assertTrue(accountNumber.getStatus() == AccountNumber.STATUS.INVALID);

    }
}
