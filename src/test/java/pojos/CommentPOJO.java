package pojos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentPOJO {
    private Integer postId;
    private Integer id;
    private String name;
    private String email;
    private String body;
}
