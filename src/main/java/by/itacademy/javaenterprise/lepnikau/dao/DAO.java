package by.itacademy.javaenterprise.lepnikau.dao;

import java.util.List;

public interface DAO<T> {

    T save(T t);

    T get(Long id);

    List<T> getAllPageByPage(int limit, int offset);

}
