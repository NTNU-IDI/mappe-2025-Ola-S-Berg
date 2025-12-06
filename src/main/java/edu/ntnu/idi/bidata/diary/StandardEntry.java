package edu.ntnu.idi.bidata.diary;

import edu.ntnu.idi.bidata.author.Author;
import java.time.LocalDateTime;

/**
 * <h1>Standard Entry.</h1>
 *
 * <p>Represents a standard free-text diary entry without any specific template fields.
 * This is the default entry type for general diary entries that don't require specialized tracking
 * fields.</p>
 */
public class StandardEntry extends DiaryEntry {

  /**
   * Constructs a new standard diary entry.
   *
   * @param id        The ID of the entry.
   * @param author    The author of the entry.
   * @param timestamp The date and time when the entry was created.
   * @param title     The title of the entry.
   * @param content   The content of the entry.
   * @param category  The category of the entry.
   */
  public StandardEntry(int id, Author author, LocalDateTime timestamp,
      String title, String content, String category) {
    super(id, author, timestamp, title, content, category);
  }

  /**
   * Retrieves the entry type for this class "standard".
   *
   * @return The entry type "standard".
   */
  @Override
  public String getEntryType() {
    return "Standard";
  }
}