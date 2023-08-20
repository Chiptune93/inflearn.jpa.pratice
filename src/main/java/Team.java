import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany
    @JoinColumn(name = "TEAM_ID") // 조인 컬럼을 사용하지 않으면, 업데이트 문 대신에 조인 테이블을 자동으로 생성한다.
    private List<Member> members = new ArrayList<>();

}
