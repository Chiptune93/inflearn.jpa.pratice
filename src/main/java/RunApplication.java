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

            Movie movie = new Movie();
            movie.setDirector("aaaa");
            movie.setActor("bbbb");
            movie.setName("바람과함께사라지다");
            movie.setPrice(10000);
            // 아이템 테이블과 무비 테이블에 해당 데이터가 들어가게 된다.
            em.persist(movie);

            em.flush();
            em.clear();

            /**
             * 이렇게 조회할 수 있지만 단점이 존재, TablePerClass 에서는
             * 조회하기 위해 여기에 묶여있는 테이블 전체를 Union all 해서 찾게 된다 -> 비효율적이다.
             */
            Item item = em.find(Item.class, movie.getId());
            System.out.println(item);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
