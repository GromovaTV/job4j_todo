package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Item;
import persistence.HbnStore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SaveChangesService {
    private static final Gson GSON = new GsonBuilder().create();

    public void handleGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    }

    public void handlePost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("START SAVE");
        Item item = GSON.fromJson(req.getReader(), Item.class);
        System.out.println(item.toString());
        var context = req.getServletContext();
        int id = Integer.parseInt(context.getAttribute("id").toString());
        HbnStore.instOf().edit(id, item.getName(), item.getDescription(), item.isDone());
        System.out.println("FINISH SAVE");
    }
}
