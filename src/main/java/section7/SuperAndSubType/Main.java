package section7.SuperAndSubType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            // 슈퍼/서브 타입이라 쿼리가 2번 나간다.
            Movie movie = new Movie();
            movie.setDirector("aaaa");
            movie.setActor("bbbb");
            movie.setName("바람과함께사라지다");
            movie.setPrice(10000);
            // 아이템 테이블과 무비 테이블에 해당 데이터가 들어가게 된다.
            em.persist(movie);

            em.flush();
            em.clear();

            Movie getMovie = em.find(Movie.class, movie.getId());
            System.out.println(getMovie);


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
