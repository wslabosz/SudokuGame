package sudoku;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static sudoku.SudokuBoard.SIZE;

class SudokuFieldsTemplateTest {

    @Test
    void verify() {
        SudokuField[] fields = new SudokuField[SIZE];
        SudokuField field0 = new SudokuField(0);
        SudokuField field1 = new SudokuField(1);
        SudokuField field2 = new SudokuField(2);
        for (int i = 0; i < 9; i++) {
            fields[i] = field0;
        }
        fields[0] = field1;
        fields[1] = field2;
        SudokuRow row = new SudokuRow(Arrays.asList(fields));
        assertTrue(row.verify());
        fields[2] = field1;
        SudokuRow row2 = new SudokuRow(Arrays.asList(fields));
        assertFalse(row2.verify());
    }

    @Test
    void testEquals() {
        SudokuField[] fields = new SudokuField[SIZE];
        SudokuField field0 = new SudokuField(0);
        for (int i = 0; i < 9; i++) {
            fields[i] = field0;
        }
        SudokuRow row1 = new SudokuRow(Arrays.asList(fields));
        SudokuRow row2 = new SudokuRow(Arrays.asList(fields));
        SudokuColumn col = new SudokuColumn(Arrays.asList(fields));
        assertEquals(row2, row1);
        assertNotEquals(row2, null);
        assertNotEquals(row2, col);
    }

    @Test
    void testHashCode() {
        SudokuField[] fields = new SudokuField[SIZE];
        SudokuField[] fields2 = new SudokuField[SIZE];
        SudokuField field5 = new SudokuField(5);
        SudokuField field1= new SudokuField(1);
        for (int i = 0; i < 9; i++) {
            fields[i] = field5;
            fields2[i] = field1;
        }
        SudokuRow row1 = new SudokuRow(Arrays.asList(fields));
        SudokuRow row2 = new SudokuRow(Arrays.asList(fields));
        SudokuRow row3 = new SudokuRow(Arrays.asList(fields2));
        assertEquals(row2.hashCode(), row1.hashCode());
        assertNotEquals(row2.hashCode(), row3.hashCode());
    }

    @Test
    void testToString() {
        SudokuField[] fields = new SudokuField[SIZE];
        SudokuField field5 = new SudokuField(5);
        for (int i = 0; i < 9; i++) {
            fields[i] = field5;
        }
        SudokuRow row1 = new SudokuRow(Arrays.asList(fields));
        String toString = row1.toString();
        assertTrue(toString.matches("SudokuRow\\{fields=\\[[a-zA-Z\\{a-z=0-9\\}\\,\\s]*?\\]\\}"));
    }

    @Test
    void testClone() throws IOException, ClassNotFoundException {
        SudokuField[] fields = new SudokuField[SIZE];
        SudokuField field5 = new SudokuField(5);
        for (int i = 0; i < 9; i++) {
            fields[i] = field5;
        }
        SudokuFieldsTemplate row = new SudokuRow(Arrays.asList(fields));
        SudokuFieldsTemplate col = new SudokuColumn(Arrays.asList(fields));
        SudokuFieldsTemplate box = new SudokuBox(Arrays.asList(fields));
        SudokuRow sudokuRowClone = (SudokuRow) row.deepClone();
        SudokuColumn sudokuColClone = (SudokuColumn) col.deepClone();
        SudokuBox sudokuBoxClone = (SudokuBox) box.deepClone();
        assertNotSame(row, sudokuRowClone);
        assertEquals(row.getClass(), sudokuRowClone.getClass());
        assertEquals(sudokuRowClone, row);
        assertNotEquals(row, sudokuBoxClone);

        assertNotSame(col, sudokuColClone);
        assertEquals(col.getClass(), sudokuColClone.getClass());
        assertEquals(sudokuColClone, col);
        assertNotEquals(col, sudokuRowClone);

        assertNotSame(box, sudokuBoxClone);
        assertEquals(box.getClass(), sudokuBoxClone.getClass());
        assertEquals(sudokuBoxClone, box);

        SudokuField field9 = new SudokuField(9);
        row.fields.set(0, field9);
        assertNotEquals(row, sudokuRowClone);
    }
}