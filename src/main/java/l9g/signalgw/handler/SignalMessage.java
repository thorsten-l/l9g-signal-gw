package l9g.signalgw.handler;

import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author Thorsten Ludewig (t.ludewig@gmail.com)
 */
@ToString
public class SignalMessage
{
  public SignalMessage(String receipient, boolean receipientIsGroup,
    String message)
  {
    this(receipient, receipientIsGroup, message, "default");
  }

  public SignalMessage(String receipient, boolean receipientIsGroup,
    String message, String clientName)
  {
    this(receipient, receipientIsGroup, message, clientName, "default");
  }

  public SignalMessage(String receipient, boolean receipientIsGroup,
    String message, String clientName, String form)
  {
    this.receipient = receipient;
    this.receipientIsGroup = receipientIsGroup;
    this.message = message;
    this.clientName = clientName;
    this.form = form;
    this.timestamp = System.currentTimeMillis();
  }

  @Getter
  private long timestamp;

  @Getter
  private String clientName;

  @Getter
  private String form;

  @Getter
  private String message;

  @Getter
  private String receipient;

  @Getter
  private boolean receipientIsGroup;
}
