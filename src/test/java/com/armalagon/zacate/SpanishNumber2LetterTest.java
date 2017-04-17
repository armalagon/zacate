package com.armalagon.zacate;

import com.armalagon.zacate.SpanishNumber2Letter.Number2LetterBuilder;
import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author aalaniz
 * @version 1.0
 * @since 1.0
 */
public class SpanishNumber2LetterTest {

    Number2LetterConverter converter;

    public SpanishNumber2LetterTest() {
    }

    @Test
    public void testUnits() throws NumberConversionException {
        converter = new Number2LetterBuilder(0).build();
        assertEquals("cero", converter.toLetter());
        converter = new Number2LetterBuilder(3).build();
        assertEquals("tres", converter.toLetter());
        converter = new Number2LetterBuilder(9).build();
        assertEquals("nueve", converter.toLetter());
        converter = new Number2LetterBuilder(10).build();
        assertEquals("diez", converter.toLetter());
    }

    @Test
    public void testTens() throws NumberConversionException {
        converter = new Number2LetterBuilder(12).build();
        assertEquals("doce", converter.toLetter());
        converter = new Number2LetterBuilder(15).build();
        assertEquals("quince", converter.toLetter());
        converter = new Number2LetterBuilder(19).build();
        assertEquals("diecinueve", converter.toLetter());
        converter = new Number2LetterBuilder(20).build();
        assertEquals("veinte", converter.toLetter());
        converter = new Number2LetterBuilder(26).build();
        assertEquals("veintiseis", converter.toLetter());
        converter = new Number2LetterBuilder(29).build();
        assertEquals("veintinueve", converter.toLetter());
        converter = new Number2LetterBuilder(30).build();
        assertEquals("treinta", converter.toLetter());
        converter = new Number2LetterBuilder(37).build();
        assertEquals("treintisiete", converter.toLetter());
        converter = new Number2LetterBuilder(44).build();
        assertEquals("cuarenticuatro", converter.toLetter());
        converter = new Number2LetterBuilder(48).build();
        assertEquals("cuarentiocho", converter.toLetter());
        converter = new Number2LetterBuilder(51).build();
        assertEquals("cincuentiuno", converter.toLetter());
        converter = new Number2LetterBuilder(59).build();
        assertEquals("cincuentinueve", converter.toLetter());
        converter = new Number2LetterBuilder(69).build();
        assertEquals("sesentinueve", converter.toLetter());
        converter = new Number2LetterBuilder(75).build();
        assertEquals("setenticinco", converter.toLetter());
        converter = new Number2LetterBuilder(80).build();
        assertEquals("ochenta", converter.toLetter());
        converter = new Number2LetterBuilder(92).build();
        assertEquals("noventidos", converter.toLetter());
        converter = new Number2LetterBuilder(99).build();
        assertEquals("noventinueve", converter.toLetter());
    }

    @Test
    public void testTensWithLongFormat() throws NumberConversionException {
        converter = new Number2LetterBuilder(16).longFormat(true).build();
        assertEquals("diez y seis", converter.toLetter());
        converter = new Number2LetterBuilder(19).longFormat(true).build();
        assertEquals("diez y nueve", converter.toLetter());
        converter = new Number2LetterBuilder(23).longFormat(true).build();
        assertEquals("veinte y tres", converter.toLetter());
        converter = new Number2LetterBuilder(29).longFormat(true).build();
        assertEquals("veinte y nueve", converter.toLetter());
        converter = new Number2LetterBuilder(35).longFormat(true).build();
        assertEquals("treinta y cinco", converter.toLetter());
        converter = new Number2LetterBuilder(69).longFormat(true).build();
        assertEquals("sesenta y nueve", converter.toLetter());
    }

    @Test
    public void testHundreds() throws NumberConversionException {
        converter = new Number2LetterBuilder(100).build();
        assertEquals("cien", converter.toLetter());
        converter = new Number2LetterBuilder(101).build();
        assertEquals("ciento uno", converter.toLetter());
        converter = new Number2LetterBuilder(106).build();
        assertEquals("ciento seis", converter.toLetter());
        converter = new Number2LetterBuilder(110).build();
        assertEquals("ciento diez", converter.toLetter());
        converter = new Number2LetterBuilder(111).build();
        assertEquals("ciento once", converter.toLetter());
        converter = new Number2LetterBuilder(115).build();
        assertEquals("ciento quince", converter.toLetter());
        converter = new Number2LetterBuilder(125).build();
        assertEquals("ciento veinticinco", converter.toLetter());
        converter = new Number2LetterBuilder(200).build();
        assertEquals("doscientos", converter.toLetter());
        converter = new Number2LetterBuilder(266).build();
        assertEquals("doscientos sesentiseis", converter.toLetter());
        converter = new Number2LetterBuilder(299).build();
        assertEquals("doscientos noventinueve", converter.toLetter());
        converter = new Number2LetterBuilder(300).build();
        assertEquals("trescientos", converter.toLetter());
        converter = new Number2LetterBuilder(362).build();
        assertEquals("trescientos sesentidos", converter.toLetter());
        converter = new Number2LetterBuilder(400).build();
        assertEquals("cuatrocientos", converter.toLetter());
        converter = new Number2LetterBuilder(500).build();
        assertEquals("quinientos", converter.toLetter());
        converter = new Number2LetterBuilder(600).build();
        assertEquals("seiscientos", converter.toLetter());
        converter = new Number2LetterBuilder(700).build();
        assertEquals("setecientos", converter.toLetter());
        converter = new Number2LetterBuilder(800).build();
        assertEquals("ochocientos", converter.toLetter());
        converter = new Number2LetterBuilder(900).build();
        assertEquals("novecientos", converter.toLetter());
    }

    @Test
    public void testThousands() throws NumberConversionException {
        converter = new Number2LetterBuilder(1000).build();
        assertEquals("un mil", converter.toLetter());
        converter = new Number2LetterBuilder(1001).build();
        assertEquals("un mil uno", converter.toLetter());
        converter = new Number2LetterBuilder(1007).build();
        assertEquals("un mil siete", converter.toLetter());
        converter = new Number2LetterBuilder(1015).build();
        assertEquals("un mil quince", converter.toLetter());
        converter = new Number2LetterBuilder(1821).build();
        assertEquals("un mil ochocientos veintiuno", converter.toLetter());
        converter = new Number2LetterBuilder(9000).build();
        assertEquals("nueve mil", converter.toLetter());
        converter = new Number2LetterBuilder(11942).build();
        assertEquals("once mil novecientos cuarentidos", converter.toLetter());
        converter = new Number2LetterBuilder(325999).build();
        assertEquals("trescientos veinticinco mil novecientos noventinueve", converter.toLetter());
        converter = new Number2LetterBuilder(999999).build();
        assertEquals("novecientos noventinueve mil novecientos noventinueve", converter.toLetter());
    }

    @Test
    public void testMillions() throws NumberConversionException {
        converter = new Number2LetterBuilder(1_000_000).build();
        assertEquals("un millon", converter.toLetter());
        converter = new Number2LetterBuilder(1_000_002).build();
        assertEquals("un millon dos", converter.toLetter());
        converter = new Number2LetterBuilder(1_001_000).build();
        assertEquals("un millon un mil", converter.toLetter());
        converter = new Number2LetterBuilder(2_001_000).build();
        assertEquals("dos millones un mil", converter.toLetter());
        converter = new Number2LetterBuilder(9_425_458).build();
        assertEquals("nueve millones cuatrocientos veinticinco mil cuatrocientos cincuentiocho", converter.toLetter());
        converter = new Number2LetterBuilder(732_425_458).build();
        assertEquals("setecientos treintidos millones cuatrocientos veinticinco mil cuatrocientos cincuentiocho",
                converter.toLetter());
        converter = new Number2LetterBuilder(999_001_000).build();
        assertEquals("novecientos noventinueve millones un mil", converter.toLetter());
        converter = new Number2LetterBuilder(999_999_999).build();
        assertEquals("novecientos noventinueve millones novecientos noventinueve mil novecientos noventinueve",
                converter.toLetter());
    }

    @Test
    public void testNumberWithCurrency() throws NumberConversionException {
        converter = new Number2LetterBuilder(new BigDecimal("1250.85")).currency("cordoba").build();
        assertEquals("un mil doscientos cincuenta cordoba con 85/100", converter.toLetter());
        converter = new Number2LetterBuilder(new BigDecimal("1257.856")).currency("cordoba").build();
        assertEquals("un mil doscientos cincuentisiete cordoba con 86/100", converter.toLetter());
    }

    @Test
    public void testNumberOutOfBounds() throws NumberConversionException {
        converter = new Number2LetterBuilder(1_000_000_000).build();
        assertEquals("1000000000", converter.toLetter());
        converter = new Number2LetterBuilder(1_999_999_999).build();
        assertEquals("1999999999", converter.toLetter());
        converter = new Number2LetterBuilder(new BigDecimal("1999999999.45")).build();
        assertEquals("1999999999", converter.toLetter());
    }

    @Test(expected = NumberConversionException.class)
    public void testNumberOutOfBoundsAndThrowError() throws NumberConversionException {
        converter = new Number2LetterBuilder(1_000_000_000).throwError(true).build();
        converter.toLetter();
    }
}
