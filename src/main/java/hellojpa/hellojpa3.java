package hellojpa;

import javax.persistence.*;
import java.util.List;

public class hellojpa3 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            /* -------------------------------------------- */
            // 영속
            Member member = em.find(Member.class,200L);
            member.setName("ZZZZ");

            em.detach(member); // 준영속 분리
            em.clear(); // 초기화
            em.close(); // 종료
            // 준영속 상태가 되어 commit 해도 쿼리 실행할 게 없음.


            /* -------------------------------------------- */


            System.out.println("/* -------------------------------------------- */");
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
