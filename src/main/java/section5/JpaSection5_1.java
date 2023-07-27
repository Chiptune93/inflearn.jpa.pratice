package section5;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaSection5_1 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            /* ------------------------------------------------------------------------------------------ */
            // TODO: 단방향 연관관계

            // 테이블에 맞춰서 외래키 값을 그대로 가지고 옴.

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setName("member1");
            // memberOfTeam.setTeamId(team.getId());
            // 연관관계가 적용된 객체 매핑
            member.setTeam(team);
            em.persist(member);

            // DB에서 가져오는 쿼리를 보고싶다.
            em.flush();
            em.clear();

            // 영속성 컨텍스트에 의해 1차 캐시에서 바로 가져올 수 있음
            // 조회할 때는 외래 키 참조를 통해 가져오기 때문에 아래와 같은 형태를 띔.
            Member findMember = em.find(Member.class, member.getId());
            // 연관관계가 없어서 ...
            // Long findTeamId = findMember.getTeamId();
            // Team findTeam = em.find(Team.class, findTeamId);

            // 연관관계가 적용된 매핑
            Team findTeam = findMember.getTeam();
            System.out.println("find team : " + findTeam);

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
