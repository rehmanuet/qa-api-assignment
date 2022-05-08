import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class utils {


    public static Integer getUser(String name) {
        Integer UserID = null;
        Response res = RestAssured.get("https://jsonplaceholder.typicode.com/users");
        JsonPath path = res.jsonPath();
        List<HashMap<String, Object>> users = path.getList(".");
        for (HashMap<String, Object> user : users) {
            if (user.get("username").equals(name)) {
                UserID = (Integer) user.get("id");
            }
        }
        return UserID;
    }

    public static ArrayList<Integer> getPosts(Integer userId) {
        Response post = RestAssured.get("https://jsonplaceholder.typicode.com/posts");

        JsonPath path1 = post.jsonPath();
        ArrayList<Integer> list = new ArrayList<>();
        List<HashMap<String, Object>> posts = path1.getList(".");
        for (HashMap<String, Object> p : posts) {
            if (p.get("userId").equals(userId)) {
                list.add((Integer) p.get("id"));
            }
        }
        return list;
    }

    public static int getStatusCode(String path) {
        Response post = RestAssured.get("https://jsonplaceholder.typicode.com/" + path);
        return post.getStatusCode();
    }

    public static Boolean getUserName(String name) {
        boolean isPresent = false;
        List<HashMap<String, Object>> users = getResponse("users");
        for (HashMap<String, Object> user : users) {
            if (user.get("username").equals(name)) {
                isPresent = true;
            }
        }
        return isPresent;
    }

    public static List<HashMap<String, Object>> getResponse(String path){

        Response res =  RestAssured.get("https://jsonplaceholder.typicode.com/" + path);
        return res.jsonPath().getList(".");
    }
}
