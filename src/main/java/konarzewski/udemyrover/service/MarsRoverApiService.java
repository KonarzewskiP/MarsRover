package konarzewski.udemyrover.service;

import konarzewski.udemyrover.dto.HomeDto;
import konarzewski.udemyrover.response.MarsPhoto;
import konarzewski.udemyrover.response.MarsRoverApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Service
public class MarsRoverApiService {

    private static final String API_KEY = "kVBXO7R76SwS8bbEwtYcNo7zlXn5bmxmZc2Bf7ns";

    private Map<String, List<String>> validCameras = new HashMap<>();

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

        apiUrlEndpoints.stream()
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
            if (method.getName().contains("getCamera") && Boolean.TRUE.equals(method.invoke(homeDto))) {
                String cameraName = method.getName().split("getCamera")[1].toUpperCase();
                if (validCameras.get(homeDto.getMarsApiRoverData()).contains(cameraName)) {
                    urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/" + homeDto.getMarsApiRoverData() + "/photos?sol=" + homeDto.getMarsSol() + "&api_key=" + API_KEY + "&camera=" + cameraName);
                }
            }
        }

/*
        if (Boolean.TRUE.equals(homeDto.getCameraFhaz())) {
            urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/" + homeDto.getMarsApiRoverData() + "/photos?sol=" + homeDto.getMarsSol() + "&api_key=" + API_KEY + "&camera=FHAZ");
        }
        if (Boolean.TRUE.equals(homeDto.getCameraChemcam()) && "curiosity".equalsIgnoreCase(homeDto.getMarsApiRoverData())) {
            urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/" + homeDto.getMarsApiRoverData() + "/photos?sol=" + homeDto.getMarsSol() + "&api_key=" + API_KEY + "&camera=CHEMCAM");
        }
        if (Boolean.TRUE.equals(homeDto.getCameraMahli()) && "curiosity".equalsIgnoreCase(homeDto.getMarsApiRoverData())) {
            urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/" + homeDto.getMarsApiRoverData() + "/photos?sol=" + homeDto.getMarsSol() + "&api_key=" + API_KEY + "&camera=MAHLI");
        }
        if (Boolean.TRUE.equals(homeDto.getCameraMardi()) && "curiosity".equalsIgnoreCase(homeDto.getMarsApiRoverData())) {
            urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/" + homeDto.getMarsApiRoverData() + "/photos?sol=" + homeDto.getMarsSol() + "&api_key=" + API_KEY + "&camera=MARDI");
        }
        if (Boolean.TRUE.equals(homeDto.getCameraMast()) && "curiosity".equalsIgnoreCase(homeDto.getMarsApiRoverData())) {
            urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/" + homeDto.getMarsApiRoverData() + "/photos?sol=" + homeDto.getMarsSol() + "&api_key=" + API_KEY + "&camera=MAST");
        }
        if (Boolean.TRUE.equals(homeDto.getCameraMinites()) && !"curiosity".equalsIgnoreCase(homeDto.getMarsApiRoverData())) {
            urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/" + homeDto.getMarsApiRoverData() + "/photos?sol=" + homeDto.getMarsSol() + "&api_key=" + API_KEY + "&camera=MINITES");
        }
        if (Boolean.TRUE.equals(homeDto.getCameraNavcam())) {
            urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/" + homeDto.getMarsApiRoverData() + "/photos?sol=" + homeDto.getMarsSol() + "&api_key=" + API_KEY + "&camera=NAVCAM");
        }
        if (Boolean.TRUE.equals(homeDto.getCameraPancam()) && !"curiosity".equalsIgnoreCase(homeDto.getMarsApiRoverData())) {
            urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/" + homeDto.getMarsApiRoverData() + "/photos?sol=" + homeDto.getMarsSol() + "&api_key=" + API_KEY + "&camera=PANCAM");
        }
        if (Boolean.TRUE.equals(homeDto.getCameraRhaz())) {
            urls.add("https://api.nasa.gov/mars-photos/api/v1/rovers/" + homeDto.getMarsApiRoverData() + "/photos?sol=" + homeDto.getMarsSol() + "&api_key=" + API_KEY + "&camera=RHAZ");
        }
*/

        return urls;
    }

    public Map<String, List<String>> getValidCameras() {
        return validCameras;
    }
}
