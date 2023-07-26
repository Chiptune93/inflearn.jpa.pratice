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
            MemberOld memberOld = new MemberOld(200L, "member200");
            em.persist(memberOld);

            em.flush();

            tx.commit();

            /* -------------------------------------------- */

            // JPQL 에서 플러시 자동 호출이 되는 이유 -> 쿼리 전 작업을 적용 후에 쿼리를 실행하기 위해
            MemberOld memberOld1 = new MemberOld(201L, "member201");
            MemberOld memberOld2 = new MemberOld(202L, "member202");
            MemberOld memberOld3 = new MemberOld(203L, "member203");
            em.persist(memberOld1);
            em.persist(memberOld2);
            em.persist(memberOld3);

            TypedQuery<MemberOld> query = em.createQuery("select m from MemberOfTeam m", MemberOld.class);
            List<MemberOld> list = query.getResultList();
            tx.commit();

            /* -------------------------------------------- */

            // 플러시 모드 옵션
            em.setFlushMode(FlushModeType.AUTO);
            em.setFlushMode(FlushModeType.COMMIT);

            /* -------------------------------------------- */
            // 영속
            MemberOld memberOld4 = em.find(MemberOld.class,200L);
            memberOld4.setUsername("ZZZZ");

            em.detach(memberOld4); // 준영속 분리
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
