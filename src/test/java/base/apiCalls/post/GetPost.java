package base.apiCalls.post;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.Constants;

public class GetPost {

    /**
     * To retrieve all the posts
     */
    public Response getAllPosts() {
        return RestAssured.get(Constants.BASE_URL + Constants.POSTS_ENDPOINT);
    }

    /**
     * To retrieve all the posts of specific userId
     *
     * @param id, id of user
     */
    public Response getPostsByUserid(Integer id) {
        return RestAssured.get(Constants.BASE_URL + Constants.POSTS_ENDPOINT + '/' + id);
    }
}
