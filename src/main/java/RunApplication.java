import org.hibernate.Hibernate;

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

            /* em.getReference */

            // 기본적인 조회
            // Member findMember = em.find(Member.class, 1L);
            // em.getReference
            Member findMember = em.getReference(Member.class, member.getId());
            // 여기서 findmember를 찍어보면 "class Member$HibernateProxy$DRUTZbVi" 라고 나온다.
            // 프록시에서 생성한 객체이기 때문에.
            System.out.println("findMember => " + findMember.getClass());
            System.out.println("findMember => " + findMember.getId()); // 여기서는 위에서 생성한 객체의 아이디니까 알 수 있음.
            System.out.println("findMember => " + findMember.getName()); // 네임은 어디서 가져왔나? 조회해서 .. 쿼리가 날라감
            // printMemberAndTeam(member);

            em.flush();
            em.clear();

            /* object 비교 */
            Member member1 = new Member();
            member1.setName("1");
            em.persist(member1);
            Member member2 = new Member();
            member2.setName("2");
            em.persist(member2);

            em.flush();
            em.clear();

            Member m1 = em.find(Member.class, member1.getId());
            Member m2 = em.getReference(Member.class, member2.getId());

            System.out.println("m1 == m2 ? -> " + (m1 == m2));
            System.out.println("m1 == m2 ? -> " + (m1 instanceof Member));

            em.flush();
            em.clear();

            /* 준영속 상태에서 조회 */
            Member refMember = em.getReference(Member.class, member1.getId());
            // 영속성 초기화 하지 않음.
            Member findMember2 = em.find(Member.class, member1.getId());

            // 같다고 나옴.
            System.out.println("ref == find ? -> " + (refMember == findMember2));

            em.flush();
            em.clear();

            /* 준영속 상태에서의 프록시 초기화 */
            Member member3 = new Member();
            member3.setName("test");
            em.persist(member3);

            em.flush();
            em.clear();

            Member refMember2 = em.getReference(Member.class, member3.getId());
            System.out.println("ref -> " + refMember2.getClass());

            // em.detach(refMember2);
            // em.close(); // -> 결과 동일
            // em.clear(); // -> 결과 동일

            // refMember2.getName();

            // 에러발생
            // org.hibernate.LazyInitializationException: could not initialize proxy [Member#4] - no Session
            // at org.hibernate.proxy.AbstractLazyInitializer.initialize(AbstractLazyInitializer.java:169)
            // at org.hibernate.proxy.AbstractLazyInitializer.getImplementation(AbstractLazyInitializer.java:309)
            // at org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor.intercept(ByteBuddyInterceptor.java:45)
            // at org.hibernate.proxy.ProxyConfiguration$InterceptorDispatcher.intercept(ProxyConfiguration.java:95)
            // at Member$HibernateProxy$fwnyhGHJ.getName(Unknown Source)
            // at RunApplication.main(RunApplication.java:82)
            // Aug 31, 2023 6:11:05 PM org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl stop
            // INFO: HHH10001008: Cleaning up connection pool [jdbc:h2:tcp://localhost/~/section8]


            /* 유틸리티 메소드 */

            // 초기화 여부 확인
            refMember2.getName(); // 있으면 초기화 됨, 없으면 안됨.
            System.out.println(emf.getPersistenceUnitUtil().isLoaded(refMember2));

            // 클래스 확인
            System.out.println(refMember2.getClass().getName());

            // 프록시 강제 초기화
            Hibernate.initialize(refMember2);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
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
