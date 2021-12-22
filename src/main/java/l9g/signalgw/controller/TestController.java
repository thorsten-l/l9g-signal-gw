package l9g.signalgw.controller;

import l9g.signalgw.BuildProperties;
import l9g.signalgw.Config;
import l9g.signalgw.handler.SignalHandler;
import l9g.signalgw.handler.SignalMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Thorsten Ludewig (t.ludewig@gmail.com)
 */
@RestController
public class TestController
{
  private final static Logger LOGGER = LoggerFactory.getLogger(
    TestController.class.getName());

  @GetMapping(path = "/api/test", produces = MediaType.APPLICATION_JSON_VALUE)
  public BuildProperties handleTest()
  {
    LOGGER.debug("test");
    
    SignalHandler.getInstance().sendMessage(new SignalMessage(Config.
      getInstance().getDefaultSignalReceipient(), true,
      "Test " + System.currentTimeMillis()));
    
    return BuildProperties.getInstance();
  }
}
