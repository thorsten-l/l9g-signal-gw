package l9g.signalgw.controller;

import l9g.signalgw.BuildProperties;
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
public class BuildController
{
  private final static Logger LOGGER = LoggerFactory.getLogger(
    BuildController.class.getName());

  @GetMapping(path = "/api/build", produces = MediaType.APPLICATION_JSON_VALUE)
  public BuildProperties handleBuildProperties()
  {
    LOGGER.debug("build");
    return BuildProperties.getInstance();
  }
}
