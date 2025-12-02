package edu.ntnu.idi.bidata.diary;

import edu.ntnu.idi.bidata.author.Author;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <h1>Diary Registry.</h1>
 *
 * <p>Registry for managing a collection of diary entries. This class is responsible for sorting,
 * retrieving, and managing diary entries in the system. Supports multiple entry types including
 * StandardEntry, FishingEntry, and GymEntry.</p>
 */
public class DiaryRegistry {

  private final ArrayList<DiaryEntry> entries;
  private int nextId;

  /**
   * Constructs a new empty diary registry.
   */
  public DiaryRegistry() {
    this.entries = new ArrayList<>();
    this.nextId = 1;
  }

  /**
   * Adds a new diary entry to the registry.
   *
   * @param entry The diary entry to add.
   * @throws IllegalArgumentException If entry is null.
   */
  public void addEntry(DiaryEntry entry) {
    if (entry == null) {
      throw new IllegalArgumentException("Diary entry cannot be null");
    }
    entries.add(entry);
  }

  /**
   * Creates and adds a standard diary entry to the registry.
   *
   * @param author    The author of the entry.
   * @param timestamp The timestamp of the entry.
   * @param title     The title of the entry.
   * @param content   The content of the entry.
   * @param category  The category of the entry.
   * @return The created diary entry.
   */
  public StandardEntry createStandardEntry(Author author, LocalDateTime timestamp,
      String title, String content, String category) {
    StandardEntry entry = new StandardEntry(nextId++, author, timestamp, title, content, category);
    addEntry(entry);
    return entry;
  }

  /**
   * Creates and adds a fishing diary entry to the registry.
   *
   * @param author     The author of the entry.
   * @param timestamp  The timestamp of the entry.
   * @param title      The title of the entry.
   * @param content    The content of the entry.
   * @param category   The category of the entry.
   * @param weather    Weather conditions.
   * @param fishCaught Fish caught.
   * @param location   Fishing location.
   * @param baitUsed   Bait used.
   * @return The created fishing entry.
   */
  public FishingEntry createFishingEntry(Author author, LocalDateTime timestamp,
      String title, String content, String category,
      String weather, String fishCaught,
      String location, String baitUsed) {
    FishingEntry entry = new FishingEntry(nextId++, author, timestamp,
        title, content, category, weather, fishCaught, location, baitUsed);
    addEntry(entry);
    return entry;
  }

  /**
   * Creates and adds a gym diary entry to the registry.
   *
   * @param author    The author of the entry.
   * @param timestamp The timestamp of the entry.
   * @param title     The title of the entry.
   * @param content   The content of the entry.
   * @param category  The category of the entry.
   * @param exercises Exercises performed.
   * @param sets      Number of sets.
   * @param reps      Number of repetitions.
   * @param weight    Weight used.
   * @return The created gym entry.
   */
  public GymEntry createGymEntry(Author author, LocalDateTime timestamp,
      String title, String content, String category,
      String exercises, String sets,
      String reps, String weight) {
    GymEntry entry = new GymEntry(nextId++, author, timestamp,
        title, content, category, exercises, sets, reps, weight);
    addEntry(entry);
    return entry;
  }

  /**
   * Method for creating and adding a generic entry (StandardEntry).
   *
   * @param author    The author of the entry.
   * @param timeStamp The timestamp of the entry.
   * @param title     The title of the entry.
   * @param content   The content of the entry.
   * @param category  The category of the entry.
   * @return The created diary.
   * @deprecated Use createStandardEntry, createFishingEntry, or createGymEntry instead.
   */
  @Deprecated
  public DiaryEntry createAndAddEntry(Author author, LocalDateTime timeStamp,
      String title, String content, String category) {
    return createStandardEntry(author, timeStamp, title, content, category);
  }

  /**
   * Finds a diary entry by its unique ID.
   *
   * @param id The ID of the entry to find.
   * @return The diary entry with the specified ID, or null if not found.
   */
  public DiaryEntry findEntryById(int id) {
    return entries.stream()
        .filter(entry -> entry.getId() == id)
        .findFirst()
        .orElse(null);
  }

  /**
   * Searches for diary entries on a specific date.
   *
   * @param date The date to search for.
   * @return A list of all entries on the specified date.
   * @throws IllegalArgumentException If the date is null.
   */
  public List<DiaryEntry> findEntriesByDate(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException("Date cannot be null");
    }
    return entries.stream()
        .filter(entry -> entry.getTimestamp().toLocalDate().equals(date))
        .collect(Collectors.toList());
  }

  /**
   * Searches for entries by category.
   *
   * @param category The category to search for.
   * @return A list of entries in the specified category.
   * @throws IllegalArgumentException If category is null or empty.
   */
  public List<DiaryEntry> findEntriesByCategory(String category) {
    if (category == null || category.trim().isEmpty()) {
      throw new IllegalArgumentException("Category cannot be null or empty");
    }
    String searchCategory = category.trim();
    return entries.stream()
        .filter(entry -> entry.getCategory().equalsIgnoreCase(searchCategory))
        .collect(Collectors.toList());
  }

  /**
   * Searches for entries by entry type.
   *
   * @param entryType The entry type to search for.
   * @return A list of entries matching the specified type.
   * @throws IllegalArgumentException If entryType is null or empty.
   */
  public List<DiaryEntry> findEntriesByType(String entryType) {
    if (entryType == null || entryType.trim().isEmpty()) {
      throw new IllegalArgumentException("Entry type cannot be null or empty");
    }
    String searchType = entryType.trim();
    return entries.stream()
        .filter(entry -> entry.getEntryType().equalsIgnoreCase(searchType))
        .collect(Collectors.toList());
  }

  /**
   * Deletes a diary entry from the registry.
   *
   * @param entry The entry to delete.
   * @return True if the entry was found and deleted, false otherwise.
   * @throws IllegalArgumentException If the entry is null.
   */
  public boolean deleteEntry(DiaryEntry entry) {
    if (entry == null) {
      throw new IllegalArgumentException("Diary entry cannot be null");
    }
    return entries.remove(entry);
  }

  /**
   * Deletes a diary entry by its ID.
   *
   * @param id The ID of the entry to delete.
   * @return True if the entry was found and deleted, false otherwise.
   */
  public boolean deleteEntryById(int id) {
    DiaryEntry entry = findEntryById(id);
    if (entry != null) {
      return entries.remove(entry);
    }
    return false;
  }

  /**
   * Returns all diary entries sorted by oldest entries first.
   *
   * @return A sorted list of all diary entries.
   */
  public List<DiaryEntry> getAllEntriesSortedAscending() {
    return entries.stream()
        .sorted(Comparator.comparing(DiaryEntry::getTimestamp))
        .collect(Collectors.toList());
  }

  /**
   * Returns all diary entries sorted by newest entries first.
   *
   * @return A sorted list of all diary entries.
   */
  public List<DiaryEntry> getAllEntriesSortedDescending() {
    return entries.stream()
        .sorted(Comparator.comparing(DiaryEntry::getTimestamp).reversed())
        .collect(Collectors.toList());
  }

  /**
   * Gets the total number of entries in the registry.
   *
   * @return The number of entries.
   */
  public int getNumberOfEntries() {
    return entries.size();
  }

  /**
   * Checks if the registry is empty.
   *
   * @return True if the registry contains no entries, false otherwise.
   */
  public boolean isEmpty() {
    return entries.isEmpty();
  }

  /**
   * Gets the next available ID for a new entry.
   *
   * @return The next ID.
   */
  public int getNextId() {
    return nextId;
  }
}