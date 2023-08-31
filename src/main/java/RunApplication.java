import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class RunApplication {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Member member = new Member();
            member.setName("hello jpa proxy");

            em.persist(member);
            em.flush();
            em.clear();

            // 다시 시작.

            // 기본적인 조회
//            Member findMember = em.find(Member.class, 1L);
            // em.getReference
            Member findMember = em.getReference(Member.class, member.getId());
            // 여기서 findmember를 찍어보면 "class Member$HibernateProxy$DRUTZbVi" 라고 나온다.
            // 프록시에서 생성한 객체이기 때문에.
            System.out.println("findMember => " + findMember.getClass());
            System.out.println("findMember => " + findMember.getId()); // 여기서는 위에서 생성한 객체의 아이디니까 알 수 있음.
            System.out.println("findMember => " + findMember.getName()); // 네임은 어디서 가져왔나? 조회해서 .. 쿼리가 날라감





            // printMemberAndTeam(member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    public static void printMember(Member member) {
        System.out.println("member = " + member.getName());
    }

    public static void printMemberAndTeam(Member member) {
        String username = member.getName();
        System.out.println("username = " + username);

        Team team = member.getTeam();
        System.out.println("team = " + team);
    }
}
