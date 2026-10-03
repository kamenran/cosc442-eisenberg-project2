import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
@ParameterizedTest
@ValueSource(doubles = {-1.0, -0.01, 0.0, 0.01, 0.50, 0.99, 1.0, 2.0})
public void insertMoneyHandlesAmountPartitions(double amount) {
    // Arrange
    VendingMachine machine = new VendingMachine();
    machine.insertMoney(2.0);

    if (amount < 0) {
        // Act and Assert
        assertThrows(VendingMachineException.class,
                () -> machine.insertMoney(amount));
        assertEquals(2.0, machine.getBalance(), 0.000001);
    } else {
        // Act
        machine.insertMoney(amount);

        // Assert
        assertEquals(2.0 + amount, machine.getBalance(), 0.000001);
    }
}
}