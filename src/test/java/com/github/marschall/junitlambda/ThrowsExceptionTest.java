package com.github.marschall.junitlambda;

import static com.github.marschall.junitlambda.ThrowsException.throwsException;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.not;
import static com.github.marschall.junitlambda.HasMessage.hasMessage;
import static com.github.marschall.junitlambda.HasCause.hasCause;

import java.io.IOException;

import org.junit.Test;

public class ThrowsExceptionTest {

  @Test
  public void positiveMatch() {
    assertThat(() -> { throw new NullPointerException();}, throwsException(NullPointerException.class));
  }

  @Test
  public void negativeMatch() {
    assertThat(() -> { throw new NullPointerException();}, not(throwsException(IllegalArgumentException.class)));
  }

  @Test
  public void positiveMessageMatch() {
    assertThat(() -> { throw new IllegalArgumentException("invalid");}, throwsException(IllegalArgumentException.class, hasMessage("invalid")));
  }

  @Test
  public void negativeMessageMatch() {
    assertThat(() -> { throw new IllegalArgumentException("invalid");}, not(throwsException(IllegalArgumentException.class, hasMessage("valid"))));
  }
  
  @Test
  public void positiveCauseMatch() {
    assertThat(this::throwExceptionWithCause, throwsException(RuntimeException.class, hasCause(IOException.class)));
  }

  private void throwExceptionWithCause() {
    try {
      this.throwIoException();
    } catch (IOException e) {
      throw new RuntimeException("io exception occurred", e);
    }
  }

  private void throwIoException() throws IOException {
    throw new IOException("space exceeded");
  }

}
