package domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS_ITEMS")
public class OrdersItems extends InfoHistory {

    @Id
    @GeneratedValue
    @Column(name = "ORDERS_ITEMS_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEMS_ID")
    private Items items;

    @ManyToOne(fetch = FetchType.LAZY)
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
        List<OrdersItems> ordersItems = new ArrayList<>();
        ordersItems.add(this);
        orders.setOrdersItems(ordersItems);
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
