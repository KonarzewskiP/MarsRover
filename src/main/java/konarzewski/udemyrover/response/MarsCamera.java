package konarzewski.udemyrover.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MarsCamera {
    private Long id;
    private String name;
    @JsonProperty("rover_id")
    private Long roverId;
    @JsonProperty("full_name")
    private String fullName;
}
