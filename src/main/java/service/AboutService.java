package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Item;
import persistence.HbnStore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


public class AboutService {
    private static final Gson GSON = new GsonBuilder().create();

    public void handleGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=utf-8");
        var context = req.getServletContext();
        var id = Integer.parseInt(context.getAttribute("id").toString());
        Item item = HbnStore.instOf().findById(id);
        String json = GSON.toJson(item);
        OutputStream output = resp.getOutputStream();
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    public void handlePost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    }
}
