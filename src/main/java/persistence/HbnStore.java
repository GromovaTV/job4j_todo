package persistence;

import model.Category;
import model.Item;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

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

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public Item findById(int id) {
        return this.tx(
                session -> {
                    final Query query = session.createQuery("select distinct i from model.Item i join fetch i.ctgrs where i.id=:id");
                    query.setParameter("id", id);
                    return (Item) query.uniqueResult();
                }
        );
    }

    @Override
    public List<Item> findAll() {
        return this.tx(
                session -> {
                    final Query query = session.createQuery("select distinct i from model.Item i join fetch i.ctgrs");
                    List<Item> list = query.list();
                    return list;
                }
        );
    }

    @Override
    public List<Item> filterByStatus(boolean status) {
        return this.tx(
                session -> {
                    final Query query = session.createQuery("select distinct i from model.Item i join fetch i.ctgrs where i.done=:done");
                    query.setParameter("done", status);
                    List<Item> list = query.list();
                    return list;
                }
        );

    }

    @Override
    public List findRecent() {
        return this.tx(
                session -> {
                    final Query query = session.createQuery("select distinct i FROM model.Item i join fetch i.ctgrs WHERE i.created BETWEEN :startDate AND :endDate");
                    var now = Timestamp.valueOf(LocalDateTime.now());
                    var oneDayAgo = Timestamp.valueOf(LocalDateTime.now().plusDays(-1));
                    query.setParameter("startDate", oneDayAgo);
                    query.setParameter("endDate", now);
                    return query.list();
                }
        );
    }

    @Override
    public List findAllCategories() {
        return this.tx(
                session -> {
                    final Query query = session.createQuery("from model.Category");
                    return query.list();
                }
        );
    }

    @Override
    public void addItem(Item item, String[] ids) {
        this.tx(session -> {
            for (String id : ids) {
                Category ctgr = session.find(Category.class, Integer.parseInt(id));
                item.addCtgr(ctgr);
            }
            session.save(item);
            return item;
        });
    }

    @Override
    public void addUser(User user) {
        this.tx(session -> {
            session.save(user);
            return user;
        });
    }

    @Override
    public void delete(int id) {
        this.tx(session -> {
            Item item = new Item();
            item.setId(id);
            session.delete(item);
            return item;
        });
    }

    @Override
    public void setCompleted(int id) {
         this.tx(
                session -> {
                    final Query query = session.createQuery(
                "update model.Item i set i.done=:done where i.id=:id");
                    query.setParameter("done", true);
                    query.setParameter("id", id);
                    query.executeUpdate();
                    return null;
                }
        );
    }

    @Override
    public void edit(int id, String name, String description, boolean done) {
        this.tx(
                session -> {
                    final Query query = session.createQuery(
                            "update model.Item i set i.name = :name, i.description = :description, i.done = :done where i.id=:id");
                    query.setParameter("name", name);
                    query.setParameter("description", description);
                    query.setParameter("done", done);
                    query.setParameter("id", id);
                    query.executeUpdate();
                    return null;
                }
        );
    }

    @Override
    public User findUserByEmail(String email) {
        return this.tx(
                session -> {
                    final Query query = session.createQuery("from model.User as u where u.email=:email");
                    query.setParameter("email", email);
                    return (User) query.uniqueResult();
                }
        );
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
