import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileCopy {
    public static void main(String[] args) {
        String inputFileName = "./input.txt";
        String outputFileName = "./output.txt";

        try (FileReader fileReader = new FileReader(inputFileName);
             FileWriter fileWriter = new FileWriter(outputFileName)) {
            int character;
            while ((character = fileReader.read()) != -1) {
                fileWriter.write(character);
            }
            System.out.println("Contents copied character by character.");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName));
             FileWriter fileWriter = new FileWriter(outputFileName, true)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileWriter.write(line + System.lineSeparator());
            }
            System.out.println("Contents copied line by line.");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
