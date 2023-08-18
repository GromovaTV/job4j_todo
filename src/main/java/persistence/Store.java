package persistence;

import model.Item;

import java.util.Collection;

public interface Store {
    Collection<Item> findAll();
    Collection<Item> filterByStatus(boolean status);
    Collection<Item> findRecent();
    Item findById(int id);
    void add(Item item);
    void delete(int id);
    void setCompleted(int id);
    void edit(int id, String name, String description, boolean done);
}
