package lepimond.database_access;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
    T get(int id) throws SQLException;
    List<T> getAll() throws SQLException;
    double getAvg(String fieldName) throws SQLException;
    void save(T t) throws SQLException;
    void delete(int id) throws SQLException;
    void deleteAll() throws SQLException;
    void update(int id, String updateContent) throws SQLException;
}
