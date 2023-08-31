package domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // Cascade
    // 해당 객체와 연관된 아이들도 같이 영속성에 넣어줄꺼야! (ALL)
    // 매핑과는 아무 관련 없다, 편리함을 제공할 뿐.
    // 명확하게 소유자가 하나 일 때, 사용. 만약 다른 객체에서 이 차일드를 쓴다? 사용하면 안됨.

    // orphan
    // 부모 엔티티와 연관관계가 사라지면 같이 삭제 시켜주겠다는 의미.
    // 마찬가지로 부모 엔티티 자체가 사라지면 자식도 다 같이 삭제 시킨다.
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child> childList = new ArrayList<>();

    public void addChild(Child child) {
        childList.add(child);
        child.setParent(this);
    }

    public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
