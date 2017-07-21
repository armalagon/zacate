package com.armalagon.zacate;

import java.util.function.UnaryOperator;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author aalaniz
 * @version 1.0
 * @since 1.0
 */
public class CedulaTest {

    private static final String HYPHEN = "-";

    private Cedula cedula;

    public CedulaTest() {
    }

    @Test
    public void testValidNationalId() {
        UnaryOperator<String> prettyFormatter = value -> value.substring(0, 3) + HYPHEN + value.substring(3, 5) +
                HYPHEN + value.substring(5, 7) + HYPHEN + value.substring(7, 9) + HYPHEN + value.substring(9, 13) +
                HYPHEN + value.charAt(13);

        cedula = Cedula.of("2811311830009v");
        assertTrue(cedula.isValid());
        assertEquals("2811311830009v", cedula.getRaw());
        assertEquals("281", cedula.getDistrict());
        assertEquals("131183", cedula.getBirthday());
        assertEquals("0009", cedula.getConsecutive());
        assertEquals(Character.valueOf('v'), cedula.getDigit());
        assertEquals("281-131183-0009v", cedula.getFormatted());
        assertEquals("281-13-11-83-0009-v", cedula.getFormatted(prettyFormatter));
        cedula = Cedula.of("2812008640022J");
        assertTrue(cedula.isValid());
        assertEquals("2812008640022J", cedula.getRaw());
        assertEquals("281", cedula.getDistrict());
        assertEquals("200864", cedula.getBirthday());
        assertEquals("0022", cedula.getConsecutive());
        assertEquals(Character.valueOf('J'), cedula.getDigit());
        assertEquals("281-200864-0022J", cedula.getFormatted());
        assertEquals("281-20-08-64-0022-J", cedula.getFormatted(prettyFormatter));
    }

    @Test
    public void testWrongNationalId() {
        cedula = Cedula.of(null);
        assertFalse(cedula.isValid());
        assertNull(cedula.getRaw());
        assertNull(cedula.getDistrict());
        assertNull(cedula.getBirthday());
        assertNull(cedula.getConsecutive());
        assertNull(cedula.getDigit());
        assertNull(cedula.getFormatted());
        cedula = Cedula.of("");
        assertFalse(cedula.isValid());
        assertEquals("", cedula.getRaw());
        assertNull(cedula.getDistrict());
        assertNull(cedula.getBirthday());
        assertNull(cedula.getConsecutive());
        assertNull(cedula.getDigit());
        assertNull(cedula.getFormatted());
        cedula = Cedula.of("    ");
        assertFalse(cedula.isValid());
        assertEquals("    ", cedula.getRaw());
        assertNull(cedula.getDistrict());
        assertNull(cedula.getBirthday());
        assertNull(cedula.getConsecutive());
        assertNull(cedula.getDigit());
        assertNull(cedula.getFormatted());
        cedula = Cedula.of("abcde");
        assertFalse(cedula.isValid());
        assertEquals("abcde", cedula.getRaw());
        assertNull(cedula.getDistrict());
        assertNull(cedula.getBirthday());
        assertNull(cedula.getConsecutive());
        assertNull(cedula.getDigit());
        assertNull(cedula.getFormatted());
        cedula = Cedula.of("281131183009");
        assertFalse(cedula.isValid());
        assertEquals("281131183009", cedula.getRaw());
        assertNull(cedula.getDistrict());
        assertNull(cedula.getBirthday());
        assertNull(cedula.getConsecutive());
        assertNull(cedula.getDigit());
        assertNull(cedula.getFormatted());
        cedula = Cedula.of("2811a11830009V");
        assertFalse(cedula.isValid());
        assertEquals("2811a11830009V", cedula.getRaw());
        assertNull(cedula.getDistrict());
        assertNull(cedula.getBirthday());
        assertNull(cedula.getConsecutive());
        assertNull(cedula.getDigit());
        assertNull(cedula.getFormatted());
        cedula = Cedula.of("2811311830009f");
        assertFalse(cedula.isValid());
        assertEquals("2811311830009f", cedula.getRaw());
        assertNull(cedula.getDistrict());
        assertNull(cedula.getBirthday());
        assertNull(cedula.getConsecutive());
        assertNull(cedula.getDigit());
        assertNull(cedula.getFormatted());
        cedula = Cedula.of("2813201830008V");
        assertFalse(cedula.isValid());
        assertEquals("2813201830008V", cedula.getRaw());
        assertNull(cedula.getDistrict());
        assertNull(cedula.getBirthday());
        assertNull(cedula.getConsecutive());
        assertNull(cedula.getDigit());
        assertNull(cedula.getFormatted());
    }
}
