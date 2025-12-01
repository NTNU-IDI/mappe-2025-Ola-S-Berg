package edu.ntnu.idi.bidata.ui;

import edu.ntnu.idi.bidata.author.Author;
import edu.ntnu.idi.bidata.author.AuthorRegistry;
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

  private void searchMenu() {
    System.out.println("Search entries");
  }

  private void deleteEntry() {
    System.out.println("Delete entry");
  }

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
        return;
      }
      default -> exitApplication();
    }
  }

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

  private void exitApplication() {
    System.out.println("Exiting application...");
    running = false;
  }

  private int getIntInput(String s) {
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
