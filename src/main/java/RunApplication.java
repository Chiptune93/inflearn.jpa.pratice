import domain.Member;
import domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class RunApplication {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Team team = new Team();
            team.setName("team1");
            em.persist(team);

            Member member = new Member();
            member.setName("member1");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            Member m = em.find(Member.class, member.getId());

            System.out.println("findMember -> " + m.getTeam().getClass());

            System.out.println("=============");
            m.getTeam().getName(); // 초기화
            System.out.println("=============");

            /* 즉시로딩의 문제점. */

            List<Member> memberList = em.createQuery("select m from Member m", Member.class).getResultList();
            // JPQL은 기본적으로 쿼리가 번역되기 때문에 ... 만약 여기서 조인 걸어서 팀까지 가져오면 상관 없음.
            // sql -> select * from Member -> 어? 팀이 없네. 가져와야되네?
            // sql 추가 -> select * from Team where team_id = member.team_id -> 멤버 개수 만큼 실행 됨.

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
