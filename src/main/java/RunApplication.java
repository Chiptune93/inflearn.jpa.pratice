import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class RunApplication {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            // 실전 예제2 -

            // 케이스 1 - 즉시성으로 오더만 조회해도 오더 아이템을 알고싶다 -> 양방향 관게 설정 후, 가져오거나 설정하기
            Order order = new Order();
            order.addOrderItem(new OrderItem());

            // 케이스 2 - 단방향 관게로도 사실 충분하다. 할 수 있으면 최대한 단방향이 좋지만, 실무에서는 편하게 조회하기 위해 양방향 설정하는 경우가 있다.
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            em.persist(orderItem);


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
