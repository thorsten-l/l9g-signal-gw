package l9g.signalgw.controller;

import com.google.common.base.Strings;
import java.util.AbstractMap.SimpleEntry;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import l9g.signalgw.Config;
import l9g.signalgw.handler.SignalHandler;
import l9g.signalgw.handler.SignalMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Thorsten Ludewig (t.ludewig@gmail.com)
 */
@RestController
public class SendController
{
  private final static Logger LOGGER = LoggerFactory.getLogger(
    SendController.class.getName());

  @GetMapping(path = "/api/send", produces = MediaType.TEXT_PLAIN_VALUE)
  public String handleSendMessage(HttpServletRequest request,
    @RequestParam(name = "c", required = false) String clientName,
    @RequestParam(name = "m", required = false) String messageText,
    @RequestParam(name = "t", required = false) String messageTemplate
  )
  {
    LOGGER.debug("handleSendMessage");

    String remoteAddr = request.getHeader("X-Forward-For");
    if (remoteAddr == null)
    {
      remoteAddr = request.getRemoteAddr();
    }

    String remoteHost = Strings.isNullOrEmpty(clientName)
      ? request.getRemoteHost()
      : clientName;

    String template = Strings.isNullOrEmpty(messageTemplate)
      ? "default"
      : messageTemplate;

    SignalMessage signalMessage = new SignalMessage(Config.
      getInstance().getDefaultSignalReceipient(), true,
      messageText, remoteHost, remoteAddr, template);

    Enumeration<String> pnames = request.getParameterNames();

    while (pnames.hasMoreElements())
    {
      String pn = pnames.nextElement();
      if (!"c".equals(pn) && !"m".equals(pn) && !"t".equals(pn))
      {
        signalMessage.getKeyValueList().add(
          new SimpleEntry<>(pn, request.getParameter(pn)));
      }
    }

    SignalHandler.getInstance().sendMessage(signalMessage);

    return "OK";
  }
}
