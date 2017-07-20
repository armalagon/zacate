package com.armalagon.zacate;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author aalaniz
 * @version 1.0
 * @since 1.0
 */
public class CedulaTest {

    private Cedula cedula;

    public CedulaTest() {
    }

    @Test
    public void testValidNationalId() {
        cedula = Cedula.create("2811311830009v");
        assertTrue(cedula.isValid());
        assertEquals("2811311830009v", cedula.getRaw());
        assertEquals("281", cedula.getDistrict());
        assertEquals("131183", cedula.getBirthday());
        assertEquals("0009", cedula.getConsecutive());
        assertEquals(Character.valueOf('v'), cedula.getDigit());
        assertEquals("281-131183-0009v", cedula.getFormatted());
        cedula = Cedula.create("2812008640022J");
        assertTrue(cedula.isValid());
        assertEquals("2812008640022J", cedula.getRaw());
        assertEquals("281", cedula.getDistrict());
        assertEquals("200864", cedula.getBirthday());
        assertEquals("0022", cedula.getConsecutive());
        assertEquals(Character.valueOf('J'), cedula.getDigit());
        assertEquals("281-200864-0022J", cedula.getFormatted());
    }

    @Test
    public void testWrongNationalId() {
        cedula = Cedula.create(null);
        assertFalse(cedula.isValid());
        assertNull(cedula.getRaw());
        assertNull(cedula.getDistrict());
        assertNull(cedula.getBirthday());
        assertNull(cedula.getConsecutive());
        assertNull(cedula.getDigit());
        assertNull(cedula.getFormatted());
        cedula = Cedula.create("");
        assertFalse(cedula.isValid());
        assertEquals("", cedula.getRaw());
        assertNull(cedula.getDistrict());
        assertNull(cedula.getBirthday());
        assertNull(cedula.getConsecutive());
        assertNull(cedula.getDigit());
        assertNull(cedula.getFormatted());
        cedula = Cedula.create("    ");
        assertFalse(cedula.isValid());
        assertEquals("    ", cedula.getRaw());
        assertNull(cedula.getDistrict());
        assertNull(cedula.getBirthday());
        assertNull(cedula.getConsecutive());
        assertNull(cedula.getDigit());
        assertNull(cedula.getFormatted());
        cedula = Cedula.create("abcde");
        assertFalse(cedula.isValid());
        assertEquals("abcde", cedula.getRaw());
        assertNull(cedula.getDistrict());
        assertNull(cedula.getBirthday());
        assertNull(cedula.getConsecutive());
        assertNull(cedula.getDigit());
        assertNull(cedula.getFormatted());
        cedula = Cedula.create("281131183009");
        assertFalse(cedula.isValid());
        assertEquals("281131183009", cedula.getRaw());
        assertNull(cedula.getDistrict());
        assertNull(cedula.getBirthday());
        assertNull(cedula.getConsecutive());
        assertNull(cedula.getDigit());
        assertNull(cedula.getFormatted());
        cedula = Cedula.create("2811a11830009V");
        assertFalse(cedula.isValid());
        assertEquals("2811a11830009V", cedula.getRaw());
        assertNull(cedula.getDistrict());
        assertNull(cedula.getBirthday());
        assertNull(cedula.getConsecutive());
        assertNull(cedula.getDigit());
        assertNull(cedula.getFormatted());
        cedula = Cedula.create("2811311830009f");
        assertFalse(cedula.isValid());
        assertEquals("2811311830009f", cedula.getRaw());
        assertNull(cedula.getDistrict());
        assertNull(cedula.getBirthday());
        assertNull(cedula.getConsecutive());
        assertNull(cedula.getDigit());
        assertNull(cedula.getFormatted());
        cedula = Cedula.create("2813201830008V");
        assertFalse(cedula.isValid());
        assertEquals("2813201830008V", cedula.getRaw());
        assertNull(cedula.getDistrict());
        assertNull(cedula.getBirthday());
        assertNull(cedula.getConsecutive());
        assertNull(cedula.getDigit());
        assertNull(cedula.getFormatted());
    }
}
