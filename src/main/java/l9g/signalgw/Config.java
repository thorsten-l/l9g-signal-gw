package l9g.signalgw;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import lombok.Getter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import l9g.signalgw.crypto.AES256;
import l9g.signalgw.crypto.PasswordDeserializer;
import l9g.signalgw.crypto.PasswordGenerator;
import l9g.signalgw.crypto.PasswordSerializer;

/**
 *
 * @author Thorsten Ludewig (t.ludewig@gmail.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Config
{
  private final static Logger LOGGER = LoggerFactory.getLogger(Config.class.
    getName());

  public final static String CONFIG_FILE = "config.json";

  public final static String SECRET_FILE = "secret.bin";

  private static Config SINGLETON = null;

  @Getter
  @JsonIgnore
  private static byte[] SECRET_KEY = new byte[48];

  @Getter
  @JsonIgnore
  private final static long startTimestamp = System.currentTimeMillis();

  private Config()
  {
  }

  static
  {
    try
    {
      File secretFile = new File(SECRET_FILE);

      if (secretFile.exists())
      {
        LOGGER.info("Loading secret file");
        try (FileInputStream input = new FileInputStream(secretFile))
        {
          input.read(SECRET_KEY);
        }
      }
      else
      {
        LOGGER.info("Writing secret file");

        try (FileOutputStream output = new FileOutputStream(secretFile))
        {
          AES256 aes256 = new AES256();
          SECRET_KEY = aes256.getSecret();
          output.write(SECRET_KEY);
        }

        // file permissions - r-- --- ---
        secretFile.setExecutable(false, false);
        secretFile.setWritable(false, false);
        secretFile.setReadable(false, false);
        secretFile.setReadable(true, true);
      }
    }
    catch (IOException | NoSuchAlgorithmException e)
    {
      LOGGER.error("ERROR: secret file ", e);
      System.exit(-1);
    }
  }

  private static Config initialize()
  {
    ObjectMapper objectMapper = new ObjectMapper();
    Config config = null;

    try
    {
      File configFile = new File(CONFIG_FILE);

      if (configFile.exists())
      {
        LOGGER.info("Loading config file");
        config = objectMapper.readValue(configFile, Config.class);
      }
      else
      {
        LOGGER.info("Writing config file");
        config = new Config();

        config.defaultSignalAccount = "<phone number>";
        config.defaultSignalReceipient = "<phone number | group id>";
        config.defaultSignalReceipientIsGroup = true;
        config.apiToken = PasswordGenerator.generate(32);
        config.webappContextPath = "";
        config.webappServerPort = 8080;
        config.signalCliHost = "localhost";
        config.signalCliPort = 7583;

        objectMapper.writerWithDefaultPrettyPrinter()
          .writeValue(configFile, config);

        // file permissions - rw- --- ---
        configFile.setExecutable(false, false);
        configFile.setWritable(false, false);
        configFile.setReadable(false, false);
        configFile.setReadable(true, true);
        configFile.setWritable(true, true);
      }
    }
    catch (IOException e)
    {
      LOGGER.error("ERROR: Config Properties ", e);
      System.exit(-1);
    }

    return config;
  }

  public static Config getInstance()
  {
    if (SINGLETON == null)
    {
      SINGLETON = initialize();
    }

    return SINGLETON;
  }

  @Getter
  private String webappContextPath;

  @Getter
  private int webappServerPort;

  @Getter
  private String signalCliHost;

  @Getter
  private int signalCliPort;

  @Getter
  @JsonSerialize(using = PasswordSerializer.class)
  @JsonDeserialize(using = PasswordDeserializer.class)
  private String apiToken;

  @Getter
  private String defaultSignalAccount;

  @Getter
  private String defaultSignalReceipient;

  @Getter
  private boolean defaultSignalReceipientIsGroup;
}
