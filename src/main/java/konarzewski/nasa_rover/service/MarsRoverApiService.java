package konarzewski.nasa_rover.service;

import konarzewski.nasa_rover.repository.PreferencesRepository;
import konarzewski.nasa_rover.response.MarsRoverApiResponse;
import konarzewski.nasa_rover.dto.HomeDto;
import konarzewski.nasa_rover.response.MarsPhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Service
public class MarsRoverApiService {

    private static final String API_KEY = "kVBXO7R76SwS8bbEwtYcNo7zlXn5bmxmZc2Bf7ns";

    private Map<String, List<String>> validCameras = new HashMap<>();

    @Autowired
    private PreferencesRepository preferencesRepo;

    public MarsRoverApiService() {
        validCameras.put("Opportunity", Arrays.asList("FHAZ", "RHAZ", "NAVCAM", "PANCAM", "MINITES"));
        validCameras.put("Curiosity", Arrays.asList("FHAZ", "RHAZ", "MAST", "CHEMCAM", "MAHLI", "MARDI", "NAVCAM"));
        validCameras.put("Spirit", Arrays.asList("FHAZ", "RHAZ", "NAVCAM", "PANCAM", "MINITES"));
    }

    public MarsRoverApiResponse getRoverData(HomeDto homeDto) throws InvocationTargetException, IllegalAccessException {
        RestTemplate rt = new RestTemplate();

        List<String> apiUrlEndpoints = getApiUrlEndpoint(homeDto);
        List<MarsPhoto> photos = new ArrayList<>();
        MarsRoverApiResponse response = new MarsRoverApiResponse();

        apiUrlEndpoints
                .forEach(url -> {
                    MarsRoverApiResponse apiResponse = rt.getForObject(url, MarsRoverApiResponse.class);
                    assert apiResponse != null;
                    photos.addAll(apiResponse.getPhotos());
                });
        response.setPhotos(photos);

        return response;
    }

    public List<String> getApiUrlEndpoint(HomeDto homeDto) throws InvocationTargetException, IllegalAccessException {
        List<String> urls = new ArrayList<>();

        Method[] methods = homeDto.getClass().getMethods();

        for (Method method : methods) {
            if (method.getName().contains("getCamera")) {
                if (method.getName().contains("getCamera") && Boolean.TRUE.equals(method.invoke(homeDto))) {
                    String cameraName = method.getName().split("getCamera")[1].toUpperCase();
                    if (validCameras.get(homeDto.getMarsApiRoverData()).contains(cameraName)) {
                        urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/" + homeDto.getMarsApiRoverData() + "/photos?sol=" + homeDto.getMarsSol() + "&api_key=" + API_KEY + "&camera=" + cameraName);
                    }
                }
            }
        }

        return urls;
    }

    public Map<String, List<String>> getValidCameras() {
        return validCameras;
    }

    public HomeDto save(HomeDto homeDto) {
        return preferencesRepo.save(homeDto);

    }

    public HomeDto findByUserId(Long userId) {
        return preferencesRepo.findByUserId(userId);
    }
}
