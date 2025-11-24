package edu.ntnu.idi.bidata;

import java.time.LocalDateTime;
import java.util.List;

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
    System.out.println("CREATING AUTHORS");

    Author author1 = authorRegistry.createAndAddAuthor("Ola Nordmann");
    Author author2 = authorRegistry.createAndAddAuthor("Kari Nordmann");
    Author author3 = authorRegistry.createAndAddAuthor("Thoralf Nordmann");

    System.out.println("Created " + authorRegistry.getAllAuthors().size() + " authors");
    printAllAuthors();

    DiaryEntry entry1 = diaryRegistry.createAndAddEntry(author1,
        LocalDateTime.now(),
        "My first lecture",
        "Today i attended my first lecture in Trondheim",
        "Studies");

    DiaryEntry entry2 = diaryRegistry.createAndAddEntry(author2,
        LocalDateTime.now().minusDays(1),
        "Walk in the park",
        "Took a walk in the park today, the weather was great!",
        "leisure");

    DiaryEntry entry3 = diaryRegistry.createAndAddEntry(author3,
        LocalDateTime.now().minusDays(2),
        "Great workout today",
        "Had a great workout today at the gym. Chest day.",
        "Gym");

    DiaryEntry entry4 = diaryRegistry.createAndAddEntry(author3,
        LocalDateTime.now().minusDays(3),
        "Awful workout today",
        "Today's legs workout was just awful, hope tomorrow's chest workout goes better.",
        "Gym");

    System.out.println("Created " + diaryRegistry.getNumberOfEntries() + " entries");

    testDiaryFunctionality();
  }

  /**
   * Tests diary registry functionality.
   */
  private void testDiaryFunctionality() {
    System.out.println("TESTING DIARY FUNCTIONALITY");
    System.out.println("\n All Diary entries (Sorted by newest first)");

    printAllEntries(diaryRegistry.getAllEntriesSortedDescending());
  }

  /**
   * Prints a list of all authors in the author registry.
   */
  private void printAllAuthors() {
    List<Author> authors = authorRegistry.getAllAuthors();
    for (Author author : authors) {
      System.out.println(author);
    }
  }

  /**
   * Prints all diary entries in the list.
   *
   * @param entries The list of entries to print.
   */
  private void printAllEntries(List<DiaryEntry> entries) {
    if (entries.isEmpty()) {
      System.out.println("No diary entries");
      return;
    }

    for (DiaryEntry entry : entries) {
      printEntry(entry);
      System.out.println();
    }
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
    System.out.println("│ Author: " + entry.getAuthor().getName()
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
        line.append("│");
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
      line.append("│");
      System.out.println(line);
    }

    System.out.println("└" + "─".repeat(70) + "┘");
  }
}
