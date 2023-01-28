import java.sql.*;
import java.io.*;
import java.util.*;
public class Main {

    public static Scanner sc = new Scanner(System.in);

    public static void SchoolAvg(String jdbcURL, String username, String password) // 1
    {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet rs = null;

        Connection connection = null;


        // Try block to catch exception/s
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            // SQL command data stored in String datatype
            String select = "select AVG(grade_avg) from highschool";
            p = connection.prepareStatement(select);
            rs = p.executeQuery();

            while (rs.next()) {
                double avg = rs.getDouble("AVG(grade_avg)");
                System.out.println("School average = "+avg);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
    }

    public static void BoysAvg(String jdbcURL, String username, String password)// 2
    {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet rs = null;

        Connection connection = null;


        // Try block to catch exception/s
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            // SQL command data stored in String datatype
            String select = "select AVG(grade_avg) from highschool " +
                    "where gender = \"Male\"";
            p = connection.prepareStatement(select);
            rs = p.executeQuery();

            // Condition check
            while (rs.next()) {
                double avg = rs.getDouble("AVG(grade_avg)");
                System.out.println("Boys average = "+avg);
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public static void GirlsAvg(String jdbcURL, String username, String password)// 3
    {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet rs = null;

        Connection connection = null;


        // Try block to catch exception/s
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            // SQL command data stored in String datatype
            String select = "select AVG(grade_avg) from highschool " +
                    "where gender = \"Female\"";
            p = connection.prepareStatement(select);
            rs = p.executeQuery();

            // Condition check
            while (rs.next()) {
                double avg = rs.getDouble("AVG(grade_avg)");
                System.out.println("Girls average = "+avg);
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public static void TallPurpleBoysAvg(String jdbcURL, String username, String password)// 4
    {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet rs = null;

        Connection connection = null;


        // Try block to catch exception/s
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            // SQL command data stored in String datatype
            String select = "select AVG(cm_height) from highschool " +
                    "where cm_height >= 200 group by car_color "+
                    "having car_color = \"Purple\"";
            p = connection.prepareStatement(select);
            rs = p.executeQuery();

            // Condition check
            while (rs.next()) {
                double avg = rs.getDouble("AVG(cm_height)");
                System.out.println("Average height of those equal or above 200 cm that have a purple car = "+avg);
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public static void friendCircles(int stInd, String jdbcURL, String username, String password)// 5
    {
		Scanner scanner = new Scanner(System.in);

        System.out.println("Please Enter Student id:\t");
        int id = scanner.nextInt();

        int friends = 0;

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            // SQL command data stored in String datatype
            String select = "select friend_id, other_friend_id from highschool_friendships where friend_id = "+id+" or other_friend_id = "+id;

            PreparedStatement statement = connection.prepareStatement(select);
            ResultSet resultSet = statement.executeQuery();

            // Condition check
            while (resultSet.next()) {
                int friend_id = resultSet.getInt("friend_id");
                int other_id = resultSet.getInt("other_friend_id");

                if(id == friend_id && friend_id != 0 && other_id != id)
                    friends = other_id;
                else if (id != 0 && id != id)
                    friends = id;

                if(friends != 0)
                {
                    System.out.println(friends);
                    String select2 = "select friend_id, other_friend_id from highschool_friendships " +
                            "where friend_id = " + friends + " or other_friend_id = " + friends;
                    PreparedStatement innerP = connection.prepareStatement(select2);
                    ResultSet innerRes = innerP.executeQuery();

                    // Condition check
                    while (innerRes.next()) {
                        int id3 = innerRes.getInt("friend_id");
                        int id4 = innerRes.getInt("other_friend_id");
                        if(id3 == friends && id4 != 0 && id4 != id) {
                            System.out.println(id4);
                        }
                        else if(id3 != 0 && id3 != id && id3 != friends) System.out.println(id3);
                    }
                }
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public static void studRatings(String jdbcURL, String username, String password)// 6
    {
		int popular = 0;
        int unpopular = 0;
        int normal = 0;
        int[] friendships = new int[1001];

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        String query = "SELECT friend_id, other_friend_id FROM highschool_friendships WHERE friend_id != NULL AND other_friend_id != NULL";

        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            int friend1 = resultSet.getInt("friend_id");
            int friend2 = resultSet.getInt("other_friend_id");

            friendships[friend1]++;
            friendships[friend2]++;

            while (resultSet.next())
            {
                friend1 = resultSet.getInt("friend_id");
                friend2 = resultSet.getInt("other_friend_id");

                friendships[friend1]++;
                friendships[friend2]++;
            }

            for(int i = 1; i <1001; i++)
            {
                if(friendships[i] == 0)
                    unpopular++;
                else if(friendships[i] == 1)
                    normal++;
                else
                    popular++;
            }

            // Divide by 10 instead of dividing by 1000 and multiplying by 100 for percent
            System.out.println("Percent of popular students: " + (float)(popular/10));
            System.out.println("Percent of unpopular students: " + (float)(unpopular/10));
            System.out.println("Percent of normal students: " + (float)(normal/10));

        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
    }

    public static void selectAvg(int id_card, String jdbcURL, String username, String password)// 7
    {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet rs = null;

        Connection connection = null;


        // Try block to catch exception/s
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            // SQL command data stored in String datatype
            String select = "select identification_card, grade_avg from highschool";
            p = connection.prepareStatement(select);
            rs = p.executeQuery();

            //System.out.println("identification_card\t\taverage");


            // Condition check
            while (rs.next()) {

                int id = rs.getInt("identification_card");
                double avg = rs.getDouble("grade_avg");
                if (id == id_card)
                    System.out.println(avg);

                //System.out.println(id + "\t\t" + avg);
            }
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public static void Menu(String jdbcURL, String username, String password)
    {
        int indexNum;
        System.out.println("Hello Sima! How u doing? I Hope your day is neat! Enter Option Number plz:");
        indexNum = sc.nextInt();

        while(indexNum != 8)
        {
            switch(indexNum)
            {
                case 1:
                    SchoolAvg(jdbcURL, username, password);
                    break;
                case 2:
                    BoysAvg(jdbcURL, username, password);
                    break;
                case 3:
                    GirlsAvg(jdbcURL, username, password);
                    break;
                case 4:
                    TallPurpleBoysAvg(jdbcURL, username, password);
                    break;
                case 5:
                    System.out.println("Enter ID:");
                    int stID;
                    stID = sc.nextInt();
                    friendCircles(stID, jdbcURL, username, password);
                    break;
                case 6:
                    studRatings(jdbcURL, username, password);
                    break;
                case 7:
                    System.out.println("Enter identification card:");
                    int id_card;
                    id_card = sc.nextInt();
                    selectAvg(id_card, jdbcURL, username, password);
                    break;
                default:
                    break;
            }
            System.out.println("Enter again plz:");
            indexNum = sc.nextInt();
        }
    }

    public static void insertValuesToFriendships(String jdbcURL, String username, String password, String csvFilePath)
    {


        int other_friend_id;
        int friend_id;

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);

            String sql = "INSERT INTO highschool_friendships (friend_id, other_friend_id) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");    // Change variables and data type

                //int id = Integer.parseInt(data[0]);

                if(data.length < 2 || data[1].equals(""))
                {
                    statement.setNull(1, Types.INTEGER);
                }
                else
                {
                    friend_id = Integer.parseInt(data[1]);
                    statement.setInt(1, friend_id);
                }

                if(data.length < 3)
                {
                    statement.setNull(2, Types.INTEGER);
                }
                else
                {
                    other_friend_id = Integer.parseInt(data[2]);
                    statement.setInt(2, other_friend_id);
                }

                //statement.setInt(1, id);



                //statement.setString(4, comment);

                statement.addBatch();

                //if (count % batchSize == 0) {
                statement.executeBatch();
                //}
            }

            lineReader.close();

            // execute the remaining queries
            statement.executeBatch();

            connection.commit();

            connection.close();

        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void insertValuesToHighschool(String jdbcURL, String username, String password, String csvFilePath)
    {

        String first_name,last_name, email, gender, ip_address, has_car, car_color;
        int cm_height, age, grade,  identification_card;
        double grade_avg;

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);

            String sql = "INSERT INTO highschool " +
                    "(first_name,last_name,email,gender,ip_address,cm_height,age,has_car,car_color,grade,grade_avg,identification_card) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;


            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null)
            {
                String[] data = lineText.split(",");    // Change variables and data type

                first_name = data[1];
                last_name = data[2];
                email = data[3];
                gender = data[4];
                ip_address = data[5];
                cm_height = Integer.parseInt(data[6]);
                age = Integer.parseInt(data[7]);
                has_car = data[8];
                car_color = data[9];
                grade = Integer.parseInt(data[10]);
                grade_avg = Double.parseDouble(data[11]);
                identification_card = Integer.parseInt(data[12]);

                statement.setString(1, first_name);
                statement.setString(2, last_name);
                statement.setString(3, email);
                statement.setString(4, gender);
                statement.setString(5, ip_address);
                statement.setInt(6, cm_height);
                statement.setInt(7, age);
                statement.setString(8, has_car);
                if (has_car.equals("false")){
                    statement.setNull(9, Types.VARCHAR);
                }
                else
                {
                    statement.setString(9, car_color);
                }

                statement.setInt(10, grade);
                statement.setDouble(11, grade_avg);
                statement.setInt(12, identification_card);





                //statement.setString(4, comment);

                statement.addBatch();

                //if (count % batchSize == 0) {
                statement.executeBatch();
                //}
            }

            lineReader.close();

            // execute the remaining queries
            statement.executeBatch();

            connection.commit();
            connection.close();

        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {

        String jdbcURL = "jdbc:mysql://localhost:3306/sima";
        String username = "gefenoga";
        String password = "Qwerty1231!";

        String csvFilePath1 = "highschool_friendships.csv";  //change
        String csvFilePath2 = "highschool.csv";  //change

        //insertValuesToFriendships(jdbcURL, username, password, csvFilePath1);
        //insertValuesToHighschool(jdbcURL, username, password, csvFilePath2);
        //selectAvg(jdbcURL, username, password);
        Menu(jdbcURL, username, password);

    }
}