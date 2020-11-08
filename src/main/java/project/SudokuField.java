package project;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.InputMismatchException;

public class SudokuField {
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

}
