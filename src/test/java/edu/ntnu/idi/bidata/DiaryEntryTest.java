package edu.ntnu.idi.bidata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiaryEntryTest {

  private LocalDateTime testTime;

  @BeforeEach
  void setUp() {
    testTime = LocalDateTime.of(2024, 10, 24, 16, 30);
  }

  @Test
  void testCreateValidDiaryEntry() {
    DiaryEntry entry = new DiaryEntry(1, "Ola Syrstad Berg", testTime, "Test day",
        "Today i tested class DiaryEntry", "Tests");

    assertEquals(1, entry.getId());
    assertEquals("Ola Syrstad Berg", entry.getAuthor());
    assertEquals(testTime, entry.getTimestamp());
    assertEquals("Test day", entry.getTitle());
    assertEquals("Today i tested class DiaryEntry", entry.getContent());
    assertEquals("Tests", entry.getCategory());
  }
}
