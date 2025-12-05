package edu.ntnu.idi.bidata.ui;

import edu.ntnu.idi.bidata.author.Author;
import edu.ntnu.idi.bidata.author.AuthorRegistry;
import edu.ntnu.idi.bidata.diary.DiaryEntry;
import edu.ntnu.idi.bidata.diary.DiaryRegistry;
import edu.ntnu.idi.bidata.diary.GymEntry;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    addSampleData();
  }

  /**
   * Adds sample diary entries for testing purposes. Commented out for release.
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

    System.out.print("Exercises performed (comma-separated, e.g., Bench Press, Squats): ");
    String exercises = scanner.nextLine().trim();

    System.out.print("Number of sets (comma-separated, e.g., 3, 4): ");
    String sets = scanner.nextLine().trim();

    System.out.print(
        "Repetitions (comma-separated, e.g., 7x60kg;6x60kg;6x55kg, 8x100kg;7x100kg;6x100kg): ");
    String reps = scanner.nextLine().trim();

    return diaryRegistry.createGymEntry(
        author, LocalDateTime.now(), title, content, category,
        exercises, sets, reps);
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
   * Prints a single diary entry with formatting and template-specific fields. Uses special
   * formatting for Gym entries.
   *
   * @param entry The entry to print.
   */
  private void printEntry(DiaryEntry entry) {
    if (entry instanceof GymEntry) {
      printGymEntry((GymEntry) entry);
    } else {
      printEntryBasicFormatting(entry);
    }
  }

  /**
   * Prints an entry with basic formatting.
   *
   * @param entry The entry to print.
   */
  private void printEntryBasicFormatting(DiaryEntry entry) {
    formatWrapper(entry);

    Map<String, String> templateFields = entry.getTemplateFields();
    if (!templateFields.isEmpty()) {
      System.out.println("├" + "─".repeat(70) + "┤");
      for (Map.Entry<String, String> field : templateFields.entrySet()) {
        String fieldLine = "│ " + field.getKey() + ": " + field.getValue();
        System.out.println(fieldLine + " ".repeat(71 - fieldLine.length()) + "│");
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
   * Wraps an entry in lines based on the format of the entry.
   *
   * @param entry The entry to wrap.
   */
  private void formatWrapper(DiaryEntry entry) {
    System.out.println("┌" + "─".repeat(70) + "┐");

    String line1 = "│ ID: " + entry.getId()
        + " │ Type: " + entry.getEntryType()
        + " │ Category: " + entry.getCategory();
    System.out.println(line1 + " ".repeat(71 - line1.length()) + "│");

    String line2 = "│ Title: " + entry.getTitle();
    System.out.println(line2 + " ".repeat(71 - line2.length()) + "│");

    String line3 = "│ Author: " + entry.getAuthor().name()
        + " │ Date: " + entry.getFormattedTimestamp();
    System.out.println(line3 + " ".repeat(71 - line3.length()) + "│");
  }

  /**
   * Prints a gym entry with table formatting.
   *
   * @param entry The gym entry to print.
   */
  private void printGymEntry(GymEntry entry) {
    formatWrapper(entry);
    System.out.println("├" + "─".repeat(70) + "┤");

    String content = entry.getContent();
    if (!content.isEmpty()) {
      int maxLineLength = 62;
      String[] words = content.split(" ");
      StringBuilder line = new StringBuilder("│ Notes: ");

      boolean firstLine = true;
      for (String word : words) {
        int currentPrefix = firstLine ? 8 : 2;
        int availableSpace = maxLineLength + currentPrefix;

        if (line.length() + word.length() + 1 > availableSpace) {
          while (line.length() < availableSpace) {
            line.append(" ");
          }
          line.append(" │");
          System.out.println(line);

          line = new StringBuilder("│ " + word + " ");
          firstLine = false;
        } else {
          line.append(word).append(" ");
        }
      }

      if (line.length() > 2) {
        int currentPrefix = firstLine ? 8 : 2;
        int availableSpace = maxLineLength + currentPrefix + 6;
        while (line.length() < availableSpace) {
          line.append(" ");
        }
        line.append(" │");
        System.out.println(line);
      }
    }

    String[] exercises = entry.getExercises().split(",");
    String[] repsData = entry.getReps().split(",");

    System.out.println("│" + padCenter("WORKOUT SUMMARY", 70) + "│");
    System.out.println("├" + "─".repeat(70) + "┤");
    System.out.println(
        "│" + padCenter("Exercise", 30) + "│"
            + padCenter("Repetitions and Weight", 39) + "│");

    System.out.println("├" + "─".repeat(30) + "┼" + "─".repeat(39) + "┤");

    for (int i = 0; i < exercises.length; i++) {
      String exercise = exercises[i].trim();
      String repsInfo = i < repsData.length ? repsData[i].trim() : "N/A";

      String[] sets = repsInfo.split(";");

      System.out.println("│ " + padRight(exercise, 28)
          + " │ " + padRight(sets[0], 37) + " │");

      for (int j = 1; j < sets.length; j++) {
        System.out.println("│ " + padRight("", 28) + " │ "
            + padRight(sets[j].trim(), 37) + " │");
      }

      if (i < exercises.length - 1) {
        System.out.println("├" + "─".repeat(30) + "┼" + "─".repeat(39) + "┤");
      }
    }

    System.out.println("└" + "─".repeat(30) + "┴" + "─".repeat(39) + "┘");
  }

  /**
   * Pads a string to the right with spaces.
   *
   * @param str    The string to pad.
   * @param length The total length.
   * @return The padded string.
   */
  private String padRight(String str, int length) {
    if (str.length() >= length) {
      return str.substring(0, length);
    }
    return str + " ".repeat(length - str.length());
  }

  /**
   * Centers a string within a given length.
   *
   * @param str    The string to center.
   * @param length The total length.
   * @return The centered string.
   */
  private String padCenter(String str, int length) {
    if (str.length() >= length) {
      return str.substring(0, length);
    }
    int leftPad = (length - str.length()) / 2;
    int rightPad = length - str.length() - leftPad;
    return " ".repeat(leftPad) + str + " ".repeat(rightPad);
  }

  /**
   * Displays the search menu.
   */
  private void searchMenu() {
    System.out.println("\nSEARCH ENTRIES");
    System.out.println("1. Search by date");
    System.out.println("2. Search by date range");
    System.out.println("3. Search by category");
    System.out.println("4. Search by author");
    System.out.println("5. Search by entry type");
    System.out.println("0. Back to main menu");

    int choice = getIntInput("\nEnter your choice: ");

    switch (choice) {
      case 1 -> searchByDate();
      case 2 -> searchByDateRange();
      case 3 -> searchByCategory();
      case 4 -> searchByAuthor();
      case 5 -> searchByEntryType();
      case 0 -> {
      }
      default -> System.out.println("Invalid choice.");
    }
  }

  /**
   * Searches for entries by date.
   */
  private void searchByDate() {
    System.out.print("\nEnter date (format: dd.MM.yyyy): ");
    String dateStr = scanner.nextLine().trim();

    try {
      java.time.format.DateTimeFormatter formatter =
          java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy");
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
      System.out.println("Invalid date format. Please use dd.MM.yyyy");
    }
  }

  /**
   * Searches for entries in a date range.
   */
  private void searchByDateRange() {
    System.out.println("\nEnter start date (format: dd.MM.yyyy): ");
    String startDateStr = scanner.nextLine().trim();

    System.out.println("\nEnter end date (format: dd.MM.yyyy): ");
    String endDateStr = scanner.nextLine().trim();

    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
      LocalDate startDate = LocalDate.parse(startDateStr, formatter);
      LocalDate endDate = LocalDate.parse(endDateStr, formatter);

      List<DiaryEntry> entries = diaryRegistry.findEntriesByDateRange(startDate, endDate);

      if (entries.isEmpty()) {
        System.out.println("No entries found between " + startDate + " and " + endDate);
      } else {
        System.out.println("\nEntries from " + startDate.format(formatter)
            + " to " + endDate.format(formatter) + ":");
        for (DiaryEntry entry : entries) {
          printEntry(entry);
          System.out.println();
        }
      }
    } catch (Exception e) {
      System.out.println("Invalid date format. Please use dd.MM.yyyy");
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
   * Edits an existing diary entry's template-specific fields.
   */
  private void editEntry() {
    System.out.println("\nEDIT ENTRY");
    int id = getIntInput("Enter entry ID to edit: ");

    DiaryEntry entry = diaryRegistry.findEntryById(id);
    if (entry == null) {
      System.out.println("Entry with ID " + id + " not found.");
      return;
    }

    System.out.println("\nCurrent entry:");
    printEntry(entry);

    if (entry instanceof edu.ntnu.idi.bidata.diary.GymEntry) {
      editGymEntry((edu.ntnu.idi.bidata.diary.GymEntry) entry);
    } else if (entry instanceof edu.ntnu.idi.bidata.diary.FishingEntry) {
      editFishingEntry((edu.ntnu.idi.bidata.diary.FishingEntry) entry);
    } else {
      System.out.println("\nStandard entries cannot be edited.");
    }
  }

  /**
   * Edits a gym entry's modifiable fields.
   *
   * @param entry The gym entry to edit.
   */
  private void editGymEntry(edu.ntnu.idi.bidata.diary.GymEntry entry) {
    System.out.println("\nEDIT GYM ENTRY - Choose field to edit:");
    System.out.println("1. Exercises");
    System.out.println("2. Sets");
    System.out.println("3. Repetitions");
    System.out.println("4. Weight");
    System.out.println("5. Edit all fields");
    System.out.println("0. Cancel");

    int choice = getIntInput("\nEnter your choice: ");

    try {
      switch (choice) {
        case 1 -> {
          System.out.print("Enter new exercises (comma-separated): ");
          String exercises = scanner.nextLine().trim();
          entry.setExercises(exercises);
          System.out.println("Exercises updated");
        }
        case 2 -> {
          System.out.print("Enter new sets (comma-separated): ");
          String sets = scanner.nextLine().trim();
          entry.setSets(sets);
          System.out.println("Sets updated");
        }
        case 3 -> {
          System.out.print("Enter new repetitions (semicolon-separated sets): ");
          String reps = scanner.nextLine().trim();
          entry.setReps(reps);
          System.out.println("Repetitions updated");
        }
        case 4 -> {
          System.out.print("Enter new exercises (comma-separated): ");
          entry.setExercises(scanner.nextLine().trim());
          System.out.print("Enter new sets (comma-separated): ");
          entry.setSets(scanner.nextLine().trim());
          System.out.print("Enter new repetitions (semicolon-separated sets): ");
          entry.setReps(scanner.nextLine().trim());
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
      printEntry(entry);

    } catch (IllegalArgumentException e) {
      System.out.println("Error updating entry: " + e.getMessage());
    }
  }

  /**
   * Edits a fishing entry's modifiable fields.
   *
   * @param entry The fishing entry to edit.
   */
  private void editFishingEntry(edu.ntnu.idi.bidata.diary.FishingEntry entry) {
    System.out.println("\nEDIT FISHING ENTRY - Choose field to edit:");
    System.out.println("1. Weather");
    System.out.println("2. Fish caught");
    System.out.println("3. Location");
    System.out.println("4. Bait used");
    System.out.println("5. Edit all fields");
    System.out.println("0. Cancel");

    int choice = getIntInput("\nEnter your choice: ");

    try {
      switch (choice) {
        case 1 -> {
          System.out.print("Enter new weather conditions: ");
          String weather = scanner.nextLine().trim();
          entry.setWeather(weather);
          System.out.println("Weather updated");
        }
        case 2 -> {
          System.out.print("Enter new fish caught: ");
          String fishCaught = scanner.nextLine().trim();
          entry.setFishCaught(fishCaught);
          System.out.println("Fish caught updated");
        }
        case 3 -> {
          System.out.print("Enter new location: ");
          String location = scanner.nextLine().trim();
          entry.setLocation(location);
          System.out.println("Location updated");
        }
        case 4 -> {
          System.out.print("Enter new bait used: ");
          String baitUsed = scanner.nextLine().trim();
          entry.setBaitUsed(baitUsed);
          System.out.println("Bait used updated");
        }
        case 5 -> {
          System.out.print("Enter new weather conditions: ");
          entry.setWeather(scanner.nextLine().trim());
          System.out.print("Enter new fish caught: ");
          entry.setFishCaught(scanner.nextLine().trim());
          System.out.print("Enter new location: ");
          entry.setLocation(scanner.nextLine().trim());
          System.out.print("Enter new bait used: ");
          entry.setBaitUsed(scanner.nextLine().trim());
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
      printEntry(entry);

    } catch (IllegalArgumentException e) {
      System.out.println("Error updating entry: " + e.getMessage());
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
    System.out.print("Confirm deletion (y/n): ");
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