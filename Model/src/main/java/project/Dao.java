package project;

public interface Dao<T> extends AutoCloseable {

    T read();

    void write(T obj);
}
