package com.bankocr.kata;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Abderraouf AYADI on 10/04/2019
 */
public class BankOcrReaderTest {
    private final String testLines = new StringBuilder()
            .append("000111222333444555666777888999\n")
            .append("000111222333444555666777888999\n")
            .append("000111222333444555666777888999\n").append("\n").toString();

    private final String validLines1 = new StringBuilder()
            .append("                           \n")
            .append("  |  |  |  |  |  |  |  |  |\n")
            .append("  |  |  |  |  |  |  |  |  |\n").append("\n").toString();

    private final String validLines2 = new StringBuilder()
            .append(" _  _  _  _  _  _  _  _  _ \n")
            .append(" _| _| _| _| _| _| _| _| _|\n")
            .append("|_ |_ |_ |_ |_ |_ |_ |_ |_ \n").append("\n").toString();

    private final String validLines123456789 = new StringBuilder()
            .append("    _  _     _  _  _  _  _ \n")
            .append("  | _| _||_||_ |_   ||_||_|\n")
            .append("  ||_  _|  | _||_|  ||_| _|\n").append("\n").toString();

    private final List<String> validDigits = Arrays.asList(new String[] { " _ | ||_|", "     |  |", " _  _||_ ",
                                                                                       " _  _| _|", "   |_|  |", " _ |_  _|", " _ |_ |_|",
                                                                                       " _   |  |", " _ |_||_|", " _ |_| _|" });

    private final List<String> illegibleDigits = Arrays
            .asList(new String[] { " _ | ||_|", "     |  |", " _  _||_ ",
                    "|_  _| _|", "   |_|  |", " _ |_  _|", " _ |_ |_|",
                    "?_   |  |", " _ |_||_|", " _ |_| _|" });

    /**
     * Vérifiez que le reader lit correctement trois lignes d'OCR et les ré-utilise dans une liste de chaînes de chiffres d'OCR
     *
     * @throws IOException
     */
    @Test
    public void testGetNextAccountLines() throws IOException {

        BankOcrReader reader = new BankOcrReader(testLines);

        List<String> result = reader.getNextAccountLines();

        Assert.assertEquals("000000000", result.get(0));
        Assert.assertEquals("111111111", result.get(1));
        Assert.assertEquals("222222222", result.get(2));
        Assert.assertEquals("333333333", result.get(3));
        Assert.assertEquals("444444444", result.get(4));
        Assert.assertEquals("555555555", result.get(5));
        Assert.assertEquals("666666666", result.get(6));
        Assert.assertEquals("777777777", result.get(7));
        Assert.assertEquals("888888888", result.get(8));
        Assert.assertEquals("999999999", result.get(9));

    }

    /**
     * Teste si le reader peut déterminer correctement le numéro de compte à partir de la liste des digits
     *
     */
    @Test
    public void testParseOcrDigits() {

        BankOcrReader reader = new BankOcrReader(new String());

        String validResult = reader.parseDigits(validDigits);
        Assert.assertEquals("0123456789", validResult);

        String illegibleResult = reader.parseDigits(illegibleDigits);
        Assert.assertEquals("012?456?89", illegibleResult);
    }

    /**
     * Teste que le reader peut lire des lignes de numéro de compte et les convertit en numéros
     *
     * @throws IOException
     */
    @Test
    public void testGetNextAccountNumber() throws IOException {

        // Set up three OCR account numbers
        BankOcrReader reader = new BankOcrReader(validLines1
                + validLines2 + validLines123456789);

        // Vérifier les 3 premiers numéros de compte
        Assert.assertEquals("111111111", reader.getNextAccountNumber());
        Assert.assertTrue(reader.getReader().ready());
        Assert.assertEquals("222222222", reader.getNextAccountNumber());
        Assert.assertTrue(reader.getReader().ready());
        Assert.assertEquals("123456789", reader.getNextAccountNumber());
        Assert.assertTrue(reader.getReader().ready());

        // 4éme ligne vide
        Assert.assertNull(reader.getNextAccountNumber());

        // Pas de lignes à traiter
        Assert.assertNull(reader.getNextAccountNumber());
        Assert.assertNull(reader.getNextAccountNumber());
        Assert.assertNull(reader.getNextAccountNumber());

    }
}
