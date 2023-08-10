package section6.ManyToMany;

import section6.OneToOneSingle.Locker;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "USERNAME")
    private String name;

    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT")
    private List<Product> productList = new ArrayList<>();


}
