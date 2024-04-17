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


    List<TargetModel> findAllByTargetCategory(TargetCategory targetCategory);

    Optional<TargetModel> findByTargetName(String targetName);


    @Query("select e from TargetModel e where e.targetBegins between :min and :max")
    List<TargetModel> findAllByTargetBeginsBetween(@Param("min") LocalDateTime minDate, @Param("max") LocalDateTime maxDate);
}
