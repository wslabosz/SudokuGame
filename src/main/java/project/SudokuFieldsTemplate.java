package project;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class SudokuFieldsTemplate {
    protected List<SudokuField> fields;

    public SudokuFieldsTemplate(final List<SudokuField> fields) {
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
