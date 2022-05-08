package base.apiCalls.comment;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import pojos.CommentsPOJO;
import utils.Constants;

public class GetComment {

    //    @Step("POST: /banking/account")
    /**
     * To retrieve all the comments of specific post
     *
     * @param postId, path of endpoint
     */
    public Response getCommentByPostId(int postId) {

        return RestAssured.get(Constants.BASE_URL + Constants.POSTS_ENDPOINT + "/" + postId + Constants.COMMENTS_ENDPOINT);
    }

    public Response getComments() {

        return RestAssured.get(Constants.BASE_URL + Constants.COMMENTS_ENDPOINT);
    }


}
