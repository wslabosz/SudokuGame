package project;

import java.util.HashSet;
import java.util.Set;


import static project.SudokuBoard.SIZE;

public abstract class SudokuFieldsTemplate {
    protected SudokuField[] fields;

    public SudokuFieldsTemplate(final SudokuField[] fields) {
        if (fields.length != SIZE) {
            throw new IllegalArgumentException("Length must be 9");
        }
        this.fields = fields;
    }

    public boolean verify() {
        Set<Integer> numbers = new HashSet<>();
        for (SudokuField value : fields) {
            if (value.getFieldValue() != 0) {
                // jesli pojawi sie powtorzenie ktore nie jest zerem to zwracamy false
                if (!numbers.add(value.getFieldValue())) {
                    return false;
                }
            }
        }
        return true;
    }
}
