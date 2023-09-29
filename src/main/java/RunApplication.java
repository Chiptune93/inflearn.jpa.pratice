import jpql.*;

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
            member.setMemberType(MemberType.ADMIN);
            em.persist(member);


            em.flush();
            em.clear();

            String query = "select " +
                    "case when m.age <= 10 then '학생요금'" +
                    "when m.age>=50 then '경로요금'" +
                    "else '일반요금' " +
                    "end " +
                    "from Member m";

            String query2 = "select colesce(m.username,'이름없는회원') as username from Member m";
            String query3 = "select nullif(m.username,'관리자') as username from Member m";

            List<String> result = em.createQuery(query3, String.class)
                    .getResultList();

            for (String s : result) {
                System.out.println("s = " + s);
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
