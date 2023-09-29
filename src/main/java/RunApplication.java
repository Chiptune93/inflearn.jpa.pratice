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

            // enum type class
            // String query = "select m.username, 'HELLO', true From Member m where m.memberType = jpql.MemberType.ADMIN";
            String query = "select m.username, 'HELLO', true From Member m where m.memberType = :userType";
            // List<Object[]> result = em.createQuery(query).getResultList();
            List<Object[]> result = em.createQuery(query)
                    .setParameter("userType", MemberType.ADMIN)
                    .getResultList();

            for (Object[] objects : result) {
                System.out.println("objects = " + objects[0]);
                System.out.println("objects = " + objects[1]);
                System.out.println("objects = " + objects[2]);
            }

            // 아래와 같이 타입 형변환을 할 수 있음.
            // Book이라는 객체는 아이템 상속 받은 객체라서.
            // em.createQuery("select i from item i where type(i) = Book ", Item.class)



            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
