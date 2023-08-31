package domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Orders extends InfoHistory {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

//    @OneToMany
//    @JoinColumn(name = "ORDER_ID")
//    private List<OrdersItems> ordersItemsList = new ArrayList<>();

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrdersItems> ordersItems = new ArrayList<>();

    public List<OrdersItems> getOrdersItems() {
        return ordersItems;
    }

    public void setOrdersItems(List<OrdersItems> ordersItems) {
        this.ordersItems = ordersItems;
    }

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public void addOrderItem(OrdersItems orderItem) {
        ordersItems.add(orderItem);
        orderItem.setOrders(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

//    public List<OrdersItems> getOrdersItemsList() {
//        return ordersItemsList;
//    }
//
//    public void setOrdersItemsList(List<OrdersItems> ordersItemsList) {
//        this.ordersItemsList = ordersItemsList;
//    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
