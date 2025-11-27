package edu.ntnu.idi.bidata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idi.bidata.author.Author;
import edu.ntnu.idi.bidata.author.AuthorRegistry;
import edu.ntnu.idi.bidata.diary.DiaryEntry;
import edu.ntnu.idi.bidata.diary.DiaryRegistry;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiaryEntryTest {

  private LocalDateTime testTime;
  private DiaryRegistry diaryRegistry;
  private AuthorRegistry authorRegistry;

  @BeforeEach
  void setUp() {
    diaryRegistry = new DiaryRegistry();
    authorRegistry = new AuthorRegistry();
    testTime = LocalDateTime.of(2024, 10, 24, 16, 30);
  }

  @Test
  void testCreateValidDiaryEntry() {
    Author author1 = authorRegistry.createAndAddAuthor("Ola Nordmann");
    DiaryEntry entry = new DiaryEntry(1, author1, testTime, "Test day",
        "Today i tested class DiaryEntry", "Tests");

    assertEquals(1, entry.getId());
    assertEquals("Ola Nordmann", entry.getAuthor());
    assertEquals(testTime, entry.getTimestamp());
    assertEquals("Test day", entry.getTitle());
    assertEquals("Today i tested class DiaryEntry", entry.getContent());
    assertEquals("Tests", entry.getCategory());
  }
}
