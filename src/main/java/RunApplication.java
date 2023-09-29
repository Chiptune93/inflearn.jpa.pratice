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

            // String query = "select m.username from Member m";

            // 묵시적 내부 조인 발생 -> 이게 발생하도록 짜면 안됨.
            String query2 = "select m.team from Member m";
            // 멤버와 연관된 팀을 가져오겠다. -> 팀도 조인해야지 데이터베이스에서 SQL로 가져올 수 있음.

            // 컬렉션 값 연관 경로 -> 묵시적 내부 조인 발생 -> 더 이상 탐색 불가.
            // 컬렉션에서 어떤걸 가져와야할 지 모르기 때문에 ...
            String query3 = "select t.memberList from Team t";

            // 이런식으로 명시적 조인을 해서 가져와야 함.
            String query4 = "select m.username from Team t join t.memberList m";

            Collection result = em.createQuery(query4, Collection.class).getResultList();

            for (Object o : result) {
                System.out.println("s => " + o);
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
