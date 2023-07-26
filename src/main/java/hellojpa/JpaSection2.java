package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaSection2 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            Member member = new Member();
//            member.setId(2);
//            member.setName("hello2");
//
//            em.persist(member);

            List<MemberOld> result = em.createQuery("select m from MemberOfTeam as m", MemberOld.class)
                    .setFirstResult(1)
                    .setMaxResults(8)
                    .getResultList();

            for(MemberOld memberOld : result) {
                System.out.println("Member Name is " + memberOld.getUsername());
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
