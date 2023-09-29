import jpql.Address;
import jpql.Member;
import jpql.MemberDTO;

import javax.persistence.*;
import java.util.List;

public class RunApplication {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(18);
            em.persist(member);

            em.flush();
            em.clear();


            List<Member> memberList = em.createQuery("select m from Member m", Member.class).getResultList();
            // 엔티티 프로젝션을 하면 결과들이 다 영속성으로 관리됨.
            // -> 변경하면 업데이트 쿼리를 날림.
            Member findMember = memberList.get(0);
            findMember.setAge(20);

            em.createQuery("select o.address from Order o", Address.class).getResultList();
            em.createQuery("select m.username, m.age from Member m", Member.class).getResultList();

            // 타입이 2개인데 어떻게 가져와야 할까?
            // 1. Object
            List list = em.createQuery("select m.username, m.age from Member m").getResultList();
            Object o = list.get(0);
            Object[] result = (Object[]) o;
            System.out.println("username -> " + result[0]);
            System.out.println("age -> " + result[1]);

            // 2.Object[]
            List<Object[]> list2 = em.createQuery("select m.username, m.age from Member m").getResultList();
            Object[] result2 = list2.get(0);
            System.out.println("username -> " + result2[0]);
            System.out.println("age -> " + result2[1]);

            // 3. new
            List<MemberDTO> memberDTOList = em.createQuery("select new jpql.MemberDTO( m.username, m.age ) from Member m").getResultList();
            MemberDTO memberDTO = memberDTOList.get(0);
            System.out.println("username -> " + memberDTO.getUsername());
            System.out.println("age -> " + memberDTO.getAge());




            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
