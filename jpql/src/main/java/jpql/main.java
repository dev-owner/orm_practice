package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            Team team1 = new Team();
            team1.setName("team1");
            em.persist(team1);

            Team team2 = new Team();
            team2.setName("team2");
            em.persist(team2);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.changeTeam(team1);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.changeTeam(team1);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("member3");
            member3.changeTeam(team2);
            em.persist(member3);

            em.flush();
            em.clear();


            //1. join fetch N:1
            /*String query = "select m from Member m join fetch m.team";
            List<Member> resultList = em.createQuery(query, Member.class).getResultList();

            for (Member member : resultList) {
                System.out.println("member = " + member.getUsername() + ", " + member.getTeam().getName());
            }*/

            //2. join fetch 1:N, Collection type
            /*String query = "select t from Team t join fetch t.members";
            List<Team> resultList = em.createQuery(query, Team.class).getResultList();
            for (Team team : resultList) {
                System.out.println("team.getName() = " + team.getName() + " | members = " + team.getMembers().size());
            }*/

            //3. join fetch with distinct
            // remove duplicate entities from resultList
            String query = "select distinct t from Team t join fetch t.members";
            List<Team> resultList = em.createQuery(query, Team.class).getResultList();
            for (Team team : resultList) {
                System.out.println("team.getName() = " + team.getName() + " | members = " + team.getMembers().size());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
