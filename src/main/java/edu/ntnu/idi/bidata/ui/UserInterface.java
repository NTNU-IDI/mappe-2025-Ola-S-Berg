package edu.ntnu.idi.bidata.ui;

import edu.ntnu.idi.bidata.author.Author;
import edu.ntnu.idi.bidata.author.AuthorRegistry;
import edu.ntnu.idi.bidata.diary.DiaryEntry;
import edu.ntnu.idi.bidata.diary.DiaryRegistry;
import edu.ntnu.idi.bidata.diary.FishingEntry;
import edu.ntnu.idi.bidata.diary.GymEntry;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

/**
 * <h1>User Interface.</h1>
 *
 * <p>User Interface class for the diary application. Responsible for displaying menus, handling
 * user choices, and delegating specific tasks to specialized classes.</p>
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>Display menus and navigate between different application features</li>
 *   <li>Coordinate entry creation, viewing, editing, and deletion operations</li>
 *   <li>Manage search functionality</li>
 *   <li>Handle author management</li>
 *   <li>Display statistics and application data</li>
 * </ul>
 */
public class UserInterface {

  private DiaryRegistry diaryRegistry;
  private AuthorRegistry authorRegistry;
  private InputReader inputReader;
  private EntryFormatter entryFormatter;
  private boolean running;

  /**
   * Initializes the user interface.
   */
  public void init() {
    diaryRegistry = new DiaryRegistry();
    authorRegistry = new AuthorRegistry();
    inputReader = new InputReader(new Scanner(System.in));
    entryFormatter = new EntryFormatter();
    running = true;

    addSampleData();
  }

  /**
   * Adds sample diary entries for testing purposes. Comment out for release.
   */
  private void addSampleData() {
    Author ola = authorRegistry.createAndAddAuthor("Ola Nordmann");
    Author kari = authorRegistry.createAndAddAuthor("Kari Nordmann");
    Author test = authorRegistry.createAndAddAuthor("Test Author");

    diaryRegistry.createStandardEntry(
        ola,
        LocalDateTime.of(2025, 11, 15, 9, 30),
        "First Day of Winter",
        "The first snow fell today. I spent the morning drinking a warm cup of coffee.",
        "Personal"
    );

    diaryRegistry.createStandardEntry(
        test,
        LocalDateTime.of(2025, 11, 20, 14, 15),
        "Busy work day",
        "Had a very busy day at work today, feeling very tired.",
        "Work"
    );

    diaryRegistry.createStandardEntry(
        kari,
        LocalDateTime.of(2025, 12, 1, 20, 0),
        "Birthday Celebration",
        "Celebrated my birthday with close friends and family.",
        "Personal"
    );

    diaryRegistry.createFishingEntry(
        ola,
        LocalDateTime.of(2025, 11, 10, 6, 45),
        "Early Morning at the Lake",
        "Perfect morning for fishing. Caught several nice bass.",
        "Outdoor",
        "Clear, 15°C, light breeze",
        "Bass (3), Salmon (2)",
        "Lofoten",
        "Plastic worm"
    );

    diaryRegistry.createFishingEntry(
        kari,
        LocalDateTime.of(2025, 11, 25, 7, 30),
        "River Fishing Adventure",
        "Tried a new spot on the river today. The current was really strong, i almost lost"
            + " my footing several times.",
        "Outdoor",
        "Sunny, 20°C, moderate wind",
        "Trout (4), Cod (1)",
        "Orkla, Storås",
        "Fly fishing"
    );

    diaryRegistry.createGymEntry(
        ola,
        LocalDateTime.of(2025, 12, 1, 17, 30),
        "Chest and Triceps Day",
        "Great workout today! Feeling strong and managed to increase weight on bench press.",
        "Fitness",
        "Bench Press, Incline Dumbbell Press, Dips",
        "4, 3, 3",
        "8x80kg;7x80kg;6x82.5kg;6x82.5kg, 10x25kg;10x25kg;8x27.5kg, "
            + "12xBodyweight;10xBodyweight;8xBodyweight");

    diaryRegistry.createGymEntry(
        kari,
        LocalDateTime.of(2025, 12, 4, 6, 0),
        "Leg Day - Squats Focus",
        "Early morning leg session. Squats felt heavy but pushed through.",
        "Fitness",
        "Squats, Leg Press, Lunges",
        "5, 3, 3",
        "10x100kg;8x110kg;6x120kg;5x130kg;5x130kg, 12x150kg;10x170kg;10x170kg,"
            + " 10x20kg;10x20kg;8x22.5kg");

    System.out.println("Sample data loaded");
  }

  /**
   * Starts the application.
   */
  public void start() {
    System.out.println("\nWelcome to the Diary Application");

    while (running) {
      displayMainMenu();
      int choice = inputReader.readInt("\nEnter your choice: ");
      handleMainMenu(choice);
    }

    inputReader.close();
  }

  /**
   * Displays the main menu.
   */
  private void displayMainMenu() {
    System.out.println("\n========== MAIN MENU ==========");
    System.out.println("1. Create new diary entry");
    System.out.println("2. View all entries");
    System.out.println("3. Search entries");
    System.out.println("4. Edit entry");
    System.out.println("5. Delete entry");
    System.out.println("6. Manage authors");
    System.out.println("7. Statistics");
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
      case 4 -> editEntry();
      case 5 -> deleteEntry();
      case 6 -> manageAuthors();
      case 7 -> showStatistics();
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

    int templateChoice = inputReader.readInt("\nChoose template (0-3): ");

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

    String title = inputReader.readString("\nEnter title: ");
    String category = inputReader.readString("Enter category: ");
    String content = inputReader.readString("Enter content: ");

    try {
      DiaryEntry entry = switch (templateChoice) {
        case 1 -> diaryRegistry.createStandardEntry(
            author, LocalDateTime.now(), title, content, category);
        case 2 -> createFishingEntry(author, title, content, category);
        case 3 -> createGymEntry(author, title, content, category);
        default -> {
          System.out.println("Invalid choice. Using standard template.");
          yield diaryRegistry.createStandardEntry(
              author, LocalDateTime.now(), title, content, category);
        }
      };

      System.out.println("\n✓ Entry created successfully!");
      entryFormatter.printEntry(entry);

    } catch (IllegalArgumentException e) {
      System.out.println("Error creating entry: " + e.getMessage());
    }
  }

  /**
   * Creates a fishing entry with template-specific fields.
   */
  private DiaryEntry createFishingEntry(Author author, String title,
      String content, String category) {
    System.out.println("\nFishing Entry Details");

    String weather = inputReader.readString("Weather conditions: ");
    String fishCaught = inputReader.readString("Fish caught (species): ");
    String location = inputReader.readString("Location: ");
    String baitUsed = inputReader.readString("Bait/lure used: ");

    return diaryRegistry.createFishingEntry(
        author, LocalDateTime.now(), title, content, category,
        weather, fishCaught, location, baitUsed);
  }

  /**
   * Creates a gym entry with template-specific fields.
   */
  private DiaryEntry createGymEntry(Author author, String title,
      String content, String category) {
    System.out.println("\nGym Entry Details");

    String exercises = inputReader.readString(
        "Exercises performed (comma-separated, e.g., Bench Press, Squats): ");
    String sets = inputReader.readString(
        "Number of sets (comma-separated, e.g., 3, 4): ");
    String reps = inputReader.readString(
        "Repetitions (comma-separated, e.g., 7x60kg;6x60kg;6x55kg, 8x100kg;7x100kg;6x100kg): ");

    return diaryRegistry.createGymEntry(
        author, LocalDateTime.now(), title, content, category,
        exercises, sets, reps);
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

    int authorChoice = inputReader.readInt("\nEnter author ID (or 0 for new): ");

    if (authorChoice == 0) {
      String name = inputReader.readString("Enter author name: ");
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

    System.out.println("\nSort by:");
    System.out.println("1. Newest first (descending)");
    System.out.println("2. Oldest first (ascending)");

    int sortChoice = inputReader.readInt("\nEnter your choice (1-2): ");

    if (sortChoice == 2) {
      entries = diaryRegistry.getAllEntriesSortedAscending();
    }

    System.out.println();

    for (DiaryEntry entry : entries) {
      entryFormatter.printEntry(entry);
      System.out.println();
    }
  }

  /**
   * Displays the search menu.
   */
  private void searchMenu() {
    System.out.println("\nSEARCH ENTRIES");
    System.out.println("1. Search by date");
    System.out.println("2. Search by date range");
    System.out.println("3. Search by keyword");
    System.out.println("4. Search by category");
    System.out.println("5. Search by author");
    System.out.println("6. Search by entry type");
    System.out.println("0. Back to main menu");

    int choice = inputReader.readInt("\nEnter your choice: ");

    switch (choice) {
      case 1 -> searchByDate();
      case 2 -> searchByDateRange();
      case 3 -> searchByKeyword();
      case 4 -> searchByCategory();
      case 5 -> searchByAuthor();
      case 6 -> searchByEntryType();
      case 0 -> {
      }
      default -> System.out.println("Invalid choice.");
    }
  }

  /**
   * Searches for entries by date.
   */
  private void searchByDate() {
    LocalDate date = inputReader.readDate("\nEnter date (format: dd.MM.yyyy): ");

    if (date == null) {
      return;
    }

    List<DiaryEntry> entries = diaryRegistry.findEntriesByDate(date);

    String displayDate = date.format(inputReader.getDateFormatter());
    System.out.println("\nEntries on " + displayDate + ":");

    displaySearchResults(entries);
  }

  /**
   * Searches for entries in a date range.
   */
  private void searchByDateRange() {
    LocalDate startDate = inputReader.readDate("\nEnter start date (format: dd.MM.yyyy): ");
    if (startDate == null) {
      return;
    }

    LocalDate endDate = inputReader.readDate("Enter end date (format: dd.MM.yyyy): ");
    if (endDate == null) {
      return;
    }

    List<DiaryEntry> entries = diaryRegistry.findEntriesByDateRange(startDate, endDate);

    if (entries.isEmpty()) {
      System.out.println("No entries found between " + startDate + " and " + endDate);
    } else {
      System.out.println("\nEntries from " + startDate.format(inputReader.getDateFormatter())
          + " to " + endDate.format(inputReader.getDateFormatter()) + ":");
      displaySearchResults(entries);
    }
  }

  /**
   * Searches for entries containing a specific keyword.
   */
  private void searchByKeyword() {
    String keyword = inputReader.readString("\nEnter keyword to search for: ");

    List<DiaryEntry> entries = diaryRegistry.findEntriesByKeyword(keyword);

    if (entries.isEmpty()) {
      System.out.println("No entries found with keyword: " + keyword);
    } else {
      System.out.println("\nEntries containing '" + keyword + "':");
      displaySearchResults(entries);
    }
  }

  /**
   * Searches for entries by category.
   */
  private void searchByCategory() {
    String category = inputReader.readString("\nEnter category: ");

    try {
      List<DiaryEntry> entries = diaryRegistry.findEntriesByCategory(category);

      System.out.println("\nEntries in category '" + category + "':");
      displaySearchResults(entries);
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  /**
   * Searches for entries by author.
   */
  private void searchByAuthor() {
    viewAllAuthors();
    int authorId = inputReader.readInt("\nEnter author ID: ");

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
    displaySearchResults(entries);
  }

  /**
   * Searches for entries by entry type.
   */
  private void searchByEntryType() {
    System.out.println("\nEntry types:");
    System.out.println("1. Standard");
    System.out.println("2. Fishing");
    System.out.println("3. Gym");

    int choice = inputReader.readInt("\nChoose type (1-3): ");

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
        displaySearchResults(entries);
      } catch (IllegalArgumentException e) {
        System.out.println("Error: " + e.getMessage());
      }
    }
  }

  /**
   * Displays search results.
   *
   * @param entries The list of entries to display
   */
  private void displaySearchResults(List<DiaryEntry> entries) {
    if (entries.isEmpty()) {
      System.out.println("No entries found.");
    } else {
      for (DiaryEntry entry : entries) {
        entryFormatter.printEntry(entry);
        System.out.println();
      }
    }
  }

  /**
   * Deletes a diary entry.
   */
  private void deleteEntry() {
    System.out.println("\nDELETE ENTRY");
    int id = inputReader.readInt("Enter entry ID to delete: ");

    DiaryEntry entry = diaryRegistry.findEntryById(id);
    if (entry == null) {
      System.out.println("Entry with ID " + id + " not found.");
      return;
    }

    System.out.println("\nEntry to delete:");
    entryFormatter.printEntry(entry);

    if (inputReader.readConfirmation("\nAre you sure you want to delete this entry? (y/n): ")) {
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
   * Edits an existing diary entry's template-specific fields.
   */
  private void editEntry() {
    System.out.println("\nEDIT ENTRY");
    int id = inputReader.readInt("Enter entry ID to edit: ");

    DiaryEntry entry = diaryRegistry.findEntryById(id);
    if (entry == null) {
      System.out.println("Entry with ID " + id + " not found.");
      return;
    }

    System.out.println("\nCurrent entry:");
    entryFormatter.printEntry(entry);

    if (entry instanceof GymEntry) {
      editGymEntry((GymEntry) entry);
    } else if (entry instanceof FishingEntry) {
      editFishingEntry((FishingEntry) entry);
    } else {
      editStandardEntry(entry);
    }
  }

  private void editStandardEntry(DiaryEntry entry) {
    System.out.println("\nEDIT STANDARD ENTRY");

    String newContent = inputReader.readString("\nEnter new content: ");

    try {
      entry.setContent(newContent);
      System.out.println("Content updated");

      System.out.println("\nUpdated entry:");
      entryFormatter.printEntry(entry);
    } catch (IllegalArgumentException e) {
      System.out.println("Error updating entry: " + e.getMessage());
    }
  }

  /**
   * Edits a gym entry's modifiable fields.
   */
  private void editGymEntry(GymEntry entry) {
    System.out.println("\nEDIT GYM ENTRY - Choose field to edit:");
    System.out.println("1. Exercises");
    System.out.println("2. Sets");
    System.out.println("3. Repetitions");
    System.out.println("4. Notes");
    System.out.println("5. Edit all fields");
    System.out.println("0. Cancel");

    int choice = inputReader.readInt("\nEnter your choice: ");

    try {
      switch (choice) {
        case 1 -> {
          String exercises = inputReader.readString("Enter new exercises (comma-separated): ");
          entry.setExercises(exercises);
          System.out.println("Exercises updated");
        }
        case 2 -> {
          String sets = inputReader.readString("Enter new sets (comma-separated): ");
          entry.setSets(sets);
          System.out.println("Sets updated");
        }
        case 3 -> {
          String reps = inputReader.readString(
              "Enter new repetitions (semicolon-separated sets): ");
          entry.setReps(reps);
          System.out.println("Repetitions updated");
        }
        case 4 -> {
          String content = inputReader.readString("Enter new notes: ");
          entry.setContent(content);
          System.out.println("Content updated");
        }
        case 5 -> {
          entry.setExercises(inputReader.readString("Enter new exercises (comma-separated): "));
          entry.setSets(inputReader.readString("Enter new sets (comma-separated): "));
          entry.setReps(
              inputReader.readString("Enter new repetitions (semicolon-separated sets): "));
          System.out.println("All fields updated");
        }
        case 0 -> {
          System.out.println("Edit cancelled.");
          return;
        }
        default -> {
          System.out.println("Invalid choice.");
          return;
        }
      }

      System.out.println("\nUpdated entry:");
      entryFormatter.printEntry(entry);

    } catch (IllegalArgumentException e) {
      System.out.println("Error updating entry: " + e.getMessage());
    }
  }

  /**
   * Edits a fishing entry's modifiable fields.
   */
  private void editFishingEntry(FishingEntry entry) {
    System.out.println("\nEDIT FISHING ENTRY - Choose field to edit:");
    System.out.println("1. Weather");
    System.out.println("2. Fish caught");
    System.out.println("3. Location");
    System.out.println("4. Bait used");
    System.out.println("5. Content");
    System.out.println("6. Edit all fields");
    System.out.println("0. Cancel");

    int choice = inputReader.readInt("\nEnter your choice: ");

    try {
      switch (choice) {
        case 1 -> {
          String weather = inputReader.readString("Enter new weather conditions: ");
          entry.setWeather(weather);
          System.out.println("Weather updated");
        }
        case 2 -> {
          String fishCaught = inputReader.readString("Enter new fish caught: ");
          entry.setFishCaught(fishCaught);
          System.out.println("Fish caught updated");
        }
        case 3 -> {
          String location = inputReader.readString("Enter new location: ");
          entry.setLocation(location);
          System.out.println("Location updated");
        }
        case 4 -> {
          String baitUsed = inputReader.readString("Enter new bait used: ");
          entry.setBaitUsed(baitUsed);
          System.out.println("Bait used updated");
        }
        case 5 -> {
          String content = inputReader.readString("Enter new content: ");
          entry.setContent(content);
          System.out.println("Content updated");
        }
        case 6 -> {
          entry.setWeather(inputReader.readString("Enter new weather conditions: "));
          entry.setFishCaught(inputReader.readString("Enter new fish caught: "));
          entry.setLocation(inputReader.readString("Enter new location: "));
          entry.setBaitUsed(inputReader.readString("Enter new bait used: "));
          System.out.println("All fields updated");
        }
        case 0 -> {
          System.out.println("Edit cancelled.");
          return;
        }
        default -> {
          System.out.println("Invalid choice.");
          return;
        }
      }

      System.out.println("\nUpdated entry:");
      entryFormatter.printEntry(entry);

    } catch (IllegalArgumentException e) {
      System.out.println("Error updating entry: " + e.getMessage());
    }
  }

  /**
   * Manages authors.
   */
  private void manageAuthors() {
    System.out.println("\nMANAGE AUTHORS");
    System.out.println("1. View all authors");
    System.out.println("2. Add new author");
    System.out.println("3. Delete author");
    System.out.println("4. Search author");
    System.out.println("0. Back to main menu");

    int choice = inputReader.readInt("\nEnter your choice: ");

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

    System.out.println("\nAll Authors:");
    for (Author author : authors) {
      System.out.println("  " + author);
    }
  }

  /**
   * Adds a new author.
   */
  private void addNewAuthor() {
    System.out.println("\nAdd New Author");
    String name = inputReader.readString("Enter author name: ");

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

    int id = inputReader.readInt("\nEnter author ID to delete: ");
    Author author = authorRegistry.findAuthorById(id);

    if (author == null) {
      System.out.println("Author not found");
      return;
    }

    System.out.println("Delete author: " + author);

    if (inputReader.readConfirmation("Confirm deletion (y/n): ")) {
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
    String name = inputReader.readString("\nEnter name to search for: ");

    try {
      List<Author> authors = authorRegistry.findAuthorByName(name);

      if (authors.isEmpty()) {
        System.out.println("No authors found matching '" + name + "'.");
      } else {
        System.out.println("\nSearch Results:");
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

    System.out.println("\nEntries per Author:");
    for (Author author : authorRegistry.getAllAuthors()) {
      long count = diaryRegistry.getAllEntriesSortedDescending().stream()
          .filter(entry -> entry.getAuthor().id() == author.id())
          .count();
      System.out.println(author.name() + ": " + count + " entries");
    }

    System.out.println("\nEntries per Type:");
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
}