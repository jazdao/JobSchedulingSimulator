import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.io.IOException;

public class RandomJobGenerator {

	public static void main(String[] args) {

		// Create new job.txt file if none exists
		try {
			File file = new File("job.txt");
			if (file.createNewFile()) {
				System.out.println("\nFile created.");
			}
			else {
				System.out.println("\nFile already exists.");
				return;
			}
		}
		catch (IOException e) {
			System.out.println("\nError: operation failed.");
			e.printStackTrace();
		}

		// Ask user for number of jobs 
		Scanner sc = new Scanner(System.in);
		System.out.println("\nHow many jobs would you like to generate?");

		int input = sc.nextInt();

		// Generate random numbers
		ArrayList<Integer> jobList = new ArrayList<Integer>();
		Random rand = new Random();

		for (int i = 0; i < input; i++) {
			jobList.add(rand.nextInt(31));
		}

		// Write numbers to file
		try {
			FileWriter writer = new FileWriter("job.txt");

			for (int i = 0; i < input; i++) {
				writer.write("Job" + (i + 1) + "\n");
				writer.write(jobList.get(i) + "\n");
			}

			writer.close();
			System.out.println("\nSuccessfully wrote to the file.");
		}
		catch (IOException e) {
			System.out.println("\nError: operation failed");
			e.printStackTrace();
		}
	}
}