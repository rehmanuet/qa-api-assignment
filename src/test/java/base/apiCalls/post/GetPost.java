package base.apiCalls.post;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.Constants;

public class GetPost {

    //    @Step("POST: /banking/account")

    /**
     * To retrieve all the comments of specific post
     */
    public Response getPosts() {

        return RestAssured.get(Constants.BASE_URL + Constants.POSTS_ENDPOINT);

    }

    public Response getPostsByUserid(Integer id) {

        return RestAssured.get(Constants.BASE_URL + Constants.POSTS_ENDPOINT + '/' + id);

    }


}
