package section5;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaSection5_4 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            /* ------------------------------------------------------------------------------------------ */
            // TODO: 양 쪽에 다 값을 넣어주어야 한다.

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setName("member1");
            member.setTeam(team);
            em.persist(member);
            // team3.getMembers().add(member); 를 넣어주지 않고
            // 영속성 컨텍스트를 초기화하지 않으면

            Team findTeam = em.find(Team.class, team.getId()); // 1차 캐시에 존재하는 상태.
            List<Member> members = findTeam.getMembers(); // 여기서 조회되지 않는다. 1차 캐시에서 데이터를 가져오기 때문에.

            // --> 따라서, 양 쪽에 다 세팅을 해주어야 한다.
            // 또, 테스트 케이스에서도 실제 DB에 작업하는게 아니기 때문에 코드 레벨에서 양 쪽에 다 세팅해주어야 한다.

            System.out.println("================");
            for(Member m : members) {
                System.out.println("m = " + m.getName());
            }
            System.out.println("================");

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
