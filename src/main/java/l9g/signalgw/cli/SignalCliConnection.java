package l9g.signalgw.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import l9g.signalgw.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Thorsten Ludewig (t.ludewig@gmail.com)
 */
public class SignalCliConnection implements Runnable
{
  private final static Logger LOGGER = LoggerFactory.getLogger(
    SignalCliConnection.class.getName());

  private final static SignalCliConnection SINGLETON = new SignalCliConnection();

  private final static Config CONFIG = Config.getInstance();
  
  private SignalCliConnection()
  {
    this.monitor = new Thread(this, "SignalCliConnectionMonitor");
    this.monitor.setDaemon(true);
  }

  public static void start()
  {
    SINGLETON.monitor.start();
  }

  public static void send(String jsonRpcMessage)
  {
    SINGLETON.synchronizedSend(jsonRpcMessage);
  }

  @Override
  public void run()
  {
    LOGGER.debug("start signal-cli connection monitor");

    while (true)
    {
      LOGGER.debug("connect to signal-cli jsonrpc service");
      try
      {
        socket = new Socket(CONFIG.getSignalCliHost(), CONFIG.getSignalCliPort());
        signalOut = new PrintWriter(socket.getOutputStream());
        signalIn = new BufferedReader(new InputStreamReader(socket.
          getInputStream()));

        LOGGER.debug("signal-cli connected");

        String line;
        while ((line = signalIn.readLine()) != null)
        {
          SignalCliResponse response = objectMapper.readValue(line, SignalCliResponse.class);
          LOGGER.debug( "response={}", response.toString() );
        }

        signalOut.close();
        signalIn.close();
        LOGGER.debug("signal-cli connection closed");
      }
      catch (Exception e)
      {
        LOGGER.error("signal-cli connection error: {}", e.getMessage());
      }
      finally
      {
        signalIn = null;
        signalOut = null;

        if (socket != null && socket.isConnected())
        {
          try
          {
            socket.close();
          }
          catch (IOException ex)
          {
            LOGGER.error("socket close");
          }
        }
      }

      try
      {
        Thread.sleep(30000);
      }
      catch (Exception e)
      {
        LOGGER.error("{}", e.getMessage());
      }
    }
  }

  private synchronized void synchronizedSend(String jsonRpcMessage)
  {
    if (signalOut != null)
    {
      signalOut.println(jsonRpcMessage);
      signalOut.flush();
    }
  }
  
  private final ObjectMapper objectMapper = new ObjectMapper();

  private Thread monitor;

  private Socket socket;

  private PrintWriter signalOut;

  private BufferedReader signalIn;
}
