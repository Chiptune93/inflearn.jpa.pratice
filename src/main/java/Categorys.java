import javax.persistence.*;

@Entity
public class Categorys extends InfoHistory {

    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", insertable = false, updatable = false)
    private Categorys parent;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Categorys getParent() {
        return parent;
    }

    public void setParent(Categorys parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
