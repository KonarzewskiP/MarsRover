package konarzewski.udemyrover.web;

import konarzewski.udemyrover.dto.HomeDto;
import konarzewski.udemyrover.response.MarsRoverApiResponse;
import konarzewski.udemyrover.service.MarsRoverApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.InvocationTargetException;

@Controller
public class HomeController {

    @Autowired
    private MarsRoverApiService roverService;

    @GetMapping("/")
    public String getHomeView(ModelMap model, HomeDto homeDto) throws InvocationTargetException, IllegalAccessException {
        if (StringUtils.isEmpty(homeDto.getMarsApiRoverData())) {
            homeDto.setMarsApiRoverData("Opportunity");
        }
        if (homeDto.getMarsSol() == null) {
            homeDto.setMarsSol(1);
        }
        MarsRoverApiResponse roverData = roverService.getRoverData(homeDto);
        //umozliwia dostep do roverData oraz homeDto ze strony index
        model.addAttribute("roverData", roverData);
        model.addAttribute("homeDto", homeDto);
        model.addAttribute("validCameras", roverService.getValidCameras().get(homeDto.getMarsApiRoverData()));

        return "index";
    }

}
