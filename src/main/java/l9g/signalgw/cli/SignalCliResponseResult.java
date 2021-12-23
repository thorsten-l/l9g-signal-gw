package l9g.signalgw.cli;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author Thorsten Ludewig (t.ludewig@gmail.com)
 */
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignalCliResponseResult
{
  @Getter
  private long timestamp;
}
