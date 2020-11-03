package konarzewski.udemyrover.web;

import konarzewski.udemyrover.dto.HomeDto;
import konarzewski.udemyrover.response.MarsRoverApiResponse;
import konarzewski.udemyrover.service.MarsRoverApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;

@Controller
public class HomeController {

    @Autowired
    private MarsRoverApiService roverService;

    @GetMapping("/")
    public String getHomeView(ModelMap model, Long userId, Boolean createUser) throws InvocationTargetException, IllegalAccessException {
        System.out.println("Get method");
        HomeDto homeDto = createDefaultHomeDto(userId);

        //Whenever we start the page from localhost:8080 the userId is null and new entry is created in database
        if (Boolean.TRUE.equals(createUser) && userId == null) {
            homeDto = roverService.save(homeDto);
        } else {
            homeDto = roverService.findByUserId(userId);
            if (homeDto == null) {
                homeDto = createDefaultHomeDto(userId);
            }
        }

        MarsRoverApiResponse roverData = roverService.getRoverData(homeDto);
        model.addAttribute("roverData", roverData);
        model.addAttribute("homeDto", homeDto);
        model.addAttribute("validCameras", roverService.getValidCameras().get(homeDto.getMarsApiRoverData()));
        if (!Boolean.TRUE.equals(homeDto.getRememberPreferences()) && userId != null) {
            HomeDto defaultHomeDto = createDefaultHomeDto(userId);
            roverService.save(defaultHomeDto);
        }
        return "index";
    }

    @GetMapping("/savedPreferences")
    @ResponseBody
    public HomeDto getSavedPreferences(Long userId) {
        if (userId != null) {
            return roverService.findByUserId(userId);
        } else {
            return createDefaultHomeDto(userId);
        }
    }

    private HomeDto createDefaultHomeDto(Long userId) {

        HomeDto homeDto = new HomeDto();
        homeDto.setMarsApiRoverData("Opportunity");
        homeDto.setMarsSol(1);
        homeDto.setUserId(userId);
        return homeDto;
    }

    @PostMapping("/")
    public String postHomeView(HomeDto homeDto) {
        System.out.println("Post method");
        homeDto = roverService.save(homeDto);
        //userId is query parameter and is displayed in Url
        return "redirect:/?userId=" + homeDto.getUserId();
    }
}
