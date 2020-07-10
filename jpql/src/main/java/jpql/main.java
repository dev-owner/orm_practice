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
            /*String query = "select distinct t from Team t join fetch t.members";
            List<Team> resultList = em.createQuery(query, Team.class).getResultList();
            for (Team team : resultList) {
                System.out.println("team.getName() = " + team.getName() + " | members = " + team.getMembers().size());
            }*/

            //4. Do not use entity alias when using fetch join
            /*String query = "select distinct t from Team t join fetch t.members m where m.username = 'member3'";
            List<Team> resultList = em.createQuery(query, Team.class).getResultList();
            for (Team team : resultList) {
                System.out.println("team.getName() = " + team.getName() + " | members = " + team.getMembers().get(0).getUsername());
            }*/

            //5. Cannot use collection fetch join but hibernate can and really dangerous. (stored in memory)
            // WARN: HHH000104: firstResult/maxResults specified with collection fetch; applying in memory!
            /*String query = "select t From Team t join fetch t.members m";
            List<Team> resultList = em.createQuery(query, Team.class)
                                    .setFirstResult(0)
                                    .setMaxResults(1)
                                    .getResultList();
            for (Team team : resultList) {
                System.out.println("team.getName() = " + team.getName() + " | members = " + team.getMembers().get(0).getUsername());
            }*/

            //5-1. workaround. change query
            /*String query = "select m from Member m join fetch m.team t";
            List<Member> resultList = em.createQuery(query, Member.class)
                    .setFirstResult(0)
                    .setMaxResults(1)
                    .getResultList();
            for (Member member : resultList) {
                System.out.println("team.getName() = " + member.getTeam().getName() + " | members = " + member.getUsername());
            }*/

            //5-2. workaround. no fetch join + lazy loading + batch size on OneToMany Column
            /*String query = "select t from Team t";
            List<Team> resultList = em.createQuery(query, Team.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            System.out.println("result = " + resultList.size());

            for (Team team : resultList) {
                System.out.println("team = " + team.getName() + "|members= " + team.getMembers().size());
                for (Member member : team.getMembers()) {
                    System.out.println("member = " + member);
                }
            }*/

            //6. Use entity directly in query
            /*String query = "select m from Member m where m = :member";
            Member member = em.createQuery(query, Member.class)
                    .setParameter("member", member1)
                    .getSingleResult();
            System.out.println("member = " + member.getUsername());*/


            //7. Named query
            /*List<Member> resultList = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "member1")
                    .getResultList();

            for (Member member : resultList) {
                System.out.println("member = " + member);
            }*/

            //8. bulk update
            int i = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();
            System.out.println("i = " + i);

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
