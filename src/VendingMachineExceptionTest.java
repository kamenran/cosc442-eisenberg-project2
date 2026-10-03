import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class VendingMachineExceptionTest {

    @Test
    public void defaultExceptionHasNullMessage() {
        // Arrange: use the default constructor

        // Act
        VendingMachineException exception = new VendingMachineException();

        // Assert
        assertNull(exception.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "Invalid code"})
    public void exceptionPreservesMessage(String message) {
        // Arrange: message is supplied by the parameterized test

        // Act
        VendingMachineException exception =
                new VendingMachineException(message);

        // Assert
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void exceptionIsRuntimeException() {
        // Arrange: use the default constructor

        // Act
        VendingMachineException exception = new VendingMachineException();

        // Assert
        assertTrue(exception instanceof RuntimeException);
    }
}