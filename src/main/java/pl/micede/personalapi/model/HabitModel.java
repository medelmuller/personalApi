package pl.micede.personalapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "habit")
public class HabitModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "habit_name")
    private String habitName;

    @Column(name = "habit_description")
    private String habitDescription;

    @Column(name = "frequency_type")
    private FrequencyType frequencyType;

    @ManyToOne
    @JoinColumn(name = "target_id")
    @JsonBackReference
    private TargetModel target;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "activities",
            joinColumns = @JoinColumn(name = "habit_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private List<ActivityModel> activities = new ArrayList<>();
}
