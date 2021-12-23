package l9g.signalgw;

import l9g.signalgw.cli.SignalCliConnection;
import l9g.signalgw.crypto.AES256;
import l9g.signalgw.crypto.PasswordGenerator;
import l9g.signalgw.handler.SignalHandler;
import l9g.signalgw.handler.SignalMessage;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
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

  private final static Options OPTIONS = new Options();

  public static void main(String[] args) throws Exception
  {

    CmdLineParser parser;

    parser = new CmdLineParser(OPTIONS);

    try
    {
      parser.parseArgument(args);
    }
    catch (CmdLineException ex)
    {
      LOGGER.error("Command line error\n");
      parser.printUsage(System.out);
      System.exit(-1);
    }

    if (OPTIONS.isDisplayHelp())
    {
      System.out.println("\nUsage: ./l9g-signal-gw.jar [options]\n");
      parser.printUsage(System.out);
      System.exit(0);
    }

    BuildProperties build = BuildProperties.getInstance();
    LOGGER.info("Project Name    : " + build.getProjectName());
    LOGGER.info("Project Version : " + build.getProjectVersion());
    LOGGER.info("Build Timestamp : " + build.getTimestamp());
    LOGGER.info("Build Profile   : " + build.getProfile() + "\n");

    if (OPTIONS.getGeneratePasswordLength() > 0)
    {
      AES256 cipher = new AES256(Config.getSECRET_KEY());
      String password = PasswordGenerator.generate(OPTIONS.
        getGeneratePasswordLength());
      System.out.println("\npassword:  '" + password + "'");
      System.out.println("encrypted: '" + cipher.encrypt(password) + "'\n");
      System.exit(0);
    }

    if (OPTIONS.getPassword() != null)
    {
      AES256 cipher = new AES256(Config.getSECRET_KEY());
      System.out.println("\npassword:  '" + OPTIONS.getPassword() + "'");
      System.out.println("encrypted: '" + cipher.encrypt(OPTIONS.getPassword())
        + "'\n");
      System.exit(0);
    }

    Config config = Config.getInstance();

    LOGGER.trace("\n\nwebappContextPath={}"
      + "\nwebappServerPort={}"
      + "\napiToken={}"
      + "\ndefaultSignalAccount={}"
      + "\ndefaultSignalReceipient={}"
      + "\ndefaultSignalReceipientIsGroup={}"
      + "\n",
      config.getWebappContextPath(),
      config.getWebappServerPort(),
      config.getApiToken(),
      config.getDefaultSignalAccount(),
      config.getDefaultSignalReceipient(),
      config.isDefaultSignalReceipientIsGroup()
    );

    SignalCliConnection.start();
    SignalHandler.getInstance().sendMessage(new SignalMessage(config.
      getDefaultSignalReceipient(), config.isDefaultSignalReceipientIsGroup(),
      "L9G IoT Signal Gateway started."));

    LOGGER.debug("*** Start Application ***");

    SpringApplication.run(L9gSignalGwApplication.class, args);
  }
}
