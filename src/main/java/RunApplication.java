import domain.Child;
import domain.Member;
import domain.Parent;
import domain.Team;

import javax.persistence.*;
import java.util.List;

public class RunApplication {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);
//            em.persist(child1);
//            em.persist(child2);

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildList().remove(0);

            // 모두 다 사용할 때,
            // @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
            // 자식의 생명주기를 부모 객체가 관리한다.
            // Child child1 = new Child();
            // Child child2 = new Child();
            //
            // Parent parent = new Parent();
            // parent.addChild(child1);
            // parent.addChild(child2);
            //
            // em.persist(parent);
            //
            // em.flush();
            // em.clear();
            //
            // Parent findParent = em.find(Parent.class, parent.getId());
            // em.remove(findParent);



            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
