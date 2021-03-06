package l9g.signalgw.handler;

import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

/**
 *
 * @author Thorsten Ludewig (t.ludewig@gmail.com)
 */
public class SignalTemplateProcessor
{
  private final static Logger LOGGER = LoggerFactory.getLogger(SignalTemplateProcessor.class.getName());

  private final static SimpleDateFormat TIMESTAMP_FORMAT
    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public static String process(SignalMessage message)
  {
    LOGGER.debug("process {} {}", message.getTemplate(), message.getMessage());
    String text = message.getMessage();
    String parsedText = text;

    StringTemplateResolver templateResolver = new StringTemplateResolver();
    templateResolver.setOrder(1);
    templateResolver.setTemplateMode(TemplateMode.TEXT);
    templateResolver.setCacheable(false);
    TemplateEngine templateEngine = new TemplateEngine();
    templateEngine.setTemplateResolver(templateResolver);
    Context context = new Context();

    context.setVariable("timestamp", TIMESTAMP_FORMAT.format(
      new Date(message.getTimestamp())));
    context.setVariable("message", message.getMessage());
    context.setVariable("clientname", message.getClientName());
    context.setVariable("remoteaddr", message.getRemoteAddr());
    context.setVariable("entries", message.getKeyValueList());

    try
    {
      File formFile = new File("templates" + File.separator + message.
        getTemplate() + ".txt");

      char[] buffer = new char[(int) formFile.length()];
      FileReader reader = new FileReader(formFile);
      int length = reader.read(buffer);
      reader.close();

      LOGGER.debug("length={}, buffer size={}", length, buffer.length);

      parsedText = templateEngine.process(new String(buffer), context);
      parsedText = parsedText.replaceAll("&quot;", "\"");
    }
    catch (Exception ex)
    {
      LOGGER.error("Form error: {}", ex.getMessage());
    }

    return parsedText;
  }

}
