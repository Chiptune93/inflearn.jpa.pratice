package domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Categorys extends InfoHistory {

    @Id
    @GeneratedValue
    @Column(name = "CATEGORY_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Categorys parent;

    @OneToMany(mappedBy = "parent")
    private List<Categorys> child = new ArrayList<>();

    @OneToMany(mappedBy = "categorys")
    private List<CategoryItems> itemsList = new ArrayList<>();

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

    public List<Categorys> getChild() {
        return child;
    }

    public void setChild(List<Categorys> child) {
        this.child = child;
    }

    public List<CategoryItems> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<CategoryItems> itemsList) {
        this.itemsList = itemsList;
    }
}
