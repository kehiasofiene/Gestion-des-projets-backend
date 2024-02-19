package tn.esprit.gestiondesmanagers.SMS;

import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioInitializer {

    private final TwilioConfiguration twilioConfiguration;

    private final static Logger logger=LoggerFactory.getLogger(TwilioInitializer.class);

    public TwilioInitializer(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
        Twilio.init(twilioConfiguration.getAccountSid(),twilioConfiguration.getAuthToken());
        logger.info("Twilio initialized ...with account sid {} ",twilioConfiguration.getAccountSid());
    }
}
