package model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "items")
public class Item {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description = "";
    private Timestamp created;
    private boolean done;


    public Item() {
    }

    public Item(String name, String description, boolean done) {
        this.name = name;
        this.description = description;
        this.created = Timestamp.valueOf(LocalDateTime.now());
        this.done = done;
    }

    public Item(String name, Timestamp created, boolean done) {
        this.name = name;
        this.description = description;
        this.created = created;
        this.done = done;
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

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
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

    @Override
    public String toString() {
        return "Item{"
                + "id=" + id + '\''
                + ", name='" + name + '\''
                + ", created=" + created + '\''
                + ", done=" + done
                + '}';
    }
}
