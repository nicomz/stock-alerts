package ar.com.sac.service;

import ar.com.sac.model.Notification;
import ar.com.sac.services.AlertService;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/application-context.xml" })
@WebAppConfiguration
public class AlertServiceTest {
   
   static{
      System.setProperty("http.proxyHost", "wwwafip");
      System.setProperty("http.proxyPort", "80");
      System.setProperty("http.nonProxyHosts", "localhost");
   }
   
   @Autowired
   private AlertService alertService;
   
   @Test
   public void test() {
      List<Notification> notifications = alertService.processAlerts();
      Assert.assertEquals( "61.11842658704150466064675129018723964691162109375", "1");
   }
}
