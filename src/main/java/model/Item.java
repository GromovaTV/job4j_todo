package model;

import javax.persistence.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "items")
public class Item {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description = "";

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    private boolean done;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Category> ctgrs = new HashSet<>();

    public void addCtgr(Category ctgr) {
        this.ctgrs.add(ctgr);
    }

    public Item() {
    }

    public Item(String name, String description, boolean done) {
        this.name = name;
        this.description = description;
        this.created = new Date(System.currentTimeMillis());
        this.done = done;
    }

    public Item(String name, Date created, boolean done) {
        this.name = name;
        this.description = description;
        this.created = created;
        this.done = done;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Set<Category> getCtgrs() {
        return ctgrs;
    }

    public void setCtgrs(Set<Category> ctgrs) {
        this.ctgrs = ctgrs;
    }

    @Override
    public String toString() {
        return "Item{"
                + "id=" + id + '\''
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", created=" + created + '\''
                + ", done=" + done + '\''
                + ", user=" + user + '\''
                + ", ctgrs=" + ctgrs + '\''
                + '}';
    }
}
