/**
* Date: 6/23/20
* Name: Jasmine Dao
* Course: CS 4310 Project
* Professor: Gilbert Young
* Description: Operating System Job Scheduling Simulator with four possible algorithms-
*              First-Come-First-Serve, Shortest-Job-First, and Round-Robin with time slices 2 and 5
**/

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class JobSchedulingSimulator {
	public static void main(String[] args) {

		// Read starting burst values in the job.txt file to an array
		ArrayList<String> jobs = new ArrayList<String>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader("job.txt"));

			String line;
			int currentLine = 1;
			while ((line = reader.readLine()) != null) {
				if (currentLine % 2 == 0) {
					jobs.add(line);
				}
				currentLine++;
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found.");
		} 
		catch (IOException i) {
			System.out.println("File is empty.");
		}


		// Receive user input for which scheduling algorithm to output
		Scanner sc = new Scanner(System.in);
		System.out.println("\nWhich of the following scheduling algorithms would you like to simulate?");
		System.out.println("1. First-Come-First-Serve (FCFS)");
		System.out.println("2. Shortest-Job-First (SJF)");
		System.out.println("3. Round-Robin with Time Slice = 2 (RR-2)");
		System.out.println("4. Round-Robin with Time Slice = 5 (RR-5)\n");

		String selected = sc.nextLine();
		System.out.println();

		while (!selected.equals("1") && !selected.equals("2") && !selected.equals("3") && !selected.equals("4")) {
			System.out.println("Please enter a number corresponding to one of the four choices: ");
			selected = sc.nextLine();
			System.out.println();
		}


		// Format table and give output depending on selected algorithm
		String leftAlignFormat = "| %-8d | %-10d | %-10d | %-10s | %-22s |%n";

		System.out.format("+----------+------------+------------+------------+------------------------+%n");
		System.out.format("| Job #    | Start Time | End Time   | Burst Time | Job Completion         |%n");
		System.out.format("+----------+------------+------------+------------+------------------------+%n");


		// First-Come-First-Serve (FCFS)
		ArrayList<Integer> endTimes = new ArrayList<Integer>();
		int ttFCFS = 0;
		float avg = 0;

		if (selected.equals("1")) {
			for (int i = 0; i < jobs.size(); i++) {
				ttFCFS += Integer.parseInt(jobs.get(i)); 
				endTimes.add(ttFCFS);
				System.out.format(leftAlignFormat, (i + 1), 0, ttFCFS, jobs.get(i), "Job " + (i + 1) + " completed @" + ttFCFS);
				System.out.format("+----------+------------+------------+------------+------------------------+%n");		
			}

			// Calculate average turnaround time
			for (int i = 0; i < endTimes.size(); i++) {
				avg = avg + endTimes.get(i);
			}

			avg = avg / jobs.size();
			System.out.println("\nAverage Turnaround Time: " + avg);
		}
		

		// Shortest-Job-First (SJF)
		int[] jobsList = new int[30];
		int ttSJF = 0;
		int min, minIndex;


		if (selected.equals("2")) {

			// Convert burst values to integers and store in an array
			for (int i = 0; i < jobs.size(); i++) {
				jobsList[i] = Integer.parseInt(jobs.get(i));
			}

			// Order the burst values from least to greatest and calculate the completion times for each job
			for (int i = 0; i < jobs.size(); i++) {
				min = 0;
				minIndex = 0;

				for (int j = 0; j < jobs.size(); j++) {
					if (j == 0) min = jobsList[j];
					if (jobsList[j] < min) {
						min = jobsList[j];
						minIndex = j;
					} 
				}

				ttSJF += min;
				endTimes.add(ttSJF);

				System.out.format(leftAlignFormat, (minIndex + 1), 0, ttSJF, jobs.get(minIndex), "Job " + (minIndex + 1) + " completed @" + ttSJF);
				System.out.format("+----------+------------+------------+------------+------------------------+%n");

				jobsList[minIndex] = 999;
			}

			// Calculate average turnaround time
			for (int i = 0; i < endTimes.size(); i++) {
				avg = avg + endTimes.get(i);
			}

			avg = avg / jobs.size();
			System.out.println("\nAverage Turnaround Time: " + avg);
		}


		// Round-Robin with Time Slice = 2 (RR-2)
		int ttRR2 = 0;
		int completed = 0;
		ArrayList<Integer> skipIndices = new ArrayList<Integer>();

		if (selected.equals("3")) {

			// Convert burst values to integers and store in an array
			for (int i = 0; i < jobs.size(); i++) {
				jobsList[i] = Integer.parseInt(jobs.get(i));
			}

			// Loop until all the jobs are completed
			while (completed < jobs.size()) {
				for (int i = 0; i < jobs.size(); i++) {
					jobsList[i] = jobsList[i] - 2;

					if (jobsList[i] > 0) {
						ttRR2 += 2;

						System.out.format(leftAlignFormat, (i + 1), (ttRR2 - 2), ttRR2, jobsList[i], " ");
						System.out.format("+----------+------------+------------+------------+------------------------+%n");
					}

					else if (jobsList[i] == 0) {
						if (skipIndices.contains(i)) {
							continue;
						}

						ttRR2 += 2;
						endTimes.add(ttRR2);
						completed++;
						skipIndices.add(i);

						System.out.format(leftAlignFormat, (i + 1), (ttRR2 - 2), ttRR2, jobsList[i], "Job " + (i + 1) + " completed @" + ttRR2);
						System.out.format("+----------+------------+------------+------------+------------------------+%n");
					}

					else if (jobsList[i] < 0) {
						if (skipIndices.contains(i)) {
							continue;
						}

						jobsList[i] = jobsList[i] + 1;
						ttRR2 += 1;
						endTimes.add(ttRR2);
						completed++;
						skipIndices.add(i);

						System.out.format(leftAlignFormat, (i + 1), (ttRR2 - 1), ttRR2, jobsList[i], "Job " + (i + 1) + " completed @" + ttRR2);
						System.out.format("+----------+------------+------------+------------+------------------------+%n");
					}
				}
			}

			// Calculate average turnaround time
			for (int i = 0; i < endTimes.size(); i++) {
				avg = avg + endTimes.get(i);
			}

			avg = avg / jobs.size();
			System.out.println("\nAverage Turnaround Time: " + avg);
		}


		// Round-Robin with Time Slice = 5 (RR-5)
		int ttRR5 = 0;
		int remainder = 0;

		if (selected.equals("4")) {
			
			// Convert burst values to integers and store in an array
			for (int i = 0; i < jobs.size(); i++) {
				jobsList[i] = Integer.parseInt(jobs.get(i));
			}

			// Loop until all the jobs are completed
			while (completed < jobs.size()) {
				for (int i = 0; i < jobs.size(); i++) {
					jobsList[i] = jobsList[i] - 5;

					if (jobsList[i] > 0) {
						ttRR5 += 5;

						System.out.format(leftAlignFormat, (i + 1), (ttRR5 - 5), ttRR5, jobsList[i], " ");
						System.out.format("+----------+------------+------------+------------+------------------------+%n");
					}

					else if (jobsList[i] == 0) {
						if (skipIndices.contains(i)) {
							continue;
						}

						ttRR5 += 5;
						endTimes.add(ttRR5);
						completed++;
						skipIndices.add(i);

						System.out.format(leftAlignFormat, (i + 1), (ttRR5 - 5), ttRR5, jobsList[i], "Job " + (i + 1) + " completed @" + ttRR5);
						System.out.format("+----------+------------+------------+------------+------------------------+%n");
					}

					else if (jobsList[i] < 0) {
						if (skipIndices.contains(i)) {
							continue;
						}

						remainder = Math.abs(jobsList[i]);

						jobsList[i] = jobsList[i] + remainder;
						ttRR5 += 5 - remainder; 
						endTimes.add(ttRR5);
						completed++;
						skipIndices.add(i);

						System.out.format(leftAlignFormat, (i + 1), (ttRR5 - (5 - remainder)), ttRR5, jobsList[i], "Job " + (i + 1) + " completed @" + ttRR5);
						System.out.format("+----------+------------+------------+------------+------------------------+%n");
					}
				}
			}

			// Calculate average turnaround time
			for (int i = 0; i < endTimes.size(); i++) {
				avg = avg + endTimes.get(i);
			}

			avg = avg / jobs.size();
			System.out.println("\nAverage Turnaround Time: " + avg);
		}
	}
}