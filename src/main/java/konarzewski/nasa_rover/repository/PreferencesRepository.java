package konarzewski.nasa_rover.repository;

import konarzewski.nasa_rover.dto.HomeDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferencesRepository  extends JpaRepository<HomeDto, Long> {
    HomeDto findByUserId(Long userId);

}
