package edu.ntnu.idi.bidata.ui;

import edu.ntnu.idi.bidata.author.Author;
import edu.ntnu.idi.bidata.author.AuthorRegistry;
import edu.ntnu.idi.bidata.diary.DiaryRegistry;
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
}
