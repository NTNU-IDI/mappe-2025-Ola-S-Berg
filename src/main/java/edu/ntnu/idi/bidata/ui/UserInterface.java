package edu.ntnu.idi.bidata.ui;

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
  }
}
