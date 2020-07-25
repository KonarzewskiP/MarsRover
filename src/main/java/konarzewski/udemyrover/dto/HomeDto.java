package konarzewski.udemyrover.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeDto {
    private String marsApiRoverData;
    private Integer marsSol;

    private Boolean cameraFhaz;
    private Boolean cameraRhaz;
    private Boolean cameraMast;
    private Boolean cameraChemcam;
    private Boolean cameraMahli;
    private Boolean cameraMardi;
    private Boolean cameraNavcam;
    private Boolean cameraPancam;
    private Boolean cameraMinites;
    private Boolean rememberPreferences;


}
