package project;

import java.util.InputMismatchException;

public class SudokuField {
    private int value = 0;

    public SudokuField(int value) {
        this.value = value;
    }

    public int getFieldValue() {
        return this.value;
    }

    public void setFieldValue(int value) {
        if (value < 0 || value > 9) {
            throw new InputMismatchException("Number must be in range from 0 to 9");
        }
        this.value = value;
    }
}
