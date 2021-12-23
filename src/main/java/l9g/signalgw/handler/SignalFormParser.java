package l9g.signalgw.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Thorsten Ludewig (t.ludewig@gmail.com)
 */
public class SignalFormParser
{
  private final static Logger LOGGER = LoggerFactory.getLogger(
    SignalFormParser.class.getName());

  public static String parse(SignalMessage message)
  {
    LOGGER.debug("parse {} {}", message.getForm(), message.getMessage());
    String text = message.getMessage();
    String parsedText = text;

    try
    {
      File formFile = new File("forms" + File.separator + message.
        getForm() + ".txt");

      char[] buffer = new char[(int) formFile.length()];

      FileReader reader = new FileReader(formFile);
      int length = reader.read(buffer);
      reader.close();
      
      LOGGER.debug("length={}, buffer size={}", length, buffer.length);
      
      parsedText = new String(buffer) + "\n" + text;
    }
    catch (Exception ex)
    {
      LOGGER.error("Form error: {}", ex.getMessage());
    }

    return parsedText;
  }

}
