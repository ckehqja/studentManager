package repository;

import java.util.List;

public interface Repository<T> {
    T findById(int id);
    int save(T t);
    boolean delete(int id);
    List<T> getList();
}
