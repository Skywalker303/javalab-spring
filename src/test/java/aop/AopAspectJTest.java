package aop;

import lab.model.ApuBar;
import lab.model.Bar;
import lab.model.Person;
import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static commons.Tests.fromSystemOut;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:aop.xml")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class AopAspectJTest {

    Bar bar;

    Person person;

    @NonFinal
    String out;

    @BeforeEach
    void setUp() {
        out = fromSystemOut(() -> bar.sellSquishee(person));
    }

    @Test
    void testBeforeAdvice() {
        assertTrue("Before advice is not good enough...", out.contains("Hello"));
        assertTrue("Before advice is not good enough...", out.contains("How are you doing?"));
    }

    @Test
    void testAfterAdvice() {
        assertTrue("After advice is not good enough...", out.contains("Good Bye!"));
    }

    @Test
    void testAfterReturningAdvice() {
        assertTrue("Customer is broken", out.contains("Good Enough?"));
    }

    @Test
    void testAroundAdvice() {
        assertTrue("Around advice is not good enough...", out.contains("Hi!"));
        assertTrue("Around advice is not good enough...", out.contains("See you!"));
    }

    @Test
    void testAllAdvices() {
        assertFalse(bar instanceof ApuBar, "barObject instanceof ApuBar");
    }
}
