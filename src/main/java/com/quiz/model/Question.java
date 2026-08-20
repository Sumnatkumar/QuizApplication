package com.quiz.model;

import java.io.Serializable;

public class Question implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String text;
    private String[] options;
    private int correctAnswer; // Index of correct option (0-based)
    private String category;
    private int difficulty; // 1=Easy, 2=Medium, 3=Hard

    // Constructor
    public Question(int id, String text, String[] options, int correctAnswer) {
        this.id = id;
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.category = "General";
        this.difficulty = 1;
    }

    // Overloaded constructor with category and difficulty
    public Question(int id, String text, String[] options, int correctAnswer,
                    String category, int difficulty) {
        this.id = id;
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.category = category;
        this.difficulty = difficulty;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String[] getOptions() { return options; }
    public void setOptions(String[] options) { this.options = options; }

    public int getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(int correctAnswer) { this.correctAnswer = correctAnswer; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getDifficulty() { return difficulty; }
    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }

    /**
     * Check if the provided answer index is correct
     */
    public boolean isCorrect(int answerIndex) {
        return answerIndex == correctAnswer;
    }

    /**
     * Display the question with options
     */
    public void display() {
        System.out.println("\n📝 " + text);
        System.out.println("Category: " + category + " | Difficulty: " + getDifficultyLevel());
        System.out.println("─".repeat(50));

        char optionLabel = 'A';
        for (String option : options) {
            System.out.println("   " + optionLabel++ + ". " + option);
        }
        System.out.println("─".repeat(50));
    }

    /**
     * Get difficulty level as string
     */
    private String getDifficultyLevel() {
        switch (difficulty) {
            case 1: return "⭐ Easy";
            case 2: return "⭐⭐ Medium";
            case 3: return "⭐⭐⭐ Hard";
            default: return "Unknown";
        }
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", category='" + category + '\'' +
                ", difficulty=" + difficulty +
                '}';
    }
}
