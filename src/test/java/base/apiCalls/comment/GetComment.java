package base.apiCalls.comment;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import utils.Constants;

public class GetComment {
    /**
     * To retrieve all the comments of specific postId
     *
     * @param postId, id of post
     */
    public Response getCommentByPostId(int postId) {
        return RestAssured.get(Constants.BASE_URL + Constants.POSTS_ENDPOINT + "/" + postId + Constants.COMMENTS_ENDPOINT);
    }

    /**
     * To retrieve all the comments
     */
    public Response getAllComments() {
        return RestAssured.get(Constants.BASE_URL + Constants.COMMENTS_ENDPOINT);
    }

}