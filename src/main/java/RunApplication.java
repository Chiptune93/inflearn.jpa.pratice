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

            // subquery
            String query1 = "select (select avg(m1.age) From Member m1) as avg from Member m join Team t on m.username = t.name";

            // from 절 서브쿼리 -> 지원안됨.
            String query2 = "select mm.age, mm.username from (select m.age, m.username from Member m) as mm";

//            하이버네이트6 변경 사항
//            하이버네이트6 부터는 FROM 절의 서브쿼리를 지원합니다.
//            참고 링크
//            https://in.relation.to/2022/06/24/hibernate-orm-61- features/


            List<Member> result = em.createQuery(query2, Member.class)
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
