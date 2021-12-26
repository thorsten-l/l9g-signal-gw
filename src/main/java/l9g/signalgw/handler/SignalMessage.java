package l9g.signalgw.handler;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
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
    this(receipient, receipientIsGroup, message, "default", "localhost");
  }

  public SignalMessage(String receipient, boolean receipientIsGroup,
    String message, String clientName, String remoteAddr)
  {
    this(receipient, receipientIsGroup, message, clientName, remoteAddr,
      "default");
  }

  public SignalMessage(String receipient, boolean receipientIsGroup,
    String message, String clientName, String remoteAddr, String template)
  {
    this.receipient = receipient;
    this.receipientIsGroup = receipientIsGroup;
    this.message = message;
    this.clientName = clientName;
    this.remoteAddr = remoteAddr;
    this.template = template;
    this.timestamp = System.currentTimeMillis();
    this.keyValueList = new ArrayList<>();
  }

  @Getter
  private long timestamp;

  @Getter
  private String clientName;

  @Getter
  private String remoteAddr;

  @Getter
  private String template;

  @Getter
  private String message;

  @Getter
  private String receipient;

  @Getter
  private boolean receipientIsGroup;

  @Getter
  private List<SimpleEntry<String, String>> keyValueList;
}
