package domain;

import javax.persistence.*;

@Entity
@Table(name = "ORDERS_ITEMS")
public class OrdersItems extends InfoHistory {

    @Id
    @GeneratedValue
    @Column(name = "ORDERS_ITEMS_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ITEMS_ID")
    private Items items;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Orders orders;

    private int orderPrice;
    private int count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
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
