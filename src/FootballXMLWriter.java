import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class FootballXMLWriter {

    public static void writeXML(List<Team> teams, String filePath) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("Football");
            rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttribute("xsi:noNamespaceSchemaLocation", "football.xsd");
            doc.appendChild(rootElement);

            for (Team team : teams) {
                Element teamElement = doc.createElement("Team");
                rootElement.appendChild(teamElement);

                Element idElement = doc.createElement("Id");
                idElement.appendChild(doc.createTextNode(String.valueOf(team.getId())));
                teamElement.appendChild(idElement);

                Element nameElement = doc.createElement("Name");
                nameElement.appendChild(doc.createTextNode(team.getName()));
                teamElement.appendChild(nameElement);

                Element countryElement = doc.createElement("Country");
                countryElement.appendChild(doc.createTextNode(team.getCountry()));
                teamElement.appendChild(countryElement);

                for (Player player : team.getPlayers()) {
                    Element playerElement = doc.createElement("Player");
                    teamElement.appendChild(playerElement);

                    Element playerIdElement = doc.createElement("Id");
                    playerIdElement.appendChild(doc.createTextNode(String.valueOf(player.getId())));
                    playerElement.appendChild(playerIdElement);

                    Element playerNameElement = doc.createElement("Name");
                    playerNameElement.appendChild(doc.createTextNode(player.getName()));
                    playerElement.appendChild(playerNameElement);

                    Element playerCountryElement = doc.createElement("Country");
                    playerCountryElement.appendChild(doc.createTextNode(player.getCountry()));
                    playerElement.appendChild(playerCountryElement);

                    Element playerPositionElement = doc.createElement("Position");
                    playerPositionElement.appendChild(doc.createTextNode(player.getPosition()));
                    playerElement.appendChild(playerPositionElement);
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));

            transformer.transform(source, result);

            System.out.println("XML successfully written.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}