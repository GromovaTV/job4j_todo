package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Item;
import model.ItemDTO;
import model.User;
import persistence.HbnStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

public class AddService {

    private static final Gson GSON = new GsonBuilder().create();

    public void handleGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        var ctgrs = HbnStore.instOf().findAllCategories();
        String json = GSON.toJson(ctgrs);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    public void handlePost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ItemDTO itemDTO = GSON.fromJson(req.getReader(), ItemDTO.class);
        System.out.println(itemDTO);
        Item item = new Item();
        item.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        HttpSession sc = req.getSession();
        User user = (User) sc.getAttribute("user");
        item.setUser(user);
        item.setId(itemDTO.getId());
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setDone(itemDTO.isDone());
        String[] cIds = itemDTO.getCtgrs();
        System.out.println("print cIds");
        System.out.println(Arrays.toString(cIds));
        HbnStore.instOf().addItem(item, cIds);
    }
}
