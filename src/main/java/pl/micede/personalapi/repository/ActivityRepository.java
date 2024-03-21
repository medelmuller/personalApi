package pl.micede.personalapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.micede.personalapi.model.ActivityModel;
public interface ActivityRepository extends JpaRepository<ActivityModel, Long> {
}
