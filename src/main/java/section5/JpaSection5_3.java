package section5;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaSection5_3 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            /* ------------------------------------------------------------------------------------------ */
            // TODO: 양방향 관계 설정 시 가장 많이 하는 실수

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setName("member1");
            em.persist(member);

            // 역방향(주인이 아닌 방향)만 연관관계 설정
            // 주인이 아닌 쪽에서 업데이트를 해봤자, member에는 team 값이 업데이트 되지 않음.
            team.getMembers().add(member);
            // 이렇게 해주어야 연관관계에 의해 업데이트가 발생함.
            member.setTeam(team);

            em.persist(member);


            /* ------------------------------------------------------------------------------------------ */

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

}
