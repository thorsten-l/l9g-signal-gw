package l9g.signalgw.cli;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.UUID;
import lombok.Getter;
import lombok.ToString;

/**
 *
 * @author Thorsten Ludewig (t.ludewig@gmail.com)
 */
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignalCliRequest
{

  public SignalCliRequest( SignalCliMethod method, String account )
  {
    this( method, new SignalCliRequestParams(account) );
  }
    
  public SignalCliRequest( SignalCliMethod method, SignalCliRequestParams params )
  {
    this.jsonrpc = "2.0";
    this.id = UUID.randomUUID().toString();
    this.method = method;
    this.params = params;
  }
  
  @Getter
  private final String jsonrpc;
  
  @Getter
  private final String id;
  
  @Getter
  private final SignalCliMethod method;
  
  @Getter
  private final SignalCliRequestParams params;
}
