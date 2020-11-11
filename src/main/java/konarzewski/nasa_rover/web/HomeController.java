package konarzewski.nasa_rover.web;
import konarzewski.nasa_rover.response.MarsRoverApiResponse;
import konarzewski.nasa_rover.dto.HomeDto;
import konarzewski.nasa_rover.service.MarsRoverApiService;
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
        System.out.println(userId);
        HomeDto homeDto = createDefaultHomeDto(userId);

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
        System.out.println(homeDto);
        homeDto = roverService.save(homeDto);
        return "redirect:/?userId=" + homeDto.getUserId();
    }
}
