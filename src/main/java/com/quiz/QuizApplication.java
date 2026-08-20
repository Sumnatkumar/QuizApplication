package com.quiz;

import com.quiz.exception.QuizDataException;
import com.quiz.service.QuizService;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main application entry point
 */


public class QuizApplication {

	private static final Logger logger = Logger.getLogger(QuizApplication.class.getName());
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("╔" + "═".repeat(58) + "╗");
		System.out.println("║" + " ".repeat(15) + "JAVA QUIZ APPLICATION" + " ".repeat(17) + "║");
		System.out.println("╚" + "═".repeat(58) + "╝");

		QuizService quizService = new QuizService();

		try {
			// Initialize quiz
			System.out.println("\n📚 Loading quiz questions...");
			quizService.initializeQuiz();
			System.out.println("✅ Quiz loaded successfully with " +
					quizService.getQuestions().size() + " questions!");

			// Display menu
			boolean exit = false;
			while (!exit) {
				displayMenu();
				int choice = getMenuChoice();

				switch (choice) {
					case 1:
						quizService.startQuiz();
						break;
					case 2:
						displayInstructions();
						break;
					case 3:
						System.out.println("\n👋 Thank you for using Quiz Application!");
						System.out.println("Have a great day! 🌟");
						exit = true;
						break;
					default:
						System.out.println("⚠️ Invalid choice. Please try again.");
				}
			}

		} catch (QuizDataException e) {
			logger.log(Level.SEVERE, "Quiz initialization failed", e);
			System.err.println("❌ Error: " + e.getMessage());
			System.err.println("Please restart the application.");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Unexpected error", e);
			System.err.println("❌ An unexpected error occurred: " + e.getMessage());
		} finally {
			scanner.close();
		}
	}

	/**
	 * Display main menu
	 */
	private static void displayMenu() {
		System.out.println("\n" + "─".repeat(50));
		System.out.println("📋 MAIN MENU");
		System.out.println("─".repeat(50));
		System.out.println("1. 🎯 Start Quiz");
		System.out.println("2. 📖 Instructions");
		System.out.println("3. 🚪 Exit");
		System.out.println("─".repeat(50));
		System.out.print("Choose an option (1-3): ");
	}

	/**
	 * Get menu choice with validation
	 */
	private static int getMenuChoice() {
		while (true) {
			try {
				int choice = scanner.nextInt();
				scanner.nextLine(); // consume newline
				if (choice >= 1 && choice <= 3) {
					return choice;
				} else {
					System.out.print("Please enter a number between 1 and 3: ");
				}
			} catch (InputMismatchException e) {
				System.out.print("Invalid input. Please enter a number: ");
				scanner.nextLine(); // clear invalid input
			}
		}
	}

	/**
	 * Display instructions
	 */
	private static void displayInstructions() {
		System.out.println("\n" + "=".repeat(60));
		System.out.println("📖 QUIZ INSTRUCTIONS");
		System.out.println("=".repeat(60));
		System.out.println("1. The quiz contains multiple choice questions about Java");
		System.out.println("2. Each question has 4 options (A, B, C, D)");
		System.out.println("3. Select your answer by typing A, B, C, or D");
		System.out.println("4. Each correct answer gives you 1 point");
		System.out.println("5. There is no negative marking");
		System.out.println("6. You can track your score during the quiz");
		System.out.println("7. After completion, you'll see detailed results");
		System.out.println("8. The quiz covers:");
		System.out.println("   - Core Java Concepts");
		System.out.println("   - Collections Framework");
		System.out.println("   - Exception Handling");
		System.out.println("\n💡 Tips:");
		System.out.println("   • Read each question carefully");
		System.out.println("   • Take your time - there's no time limit");
		System.out.println("   • Review the results to identify areas for improvement");
		System.out.println("=".repeat(60));
		System.out.println("\nPress Enter to continue...");
		scanner.nextLine();
	}

}
