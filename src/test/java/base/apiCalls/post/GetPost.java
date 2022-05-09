package base.apiCalls.post;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import utils.Constants;
import validators.BaseValidation;

@Slf4j
public class GetPost {

    /**
     * To retrieve all the posts
     */
    public Response getAllPosts() {
        log.info("getAllPosts()");
        return RestAssured.get(BaseValidation.BASE_URL + Constants.POSTS_ENDPOINT);
    }

    /**
     * To retrieve all the posts of specific userId
     *
     * @param id, id of user
     */
    public Response getPostsByUserid(Integer id) {
        log.info("getPostsByUserid(): userid: " + id);
        return RestAssured.get(BaseValidation.BASE_URL + Constants.POSTS_ENDPOINT + '/' + id);
    }
}
