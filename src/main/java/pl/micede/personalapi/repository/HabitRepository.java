package pl.micede.personalapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.micede.personalapi.model.HabitModel;

import java.util.List;
import java.util.Optional;

public interface HabitRepository extends JpaRepository<HabitModel, Long> {

    /**
     * Finds HabitModel entity by its name;
     *
     * @param habitName The unique name of the habit;
     * @return HabitModel Object found by its name;
     */
    Optional<HabitModel> findByHabitName(String habitName);


}
