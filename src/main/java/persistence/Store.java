package persistence;

import model.Item;
import model.User;

import java.util.List;

public interface Store {
    List findAll();
    List filterByStatus(boolean status);
    List findRecent();
    Item findById(int id);
//    Role findRoleById(int id);
    User findUserByEmail(String email);
    void addItem(Item item);
    void addUser(User user);
    void delete(int id);
    void setCompleted(int id);
    void edit(int id, String name, String description, boolean done);
}
