import java.util.Scanner;
public class MBTI{
    String questions[];
    int answers[][];
    String MBTI;
    double Scores[];
    String MBTI_details[];
    public void displayQuestions() {
        int row = 0; 
        Scanner scanner = new Scanner(System.in); 
        for (int i = 0; i < 20; i++) {
            System.out.println(questions[i]);
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

            answers[row][i] = answer;

            if ((i + 1) % 5 == 0) {
                row++;
            }
        }
        scanner.close(); 
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
    
    public double RowAvg(int row) {
        int sum = 0;
        if (row >= 0 && row < 5) {
            for (int j = 0; j < 5; j++) {
                sum += answers[row][j];
            }
        }
        return ((double)sum/5);
    }

    public void calc_Score(){
        int j = 0;
        for (int i = 0; i < 5; i++) {
            Scores[j++] = RowAvg(i);
        }
    }
}
