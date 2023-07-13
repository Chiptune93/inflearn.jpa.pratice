package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JpaSection3 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            // 영속성 컨텍스트
            Member member = new Member(200L, "member200");
            em.persist(member);

            em.flush();

            tx.commit();

            /* -------------------------------------------- */

            // JPQL 에서 플러시 자동 호출이 되는 이유 -> 쿼리 전 작업을 적용 후에 쿼리를 실행하기 위해
            Member member1 = new Member(201L, "member201");
            Member member2 = new Member(202L, "member202");
            Member member3 = new Member(203L, "member203");
            em.persist(member1);
            em.persist(member2);
            em.persist(member3);

            TypedQuery<Member> query = em.createQuery("select m from Member m",Member.class);
            List<Member> list = query.getResultList();
            tx.commit();

            /* -------------------------------------------- */

            // 플러시 모드 옵션
            em.setFlushMode(FlushModeType.AUTO);
            em.setFlushMode(FlushModeType.COMMIT);

            /* -------------------------------------------- */
            // 영속
            Member member4 = em.find(Member.class,200L);
            member4.setUsername("ZZZZ");

            em.detach(member4); // 준영속 분리
            em.clear(); // 초기화
            em.close(); // 종료
            // 준영속 상태가 되어 commit 해도 쿼리 실행할 게 없음.


            /* -------------------------------------------- */

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
