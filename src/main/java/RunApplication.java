import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class RunApplication {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Categorys categorys = new Categorys();
            categorys.setName("Parent");
            categorys.setCreatedDateTime(LocalDateTime.now());
            em.persist(categorys);

            Categorys categorys2 = new Categorys();
            categorys2.setParent(categorys);
            categorys2.setName("SubCategory1");
            categorys2.setCreatedDateTime(LocalDateTime.now());
            em.persist(categorys2);

            Categorys categorys3 = new Categorys();
            categorys3.setParent(categorys);
            categorys3.setName("SubCategory2");
            categorys3.setCreatedDateTime(LocalDateTime.now());
            em.persist(categorys3);
            /**
             * ITEM
             */
            for (int i = 0; i < 50; i++) {
                Book book = new Book();
                book.setName("Book" + i);
                book.setAuthor("Author" + i);
                book.setIsbn(i);
                book.setCreatedDateTime(LocalDateTime.now());

                Album album = new Album();
                album.setName("album" + i);
                album.setArtist("artist" + i);
                album.setEtc("etc" + i);
                album.setCreatedDateTime(LocalDateTime.now());

                Movie movie = new Movie();
                movie.setName("movie" + i);
                movie.setDirector("director" + i);
                movie.setActor("actor" + i);
                movie.setCreatedDateTime(LocalDateTime.now());

                em.persist(book);
                em.persist(album);
                em.persist(movie);
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
