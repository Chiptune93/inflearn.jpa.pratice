package section6.ManyToOneMultiple;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team") // mappedBy 변수 명은 참조 대상의 변수명 (Member.java 의 team 변수)
    List<Member> members = new ArrayList<>();

}
