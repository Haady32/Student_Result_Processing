import java.io.*;
import java.util.*;

// Student class
class Student implements Serializable {

    private int rollNo;
    private String name;
    private int marks[];
    private double total;
    private double average;
    private char grade;

    // Constructor
    public Student(int rollNo, String name, int marks[]) {
        this.rollNo = rollNo;
        this.name = name;
        this.marks = marks;
        calculateResult();
    }

    // Calculate total, average and grade
    private void calculateResult() {
        total = 0;

        for (int i = 0; i < marks.length; i++) {
            total = total + marks[i];
        }

        average = total / 5.0;

        if (average >= 90)
            grade = 'A';
        else if (average >= 75)
            grade = 'B';
        else if (average >= 50)
            grade = 'C';
        else
            grade = 'F';
    }

    // Check failed
    public boolean isFailed() {
        return grade == 'F';
    }

    // Display method
    public void display() {
        System.out.println("Roll No : " + rollNo);
        System.out.println("Name    : " + name);
        System.out.println("Average : " + average);
        System.out.println("Grade   : " + grade);
        System.out.println("---------------------------");
    }

    // Getter methods (needed for writing output to file)
    public int getRollNo() { return rollNo; }
    public String getName() { return name; }
    public double getAverage() { return average; }
    public char getGrade() { return grade; }
}

// Main class
public class StudentSystem {

    static final String FILE_NAME = "records.dat";
    static Scanner sc = new Scanner(System.in);

    public static void main(String args[]) {

        ArrayList<Student> studentList = loadFromFile();
        int choice;

        do {
            System.out.println("\n===== Student Result System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. View Failed Students");
            System.out.println("4. Save Output to File");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    Student s = createStudent();
                    studentList.add(s);
                    saveToFile(studentList);
                    System.out.println("Student record saved successfully!");
                    break;

                case 2:
                    System.out.println("\n--- All Students ---");
                    for (Student st : studentList) {
                        st.display();
                    }
                    break;

                case 3:
                    System.out.println("\n--- Failed Students ---");
                    for (Student st : studentList) {
                        if (st.isFailed()) {
                            st.display();
                        }
                    }
                    break;

                case 4:
                    saveOutputToTextFile(studentList);
                    break;

                case 5:
                    System.out.println("Program Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);
    }

    // Create student
    public static Student createStudent() {

        System.out.print("Enter Roll Number: ");
        int roll = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        int marks[] = new int[5];

        for (int i = 0; i < 5; i++) {
            System.out.print("Enter marks for Subject " + (i + 1) + ": ");
            marks[i] = sc.nextInt();
        }

        return new Student(roll, name, marks);
    }

    // Save objects to binary file
    public static void saveToFile(ArrayList<Student> list) {
        try {
            FileOutputStream fos = new FileOutputStream(FILE_NAME);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(list);

            oos.close();
            fos.close();
        } catch (Exception e) {
            System.out.println("Error saving data.");
        }
    }

    // Load objects from file
    public static ArrayList<Student> loadFromFile() {

        ArrayList<Student> list = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);

            list = (ArrayList<Student>) ois.readObject();

            ois.close();
            fis.close();
        } catch (Exception e) {
            // If file not found, return empty list
        }

        return list;
    }

    // Save formatted output to text file
    public static void saveOutputToTextFile(ArrayList<Student> list) {

        try {
            FileWriter fw = new FileWriter("output.txt");
            PrintWriter pw = new PrintWriter(fw);

            pw.println("===== Student Report =====");

            for (Student s : list) {
                pw.println("Roll No : " + s.getRollNo());
                pw.println("Name    : " + s.getName());
                pw.println("Average : " + s.getAverage());
                pw.println("Grade   : " + s.getGrade());
                pw.println("---------------------------");
            }

            pw.close();
            fw.close();

            System.out.println("Output saved to output.txt successfully!");

        } catch (Exception e) {
            System.out.println("Error writing output file.");
        }
    }
}
