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

            // 엔티티 페치 조인
            String query = "select m from Member m";
            String joinFetchQuery = "select m From Member m join fetch m.team";
            List<Member> memberList = em.createQuery(joinFetchQuery, Member.class).getResultList();

            for (Member s : memberList) {
                System.out.println(s.toString() + " Team -> " + s.getTeam().getName());
                // 팀을 가져와야 하는 쿼리를 처음에 날리고 이후에는 캐싱되어 쿼리 안날리게 됨.
                // 회원 1 -> 팀A (SQL)
                // 회원 2 -> 팀A (1차 캐시)
                // 회원 3 -> 팀B (SQL)
                // 회원 4 -> 팀B (1차 캐시)
//                Hibernate:
//                select
//                team0_.id as id1_3_0_,
//                        team0_.name as name2_3_0_
//                from
//                Team team0_
//                where
//                team0_.id=?
//                Member{id=3, username='회원1', age=18} Team -> teamA
//                Member{id=4, username='회원2', age=18} Team -> teamA
//                Hibernate:
//                select
//                team0_.id as id1_3_0_,
//                        team0_.name as name2_3_0_
//                from
//                Team team0_
//                where
//                team0_.id=?
//                Member{id=5, username='회원3', age=18} Team -> teamB
//                Member{id=6, username='회원4', age=18} Team -> teamB

                // 조인 패치를 사용하는 경우 로그 -> 팀 가져오는게 프록시가 아님. 실제 데이터를 조회함.
                //    Hibernate:
                //    /* select
                //        m
                //    From
                //        Member m
                //    join
                //        fetch m.team */ select
                //                member0_.id as id1_0_0_,
                //                        team1_.id as id1_3_1_,
                //                member0_.age as age2_0_0_,
                //                        member0_.memberType as memberTy3_0_0_,
                //                member0_.TEAM_ID as TEAM_ID5_0_0_,
                //                        member0_.username as username4_0_0_,
                //                team1_.name as name2_3_1_
                //                        from
                //                Member member0_
                //                inner join
                //                Team team1_
                //                on member0_.TEAM_ID=team1_.id
                //                Member{id=3, username='회원1', age=18} Team -> teamA
                //                Member{id=4, username='회원2', age=18} Team -> teamA
                //                Member{id=5, username='회원3', age=18} Team -> teamB
                //                Member{id=6, username='회원4', age=18} Team -> teamB
            }

            // 컬레션 페치 조인
            String query2 = "select t from Team t join fetch t.memberList where t.name = 'teamA'";
            List<Team> memberList2 = em.createQuery(query2, Team.class).getResultList();

            for (Team t : memberList2) {
                System.out.println(t.toString());
                for(Member member : t.getMemberList()) {
                    // DB에서 결과 조회 한 만큼 가져옴.
                    System.out.println("member -> " + member);
                }
            }

            // distinct
            String query3 = "select distinct t from Team t join fetch t.memberList where t.name = 'teamA'";
            List<Team> memberList3 = em.createQuery(query3, Team.class).getResultList();

            for (Team t : memberList3) {
                System.out.println(t.toString());
                for(Member member : t.getMemberList()) {
                    // DB에서 결과 조회 한 만큼 가져옴.
                    System.out.println("member -> " + member);
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
