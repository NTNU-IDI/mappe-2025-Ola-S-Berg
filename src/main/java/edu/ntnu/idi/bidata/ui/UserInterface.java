package edu.ntnu.idi.bidata.ui;

import edu.ntnu.idi.bidata.author.Author;
import edu.ntnu.idi.bidata.author.AuthorRegistry;
import edu.ntnu.idi.bidata.diary.DiaryEntry;
import edu.ntnu.idi.bidata.diary.DiaryRegistry;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * <h1>User Interface.</h1>
 *
 * <p>Handles all user interaction for the diary application using a text-based menu system.
 * Supports multiple entry templates including Standard, Fishing, and Gym entries.</p>
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
    System.out.println("  Welcome to Diary Application");

    while (running) {
      displayMenu();
      int choice = getIntInput("\nEnter your choice: ");
      handleMainMenu(choice);
    }

    scanner.close();
  }

  /**
   * Displays the main menu.
   */
  private void displayMenu() {
    System.out.println("\n========== MAIN MENU ==========");
    System.out.println("1. Create new diary entry");
    System.out.println("2. View all entries");
    System.out.println("3. Search entries");
    System.out.println("4. Delete entry");
    System.out.println("5. Manage authors");
    System.out.println("6. Statistics");
    System.out.println("0. Exit");
    System.out.println("================================");
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
      default -> System.out.println("Invalid choice. Please try again.");
    }
  }

  /**
   * Creates a new diary entry based on user-selected template.
   */
  private void createNewEntry() {
    System.out.println("\n========== CREATE NEW ENTRY ==========");
    System.out.println("Select entry template:");
    System.out.println("1. Standard entry - Free text diary entry");
    System.out.println("2. Fishing entry - Track your fishing adventures");
    System.out.println("3. Gym entry - Track your workouts");
    System.out.println("0. Cancel");
    System.out.println("================================");

    int templateChoice = getIntInput("\nChoose template (0-3): ");

    if (templateChoice == 0) {
      System.out.println("Entry creation cancelled.");
      return;
    }

    if (templateChoice < 1 || templateChoice > 3) {
      System.out.println("Invalid choice. Returning to main menu.");
      return;
    }

    Author author = selectOrCreateAuthor();
    if (author == null) {
      return;
    }

    System.out.print("\nEnter title: ");
    String title = scanner.nextLine().trim();

    System.out.print("Enter category: ");
    String category = scanner.nextLine().trim();

    System.out.print("Enter content: ");
    String content = scanner.nextLine().trim();

    try {
      DiaryEntry entry = switch (templateChoice) {
        case 1 -> diaryRegistry.createStandardEntry(
            author, LocalDateTime.now(), title, content, category);
        case 2 -> createFishingEntryWithInput(
            author, title, content, category);
        case 3 -> createGymEntryWithInput(
            author, title, content, category);
        default -> {
          System.out.println("Invalid choice. Using standard template.");
          yield diaryRegistry.createStandardEntry(
              author, LocalDateTime.now(), title, content, category);
        }
      };

      System.out.println("\n✓ Entry created successfully!");
      printEntry(entry);

    } catch (IllegalArgumentException e) {
      System.out.println("Error creating entry: " + e.getMessage());
    }
  }

  /**
   * Allows user to select an existing author or create a new one.
   *
   * @return The selected or created Author, or null if cancelled.
   */
  private Author selectOrCreateAuthor() {
    System.out.println("\nSelect Author");

    List<Author> authors = authorRegistry.getAllAuthors();
    if (!authors.isEmpty()) {
      System.out.println("Existing authors:");
      for (Author author : authors) {
        System.out.println("  " + author.id() + ". " + author.name());
      }
    } else {
      System.out.println("No existing authors.");
    }

    System.out.println("0. Create new author");

    int authorChoice = getIntInput("\nEnter author ID (or 0 for new): ");

    if (authorChoice == 0) {
      System.out.print("Enter author name: ");
      String name = scanner.nextLine().trim();
      try {
        Author author = authorRegistry.createAndAddAuthor(name);
        System.out.println("Author created: " + author.name());
        return author;
      } catch (IllegalArgumentException e) {
        System.out.println("Error creating author: " + e.getMessage());
        return null;
      }
    } else {
      Author author = authorRegistry.findAuthorById(authorChoice);
      if (author == null) {
        System.out.println("Author not found");
        return null;
      }
      return author;
    }
  }

  /**
   * Creates a fishing diary entry with template-specific fields.
   *
   * @param author   The author of the entry.
   * @param title    The title of the entry.
   * @param content  The content of the entry.
   * @param category The category of the entry.
   * @return The created FishingEntry.
   */
  private DiaryEntry createFishingEntryWithInput(Author author, String title,
      String content, String category) {
    System.out.println("\nFishing Entry Details");

    System.out.print("Weather conditions: ");
    String weather = scanner.nextLine().trim();

    System.out.print("Fish caught (species): ");
    String fishCaught = scanner.nextLine().trim();

    System.out.print("Location: ");
    String location = scanner.nextLine().trim();

    System.out.print("Bait/lure used: ");
    String baitUsed = scanner.nextLine().trim();

    return diaryRegistry.createFishingEntry(
        author, LocalDateTime.now(), title, content, category,
        weather, fishCaught, location, baitUsed);
  }

  /**
   * Creates a gym diary entry with template-specific fields.
   *
   * @param author   The author of the entry.
   * @param title    The title of the entry.
   * @param content  The content of the entry.
   * @param category The category of the entry.
   * @return The created GymEntry.
   */
  private DiaryEntry createGymEntryWithInput(Author author, String title,
      String content, String category) {
    System.out.println("\nGym Entry Details");

    System.out.print("Exercises performed: ");
    String exercises = scanner.nextLine().trim();

    System.out.print("Number of sets: ");
    String sets = scanner.nextLine().trim();

    System.out.print("Repetitions: ");
    String reps = scanner.nextLine().trim();

    System.out.print("Weight used: ");
    String weight = scanner.nextLine().trim();

    return diaryRegistry.createGymEntry(
        author, LocalDateTime.now(), title, content, category,
        exercises, sets, reps, weight);
  }

  /**
   * Displays all diary entries.
   */
  private void viewAllEntries() {
    System.out.println("\nALL DIARY ENTRIES");

    List<DiaryEntry> entries = diaryRegistry.getAllEntriesSortedDescending();

    if (entries.isEmpty()) {
      System.out.println("No diary entries found.");
      return;
    }

    System.out.println("Total entries: " + entries.size());
    System.out.println();

    for (DiaryEntry entry : entries) {
      printEntry(entry);
      System.out.println();
    }
  }

  /**
   * Prints a single diary entry with formatting and template-specific fields.
   *
   * @param entry The entry to print.
   */
  private void printEntry(DiaryEntry entry) {
    System.out.println("┌" + "─".repeat(70) + "┐");
    System.out.println("│ ID: " + entry.getId()
        + " │ Type: " + entry.getEntryType()
        + " │ Category: " + entry.getCategory());
    System.out.println("│ Title: " + entry.getTitle());
    System.out.println("│ Author: " + entry.getAuthor().name()
        + " │ Date: " + entry.getFormattedTimestamp());

    Map<String, String> templateFields = entry.getTemplateFields();
    if (!templateFields.isEmpty()) {
      System.out.println("├" + "─".repeat(70) + "┤");
      for (Map.Entry<String, String> field : templateFields.entrySet()) {
        System.out.println("│ " + field.getKey() + ": " + field.getValue());
      }
    }

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
    System.out.println("\nSEARCH ENTRIES");
    System.out.println("1. Search by date");
    System.out.println("2. Search by category");
    System.out.println("3. Search by author");
    System.out.println("4. Search by entry type");
    System.out.println("0. Back to main menu");

    int choice = getIntInput("\nEnter your choice: ");

    switch (choice) {
      case 1 -> searchByDate();
      case 2 -> searchByCategory();
      case 3 -> searchByAuthor();
      case 4 -> searchByEntryType();
      case 0 -> {
      }
      default -> System.out.println("Invalid choice.");
    }
  }

  /**
   * Searches for entries by date.
   */
  private void searchByDate() {
    System.out.print("\nEnter date (format: dd.mm.yyyy): ");
    String dateStr = scanner.nextLine().trim();

    try {
      java.time.format.DateTimeFormatter formatter =
          java.time.format.DateTimeFormatter.ofPattern("dd.mm.yyyy");
      java.time.LocalDate date = java.time.LocalDate.parse(dateStr, formatter);

      List<DiaryEntry> entries = diaryRegistry.findEntriesByDate(date);

      String displayDate = date.format(formatter);
      System.out.println("\nEntries on " + displayDate + ":");

      if (entries.isEmpty()) {
        System.out.println("No entries found.");
      } else {
        for (DiaryEntry entry : entries) {
          printEntry(entry);
          System.out.println();
        }
      }
    } catch (Exception e) {
      System.out.println("Invalid date format. Please use dd.mm.yyyy");
    }
  }


  /**
   * Searches for entries by category.
   */
  private void searchByCategory() {
    System.out.print("\nEnter category: ");
    String category = scanner.nextLine().trim();

    try {
      List<DiaryEntry> entries = diaryRegistry.findEntriesByCategory(category);

      System.out.println("\nEntries in category '" + category + "':");
      if (entries.isEmpty()) {
        System.out.println("No entries found.");
      } else {
        for (DiaryEntry entry : entries) {
          printEntry(entry);
          System.out.println();
        }
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  /**
   * Searches for entries by author.
   */
  private void searchByAuthor() {
    viewAllAuthors();
    int authorId = getIntInput("\nEnter author ID: ");

    Author author = authorRegistry.findAuthorById(authorId);
    if (author == null) {
      System.out.println("Author not found");
      return;
    }

    List<DiaryEntry> entries = diaryRegistry.getAllEntriesSortedDescending()
        .stream()
        .filter(entry -> entry.getAuthor().id() == author.id())
        .toList();

    System.out.println("\nEntries by " + author.name() + ":");
    if (entries.isEmpty()) {
      System.out.println("No entries found.");
    } else {
      for (DiaryEntry entry : entries) {
        printEntry(entry);
        System.out.println();
      }
    }
  }

  /**
   * Searches for entries by entry type.
   */
  private void searchByEntryType() {
    System.out.println("\nEntry types:");
    System.out.println("1. Standard");
    System.out.println("2. Fishing");
    System.out.println("3. Gym");

    int choice = getIntInput("\nChoose type (1-3): ");

    String entryType = switch (choice) {
      case 1 -> "Standard";
      case 2 -> "Fishing";
      case 3 -> "Gym";
      default -> {
        System.out.println("Invalid choice.");
        yield null;
      }
    };

    if (entryType != null) {
      try {
        List<DiaryEntry> entries = diaryRegistry.findEntriesByType(entryType);

        System.out.println("\nEntries of type '" + entryType + "':");
        if (entries.isEmpty()) {
          System.out.println("No entries found.");
        } else {
          for (DiaryEntry entry : entries) {
            printEntry(entry);
            System.out.println();
          }
        }
      } catch (IllegalArgumentException e) {
        System.out.println("Error: " + e.getMessage());
      }
    }
  }

  /**
   * Deletes a diary entry.
   */
  private void deleteEntry() {
    System.out.println("\nDELETE ENTRY");
    int id = getIntInput("Enter entry ID to delete: ");

    DiaryEntry entry = diaryRegistry.findEntryById(id);
    if (entry == null) {
      System.out.println("Entry with ID " + id + " not found.");
      return;
    }

    System.out.println("\nEntry to delete:");
    printEntry(entry);

    System.out.print("\nAre you sure you want to delete this entry? (y/n): ");
    String confirmation = scanner.nextLine().trim().toLowerCase();

    if (confirmation.equals("y") || confirmation.equals("yes")) {
      if (diaryRegistry.deleteEntryById(id)) {
        System.out.println("Entry deleted.");
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
    System.out.println("\nMANAGE AUTHORS");
    System.out.println("1. View all authors");
    System.out.println("2. Add new author");
    System.out.println("3. Delete author");
    System.out.println("4. Search author");
    System.out.println("0. Back to main menu");

    int choice = getIntInput("\nEnter your choice: ");

    switch (choice) {
      case 1 -> viewAllAuthors();
      case 2 -> addNewAuthor();
      case 3 -> deleteAuthor();
      case 4 -> searchAuthor();
      case 0 -> {
      }
      default -> System.out.println("Invalid choice.");
    }
  }

  /**
   * Displays all authors.
   */
  private void viewAllAuthors() {
    List<Author> authors = authorRegistry.getAllAuthors();

    if (authors.isEmpty()) {
      System.out.println("No authors found.");
      return;
    }

    System.out.println("\nAll Authors");
    for (Author author : authors) {
      System.out.println("  " + author);
    }
  }

  /**
   * Adds a new author.
   */
  private void addNewAuthor() {
    System.out.println("\nAdd New Author");
    System.out.print("Enter author name: ");
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
    System.out.println("\nDelete Author");
    viewAllAuthors();

    int id = getIntInput("\nEnter author ID to delete: ");
    Author author = authorRegistry.findAuthorById(id);

    if (author == null) {
      System.out.println("Author not found");
      return;
    }

    System.out.println("Delete author: " + author);
    System.out.print("Confirm deletion (y/n)");
    String confirmation = scanner.nextLine().trim().toLowerCase();

    if (confirmation.equals("y") || confirmation.equals("yes")) {
      if (authorRegistry.deleteAuthorById(id)) {
        System.out.println("Author deleted");
      } else {
        System.out.println("Failed to delete author.");
      }
    } else {
      System.out.println("Deletion cancelled.");
    }
  }

  /**
   * Searches for an author.
   */
  private void searchAuthor() {
    System.out.print("\nEnter name to search for: ");
    String name = scanner.nextLine().trim();

    try {
      List<Author> authors = authorRegistry.findAuthorByName(name);

      if (authors.isEmpty()) {
        System.out.println("No authors found matching '" + name + "'.");
      } else {
        System.out.println("\nSearch Results");
        for (Author author : authors) {
          System.out.println("  " + author);
        }
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  /**
   * Shows statistics.
   */
  private void showStatistics() {
    System.out.println("\nSTATISTICS");
    System.out.println("Total entries: " + diaryRegistry.getNumberOfEntries());
    System.out.println("Total authors: " + authorRegistry.getAllAuthors().size());

    System.out.println("\nEntries per Author");
    for (Author author : authorRegistry.getAllAuthors()) {
      long count = diaryRegistry.getAllEntriesSortedDescending().stream()
          .filter(entry -> entry.getAuthor().id() == author.id())
          .count();
      System.out.println(author.name() + ": " + count + " entries");
    }

    System.out.println("\nEntries per Type");
    long standardCount = diaryRegistry.findEntriesByType("Standard").size();
    long fishingCount = diaryRegistry.findEntriesByType("Fishing").size();
    long gymCount = diaryRegistry.findEntriesByType("Gym").size();

    System.out.println("Standard entries: " + standardCount);
    System.out.println("Fishing entries: " + fishingCount);
    System.out.println("Gym entries: " + gymCount);
  }

  /**
   * Exits the application.
   */
  private void exitApplication() {
    System.out.println("Exiting application...");
    running = false;
  }

  /**
   * Gets integer input from the user with error handling.
   *
   * @param prompt The prompt to display.
   * @return The integer entered by the user.
   */
  private int getIntInput(String prompt) {
    while (true) {
      try {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return Integer.parseInt(input);
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a number.");
      }
    }
  }
}