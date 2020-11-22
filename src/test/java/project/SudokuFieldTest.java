package project;

import org.junit.jupiter.api.Test;

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
}