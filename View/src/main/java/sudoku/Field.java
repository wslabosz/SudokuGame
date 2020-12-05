package sudoku;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class Field {
    private int xCoordinate;
    private int yCoordinate;

    public Field (int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("xCoordinate", xCoordinate)
                .add("yCoordinate", yCoordinate)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return xCoordinate == field.xCoordinate &&
                yCoordinate == field.yCoordinate;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(xCoordinate, yCoordinate);
    }
}
