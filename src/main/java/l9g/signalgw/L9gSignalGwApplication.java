package l9g.signalgw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author Thorsten Ludewig (t.ludewig@gmail.com)
 */
@SpringBootApplication
public class L9gSignalGwApplication
{
  private final static Logger LOGGER = LoggerFactory.getLogger(
    L9gSignalGwApplication.class.getName());

  public static void main(String[] args)
  {
    BuildProperties build = BuildProperties.getInstance();
    LOGGER.info("Project Name    : " + build.getProjectName());
    LOGGER.info("Project Version : " + build.getProjectVersion());
    LOGGER.info("Build Timestamp : " + build.getTimestamp());
    LOGGER.info("Build Profile   : " + build.getProfile() + "\n");

    SpringApplication.run(L9gSignalGwApplication.class, args);
  }
}
