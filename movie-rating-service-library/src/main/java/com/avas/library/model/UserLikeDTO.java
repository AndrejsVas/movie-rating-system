package main.java.com.avas.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLikeDTO {

    private Long id;
    private Long userId;
    private Long reviewId;
}