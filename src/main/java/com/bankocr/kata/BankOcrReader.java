package com.bankocr.kata;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Abderraouf AYADI on 10/04/2019
 */
public class BankOcrReader {

    private static final Logger log = LoggerFactory.getLogger(BankOcrReader.class);

    // buffer pour lire le fichier
    private BufferedReader reader;

    // Contient la représentation des 3 lignes pour chaque chiffre de 0 à 9
    private static final Map<String, Character> DIGITS = new HashMap<String, Character>();

    static {
        DIGITS.put(" _ | ||_|", '0');
        DIGITS.put("     |  |", '1');
        DIGITS.put(" _  _||_ ", '2');
        DIGITS.put(" _  _| _|", '3');
        DIGITS.put("   |_|  |", '4');
        DIGITS.put(" _ |_  _|", '5');
        DIGITS.put(" _ |_ |_|", '6');
        DIGITS.put(" _   |  |", '7');
        DIGITS.put(" _ |_||_|", '8');
        DIGITS.put(" _ |_| _|", '9');
    }

    public BankOcrReader(final File inputFile) throws FileNotFoundException {
        this.reader = new BufferedReader(new FileReader(inputFile));
    }

    public BankOcrReader(final String lines) {
        this.reader = new BufferedReader(new StringReader(lines));
    }

    /**
     *
     * @return next Acount Number
     * @throws IOException
     */
    public String getNextAccountNumber() throws IOException {
        return parseDigits(getNextAccountLines());
    }


    /**
     *
     * @param digits
     * @return the account number in digits
     */
    String parseDigits(final List<String> digits) {
        log.debug("parseDigits(): digits={}", digits);

        if (digits == null || digits.isEmpty()) {
            return null;
        }

        StringBuilder digitBuilder = new StringBuilder();

        for (String digit : digits) {
            if (DIGITS.containsKey(digit)){
                digitBuilder.append(DIGITS.get(digit));
            }else {
                digitBuilder.append('?');
            }
        }

        log.info("... returning'{}'", digitBuilder);

        return digitBuilder.toString();
    }


    /**
     *
     * @return next account line
     * @throws IOException
     */
    List<String> getNextAccountLines() throws IOException {
        log.debug("getNextAccountLines()");

        if (reader.ready()) {

            List<StringBuilder> digits = new ArrayList<>();

            for (int i = 0; i < 3; i++) {
                String line = reader.readLine();
                log.debug("... i={}, line='{}'", i, line);
                if (line != null) {
                    int j = 0;
                    // split each line into groups of 3 OCR characters
                    for (String digit : String.format("%-27s", line).split(
                            "(?<=\\G.{3})")) {
                        try {
                            // try to append the characters to a
                            // StringBuilder for the current digit
                            digits.set(j, digits.get(j).append(digit));
                        } catch (IndexOutOfBoundsException e) {
                            // if there is no StringBuilder for the current
                            // digit, make one
                            digits.add(new StringBuilder(digit));
                        }
                        j++;
                    }
                }
            }

            // read the blank line following the OCR lines
            reader.readLine();

            log.debug("digits={}", digits);

            if (!digits.isEmpty()) {
                List<String> digitsToReturn = new ArrayList<>();
                for (StringBuilder sb : digits) {
                    digitsToReturn.add(sb.toString());
                }
                log.info("... returning digits={}", digits);
                return digitsToReturn;
            }

        }

        log.debug("... returning null");

        // Si pas de numéro de compte (fichier terminé)
        return null;

    }

    public BufferedReader getReader() {
        return reader;
    }
}
