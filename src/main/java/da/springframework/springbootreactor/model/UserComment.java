package da.springframework.springbootreactor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserComment {

    private User user;
    private Comment comment;
}
