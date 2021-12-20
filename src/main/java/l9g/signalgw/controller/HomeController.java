package l9g.signalgw.controller;

import l9g.signalgw.BuildProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * 
 * @author Thorsten Ludewig (t.ludewig@gmail.com)
 */
@Controller
public class HomeController
{
  private final static Logger LOGGER = LoggerFactory.getLogger(
    HomeController.class.getName());

  @GetMapping("/kaputt")
  public void kaputt()
  {
    throw new RuntimeException("kaputt");
  }

  @GetMapping("/")
  public String homeGET(Model model)
  {
    LOGGER.debug("homeGET");
    model.addAttribute("build", BuildProperties.getInstance());
    return "home";
  }
}
