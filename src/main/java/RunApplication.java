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

            String query1 = "select 'a' || 'b' from Member m"; // concat(a,b) 가능.
            String query2 = "select substring('asd',0,1) from Member m";
            String query3 = "select trim(m.username) from Member m";
            String query4 = "select upper(m.username) from Member m";
            String query5 = "select locate('de','asdsdfg') from Member m";
            String query6 = "select length(m.username) from Member m";
            String query7 = "select size(t.memberList) From Team t"; // 컬렉션에 대한 크기 리턴
            String query8 = "select index(t.memberList) From Team t"; // 값 타입 컬렉션 위치 값을 구할 때 사용

            // 사용자 정의 함수 호출
            String query9 = "select function('group_concat',m.username) From Member m";


            em.createQuery(query9);


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
