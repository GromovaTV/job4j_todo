package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Item;
import persistence.HbnStore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AddService {
    private static final Gson GSON = new GsonBuilder().create();

    public void handleGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    }

    public void handlePost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Item item = GSON.fromJson(req.getReader(), Item.class);
        item.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        HbnStore.instOf().addItem(item);
    }
}
