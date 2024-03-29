package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import persistence.HbnStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteTaskService {

    private static final Gson GSON = new GsonBuilder().create();

    public void handleGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    }

    public void handlePost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var context = req.getServletContext();
        var id = Integer.parseInt(context.getAttribute("id").toString());
        HbnStore.instOf().delete(id);
    }
}
