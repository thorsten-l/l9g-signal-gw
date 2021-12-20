package l9g.signalgw.crypto;

import java.util.Random;

/**
 *
 * @author Thorsten Ludewig (t.ludewig@gmail.com)
 */
public class PasswordGenerator
{
  private final static char[] PWCHARS =
  {
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '.', '!', '#', '%',
    '/', '?', '+', '*', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
    'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B',
    'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'R', 'S', 'T',
    'U', 'V', 'W', 'X', 'Y', 'Z', '$', '&', '<', '>'
  };

  private final static PasswordGenerator SINGLETON = new PasswordGenerator();
  private final Random random;
  
  private PasswordGenerator()
  {
    random = new Random(System.currentTimeMillis());
  }

  public static String generate(int length)
  {
    char[] pwd = new char[length];
    
    for( int i=0; i<length; i++ )
    {
      pwd[i] = PWCHARS[SINGLETON.random.nextInt(PWCHARS.length)];
    }
    
    return String.valueOf(pwd);
  }
}

