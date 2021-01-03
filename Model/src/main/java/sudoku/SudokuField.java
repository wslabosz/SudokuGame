package sudoku;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import sudoku.exceptions.WrongFieldValueSudokuException;

public class SudokuField implements Cloneable, Comparable<SudokuField>, Serializable {
    private int value;
    private final PropertyChangeSupport propertySupport = new PropertyChangeSupport(this);

    public SudokuField(int value) {
        this.value = value;
    }

    public int getNumber() {
        return this.value;
    }

    public void setNumber(int value) {
        if (value < 0 || value > 9) {
            throw new WrongFieldValueSudokuException("Number must be in range from 0 to 9");

        }
        propertySupport.firePropertyChange("value", this.value, value);
        this.value = value;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        propertySupport.addPropertyChangeListener(pcl);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SudokuField that = (SudokuField) obj;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public SudokuField clone() throws CloneNotSupportedException {
        return (SudokuField) super.clone();
    }

    @Override
    public int compareTo(SudokuField field)  {
        if (field == null) {
            throw new NullPointerException();
        }
        if (field.value > this.value) {
            return -1;
        } else if (field.value == this.value) {
            return 0;
        } else {
            return 1;
        }
    }
}
