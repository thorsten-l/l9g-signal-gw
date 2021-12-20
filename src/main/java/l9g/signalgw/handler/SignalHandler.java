package l9g.signalgw.handler;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
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
  }

  public static SignalHandler getInstance()
  {
    if (!SINGLETON.handlerThread.isAlive())
    {
      LOGGER.debug("Starting Handler");
      SINGLETON.handlerThread.start();
    }
    return SINGLETON;
  }

  public void sendMessage(SignalMessage message)
  {
    sendingQueue.add(message);
  }

  @Override
  public void run()
  {
    while (true)
    {
      try
      {
        SignalMessage message = sendingQueue.poll();
        if ( message != null )
        {
          LOGGER.debug(message.toString());
        }
        Thread.sleep(100);
      }
      catch (InterruptedException ex)
      {
        LOGGER.error("error", ex);
      }
    }
  }

  private final Thread handlerThread;

  private final Queue<SignalMessage> sendingQueue = new LinkedBlockingQueue<>();
}
