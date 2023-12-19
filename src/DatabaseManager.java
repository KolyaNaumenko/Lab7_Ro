import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/rolab7";
    private static final String USER = "root";
    private static final String PASSWORD = "05052004";

    public static List<Team> readTeamsFromDatabase() {
        List<Team> teams = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM team";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Team team = new Team();
                team.setId(resultSet.getInt("id"));
                team.setName(resultSet.getString("name"));
                team.setCountry(resultSet.getString("country"));
                readPlayersFromDatabase(team);

                teams.add(team);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teams;
    }

    public static void readPlayersFromDatabase(Team team) {
        List<Player> players = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM player WHERE teamid = ?")) {

            preparedStatement.setInt(1, team.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Player player = new Player();
                player.setId(resultSet.getInt("id"));
                player.setName(resultSet.getString("name"));
                player.setCountry(resultSet.getString("country"));
                player.setPosition(resultSet.getString("position"));

                players.add(player);
            }

            team.setPlayers(players);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void writeTeamsToDatabase(List<Team> teams) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DELETE FROM team");
            statement.executeUpdate("DELETE FROM player");

            for (Team team : teams) {
                String query = String.format("INSERT INTO team (id, name, country) VALUES (%d, '%s', '%s')",
                        team.getId(), team.getName(), team.getCountry());
                statement.executeUpdate(query);

                writePlayersToDatabase(team);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void writePlayersToDatabase(Team team) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            for (Player player : team.getPlayers()) {
                String query = String.format("INSERT INTO player (id, name, country, position, teamid) " +
                                "VALUES (%d, '%s', '%s', '%s', %d)",
                        player.getId(), player.getName(), player.getCountry(),
                        player.getPosition(), team.getId());
                statement.executeUpdate(query);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}