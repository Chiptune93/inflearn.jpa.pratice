package domain;

import javax.persistence.*;

@Entity
public class CategoryItems extends InfoHistory {

    @Id @GeneratedValue
    @Column(name = "CATEGORY_ITEMS_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private Categorys categorys;

    @ManyToOne
    @JoinColumn(name = "ITEMS_ID")
    private Items items;


}
