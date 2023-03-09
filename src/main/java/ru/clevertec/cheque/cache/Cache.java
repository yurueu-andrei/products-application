package ru.clevertec.cheque.cache;

import java.util.List;

public interface Cache {
    Object get(Long key);

    List<Object> getAll();

    void set(Long key, Object value);

    void delete(Long key);
}
