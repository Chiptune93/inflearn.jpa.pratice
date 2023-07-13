package hellojpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class JpaSection4 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            /* -------------------------------------------- */
            // EnumType.ORDINAL 로 사용하게 되면, enum의 열거형 숫자가 저장되게 되므로 STRING 으로 사용하여야 함

            // 만약 중간에 순서가 바뀌게 되면 기존 값들에 영향을 끼침
            // 예를 들어 USER,ADMIN 이면 0,1이 저장되는데
            // GUEST가 USER 앞에 추가되어 GUEST,USER, ADMIN 이게 되면 0,1,2 가 된다.
            // 새로 생성된 객체에서는 바뀐 값이 업데이트 되지만, 기존 값은 그대로라 중복이 발생한다.
            Member member = new Member();
            member.setId(4L);
            member.setUsername("test");
            member.setRoleType(RoleType.USER);

            Member member2 = new Member();
            member2.setId(4L);
            member2.setUsername("test");
            member2.setRoleType(RoleType.GUEST);

            em.persist(member);
            em.persist(member2);

            /* -------------------------------------------- */

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
