import jpql.Member;

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

            // 리턴 타입을 받을 수 있음
            TypedQuery<Member> memberTypedQuery = em.createQuery("select m from Member m",Member.class);
            TypedQuery<String> memberTypedQuery2 = em.createQuery("select m.username from Member m",String.class);
            // 리턴 타입이 명확하지 않을 때는 그냥 쿼리 객체를 이용해 결과 받음
            Query emQuery = em.createQuery("select m.username, m.age from Member m",String.class);

            // 리스트로 받기 - 여러개 또는 하나 있어야 함
            TypedQuery<Member> memberTypedQuery3 = em.createQuery("select m from Member m",Member.class);
            List<Member> memberList = memberTypedQuery3.getResultList();

            // 싱글 객체로 받기 - 무조건 결과가 하나가 나와야 함
            TypedQuery<Member> memberTypedQuery4 = em.createQuery("select m from Member m where m.id = 10L",Member.class);
            Member member1 = memberTypedQuery4.getSingleResult();
            // Spring Data Jpa -> 결과가 없어도 익셉션 안 나옴. (스프링이 트라이 캐치 한 번 해줌)

            // 파라미터 바인딩
//            TypedQuery<Member> memberTypedQuery5 = em.createQuery("select m from Member m where m.username = :username",Member.class);
//            memberTypedQuery5.setParameter("username","member1");
            TypedQuery<Member> memberTypedQuery5 = em.createQuery("select m from Member m where m.username = :username",Member.class).setParameter("username","member1");
            Member singleResult = memberTypedQuery5.getSingleResult();
            System.out.println("singleResult = " + singleResult.getUsername());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
