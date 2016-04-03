package com.viacheslavinozemtsev.cechallenge;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConsoleEMailSenderTest {
    @InjectMocks
    private ConsoleEMailSender eMailSender;

    @Test
    public void testSendEMail() throws Exception {
        final EMail nullEMail = null;
        Assertions.assertThatThrownBy(() -> eMailSender.sendEMail(nullEMail))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("eMail can't be null!");
    }
}