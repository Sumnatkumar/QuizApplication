package com.quiz.service;

import com.quiz.exception.InvalidOptionException;
import com.quiz.exception.QuizDataException;
import com.quiz.model.Question;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Main service class handling quiz logic
 */

public class QuizService {
    private static final Logger logger = Logger.getLogger(QuizService.class.getName());

    private List<Question> questions;
    private int currentIndex;
    private int score;
    private int totalQuestions;
    private Scanner scanner;
    private List<Integer> userAnswers;
    private long startTime;
    private String quizTitle;

    // Constructor
    public QuizService() {
        this.questions = new ArrayList<>();
        this.currentIndex = 0;
        this.score = 0;
        this.totalQuestions = 0;
        this.scanner = new Scanner(System.in);
        this.userAnswers = new ArrayList<>();
        this.quizTitle = "Java Quiz Challenge";
    }

    /**
     * Initialize quiz with questions
     */
    public void initializeQuiz() throws QuizDataException {
        try {
            // Java Core Questions
            questions.add(new Question(1,
                    "What is the size of int variable in Java?",
                    new String[]{"4 bytes", "8 bytes", "2 bytes", "Depends on platform"},
                    0, "Core Java", 1));

            questions.add(new Question(2,
                    "Which of these is NOT a primitive data type in Java?",
                    new String[]{"int", "boolean", "float", "String"},
                    3, "Core Java", 1));

            questions.add(new Question(3,
                    "What is the default value of a boolean variable in Java?",
                    new String[]{"true", "false", "null", "0"},
                    1, "Core Java", 1));

            questions.add(new Question(4,
                    "Which collection allows duplicate elements?",
                    new String[]{"HashSet", "TreeSet", "ArrayList", "HashMap"},
                    2, "Collections", 2));

            questions.add(new Question(5,
                    "What is the superclass of all exceptions in Java?",
                    new String[]{"Error", "Throwable", "Exception", "RuntimeException"},
                    1, "Exception Handling", 2));

            questions.add(new Question(6,
                    "Which keyword is used to implement inheritance in Java?",
                    new String[]{"extends", "implements", "inherits", "super"},
                    0, "Core Java", 1));

            questions.add(new Question(7,
                    "What is the time complexity of HashMap get() operation?",
                    new String[]{"O(1)", "O(n)", "O(log n)", "O(n²)"},
                    0, "Collections", 3));

            questions.add(new Question(8,
                    "Which class handles checked exceptions?",
                    new String[]{"RuntimeException", "IOException", "NullPointerException",
                            "ArithmeticException"},
                    1, "Exception Handling", 2));

            questions.add(new Question(9,
                    "What is the purpose of finally block?",
                    new String[]{"To handle exceptions", "To catch exceptions",
                            "To execute code regardless of exception", "To throw exceptions"},
                    2, "Exception Handling", 1));

            questions.add(new Question(10,
                    "Which interface provides the largest number of utility methods?",
                    new String[]{"List", "Set", "Map", "Collection"},
                    3, "Collections", 2));

            this.totalQuestions = questions.size();

            // Shuffle questions for variety
            Collections.shuffle(questions);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error initializing quiz", e);
            throw new QuizDataException("Failed to initialize quiz data: " + e.getMessage());
        }
    }

    /**
     * Start the quiz
     */
    public void startQuiz() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🎯 WELCOME TO " + quizTitle);
        System.out.println("=".repeat(60));
        System.out.println("Total Questions: " + totalQuestions);
        System.out.println("You need to select the correct option (A/B/C/D)");
        System.out.println("Press Enter to continue...");
        scanner.nextLine();

        startTime = System.currentTimeMillis();
        currentIndex = 0;
        score = 0;
        userAnswers.clear();

        while (currentIndex < totalQuestions) {
            try {
                Question currentQuestion = questions.get(currentIndex);
                displayQuestionWithOptions(currentQuestion);
                int userAnswer = getUserAnswer();
                userAnswers.add(userAnswer);

                if (currentQuestion.isCorrect(userAnswer)) {
                    score++;
                    System.out.println("✅ Correct! +1 point");
                } else {
                    System.out.println("❌ Wrong! Correct answer was: " +
                            (char)('A' + currentQuestion.getCorrectAnswer()));
                }

                currentIndex++;

                if (currentIndex < totalQuestions) {
                    System.out.println("\nPress Enter for next question...");
                    scanner.nextLine();
                }

            } catch (InvalidOptionException e) {
                System.out.println("⚠️ " + e.getMessage());
                // Allow retry for same question
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Please enter a valid option (A/B/C/D)");
                scanner.nextLine(); // Clear invalid input
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error during quiz", e);
                System.out.println("An error occurred. Please try again.");
            }
        }

        showResult();
    }

    /**
     * Display question with options
     */
    private void displayQuestionWithOptions(Question question) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Question " + (currentIndex + 1) + " of " + totalQuestions);
        question.display();
        System.out.println("Score: " + score + "/" + currentIndex);
    }

    /**
     * Get user's answer with validation
     */
    private int getUserAnswer() throws InvalidOptionException {
        System.out.print("Your answer (A/B/C/D): ");
        String input = scanner.nextLine().trim().toUpperCase();

        if (input.isEmpty()) {
            throw new InvalidOptionException("Please enter an option (A/B/C/D)");
        }

        char choice = input.charAt(0);

        // Map A=0, B=1, C=2, D=3
        switch (choice) {
            case 'A': return 0;
            case 'B': return 1;
            case 'C': return 2;
            case 'D': return 3;
            default: throw new InvalidOptionException("Invalid option: " + choice +
                    ". Please choose A, B, C, or D");
        }
    }

    /**
     * Show final results
     */
    public void showResult() {
        long endTime = System.currentTimeMillis();
        long timeTaken = (endTime - startTime) / 1000; // in seconds

        System.out.println("\n" + "=".repeat(60));
        System.out.println("🏆 QUIZ COMPLETED! 🏆");
        System.out.println("=".repeat(60));

        // Calculate percentage
        double percentage = ((double) score / totalQuestions) * 100;

        System.out.println("📊 Final Score: " + score + "/" + totalQuestions);
        System.out.println("📈 Percentage: " + String.format("%.2f", percentage) + "%");
        System.out.println("⏱️ Time Taken: " + timeTaken + " seconds");

        // Performance analysis
        System.out.println("\n📝 Performance Analysis:");
        System.out.println("-".repeat(60));
        analyzePerformance();

        // Grade based on performance
        System.out.println("\n🎓 Grade: " + getGrade(percentage));
        System.out.println("💡 " + getFeedback(percentage));

        // Show detailed review
        System.out.println("\n📋 Detailed Review:");
        System.out.println("-".repeat(60));
        for (int i = 0; i < totalQuestions; i++) {
            Question q = questions.get(i);
            int userAns = userAnswers.get(i);
            boolean correct = q.isCorrect(userAns);
            System.out.println("Q" + (i+1) + ": " +
                    (correct ? "✅" : "❌") +
                    " Your answer: " + (char)('A' + userAns) +
                    " | Correct: " + (char)('A' + q.getCorrectAnswer()));
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("Thank you for taking the quiz! 🎉");
    }

    /**
     * Analyze performance by category and difficulty
     */
    private void analyzePerformance() {
        Map<String, Integer> categoryCorrect = new HashMap<>();
        Map<String, Integer> categoryTotal = new HashMap<>();
        Map<Integer, Integer> difficultyCorrect = new HashMap<>();
        Map<Integer, Integer> difficultyTotal = new HashMap<>();

        for (int i = 0; i < totalQuestions; i++) {
            Question q = questions.get(i);
            String category = q.getCategory();
            int difficulty = q.getDifficulty();
            boolean correct = q.isCorrect(userAnswers.get(i));

            categoryTotal.put(category, categoryTotal.getOrDefault(category, 0) + 1);
            if (correct) {
                categoryCorrect.put(category, categoryCorrect.getOrDefault(category, 0) + 1);
            }

            difficultyTotal.put(difficulty, difficultyTotal.getOrDefault(difficulty, 0) + 1);
            if (correct) {
                difficultyCorrect.put(difficulty, difficultyCorrect.getOrDefault(difficulty, 0) + 1);
            }
        }

        System.out.println("\nCategory-wise Performance:");
        for (String category : categoryTotal.keySet()) {
            int correct = categoryCorrect.getOrDefault(category, 0);
            int total = categoryTotal.get(category);
            double pct = (total > 0) ? ((double) correct / total) * 100 : 0;
            System.out.println("  " + category + ": " + correct + "/" + total +
                    " (" + String.format("%.1f", pct) + "%)");
        }

        System.out.println("\nDifficulty-wise Performance:");
        for (int diff : difficultyTotal.keySet()) {
            int correct = difficultyCorrect.getOrDefault(diff, 0);
            int total = difficultyTotal.get(diff);
            double pct = (total > 0) ? ((double) correct / total) * 100 : 0;
            String diffLabel = diff == 1 ? "Easy" : diff == 2 ? "Medium" : "Hard";
            System.out.println("  " + diffLabel + ": " + correct + "/" + total +
                    " (" + String.format("%.1f", pct) + "%)");
        }
    }

    /**
     * Get grade based on percentage
     */
    private String getGrade(double percentage) {
        if (percentage >= 90) return "A+ (Outstanding)";
        else if (percentage >= 80) return "A (Excellent)";
        else if (percentage >= 70) return "B (Good)";
        else if (percentage >= 60) return "C (Average)";
        else if (percentage >= 50) return "D (Below Average)";
        else return "F (Need Improvement)";
    }

    /**
     * Get feedback based on percentage
     */
    private String getFeedback(double percentage) {
        if (percentage >= 90) {
            return "Exceptional performance! You are a Java expert! 🚀";
        } else if (percentage >= 80) {
            return "Great work! You have strong Java knowledge! 💪";
        } else if (percentage >= 70) {
            return "Good job! Keep practicing to master Java! 📚";
        } else if (percentage >= 60) {
            return "Fair performance. Review the concepts you missed! 🤔";
        } else if (percentage >= 50) {
            return "You need more practice. Focus on basics! 📖";
        } else {
            return "Don't give up! Start from basics and practice more! 💪";
        }
    }

    /**
     * Get all questions (for testing/admin purposes)
     */
    public List<Question> getQuestions() {
        return new ArrayList<>(questions);
    }

    /**
     * Add custom question
     */
    public void addQuestion(Question question) {
        questions.add(question);
        totalQuestions = questions.size();
    }

    /**
     * Reset quiz
     */
    public void resetQuiz() {
        currentIndex = 0;
        score = 0;
        userAnswers.clear();
        startTime = 0;
    }
}
