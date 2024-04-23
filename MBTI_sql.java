import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MBTI_sql {
    String questions[];
    int answers[][];
    String MBTI;
    double Scores[] = new double[4];

    public void displayQuestions() {

        File file = new File("Questions.txt");
        try {
            // Create the file (if it doesn't exist)
            if (file.createNewFile()) {
                System.out.println(" ");
            } else {
                System.out.println(" ");}
            } catch(IOException e){
                System.out.println("An error occurred while creating the file: " + e.getMessage());
        }


        //copying questions to questions.txt
        try (FileWriter writer = new FileWriter("Questions.txt")) {

            writer.write("I feel energized after spending time with a large group of people\n");
            writer.write("In social settings, I typically initiate conversations and enjoy meeting new people\n");
            writer.write("When faced with a problem I prefer to ask help from others rather than self-introspection.\n");
            writer.write("I do not find the idea of networking and making new connections daunting.\n");
            writer.write("I enjoy participating in team-based activities.\n");
            writer.write("When reading a book, I am immersed in the vivid descriptions and  details, feeling as though I am in the story.\n");
            writer.write("In a group project, I am a practical problem solver coming up with tangible solutions.\n");
            writer.write("I do not prefer deep philosophical questions as ice-breakers.\n");
            writer.write("I do not like to listen to baseless theories about secret societies, government coverups and alien invasions.\n");
            writer.write("I tend to find art museums interesting.\n");
            writer.write("I make decisions by weighing pros and cons objectively over personal feelings.\n");
            writer.write("I find it much easier to offer practical solutions than emotional support.\n");
            writer.write("I tend to follow my head rather than my heart.\n");
            writer.write("I do not consider it my personal mission to help others achieve their goals.\n");
            writer.write("I believe relying more on rationality and less on feelings would improve the world.\n");
            writer.write("I do not like using organizing tools and to do lists.\n");
            writer.write("I often end up doing things at the last possible moment.\n");
            writer.write("I find it challenging to maintain a consistent work or study schedule.\n");
            writer.write("If my plans are interrupted, I am unbothered about getting back on track\n");
            writer.write("My personal work style is closer to spontaneous bursts of energy than organized and consistent efforts.\n");

          } catch (IOException e) {
           System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }


        String filename = "Questions.txt";
        Scanner Filescanner = null;
        try{
        Filescanner = new Scanner(new File(filename));
        System.out.println("Enter how much you agree with the statements displayed. \n 1 - Completely Disagree \n 5 - Completely Agree");
        answers = new int[4][5];
        Scanner scanner = new Scanner(System.in);
        int questionIndex = 0;
            while (Filescanner.hasNextLine()) {
                String line = Filescanner.nextLine();
                if (!line.trim().isEmpty()) {
                    System.out.println(line);
                int answer;
                while (true) {
                    try {
                       answer = scanner.nextInt();
                           if (answer < 1 || answer > 5) {
                              System.out.println("Invalid input. Please enter a number between 1 and 5.");
                           } else {
                             break;
                           }
                        } catch (Exception e) {
                             System.out.println("Invalid input. Please enter a number between 1 and 5.");
                             scanner.next();
                        }
                    }
                    answers[questionIndex/5][questionIndex%5] = answer;
                    questionIndex++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        } finally{
            if (Filescanner != null) {
                Filescanner.close();
            }
        }

        //Delete file after use
        if (file.delete()) {
            System.out.println("");
        } else {
            System.out.println("");
        }
    }
    
    // Function to retrieve MBTI details from the database
    public static void display_details(String MBTIResult) {
            String url = "jdbc:mysql://localhost:3306/MBTI";
            String user = "root";
            String password = "Whalien34";
            String query = "SELECT * FROM DETAILS WHERE MBTI = ?";
    
            try (Connection con = DriverManager.getConnection(url, user, password);
                 PreparedStatement pst = con.prepareStatement(query)) {
    
                // Set parameter for MBTI
                pst.setString(1, MBTIResult);
    
                // Execute query
                ResultSet rs = pst.executeQuery();
    
                // Process results
                while (rs.next()) {
                    String mbti = rs.getString("MBTI");
                    String details = rs.getString("DESCRIPTION");
                    String strengths = rs.getString("STRENGTHS");
                    String weakness = rs.getString("WEAKNESS");
                    System.out.println("MBTI: " + mbti + "\n Description: " + details + "\n Strengths: "+ strengths + "\n Weakness: " + weakness);
                }
    
            } catch (SQLException e) {
                System.out.println("Error fetching MBTI details: " + e.getMessage());
            }
    }

    void display_MBTI() {
        int sum, k = 0;
        double avg;
        for(int i = 0; i<4; i++){
            sum = 0;
            for(int j = 0; j<5; j++){
                sum += answers[i][j];
            }
            avg = sum / 5;
            Scores[k++] = avg;
        }

        StringBuilder result = new StringBuilder();
        // Energy score
        if (Scores[0] > 2.5) {
            result.append("E");
        } else {
            result.append("I");
        }

        // Sensing score
        if (Scores[1] > 2.5) {
            result.append("N");
        } else {
            result.append("S");
        }

        // Thinking score
        if (Scores[2] > 2.5) {
            result.append("T");
        } else {
            result.append("F");
        }

        // Judging score
        if (Scores[3] > 2.5) {
            result.append("P");
        } else {
            result.append("J");
        }

        System.out.println("Your personality type is " + result);

        // Getting MBTI details
        display_details(result.toString());
        
        Scanner scanner = null;
        try{
        // Ask user for permission to store their result
        scanner = new Scanner(System.in);
        System.out.println("Do you want to save your MBTI result? (yes/no)");
        String permission = scanner.nextLine().toLowerCase();
        if (permission.equals("yes")) {
            saveToDatabase(result.toString());
        }
    } finally{
        if (scanner != null) {
            scanner.close();
        }
    }
}

    // Function to save MBTI result to database
    void saveToDatabase(String MBTIResult) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your name:");
        String name = scanner.nextLine();

        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://localhost:3306/MBTI";
        String user = "root";
        String password = "Whalien34";

        // SQL query to insert data into database
        String query = "INSERT INTO STUDENTRESULTS (name, MBTI) VALUES (?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, name);
            pst.setString(2, MBTIResult);

            pst.executeUpdate();
            System.out.println("MBTI result saved successfully!");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }

    // Main function
    public static void main(String[] args) {
        MBTI_sql user = new MBTI_sql();
        user.displayQuestions();
        user.display_MBTI();
    }
}
