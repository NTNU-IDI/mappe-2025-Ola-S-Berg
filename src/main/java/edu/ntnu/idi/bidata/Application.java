package edu.ntnu.idi.bidata;

/**
 * <h1>Application.</h1>
 *
 * <p>This class represents the main application and user interface for the diary system.
 * Initializes the system and handles the functionality of the system.</p>
 */
public class Application {

  private DiaryRegistry diaryRegistry;
  private AuthorRegistry authorRegistry;

  /**
   * The main entry point of the application.
   *
   * @param args The command line arguments.
   */
  public static void main(String[] args) {
    Application app = new Application();

    app.init();
    app.start();
  }

  /**
   * Initializes the application by creating instances of the registry classes.
   */
  public void init() {
    System.out.println("INITIALIZING DIARY APPLICATION");

    diaryRegistry = new DiaryRegistry();
    authorRegistry = new AuthorRegistry();
  }

  /**
   * Starts the application and runs functionality.
   */
  public void start() {
    System.out.println("STARTING DIARY APPLICATION");
  }
}
