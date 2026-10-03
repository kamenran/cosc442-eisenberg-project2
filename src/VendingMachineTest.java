import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullSource;

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
@ParameterizedTest
@ValueSource(strings = {"A", "B", "C", "D"})
public void addItemStoresItemInEachSlot(String code) {
    // Arrange
    VendingMachine machine = new VendingMachine();
    VendingMachineItem item = new VendingMachineItem("Snack", 1.50);

    // Act
    machine.addItem(item, code);

    // Assert
    assertNotNull(machine.getItem(code));
    assertSame(item, machine.getItem(code));
    for (String other : new String[]{"A", "B", "C", "D"}) {
        if (!other.equals(code)) {
            assertNull(machine.getItem(other));
        }
    }
}

@Test
public void addItemRejectsOccupiedSlot() {
    // Arrange
    VendingMachine machine = new VendingMachine();
    VendingMachineItem original = new VendingMachineItem("Snack", 1.50);
    machine.addItem(original, "A");
    VendingMachineItem replacement = new VendingMachineItem("Drink", 2.00);

    // Act and Assert
    assertThrows(VendingMachineException.class,
            () -> machine.addItem(replacement, "A"));
    assertSame(original, machine.getItem("A"));
}

@Test
public void getItemReturnsNullForEmptySlot() {
    // Arrange
    VendingMachine machine = new VendingMachine();

    // Act
    VendingMachineItem result = machine.getItem("D");

    // Assert
    assertNull(result);
}

@Test
public void removeItemReturnsItemAndClearsSlot() {
    // Arrange
    VendingMachine machine = new VendingMachine();
    VendingMachineItem item = new VendingMachineItem("Snack", 1.50);
    machine.addItem(item, "D");

    // Act
    VendingMachineItem removed = machine.removeItem("D");

    // Assert
    assertSame(item, removed);
    assertNull(machine.getItem("D"));
}

@Test
public void removeItemRejectsEmptySlot() {
    // Arrange
    VendingMachine machine = new VendingMachine();

    // Act and Assert
    assertThrows(VendingMachineException.class,
            () -> machine.removeItem("A"));
    assertNull(machine.getItem("A"));
}

@Test
public void removedSlotCanBeRefilled() {
    // Arrange
    VendingMachine machine = new VendingMachine();
    machine.addItem(new VendingMachineItem("Snack", 1.50), "A");
    machine.removeItem("A");
    VendingMachineItem replacement = new VendingMachineItem("Drink", 2.00);

    // Act
    machine.addItem(replacement, "A");

    // Assert
    assertSame(replacement, machine.getItem("A"));
}

@ParameterizedTest
@NullSource
@ValueSource(strings = {"E", "a", ""})
public void slotOperationsRejectInvalidCodes(String code) {
    // Arrange
    VendingMachine machine = new VendingMachine();
    VendingMachineItem item = new VendingMachineItem("Snack", 1.50);

    // Act and Assert
    assertAll(
        () -> assertThrows(VendingMachineException.class,
                () -> machine.addItem(item, code)),
        () -> assertThrows(VendingMachineException.class,
                () -> machine.getItem(code)),
        () -> assertThrows(VendingMachineException.class,
                () -> machine.removeItem(code)),
        () -> assertThrows(VendingMachineException.class,
                () -> machine.makePurchase(code))
    );
}
}