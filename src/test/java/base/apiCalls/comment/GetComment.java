package base.apiCalls.comment;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import lombok.extern.slf4j.Slf4j;
import utils.Constants;
import validators.BaseValidation;

@Slf4j
public class GetComment {
    /**
     * To retrieve all the comments of specific postId
     *
     * @param postId, id of post
     */
    public Response getCommentByPostId(int postId) {
        log.info("getCommentByPostId(): postId: "+postId);
        return RestAssured.get(BaseValidation.BASE_URL + Constants.POSTS_ENDPOINT + "/" + postId + Constants.COMMENTS_ENDPOINT);
    }

    /**
     * To retrieve all the comments
     */
    public Response getAllComments() {
        log.info("getAllComments()");
        return RestAssured.get(BaseValidation.BASE_URL + Constants.COMMENTS_ENDPOINT);
    }

}