import jpashop.Member;
import jpashop.Order;

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

            // 실전 예제1 - 데이터 중심의 설계 방식
            Order order = em.find(Order.class, 1L);

            // 데이터 관계에만 중심을 맞춘 설계는 찾을 때 이렇게 찾게 된다.
            Long memberId = order.getMemberId();
            Member member = em.find(Member.class, memberId);

            // 하지만 객체 지향적인 설계라면 바로 찾아갈 수 있어야 한다.
            // Member findMember = order.getMember(member);


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
