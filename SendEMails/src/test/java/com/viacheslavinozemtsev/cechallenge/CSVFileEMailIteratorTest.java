package com.viacheslavinozemtsev.cechallenge;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;

@RunWith(MockitoJUnitRunner.class)
public class CSVFileEMailIteratorTest {
    @Mock
    private BufferedReader in;
    @InjectMocks
    private CSVFileEMailIterator csvFileEMailIterator;

    @Test
    public void testHasNext() throws Exception {
        final String line = "sender8@gmail.com,rec8@gmail.com,Subject8,Body8,0";

        Whitebox.setInternalState(csvFileEMailIterator, "eof", false);
        Whitebox.setInternalState(csvFileEMailIterator, "line", null);
        Whitebox.setInternalState(csvFileEMailIterator, "in", in);
        Mockito.when(in.readLine()).thenReturn(line);

        Assertions.assertThat(csvFileEMailIterator.hasNext()).isTrue();
        Assertions.assertThat((boolean) Whitebox.getInternalState(csvFileEMailIterator, "eof")).isFalse();
        Assertions.assertThat(Whitebox.getInternalState(csvFileEMailIterator, "line")).isNotNull();
        Mockito.verify(in, Mockito.times(1)).readLine();

        Assertions.assertThat(csvFileEMailIterator.hasNext()).isTrue();
        Assertions.assertThat((boolean) Whitebox.getInternalState(csvFileEMailIterator, "eof")).isFalse();
        Assertions.assertThat(Whitebox.getInternalState(csvFileEMailIterator, "line")).isNotNull();
        Mockito.verify(in, Mockito.times(1)).readLine();
    }

    @Test
    public void testHasNextEOF() throws Exception {
        Whitebox.setInternalState(csvFileEMailIterator, "eof", false);
        Whitebox.setInternalState(csvFileEMailIterator, "line", null);
        Whitebox.setInternalState(csvFileEMailIterator, "in", in);
        Mockito.when(in.readLine()).thenReturn(null);

        Assertions.assertThat(csvFileEMailIterator.hasNext()).isFalse();
        Assertions.assertThat((boolean) Whitebox.getInternalState(csvFileEMailIterator, "eof")).isTrue();
        Assertions.assertThat(Whitebox.getInternalState(csvFileEMailIterator, "line")).isNull();
    }

    @Test
    public void testHasNextAfterEOF() throws Exception {
        Whitebox.setInternalState(csvFileEMailIterator, "eof", true);

        Assertions.assertThat(csvFileEMailIterator.hasNext()).isFalse();
    }

    @Test
    public void testNext() throws Exception {
        final String sender = "sender8@gmail.com";
        final String recipients = "rec8@gmail.com";
        final String subject = "Subject8";
        final String body = "Body8";
        final boolean active = true;
        final String line = sender + "," + recipients + "," + subject + "," + body + "," + (active ? 1 : 0);

        Whitebox.setInternalState(csvFileEMailIterator, "eof", false);
        Whitebox.setInternalState(csvFileEMailIterator, "line", null);
        Whitebox.setInternalState(csvFileEMailIterator, "in", in);
        Mockito.when(in.readLine()).thenReturn(line);

        final EMail eMail = csvFileEMailIterator.next();
        Assertions.assertThat(eMail).isNotNull();
        Assertions.assertThat(eMail.getSender()).isEqualTo(sender);
        Assertions.assertThat(eMail.getRecipients()).isEqualTo(recipients);
        Assertions.assertThat(eMail.getSubject()).isEqualTo(subject);
        Assertions.assertThat(eMail.getBody()).isEqualTo(body);
        Assertions.assertThat(eMail.isActive()).isEqualTo(active);

        Mockito.verify(in, Mockito.times(1)).readLine();
        Assertions.assertThat(Whitebox.getInternalState(csvFileEMailIterator, "line")).isNull();
    }

    @Test
    public void testNextAfterHasNext() throws Exception {
        final String sender = "sender8@gmail.com";
        final String recipients = "rec8@gmail.com";
        final String subject = "Subject8";
        final String body = "Body8";
        final boolean active = true;
        final String line = sender + "," + recipients + "," + subject + "," + body + "," + (active ? 1 : 0);

        Whitebox.setInternalState(csvFileEMailIterator, "eof", false);
        Whitebox.setInternalState(csvFileEMailIterator, "line", line);

        final EMail eMail = csvFileEMailIterator.next();
        Assertions.assertThat(eMail).isNotNull();
        Assertions.assertThat(eMail.getSender()).isEqualTo(sender);
        Assertions.assertThat(eMail.getRecipients()).isEqualTo(recipients);
        Assertions.assertThat(eMail.getSubject()).isEqualTo(subject);
        Assertions.assertThat(eMail.getBody()).isEqualTo(body);
        Assertions.assertThat(eMail.isActive()).isEqualTo(active);

        Mockito.verify(in, Mockito.never()).readLine();
        Assertions.assertThat(Whitebox.getInternalState(csvFileEMailIterator, "line")).isNull();
    }

    @Test
    public void testNextCantParseLine() throws Exception {
        final String line = "sender8@gmail.com";

        Whitebox.setInternalState(csvFileEMailIterator, "eof", false);
        Whitebox.setInternalState(csvFileEMailIterator, "line", line);

        Assertions.assertThatThrownBy(() -> csvFileEMailIterator.next())
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Couldn't parse line in the storage file!");

        Mockito.verify(in, Mockito.never()).readLine();
    }

    @Test
    public void testNextAfterEOF() throws Exception {
        Whitebox.setInternalState(csvFileEMailIterator, "eof", true);

        Assertions.assertThatThrownBy(() -> csvFileEMailIterator.next())
                .isInstanceOf(RuntimeException.class)
                .hasMessage("EOF was reached!");
    }
}