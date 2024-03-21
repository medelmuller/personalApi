package pl.micede.personalapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.micede.personalapi.model.TargetCategory;
import pl.micede.personalapi.model.TargetModel;

import java.util.List;
import java.util.Optional;

public interface TargetRepository extends JpaRepository<TargetModel,Long> {


    List<TargetModel> findAllByTargetCategory(TargetCategory targetCategory);

    Optional<TargetModel> findByTargetName(String targetName);


}
