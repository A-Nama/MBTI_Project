import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;

public class MBTI{
    String questions[];
    int answers[][];
    String MBTI;
    double Scores[];
    String MBTI_details[];
    int progress;

    public void displayQuestions() {

        File file = new File("Questions.txt");
        try {
            // Create the file (if it doesn't exist)
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");}
            } catch(IOException e){
                System.out.println("An error occurred while creating the file: " + e.getMessage());
        }


        //copying questions to questions.txt
        try (FileWriter writer = new FileWriter("Questions.txt")) {

            writer.write("I feel energized after spending time with a large group of people\n");
            writer.write("In social settings, I typically initiate conversations and enjoy meeting new people");
            writer.write("When faced with a problem I prefer to ask help from others rather than self-introspection.");
            writer.write("I don’t find the idea of networking and making new connections daunting.\n");
            writer.write("I enjoy participating in team-based activities.\n");
            writer.write("When reading a book, I am immersed in the vivid descriptions and  details, feeling as though I am in the story.\n");
            writer.write("In a group project, I am a practical problem solver coming up with tangible solutions.\n");
            writer.write("I don’t prefer deep philosophical questions as ice-breakers.\n");
            writer.write("I don’t like to listen to baseless theories about secret societies, government coverups and alien invasions.\n");
            writer.write("I tend to find art museums interesting.\n");
            writer.write("I make decisions by weighing pros and cons objectively over personal feelings.\n");
            writer.write("I find it much easier to offer practical solutions than emotional support.\n");
            writer.write("I tend to follow my head rather than my heart.\n");
            writer.write("I don’t consider it my personal mission to help others achieve their goals.\n");
            writer.write("I believe relying more on rationality and less on feelings would improve the world.\n");
            writer.write("I don’t like using organizing tools and to do lists.\n");
            writer.write("I often end up doing things at the last possible moment.\n");
            writer.write("I find it challenging to maintain a consistent work or study schedule.\n");
            writer.write("If my plans are interrupted, I am unbothered about getting back on track\n");
            writer.write("My personal work style is closer to spontaneous bursts of energy than organized and consistent efforts.\n");


          } catch (IOException e) {
           System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }


        String filename = "Questions.txt";
        int row = 0;
        Scanner scanner = new Scanner(System.in);
        try (Scanner Filescanner = new Scanner(new File(filename))) {
            while (Filescanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
                System.out.println("Enter how much you agree with the statement. \n 1 - Completely Disagree \n 5 - Completely Agree");
                int answer = 0;
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
                    answers = new int[20][1];
                    answers[row][0] = answer;
                    row++;
                    progress =row * 100 / 20; // Update progress
                    System.out.println("Progress: " + progress + "%");
                }
                scanner.close();



        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }

        //Delete file after use
        if (file.delete()) {
            System.out.println("File deleted.");
        } else {
            System.out.println("Failed to delete the file.");
        }
    }


    public void display_details(){
        switch(MBTI){
            case "ENFJ":
            System.out.println(MBTI_details[0]);
            break;
            case "ENFP":
            System.out.println(MBTI_details[1]);
            break;
            case "INFJ":
            System.out.println(MBTI_details[2]);
            break;
            case "INFP":
            System.out.println(MBTI_details[3]);
            break;
            case "ENTJ":
            System.out.println(MBTI_details[4]);
            break;
            case "ENTP":
            System.out.println(MBTI_details[5]);
            break;
            case "INTJ":
            System.out.println(MBTI_details[6]);
            break;
            case "INTP":
            System.out.println(MBTI_details[7]);
            break;
            case "ESFP":
            System.out.println(MBTI_details[8]);
            break;
            case "ESTP":
            System.out.println(MBTI_details[9]);
            break;
            case "ISTP":
            System.out.println(MBTI_details[10]);
            break;
            case "ISFP":
            System.out.println(MBTI_details[11]);
            break;
            case "ESFJ":
            System.out.println(MBTI_details[12]);
            break;
            case "ESTJ":
            System.out.println(MBTI_details[13]);
            break;
            case "ISTJ":
            System.out.println(MBTI_details[14]);
            break;
            case "ISFJ":
            System.out.println(MBTI_details[15]);
            break;
            default:
            System.out.println("Invalid MBTI");
        }
    }

    void display_MBTI() {

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
    }

    class ProgressUpdater extends Thread {
        MBTI mbti; // Reference to the outer class

        ProgressUpdater(MBTI mbti) {
            this.mbti = mbti;
        }

        public void run() {
            while (mbti.progress < 100) {
                System.out.println("Progress: " + mbti.progress + "%");
                try {
                    Thread.sleep(1000); // Update every second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args){


        MBTI user = new MBTI();
        MBTI.ProgressUpdater progressUpdater = user.new ProgressUpdater(user); // Pass user to ProgressUpdater
        progressUpdater.start(); // Start the progress updating thread
        user.displayQuestions();
        user.display_MBTI();
        user.display_details();
    }


}


