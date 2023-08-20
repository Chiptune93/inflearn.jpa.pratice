import javax.persistence.*;
import java.util.List;

@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipcode;
    @OneToMany
    @JoinColumn(name = "ORDER_ID")
    private List<Order> orders;
}
