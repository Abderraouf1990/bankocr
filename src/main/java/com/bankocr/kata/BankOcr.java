package com.bankocr.kata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Abderraouf AYADI on 10/04/2019
 */
public class BankOcr {

    private static final Logger log = LoggerFactory.getLogger(BankOcr.class);

    public static void main(String[] args) throws IOException {
        log.info("Commencer le calcul des numéros de compte...");
        new BankOcr().run();
    }

    private void run() throws IOException {
        try {
            File accountFile = new File(BankOcr.class.getResource("/AccountNumbers").getFile());
            BankOcrReader reader = new BankOcrReader(accountFile);

            AccountNumber accountNumber;
            while (reader.getReader().ready() && (accountNumber = new AccountNumber(reader.getNextAccountNumber())) != null) {
                log.info("Account Number: {}", accountNumber.getNumber());
                log.info("Account Status: {}", accountNumber.getStatus());
            }

            log.info("Lecture des numéros de compte terminée avec succès");

        } catch (FileNotFoundException fnfe) {
            log.error("fichier introuvable");
        } catch (Exception e) {
            log.error("Lecture des numéros de compte abondonnée", e);
        }

    }
}
