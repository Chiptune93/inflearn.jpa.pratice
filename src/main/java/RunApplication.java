import domain.OrderStatus;
import domain.Orders;
import domain.OrdersItems;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.Random;

public class RunApplication {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Orders orders = new Orders();
            orders.setOrderStatus(OrderStatus.DELIVERY);
            orders.setOrderDate(LocalDateTime.now());

            OrdersItems ordersItems1 = new OrdersItems();
            ordersItems1.setOrderPrice(100);
            ordersItems1.setCount(100);
            ordersItems1.setOrders(orders);

            OrdersItems ordersItems2 = new OrdersItems();
            ordersItems2.setOrderPrice(100);
            ordersItems2.setCount(100);
            ordersItems2.setOrders(orders);

            em.persist(orders);

            Long id = orders.getId();
            em.clear();

            Orders orders1 = em.find(Orders.class, id);
            System.out.println(orders1);


            tx.commit();

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

    static long randomItems() {
        Random rand = new Random();
        return rand.nextInt((3 - 1) + 1) + 1;
    }

    static long randomItemsNumber() {
        Random rand = new Random();
        return rand.nextInt((19) + 1);
    }
}
