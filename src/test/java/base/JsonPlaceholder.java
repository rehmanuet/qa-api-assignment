package base;

import base.apiCalls.comment.Comment;
import base.apiCalls.post.Post;
import base.apiCalls.user.User;

public class JsonPlaceholder {

    private static final JsonPlaceholder instance = new JsonPlaceholder();

    public Comment comment = new Comment();
    public Post post = new Post();
    public User user = new User();

    private JsonPlaceholder() {
    }

    public static JsonPlaceholder getInstance() {
        return instance;
    }
}
