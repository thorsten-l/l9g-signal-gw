package l9g.signalgw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

/**
 *
 * @author Thorsten Ludewig (t.ludewig@gmail.com)
 */
@Component
public class WebServerCustomizer
  implements WebServerFactoryCustomizer<TomcatServletWebServerFactory>
{
  
  private final static Logger LOGGER = LoggerFactory.getLogger(
    WebServerCustomizer.class.getName());
  
  @Override
  public void customize(TomcatServletWebServerFactory factory)
  {
    LOGGER.debug("customize");
    Config config = Config.getInstance();
    factory.setPort(config.getWebappServerPort());
    factory.setContextPath(config.getWebappContextPath());
  }
}
