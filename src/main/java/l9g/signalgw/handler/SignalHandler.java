package l9g.signalgw.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.StringWriter;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import l9g.signalgw.Config;
import l9g.signalgw.cli.SignalCliMethod;
import l9g.signalgw.cli.SignalCliRequest;
import l9g.signalgw.cli.SignalCliRequestParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Thorsten Ludewig (t.ludewig@gmail.com)
 */
public class SignalHandler implements Runnable
{
  private final static Logger LOGGER = LoggerFactory.getLogger(
    SignalHandler.class.getName());

  private final static SignalHandler SINGLETON = new SignalHandler();

  private SignalHandler()
  {
    handlerThread = new Thread(this);
    handlerThread.setName("SignalHandler");
    handlerThread.setDaemon(true);
    handlerThread.start();
  }

  public static SignalHandler getInstance()
  {
    return SINGLETON;
  }

  public void sendMessage(SignalMessage message)
  {
    LOGGER.debug(">>> {}", message.toString());
    sendingQueue.add(message);
  }

  @Override
  public void run()
  {
    try
    {
      Thread.sleep(2000);
      LOGGER.debug("Handler started");

      while (true)
      {
        try
        {
          SignalMessage message = sendingQueue.poll();

          if (message != null)
          {
            LOGGER.debug("<<< {}", message.toString());

            Config config = Config.getInstance();

            SignalCliRequestParams params = new SignalCliRequestParams(config.
              getDefaultSignalAccount());

            // TODO: Message fill form
            params.setMessage(message.getMessage());

            if (message.isReceipientIsGroup())
            {
              params.setGroupId(message.getReceipient());
            }
            else
            {
              params.setRecipient(message.getReceipient());
            }

            SignalCliRequest request = new SignalCliRequest(SignalCliMethod.send,
              params);

            StringWriter writer = new StringWriter();
            objectMapper.writeValue(writer, request);
            LOGGER.debug("\n\nrequest={}\n", writer.toString());
          }
          else
          {
            Thread.sleep(250); // wait 250ms if queue is empty
          }
        }
        catch (Exception ex)
        {
          LOGGER.error("error", ex);
        }
      }
    }
    catch (InterruptedException ex)
    {
      LOGGER.error("handler startup error ", ex);
    }

  }

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final Thread handlerThread;

  private final Queue<SignalMessage> sendingQueue = new LinkedBlockingQueue<>();
}
