import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class VendingMachineTest {

    @Test
    public void constructorStartsEmptyWithZeroBalance() {
        // Arrange
        String[] codes = {"A", "B", "C", "D"};

        // Act
        VendingMachine machine = new VendingMachine();

        // Assert
        assertEquals(0.0, machine.getBalance(), 0.000001);
        for (String code : codes) {
            assertNull(machine.getItem(code));
        }
    }
}