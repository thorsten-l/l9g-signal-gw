package l9g.signalgw.crypto;

import ch.qos.logback.core.PropertyDefinerBase;
import l9g.signalgw.Config;

/**
 *
 * @author Thorsten Ludewig (t.ludewig@gmail.com)
 */
public class DecryptionPropertyDefiner extends PropertyDefinerBase
{
  private final AES256 CIPHER = new AES256(Config.getSECRET_KEY());

  @Override
  public String getPropertyValue()
  {
    return CIPHER.decrypt(encryptedValue);
  }

  public void setEncryptedValue(String encryptedValue)
  {
    this.encryptedValue = encryptedValue;
  }

  private String encryptedValue;
}
