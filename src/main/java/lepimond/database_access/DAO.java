package lepimond.database_access;

import lepimond.exceptions.PeopleCLIException;

import java.util.List;

public interface DAO<T> {
    T get(int id) throws PeopleCLIException;
    List<T> getAll() throws PeopleCLIException;
    double getAvg(String fieldName) throws PeopleCLIException;
    void save(T t) throws PeopleCLIException;
    void delete(int id) throws PeopleCLIException;
    void deleteAll() throws PeopleCLIException;
    void update(int id, String updateContent) throws PeopleCLIException;
}
