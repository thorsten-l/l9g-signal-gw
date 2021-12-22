package l9g.signalgw.cli;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Thorsten Ludewig (t.ludewig@gmail.com)
 */
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignalCliRequestParams
{
  public SignalCliRequestParams( String account )
  {
    this.account = account;
  }
  
  @Getter
  private final String account;

  @Getter
  @Setter
  private String recipient;

  @Getter
  @Setter
  private String[] recipients;

  @Getter
  @Setter
  private String groupId;

  @Getter
  @Setter
  private String message;
}
