package persistence;

import model.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public class HbnStore implements Store, AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory();

    private static final class Lazy {
        private static Store INST;
    }
    private HbnStore() {
    }
    public static Store instOf() {
        if (Lazy.INST == null) {
            Lazy.INST = new HbnStore();
        }
        return Lazy.INST;
    }

    @Override
    public Item findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from model.Item as i where i.id=:id");
        query.setParameter("id", id);
        @SuppressWarnings("unchecked")
        Item item = (Item) query.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return item;
    }

    @Override
    public Collection<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from model.Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public Collection<Item> filterByStatus(boolean status) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from model.Item as i where i.done=:done");
        query.setParameter("done", status);
        @SuppressWarnings("unchecked")
        List<Item> items = query.list();
        session.getTransaction().commit();
        session.close();
        return items;
    }

    @Override
    public Collection<Item> findRecent() {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM model.Item as i WHERE i.created BETWEEN :startDate AND :endDate");
        var now = Timestamp.valueOf(LocalDateTime.now());
        var oneDayAgo = Timestamp.valueOf(LocalDateTime.now().plusDays(-1));
        query.setParameter("startDate", oneDayAgo);
        query.setParameter("endDate", now);
        @SuppressWarnings("unchecked")
        List<Item> items = query.list();
        session.getTransaction().commit();
        session.close();
        return items;
    }

    @Override
    public void add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = new Item();
        item.setId(id);
        session.delete(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void setCompleted(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery(
                "update model.Item i set i.done=:done where i.id=:id");
        query.setParameter("done", true);
        query.setParameter("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void edit(int id, String name, String description, boolean done) {
        Session session = sf.openSession();
        session.beginTransaction();
        Query query = session.createQuery(
                "update model.Item i set i.name = :name, i.description = :description, i.done = :done where i.id=:id");
        query.setParameter("name", name);
        query.setParameter("description", description);
        query.setParameter("done", done);
        query.setParameter("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
