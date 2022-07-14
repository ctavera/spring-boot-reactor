package da.springframework.springbootreactor.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Comment {

    private List<String> comments = new ArrayList<>();

    public void addComment(String comment){
        this.comments.add(comment);
    }
}
