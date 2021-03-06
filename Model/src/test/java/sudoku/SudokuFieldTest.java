package sudoku;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sudoku.exceptions.WrongFieldValueSudokuException;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {

    @Test
    void testEquals() {
        SudokuField field1 = new SudokuField(1);
        SudokuField field2 = new SudokuField(1);
        SudokuField field0 = new SudokuField(0);
        assertEquals(field1,field1);
        assertEquals(field1,field2);
        assertNotEquals(field1,null);
        assertNotEquals(field1, field0);
        assertNotEquals(field1, 1);
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
        Assertions.assertThrows(WrongFieldValueSudokuException.class, () -> {
            field.setValue(-1);
        });
        Assertions.assertThrows(WrongFieldValueSudokuException.class, () -> {
            field.setValue(10);
        });
    }

    @Test
    public void compareToTest() {
        SudokuField field = new SudokuField(4);
        SudokuField field1 = new SudokuField(4);
        SudokuField field2 = new SudokuField(5);
        assertEquals(1, field2.compareTo(field1));
        assertEquals(0, field1.compareTo(field));
        assertEquals(-1, field.compareTo(field2));
        assertThrows(NullPointerException.class, () -> {
            field.compareTo(null);
        });
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        SudokuField field = new SudokuField(5);
        SudokuField cloned = field.clone();
        assertNotSame(field, cloned);
        assertEquals(field.getClass(), cloned.getClass());
        assertEquals(cloned, field);
        field.setValue(3);
        assertNotEquals(cloned, field);
    }
}