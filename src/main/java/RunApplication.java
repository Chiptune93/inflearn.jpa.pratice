import jpql.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class RunApplication {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Book book = new Book();
            book.setAuthor("BOOK");
            book.setName("book1");
            book.setPrice(1000);
            em.persist(book);

            Movie movie = new Movie();
            movie.setActor("actor");
            movie.setName("movie1");
            movie.setPrice(2000);
            movie.setDirector("123");
            em.persist(movie);

            String query = "select i from Items i where type(i) IN (Book, Movie)";
            List<Items> items = em.createQuery(query, Items.class).getResultList();

            for (Items i : items) {
                System.out.println(i.toString());
            }


            em.flush();
            em.clear();


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
