package project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {

    @Test
    void testEquals() {
        SudokuBoard board = new SudokuBoard(null);
        SudokuField field1 = new SudokuField(1);
        SudokuField field2 = new SudokuField(1);
        SudokuField field0 = new SudokuField(0);
        assertEquals(field1,field1);
        assertEquals(field1,field2);
        assertNotEquals(field1,null);
        assertNotEquals(field1,field0);
        assertNotEquals(field1, board);
    }

    @Test
    void testHashCode() {
        SudokuField field1 = new SudokuField(1);
        SudokuField field2 = new SudokuField(1);
        SudokuField field0 = new SudokuField(0);
        assertEquals(field1.hashCode(),field1.hashCode());
        assertEquals(field1.hashCode(),field2.hashCode());
        assertNotEquals(field1.hashCode(),field0.hashCode());
    }

    @Test
    void testToString() {
        SudokuField field1 = new SudokuField(1);
        String toString = field1.toString();
        assertTrue(toString.matches("SudokuField\\{[a-z=0-9]*?\\}"));
    }

    @Test
    public void setWrongFieldValueTest() {
        SudokuField field = new SudokuField(0);
        Assertions.assertThrows(InputMismatchException.class, () -> {
            field.setFieldValue(-1);
        });
        Assertions.assertThrows(InputMismatchException.class, () -> {
            field.setFieldValue(10);
        });
    }
}