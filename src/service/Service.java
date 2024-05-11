package service;

public interface Service<T> {
    int save(T t);
    T findById(int id);
    boolean delete(int id);
}
