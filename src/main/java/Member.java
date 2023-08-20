import javax.persistence.*;

@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "USERNAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
    // insertable, updateable 이 true 인 경우, 어디를 먼저 업데이트 하거나 넣을지 순서가 꼬이기 때문에 비활성화 시켜줘야 한다.
    private Team team;

}
