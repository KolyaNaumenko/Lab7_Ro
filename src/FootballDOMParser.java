import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FootballDOMParser {

    public static List<Team> parseXML(String filePath) {
        List<Team> teams = new ArrayList<>();

        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            NodeList teamList = doc.getElementsByTagName("Team");

            for (int temp = 0; temp < teamList.getLength(); temp++) {
                Node teamNode = teamList.item(temp);

                if (teamNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element teamElement = (Element) teamNode;

                    Team team = new Team();

                    team.setId(Integer.parseInt(teamElement.getElementsByTagName("Id").item(0).getTextContent()));
                    team.setName(teamElement.getElementsByTagName("Name").item(0).getTextContent());
                    team.setCountry(teamElement.getElementsByTagName("Country").item(0).getTextContent());

                    NodeList playerList = teamElement.getElementsByTagName("Player");
                    List<Player> players = new ArrayList<>();

                    for (int i = 0; i < playerList.getLength(); i++) {
                        Node playerNode = playerList.item(i);

                        if (playerNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element playerElement = (Element) playerNode;

                            Player player = new Player();

                            player.setId(Integer.parseInt(playerElement.getElementsByTagName("Id").item(0).getTextContent()));
                            player.setName(playerElement.getElementsByTagName("Name").item(0).getTextContent());
                            player.setCountry(playerElement.getElementsByTagName("Country").item(0).getTextContent());
                            player.setPosition(playerElement.getElementsByTagName("Position").item(0).getTextContent());

                            players.add(player);
                        }
                    }

                    team.setPlayers(players);
                    teams.add(team);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return teams;
    }
}