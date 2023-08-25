package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Category;
import model.Item;
import model.ItemDTO;
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
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setDone(item.isDone());
        itemDTO.setCreated(item.getCreated());
        String[] ctgrs = new String[item.getCtgrs().size()];
        int i = 0;
        for (Category c : item.getCtgrs()) {
            ctgrs[i++] = c.getName();
        }
        itemDTO.setCtgrs(ctgrs);
        String json = GSON.toJson(itemDTO);
        OutputStream output = resp.getOutputStream();
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    public void handlePost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    }
}
