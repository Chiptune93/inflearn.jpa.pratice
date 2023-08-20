import javax.persistence.*;

@Entity
public class OrdersItems extends InfoHistory {

    @Id
    @GeneratedValue
    @Column(name = "ORDERS_ITEMS_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ITEMS_ID")
    private Items item;

    @ManyToOne
    @JoinColumn(name = "OREDR_ID")
    private Orders orders;

    private int orderPrice;
    private int count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Items getItem() {
        return item;
    }

    public void setItem(Items item) {
        this.item = item;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
