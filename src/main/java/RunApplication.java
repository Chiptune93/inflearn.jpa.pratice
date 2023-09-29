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

            Team team1 = new Team();
            team1.setName("teamA");
            em.persist(team1);

            Team team2 = new Team();
            team2.setName("teamB");
            em.persist(team2);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setAge(18);
            member1.setTeam(team1);
            member1.setMemberType(MemberType.ADMIN);
            em.persist(member1);
            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setAge(18);
            member2.setTeam(team1);
            member2.setMemberType(MemberType.ADMIN);
            em.persist(member2);
            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setAge(18);
            member3.setTeam(team2);
            member3.setMemberType(MemberType.ADMIN);
            em.persist(member3);

            em.flush();
            em.clear();

//            String query2 = "select t from Team t join fetch t.memberList m";
//            // 경고 처리 -> 조인 페치 시, 페이징 쓰면 안됨.
//            List<Team> memberList2 = em.createQuery(query2, Team.class)
//                    .setFirstResult(0)
//                    .setMaxResults(2)
//                    .getResultList();

            String query3 = "select t from Team t";

            List<Team> memberList3 = em.createQuery(query3, Team.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            for (Team t : memberList3) {
                System.out.println(t.toString());
                for(Member member : t.getMemberList()) {
                    // DB에서 결과 조회 한 만큼 가져옴.
                    // 레이지로딩이라 쿼리가 3번이나 나감. (연관 팀 2개 조회 -> 다 가져옴)
                    System.out.println("member -> " + member);
//
//                    Hibernate:
//                    select
//                    memberlist0_.TEAM_ID as TEAM_ID5_0_0_,
//                            memberlist0_.id as id1_0_0_,
//                    memberlist0_.id as id1_0_1_,
//                            memberlist0_.age as age2_0_1_,
//                    memberlist0_.memberType as memberTy3_0_1_,
//                            memberlist0_.TEAM_ID as TEAM_ID5_0_1_,
//                    memberlist0_.username as username4_0_1_
//                            from
//                    Member memberlist0_
//                    where
//                    memberlist0_.TEAM_ID=?
//                    member -> Member{id=3, username='회원1', age=18}
//                    member -> Member{id=4, username='회원2', age=18}
//                    Team{id=2, name='teamB'}
//                    Hibernate:
//                    select
//                    memberlist0_.TEAM_ID as TEAM_ID5_0_0_,
//                            memberlist0_.id as id1_0_0_,
//                    memberlist0_.id as id1_0_1_,
//                            memberlist0_.age as age2_0_1_,
//                    memberlist0_.memberType as memberTy3_0_1_,
//                            memberlist0_.TEAM_ID as TEAM_ID5_0_1_,
//                    memberlist0_.username as username4_0_1_
//                            from
//                    Member memberlist0_
//                    where
//                    memberlist0_.TEAM_ID=?
//                    member -> Member{id=5, username='회원3', age=18}
                }
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
