package konarzewski.nasa_rover.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
public class MarsRoverApiResponse {

    List<MarsPhoto> photos = new ArrayList<>();
}
