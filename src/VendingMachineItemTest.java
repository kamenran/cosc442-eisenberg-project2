import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class VendingMachineItemTest {

    @Test
    public void itemPreservesNameAndPrice() {
        // Arrange
        String name = "Snack";
        double price = 1.50;

        // Act
        VendingMachineItem item = new VendingMachineItem(name, price);

        // Assert
        assertEquals(name, item.getName());
        assertEquals(price, item.getPrice(), 0.000001);
    }

    @Test
    public void itemAllowsZeroPrice() {
        // Arrange
        double price = 0.0;

        // Act
        VendingMachineItem item = new VendingMachineItem("Free sample", price);

        // Assert
        assertEquals(price, item.getPrice(), 0.000001);
    }

    @Test
    public void itemRejectsNegativePrice() {
        // Arrange
        double price = -0.01;

        // Act and Assert
        VendingMachineException exception = assertThrows(
                VendingMachineException.class,
                () -> new VendingMachineItem("Snack", price));
        assertEquals("Price cannot be less than zero", exception.getMessage());
    }
}