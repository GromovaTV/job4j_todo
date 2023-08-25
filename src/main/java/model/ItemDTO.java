package model;

import java.sql.Timestamp;
import java.util.Arrays;

public class ItemDTO {
    private int id;
    private String name;
    private String description = "";
    private boolean done;
    private Timestamp created;

    private String[] ctgrs;

    public ItemDTO() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String[] getCtgrs() {
        return ctgrs;
    }

    public void setCtgrs(String[] ctgrs) {
        this.ctgrs = ctgrs;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "ItemDTO{"
                + "id=" + id + '\''
                + ", name='" + name + '\''
                + ", description='" + description + '\''
                + ", done=" + done + '\''
                + ", created=" + created + '\''
                + ", ctgrs=" + Arrays.toString(ctgrs)
                + '}';
    }
}
