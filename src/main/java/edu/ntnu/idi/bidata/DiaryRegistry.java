package edu.ntnu.idi.bidata;

import java.util.ArrayList;

public class DiaryRegistry {

  private final ArrayList<DiaryEntry> entries;
  private int nextId;

  public DiaryRegistry() {
    this.entries = new ArrayList<>();
    this.nextId = 1;
  }

  public void addEntry(DiaryEntry entry) {
    if (entry == null) {
      throw new IllegalArgumentException("Diary entry cannot be null");
    }
    entries.add(entry);
  }

  public DiaryEntry createAndAddEntry(String author, String title,
      String content, String category) {
    DiaryEntry entry = new DiaryEntry(nextId++, author, java.time.LocalDateTime.now(),
        title, content, category);
    addEntry(entry);
    return entry;
  }

  public DiaryEntry findEntryById(int id) {
    return entries.stream().filter(entry ->
        entry.getId() == id).findFirst().orElse(null);
  }

  public boolean deleteEntry(DiaryEntry entry) {
    if (entry == null) {
      throw new IllegalArgumentException("Diary entry cannot be null");
    }
    return entries.remove(entry);
  }

  public boolean deleteEntryById(int id) {
    DiaryEntry entry = findEntryById(id);
    if (entry != null) {
      return entries.remove(entry);
    }
    return false;
  }

  public int getNumberOfEntries() {
    return entries.size();
  }

  public boolean isEmpty() {
    return entries.isEmpty();
  }

  public int getNextId() {
    return nextId;
  }
}
