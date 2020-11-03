package konarzewski.udemyrover.repository;

import konarzewski.udemyrover.dto.HomeDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferencesRepository  extends JpaRepository<HomeDto, Long> {
    //using naming convention
    HomeDto findByUserId(Long userId);

}
