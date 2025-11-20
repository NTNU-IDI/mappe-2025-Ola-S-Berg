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

  
}
