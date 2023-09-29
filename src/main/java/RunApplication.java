import jpql.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RunApplication {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Team team1 = new Team();
            team1.setName("teamA");
            em.persist(team1);

            Team team2 = new Team();
            team2.setName("teamB");
            em.persist(team2);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setAge(18);
            member1.setTeam(team1);
            member1.setMemberType(MemberType.ADMIN);
            em.persist(member1);
            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setAge(18);
            member2.setTeam(team1);
            member2.setMemberType(MemberType.ADMIN);
            em.persist(member2);
            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setAge(18);
            member3.setTeam(team2);
            member3.setMemberType(MemberType.ADMIN);
            em.persist(member3);

            em.flush();
            em.clear();

            // 기본 키를 사용 조회
            String jpql = "select m from Member m where m = :member";
            Member findMember = em.createQuery(jpql, Member.class)
                    .setParameter("member", member1)
                    .getSingleResult();

            System.out.println(findMember.toString());

            // 외래 키를 사용하여 조회
            String query = "select m from Member m where m.team = :team";

            Member findMember2 = em.createQuery(query, Member.class)
                    .setParameter("team", team2)
                    .getSingleResult();

            System.out.println(findMember2.toString());


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
