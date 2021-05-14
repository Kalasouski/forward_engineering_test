package models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @Column(name = "player_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private Integer id;

    @Column(name = "name",length = 20, nullable = false)
    @Setter
    @Getter
    private String name;

    @Column(name = "games_finished")
    @ColumnDefault("1")
    @Setter
    @Getter
    private Integer gamesFinished;

}
