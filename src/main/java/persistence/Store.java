package persistence;

import model.Item;

import java.util.List;

public interface Store {
    List findAll();
    List filterByStatus(boolean status);
    List findRecent();
    Item findById(int id);
    void add(Item item);
    void delete(int id);
    void setCompleted(int id);
    void edit(int id, String name, String description, boolean done);
}
