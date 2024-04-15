package pl.micede.personalapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "target")
public class TargetModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "target_name")
    private String targetName;


    @Column(name = "description")
    private String description;

    @Column(name = "target_begins")
    private LocalDateTime targetBegins;

    @Column(name = "target_ends")
    private LocalDateTime targetEnds;

    @Column(name = "category")
    private TargetCategory targetCategory;

    @OneToMany(mappedBy = "target", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<HabitModel> habits = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserModel user;
}
