package edu.ntnu.idi.bidata.ui;

import edu.ntnu.idi.bidata.author.Author;
import edu.ntnu.idi.bidata.author.AuthorRegistry;
import edu.ntnu.idi.bidata.diary.DiaryEntry;
import edu.ntnu.idi.bidata.diary.DiaryRegistry;
import java.util.List;
import java.util.Scanner;

/**
 * <h1>User Interface.</h1>
 *
 * <p>Handles all user interaction for the diary application using a text-based menu system.</p>
 */
public class UserInterface {

  private DiaryRegistry diaryRegistry;
  private AuthorRegistry authorRegistry;
  private Scanner scanner;
  private boolean running;

  /**
   * Initializes the user interface.
   */
  public void init() {
    diaryRegistry = new DiaryRegistry();
    authorRegistry = new AuthorRegistry();
    scanner = new Scanner(System.in);
    running = true;
  }

  /**
   * Starts the application.
   */
  public void start() {

    while (running) {
      displayMenu();
    }

    scanner.close();
  }

  /**
   * Displays the menu.
   */
  private void displayMenu() {
    System.out.println("Main menu");
    System.out.println("1. Create new diary entry");
    System.out.println("2. View all entries");
    System.out.println("3. Search entries");
    System.out.println("4. Delete entry");
    System.out.println("5. Manage authors");
    System.out.println("6. Statistics");
    System.out.println("7. Exit");
  }

  /**
   * Handles main menu selection.
   *
   * @param choice The user's menu choice.
   */
  private void handleMainMenu(int choice) {
    switch (choice) {
      case 1 -> createNewEntry();
      case 2 -> viewAllEntries();
      case 3 -> searchMenu();
      case 4 -> deleteEntry();
      case 5 -> manageAuthors();
      case 6 -> showStatistics();
      case 0 -> exitApplication();
    }
  }

  private void createNewEntry() {
    System.out.println("Create new diary entry");
  }

  private void viewAllEntries() {
    System.out.println("All diary entries");

  }

  /**
   * Prints a single diary entry with formatting.
   *
   * @param entry The entry to print.
   */
  private void printEntry(DiaryEntry entry) {
    System.out.println("┌" + "─".repeat(70) + "┐");
    System.out.println("│ ID: " + entry.getId()
        + " │ Category: " + entry.getCategory());
    System.out.println("│ Title: " + entry.getTitle());
    System.out.println("│ Author: " + entry.getAuthor().name()
        + " │ Date: " + entry.getFormattedTimestamp());
    System.out.println("├" + "─".repeat(70) + "┤");

    String content = entry.getContent();
    int maxLineLength = 68;
    String[] words = content.split(" ");
    StringBuilder line = new StringBuilder("│ ");

    for (String word : words) {
      if (line.length() + word.length() + 1 > maxLineLength) {
        while (line.length() < maxLineLength + 2) {
          line.append(" ");
        }
        line.append(" │");
        System.out.println(line);
        line = new StringBuilder("│ " + word + " ");
      } else {
        line.append(word).append(" ");
      }
    }

    if (line.length() > 2) {
      while (line.length() < maxLineLength + 2) {
        line.append(" ");
      }
      line.append(" │");
      System.out.println(line);
    }

    System.out.println("└" + "─".repeat(70) + "┘");
  }

  /**
   * Displays the search menu.
   */
  private void searchMenu() {
    System.out.println("Search entries");
  }

  /**
   * Deletes a diary entry.
   */
  private void deleteEntry() {
    System.out.println("Delete entry");
    int id = getIntInput("Enter entry ID to delete: ");

    DiaryEntry entry = diaryRegistry.findEntryById(id);
    if (entry == null) {
      System.out.println("Entry with ID " + id + " not found");
    }

    System.out.println("\nEntry to delete:");
    assert entry != null;
    printEntry(entry);

    System.out.print("\nAre you sure you want to delete this entry? (y/n)");
    String confirmation = scanner.nextLine();
    if (confirmation.equalsIgnoreCase("y")) {
      if (diaryRegistry.deleteEntryById(id)) {
        System.out.println("Entry with ID " + id + " successfully deleted");
      } else {
        System.out.println("Failed to delete entry.");
      }
    } else {
      System.out.println("Deletion cancelled.");
    }
  }

  /**
   * Manages authors. Allows for adding, viewing or deleting authors.
   */
  private void manageAuthors() {
    System.out.println("Manage authors");
    System.out.println("1. View all authors");
    System.out.println("2. Add new author");
    System.out.println("3. Delete author");
    System.out.println("4. Search author");
    System.out.println("0. Back to main menu");

    int choice = getIntInput("Enter author ID to delete: ");

    switch (choice) {
      case 1 -> viewAllAuthors();
      case 2 -> addNewAuthor();
      case 3 -> deleteAuthor();
      case 4 -> searchAuthor();
      case 0 -> {
      }
      default -> exitApplication();
    }
  }

  /**
   * Displays all authors.
   */
  private void viewAllAuthors() {
    List<Author> authors = authorRegistry.getAllAuthors();

    if (authors.isEmpty()) {
      System.out.println("No authors found");
      return;
    }

    System.out.println("\nAll authors:");
    for (Author author : authors) {
      System.out.println(author);
    }
  }

  /**
   * Adds a new author.
   */
  private void addNewAuthor() {
    System.out.println("Add new author");
    String name = scanner.nextLine().trim();

    try {
      Author author = authorRegistry.createAndAddAuthor(name);
      System.out.println("Author created: " + author);
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  /**
   * Deletes an author.
   */
  private void deleteAuthor() {
    int id = getIntInput("Enter author ID to delete: ");
    Author author = authorRegistry.findAuthorById(id);

    if (author == null) {
      System.out.println("Author not found");
    }

    System.out.println("Delete author: " + author);
    System.out.print("Confirm deletion (y/n)");
    String confirmation = scanner.nextLine().trim().toLowerCase();

    if (confirmation.equals("y")) {
      if (authorRegistry.deleteAuthorById(id)) {
        System.out.println("Author deleted");
      } else {
        System.out.println("Failed to delete author");
      }
    } else {
      System.out.println("Deletion cancelled, returning to main menu...");
    }
  }

  /**
   * Searches for an author.
   */
  private void searchAuthor() {
    System.out.println("Enter name of author to search for: ");
    String name = scanner.nextLine().trim();

    List<Author> authors = authorRegistry.findAuthorByName(name);

    if (authors.isEmpty()) {
      System.out.println("No authors found " + name);
      return;
    }

    System.out.println("Search results:");
    for (Author author : authors) {
      System.out.println(author);
    }
  }

  /**
   * Shows statistics.
   */
  private void showStatistics() {
    System.out.println("Statistics");
    System.out.println("Total entries: " + diaryRegistry.getNumberOfEntries());
    System.out.println("Total authors: " + authorRegistry.getAllAuthors().size());

    System.out.println("\n");

    for (Author author : authorRegistry.getAllAuthors()) {
      long count = diaryRegistry.getAllEntriesSortedDescending().stream().filter(
          entry -> entry.getAuthor().id() == author.id()).count();
    }
  }

  /**
   * Exits the application.
   */
  private void exitApplication() {
    System.out.println("Exiting application...");
    running = false;
  }

  /**
   * Gets integer input from the user.
   *
   * @param prompt The prompt to display.
   * @return The integer entered by the user.
   */
  private int getIntInput(String prompt) {
    while (true) {
      try {
        System.out.println("Enter your choice: ");
        String input = scanner.nextLine().trim();
        return Integer.parseInt(input);
      } catch (NumberFormatException e) {
        System.out.println("Please enter a number");
      }
    }
  }
}
