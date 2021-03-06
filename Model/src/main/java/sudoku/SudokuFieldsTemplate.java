package sudoku;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class SudokuFieldsTemplate implements Cloneable, Serializable {
    protected List<SudokuField> fields;

    public SudokuFieldsTemplate(final List<SudokuField> fields) {
        this.fields = fields;
    }

    public boolean verify() {
        Set<Integer> numbers = new HashSet<>();
        for (SudokuField value : fields) {
            if (value.getValue() != 0) {
                // jesli pojawi sie powtorzenie ktore nie jest zerem to zwracamy false
                if (!numbers.add(value.getValue())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SudokuFieldsTemplate other = (SudokuFieldsTemplate) obj;
        return Objects.equal(this.fields, other.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.fields);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("fields", fields)
                .toString();
    }

    public SudokuFieldsTemplate deepClone() throws IOException, ClassNotFoundException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(this);
            try (ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
                 ObjectInputStream ois = new ObjectInputStream(bis)) {
                return (SudokuFieldsTemplate) ois.readObject();
            }
        }
    }

    //      SudokuFieldsTemplate cloned = (SudokuFieldsTemplate) super.clone();
    //      ArrayList<SudokuField> fieldsClone = new ArrayList<>();
    //      for (SudokuField sudokuField : fields) {
    //          fieldsClone.add(sudokuField.clone());
    //      }
    //      cloned.fields = fieldsClone;
    //      return cloned;

}
