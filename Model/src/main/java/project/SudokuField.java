package project;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.InputMismatchException;

public class SudokuField implements Cloneable, Comparable {
    private int value;
    private final PropertyChangeSupport propertySupport = new PropertyChangeSupport(this);

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
    public int compareTo(Object o) throws ClassCastException{
        SudokuField pom = (SudokuField) o;
        if (pom.value > this.value) { return -1;}
        else if (pom.value == this.value) { return 0;}
        else { return 1;}
    }
}
