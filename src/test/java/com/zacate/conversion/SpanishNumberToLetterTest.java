package com.zacate.conversion;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author aalaniz
 * @version 1.0
 * @since 1.0
 */
public class SpanishNumberToLetterTest {

    @Test
    public void testUnits() {
        assertEquals("cero", new SpanishNumberToLetter(0).getLetter());
        assertEquals("tres", new SpanishNumberToLetter(3).getLetter());
        assertEquals("nueve", new SpanishNumberToLetter(9).getLetter());
        assertEquals("diez", new SpanishNumberToLetter(10).getLetter());
        assertEquals("siete", new SpanishNumberToLetter(7).getLetter());
    }

    @Test
    public void testTens() {
        assertEquals("doce", new SpanishNumberToLetter(12).getLetter());
        assertEquals("quince", new SpanishNumberToLetter(15).getLetter());
        assertEquals("diez y nueve", new SpanishNumberToLetter(19).getLetter());
        assertEquals("veinte", new SpanishNumberToLetter(20).getLetter());
        assertEquals("veinte y seis", new SpanishNumberToLetter(26).getLetter());
        assertEquals("treinta", new SpanishNumberToLetter(30).getLetter());
        assertEquals("cuarenta y cuatro", new SpanishNumberToLetter(44).getLetter());
        assertEquals("cincuenta y uno", new SpanishNumberToLetter(51).getLetter());
        assertEquals("sesenta y nueve", new SpanishNumberToLetter(69).getLetter());
        assertEquals("setenta y cinco", new SpanishNumberToLetter(75).getLetter());
        assertEquals("ochenta", new SpanishNumberToLetter(80).getLetter());
        assertEquals("noventa y dos", new SpanishNumberToLetter(92).getLetter());
        assertEquals("noventa y nueve", new SpanishNumberToLetter(99).getLetter());
    }

    @Test
    public void testHundreds() {
        assertEquals("cien", new SpanishNumberToLetter(100).getLetter());
        assertEquals("ciento diez", new SpanishNumberToLetter(110).getLetter());
        assertEquals("ciento quince", new SpanishNumberToLetter(115).getLetter());
        assertEquals("ciento veinte y cinco", new SpanishNumberToLetter(125).getLetter());
        assertEquals("doscientos", new SpanishNumberToLetter(200).getLetter());
        assertEquals("doscientos sesenta y seis", new SpanishNumberToLetter(266).getLetter());
        assertEquals("doscientos noventa y nueve", new SpanishNumberToLetter(299).getLetter());
        assertEquals("trescientos", new SpanishNumberToLetter(300).getLetter());
        assertEquals("trescientos sesenta y dos", new SpanishNumberToLetter(362).getLetter());
        assertEquals("cuatrocientos", new SpanishNumberToLetter(400).getLetter());
        assertEquals("quinientos", new SpanishNumberToLetter(500).getLetter());
        assertEquals("setecientos", new SpanishNumberToLetter(700).getLetter());
        assertEquals("ochocientos", new SpanishNumberToLetter(800).getLetter());
        assertEquals("novecientos", new SpanishNumberToLetter(900).getLetter());
    }

    @Test
    public void testThousands() {
        assertEquals("un mil", new SpanishNumberToLetter(1000).getLetter());
        assertEquals("un mil uno", new SpanishNumberToLetter(1001).getLetter());
        assertEquals("un mil quince", new SpanishNumberToLetter(1015).getLetter());
        assertEquals("un mil ochocientos veinte y uno", new SpanishNumberToLetter(1821).getLetter());
        assertEquals("nueve mil", new SpanishNumberToLetter(9000).getLetter());
        assertEquals("once mil novecientos cuarenta y dos", new SpanishNumberToLetter(11942).getLetter());
        assertEquals("trescientos veinte y cinco mil novecientos noventa y nueve", new SpanishNumberToLetter(325999).getLetter());
        assertEquals("novecientos noventa y nueve mil novecientos noventa y nueve", new SpanishNumberToLetter(999999).getLetter());
    }

    @Test
    public void testMillions() {
        assertEquals("un millon", new SpanishNumberToLetter(1_000_000).getLetter());
        assertEquals("un millon un mil", new SpanishNumberToLetter(1_001_000).getLetter());
        assertEquals("dos millones un mil", new SpanishNumberToLetter(2_001_000).getLetter());
        assertEquals("nueve millones cuatrocientos veinte y cinco mil cuatrocientos cincuenta y ocho",
                new SpanishNumberToLetter(9_425_458).getLetter());
        assertEquals("setecientos treinta y dos millones cuatrocientos veinte y cinco mil cuatrocientos cincuenta y ocho",
                new SpanishNumberToLetter(732_425_458).getLetter());
        assertEquals("novecientos noventa y nueve millones un mil", new SpanishNumberToLetter(999_001_000).getLetter());
        assertEquals("novecientos noventa y nueve millones novecientos noventa y nueve mil novecientos noventa y nueve",
                new SpanishNumberToLetter(999_999_999).getLetter());
    }

}
