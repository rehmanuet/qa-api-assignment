package pojos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
public class PostsPOJO {

    private Integer userId;
    private Integer id;
    private String title;
    private String body;
}
