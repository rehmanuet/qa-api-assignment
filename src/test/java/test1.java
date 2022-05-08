import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;


public class test1 {

    @Test
    public void tc1StatusCodeUsers() {
        int user_ = utils.getStatusCode("users");
        Assert.assertEquals(user_, 200);

    }

    @Test
    public void tc1StatusCodePosts() {
        int user_ = utils.getStatusCode("posts");
        Assert.assertEquals(user_, 200);

    }

    @Test
    public void tc1StatusCodeComments() {
        int user_ = utils.getStatusCode("comments");
        Assert.assertEquals(user_, 200);

    }

    @Test
    public void tcSearchUserValid() {

        String username = "Samantha";
        boolean user = utils.getUserName(username);
        Assert.assertTrue(user, String.format("$s is available in the response", username));
    }

    @Test
    public void tcSearchUserInvalid() {

        String username = "AbdurRehman";
        boolean user = utils.getUserName(username);
        Assert.assertFalse(user);
    }

    @Test
    public void tcCheckIfUserIdExist() {
        String username = "Samantha";
        int userId = utils.getUser(username);
        Assert.assertNotNull(userId);
    }


    @Test
    public void tcCheckIfPostsExist() {

        String username = "Samantha";
        ArrayList<Integer> posts = utils.getPosts(utils.getUser(username));
        Assert.assertNotNull(posts);
    }

    @Test
    public void tcCheckIfPostsDoestNotExist() {

        String username = "Samanthsa";
        ArrayList<Integer> post = utils.getPosts(utils.getUser(username));
        Assert.assertEquals(post.size(), 0);
    }
}

