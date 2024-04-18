package pl.micede.personalapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.micede.personalapi.model.TargetCategory;
import pl.micede.personalapi.model.TargetModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TargetRepository extends JpaRepository<TargetModel,Long> {


    /**
     * Finds all TargetModel entities by its category;
     *
     * @param targetCategory The unique category of the target;
     * @return The list of TargetModel Objects found by its category;
     */
    List<TargetModel> findAllByTargetCategory(TargetCategory targetCategory);

    /**
     * Finds TargetModel entity by its name;
     *
     * @param targetName The unique name of the target;
     * @return TargetModel Object found by its name;
     */
    Optional<TargetModel> findByTargetName(String targetName);


    /**
     * Finds all TargetModel entities within the set period of time when the target begins;
     *
     * @param minDate The minimal date of the target;
     * @param maxDate The maximal date of the target;
     * @return The list of TargetModel Objects found by its beginning range of time;
     */
    @Query("select e from TargetModel e where e.targetBegins between :min and :max")
    List<TargetModel> findAllByTargetBeginsBetween(@Param("min") LocalDateTime minDate, @Param("max") LocalDateTime maxDate);
}
