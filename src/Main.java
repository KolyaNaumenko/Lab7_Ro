import java.util.List;

public class Main {

    public static void main(String[] args) {
        //String filePath = "src/football.xml";
        //List<Team> teams = FootballDOMParser.parseXML(filePath);

        List<Team> teams = DatabaseManager.readTeamsFromDatabase();

        print(teams);

        teams.get(1).setName("Barcelona");

        teams.get(0).getPlayers().add(new Player());
        teams.get(0).getPlayers().get(2).setId(3);
        teams.get(0).getPlayers().get(2).setName("Sterling");
        teams.get(0).getPlayers().get(2).setCountry("England");
        teams.get(0).getPlayers().get(2).setPosition("RW");

        teams.get(1).getPlayers().remove(1);

        print(teams);

        //FootballXMLWriter.writeXML(teams, filePath);

        DatabaseManager.writeTeamsToDatabase(teams);
    }

    public static void print(List<Team> teams){
        for (Team team : teams) {
            System.out.println("Team ID: " + team.getId());
            System.out.println("Team Name: " + team.getName());
            System.out.println("Team Country: " + team.getCountry());

            System.out.println("Players:");

            for (Player player : team.getPlayers()) {
                System.out.println("\tPlayer ID: " + player.getId());
                System.out.println("\tPlayer Name: " + player.getName());
                System.out.println("\tPlayer Country: " + player.getCountry());
                System.out.println("\tPlayer Position: " + player.getPosition());
                System.out.println("\t-----------");
            }

            System.out.println("---------------");
        }
    }
}