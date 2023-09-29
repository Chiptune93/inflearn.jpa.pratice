import jpql.Address;
import jpql.Member;
import jpql.MemberDTO;
import jpql.Team;

import javax.persistence.*;
import java.util.List;

public class RunApplication {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("memberA");
            member.setAge(18);
            member.setTeam(team);
            em.persist(member);


            em.flush();
            em.clear();

            // inner join
            String query1 = "select m from Member m inner join m.team t";
            // left join
            String query2 = "select m from Member m left join m.team t";
            // setter join
            String query3 = "select m from Member m, Team t Where m.username = t.name";

            // join on 절
            String query4 = "select m from Member m left join m.team t on t.name = 'teamA'";

            // 연관관계 조인
            String query5 = "select m from Member m left join Team t on m.username = t.name";



            List<Member> result = em.createQuery(query5, Member.class)
                    .getResultList();

            System.out.println("result = " + result.size());
            for (Member m : result) {
                System.out.println("Member -> " + m.toString());
            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
