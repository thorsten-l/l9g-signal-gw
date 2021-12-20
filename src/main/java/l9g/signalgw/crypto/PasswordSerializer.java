package l9g.signalgw.crypto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import l9g.signalgw.Config;

/**
 *
 * @author Thorsten Ludewig (t.ludewig@gmail.com)
 */
public class PasswordSerializer extends StdSerializer<String>
{
  private final AES256 CIPHER = new AES256(Config.getSECRET_KEY());

  public PasswordSerializer()
  {
    this(null);
  }

  public PasswordSerializer(Class<String> t)
  {
    super(t);
  }

  @Override
  public void serialize(String password, JsonGenerator jsonGenerator, SerializerProvider sp)
    throws IOException
  {
    jsonGenerator.writeString(CIPHER.encrypt(password));
  }
}
