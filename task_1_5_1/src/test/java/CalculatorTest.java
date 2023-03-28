import org.junit.jupiter.api.Test;
import ru.nsu.fit.trubinov.Calculator;
import ru.nsu.fit.trubinov.number.ComplexNumber;
import ru.nsu.fit.trubinov.parser.StringParser;

import static java.lang.Math.cos;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
    @Test
    void testCalculator() {
        Calculator<StringParser, ComplexNumber> calculator = new Calculator<>(new StringParser("sin + - 1 2 1"));
        assertEquals("(0.0)", calculator.calculate().toString());
        calculator = new Calculator<>(new StringParser("* 5 9.5"));
        assertEquals("(47.5)", calculator.calculate().toString());
        calculator = new Calculator<>(new StringParser("/ 58 7"));
        assertEquals("(" + (58. / 7) + ")", calculator.calculate().toString());
        calculator = new Calculator<>(new StringParser("cos 56.256"));
        assertEquals("(" + cos(56.256) + ")", calculator.calculate().toString());
        calculator = new Calculator<>(new StringParser("log 5"));
        assertEquals("(3.2188758248682006 + i * (1.5707963267948966))", calculator.calculate().toString());
    }
}