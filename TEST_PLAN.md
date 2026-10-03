# Test Plan

I will use JUnit 5 to test all 3 classes, with tests focused
on VendingMachine. Expected results come from  supplied documentation.

| Behavior | Valid cases | Invalid cases | Boundary cases | Expected result / oracle | Related tests |
|---|---|---|---|---|---|
| Create machine | New machine | N/A: no input | Four empty slots; zero balance | Construction succeeds; A–D contain null; balance is 0 | constructorStartsEmptyWithZeroBalance |
| Add/get items | Add and retrieve items in A–D | Occupied slot; invalid code | First/last slots; empty slot | Stores and returns the same item; empty slot returns null; invalid actions throw VendingMachineException | addItemStoresItemInEachSlot, addItemRejectsOccupiedSlot, getItemReturnsNullForEmptySlot, slotOperationsRejectInvalidCodes |
| Remove items | Remove stored item | Empty slot; invalid code | Remove twice; refill removed slot | Returns the item and clears the slot; invalid actions throw VendingMachineException | removeItemReturnsItemAndClearsSlot, removeItemRejectsEmptySlot, removedSlotCanBeRefilled, slotOperationsRejectInvalidCodes |
| Deposit money | Positive amounts; repeated deposits | Negative amounts | Zero; small amounts below 1 | Nonnegative deposits increase balance; negative deposits throw VendingMachineException and preserve balance | insertMoneyHandlesAmountPartitions, insertMoneyAccumulatesDeposits |
| Read balance | Read after deposits | N/A: no input | Initial balance 0 | Returns balance without changing it | getBalanceDoesNotChangeBalance |
| Buy items | Enough money | Invalid code | Below/equal/above price; free item; empty slot | Success returns true, removes item, deducts price; insufficient funds or empty slot returns false and preserves state; invalid code throws VendingMachineException | purchaseWithEnoughMoneyRemovesItemAndDeductsPrice, purchaseWithExactBalanceSucceeds, purchaseWithInsufficientMoneyPreservesState, purchaseOfFreeItemSucceeds, purchaseFromEmptySlotPreservesBalance, slotOperationsRejectInvalidCodes |
| Return change | Available balance | N/A: no input | Zero balance; repeated calls | Returns previous balance and resets it to 0 | returnChangeReturnsBalanceAndResetsIt, returnChangeFromEmptyBalanceReturnsZero |
| Item constructor/getters | Name and positive price | Negative price | Zero price; -0.01 | Preserves name and price; negative price throws VendingMachineException | itemPreservesNameAndPrice, itemAllowsZeroPrice, itemRejectsNegativePrice |
| Exception constructors | Default or supplied message | N/A: no invalid input defined | Null/empty messages | Default message is null; supplied message is preserved; extends RuntimeException | defaultExceptionHasNullMessage, exceptionPreservesMessage, exceptionIsRuntimeException |

## Parameterized tests

insertMoneyHandlesAmountPartitions will test eight amounts:
-1.0, -0.01, 0.0, 0.01, 0.50, 0.99, 1.0, and 2.0.

TCovers the negative invalid partition, the zero boundary, small
positive deposits below 1, and larger valid deposits. 
Each checks the resulting balance or the exception and unchanged balance.

Valid-slot tests will be covering A, B, C, and D.
slotOperationsRejectInvalidCodes will test "E", "a", "", and null
for adding, getting, removing, and purchasing. Null is seen as an
invalid code because it is not A–D.

## Test design

I will create VendingMachineTest, VendingMachineItemTest, and
VendingMachineExceptionTest.

Tests will have descriptive names and Arrange–Act–Assert sections.
I will use assertEquals for values, assertSame for item identity,
assertNull/assertNotNull for slots, assertTrue/assertFalse for purchases,
and assertThrows for invalid actions. Double comparisons will use
a tolerance of 0.000001.

Null items and nonfinite monetary values are outside this plan because
the documentation does not define their behavior.

## Bug records and sensitivity check

I will then record each discovered fault in BUGS.md, including the failing
test, observed result, debugger diagnosis, and correction.

After all tests pass, I will temp. change the purchase comparison
from >= to > and mark it:
// INJECTED FAULT FOR TEST VALIDATION

I will rerun tests and record the failing test names, exact messages,
and why they detect the fault. I will commit and push the temporary
fault, then restore the comparison, rerun all tests, and commit and
push the passing final version.

## GitHub

I will commit this plan before implementing tests. I will make
meaningful commits for tests, fixes and bug records, the injected
fault, and the restored passing implementation.