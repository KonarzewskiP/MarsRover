package konarzewski.nasa_rover.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Camera {

    private String name;
    private String abbreviation;
    private Boolean active;
    private String description;

}
