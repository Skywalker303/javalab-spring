package aop;

import lab.model.Bar;
import lab.model.CustomerBrokenException;
import lab.model.Person;
import lombok.AllArgsConstructor;
import lombok.experimental.NonFinal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static commons.Tests.fromSystemOut;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:aop.xml")
@AllArgsConstructor(onConstructor = @__(@Autowired))
class AopAspectJExceptionTest {

    private Bar bar;

    @NonFinal
    private Person person;

    @BeforeEach
    void setUp() throws Exception {
        person = person.withBroke(true);
//        Tests.setValue2Field(person, "isBroke", true);
    }

    @Test
    void testAfterThrowingAdvice() {
        Assertions.assertThrows(CustomerBrokenException.class, () ->
                assertTrue("Customer is not broken ",
                        fromSystemOut(()-> bar.sellSquishee(person)).contains("Hmmm...")));
    }

//    @AfterEach
//    void tearDown() {
//        Tests.setValue2Field(person, "isBroke", false);
//    }
}