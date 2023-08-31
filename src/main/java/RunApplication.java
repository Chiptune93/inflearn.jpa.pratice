import domain.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static domain.DeliveryStatus.*;
import static domain.OrderStatus.*;

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
            for (int i = 1; i < 20; i++) {
                Book book = new Book();
                book.setName("book" + i);
                book.setAuthor("author" + i);
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

            for (int i = 1; i < 10; i++) {
                Member member = new Member();
                member.setName("member" + i);
                member.setId(Integer.toUnsignedLong(i));
                member.setCity("seoul");
                member.setStreet(i + " GIL");
                member.setZipcode(i + " ZIP");
                member.setCreatedDateTime(LocalDateTime.now());
                em.persist(member);
            }

            for (int i = 1; i < 10; i++) {
                Orders order = new Orders();
                order.setId(Integer.toUnsignedLong(i));
                order.setOrderDate(LocalDateTime.now());
                order.setOrderStatus(WAIT);
                order.setOrderDate(LocalDateTime.now());
                order.setCreatedDateTime(LocalDateTime.now());
                em.persist(order);
            }

            for (int i = 1; i < 10; i++) {
                OrdersItems ordersItems = new OrdersItems();
                ordersItems.setId(Integer.toUnsignedLong(i));
                ordersItems.setOrderPrice(i * 1000);
                ordersItems.setCount(i * 10);
                ordersItems.setCreatedDateTime(LocalDateTime.now());
                em.persist(ordersItems);
            }

            for (int i = 1; i < 10; i++) {
                Delivery delivery = new Delivery();
                delivery.setId(Integer.toUnsignedLong(i));
                delivery.setCreatedDateTime(LocalDateTime.now());
                delivery.setStatus(READY);
                em.persist(delivery);
            }

            em.flush();
            em.clear();

            for (int i = 1; i < 10; i++) {
                Member member = em.find(Member.class, Integer.toUnsignedLong(i));
                Orders orders = em.find(Orders.class, Integer.toUnsignedLong(i));
                orders.setMember(member);
                OrdersItems ordersItems = em.find(OrdersItems.class, Integer.toUnsignedLong(i));
                Items items;
                if (randomItems() == 1L) {
                    items = em.createQuery("select a from Album a", Album.class).setMaxResults(1).getSingleResult();
                } else if (randomItems() == 2L) {
                    items = em.createQuery("select a from Book a", Book.class).setMaxResults(1).getSingleResult();
                } else {
                    items = em.createQuery("select a from Movie a", Movie.class).setMaxResults(1).getSingleResult();
                }
                System.out.println("items -> " + items);
                ordersItems.setItems(items);
                Delivery delivery = em.find(Delivery.class, Integer.toUnsignedLong(i));
                delivery.setCity(member.getCity());
                delivery.setStreet(member.getStreet());
                delivery.setZipcode(member.getZipcode());
                orders.setOrdersItemsList(Collections.singletonList(ordersItems));
                orders.setDelivery(delivery);
            }

            List<Member> members = em.createQuery("select a from Member a", Member.class).getResultList();
            System.out.println("size : " + members.size());
            for (Member m : members) {
                System.out.println("getName : " + m.getName());
            }

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    static long randomItems() {
        Random rand = new Random();
        return rand.nextInt((3 - 1) + 1) + 1;
    }

    static long randomItemsNumber() {
        Random rand = new Random();
        return rand.nextInt((19) + 1);
    }
}
