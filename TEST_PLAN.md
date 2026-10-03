# Test Plan

Tests use JUnit 5 and Arrange–Act–Assert. Expected results come from
the supplied documentation. Double comparisons use a 0.000001 tolerance.

| Behavior | Valid cases | Invalid cases | Boundary cases | Expected result / oracle | Related tests |
|---|---|---|---|---|---|
| Initialization | New machine | N/A: no inputs | Four empty slots; balance 0 | Construction succeeds, A–D contain null, balance is 0 | constructorStartsEmptyWithZeroBalance |
| Add items | Add to A–D | Occupied slot; invalid code | First and last slots | Stores the same item without affecting other slots; invalid operations throw VendingMachineException | addItemStoresItemInEachSlot, addItemRejectsOccupiedSlot, slotOperationsRejectInvalidCodes |
| Get items | Read occupied or empty slot | Invalid code | A and D | Returns stored item or null | getItemReturnsNullForEmptySlot, addItemStoresItemInEachSlot, slotOperationsRejectInvalidCodes |
| Remove items | Remove stored item; refill slot | Empty slot; invalid code | Remove twice | Returns same item and empties slot; invalid operations throw VendingMachineException | removeItemReturnsItemAndClearsSlot, removeItemRejectsEmptySlot, removedSlotCanBeRefilled, slotOperationsRejectInvalidCodes |
| Insert money | Nonnegative amounts; repeated deposits | Negative amounts | -0.01, 0, 0.01; amounts below 1 | Adds valid deposits; invalid deposits throw VendingMachineException and preserve balance | insertMoneyHandlesAmountPartitions, insertMoneyAccumulatesDeposits |
| Read balance | Read after deposits | N/A: no inputs | Initial balance 0 | Returns balance without changing it | getBalanceDoesNotChangeBalance |
| Purchase | Sufficient funds | Invalid code | Below/equal/above price; free item; empty slot | Success returns true, removes item, deducts price; insufficient funds or empty slot returns false and preserves state; invalid code throws VendingMachineException | purchaseWithEnoughMoneyRemovesItemAndDeductsPrice, purchaseWithExactBalanceSucceeds, purchaseWithInsufficientMoneyPreservesState, purchaseFromEmptySlotPreservesBalance, purchaseOfFreeItemSucceeds, slotOperationsRejectInvalidCodes |
| Return change | Deposited or remaining balance | N/A: no inputs | Zero balance; repeated calls | Returns previous balance and resets it to 0 | returnChangeReturnsBalanceAndResetsIt, returnChangeFromEmptyBalanceReturnsZero |
| Item constructor/getters | Name and positive price | Negative price | Zero and -0.01 | Preserves name/price; negative price throws VendingMachineException | itemPreservesNameAndPrice, itemAllowsZeroPrice, itemRejectsNegativePrice |
| Exception constructors | Default and supplied message | N/A: no defined invalid inputs | Null/empty messages | Default message is null; supplied message is preserved; extends RuntimeException | defaultExceptionHasNullMessage, exceptionPreservesMessage, exceptionIsRuntimeException |

## Parameterized inputs

insertMoneyHandlesAmountPartitions uses eight inputs:
-1.0, -0.01, 0.0, 0.01, 0.50, 0.99, 1.0, and 2.0.

These cover the negative invalid partition, zero boundary, small positive
deposits below 1, and larger valid deposits. Each invocation checks the
expected balance or exception and unchanged balance.

Valid-slot tests cover A, B, C, and D. Invalid-code tests cover "E",
"a", "", and null for add, get, remove, and purchase. Null is treated
as invalid because it is not A–D.

## Test structure and scope

Create VendingMachineTest, VendingMachineItemTest, and
VendingMachineExceptionTest, with most tests targeting VendingMachine.
Use descriptive names and Arrange–Act–Assert.

Use assertEquals for values, assertSame for item identity,
assertNull/assertNotNull for slots, assertTrue/assertFalse for purchases,
and assertThrows for invalid operations.

Null items and nonfinite monetary values are outside this plan because
their behavior is not specified.

## Fault tracking and sensitivity

Record discovered faults in BUGS.md: failing test, observed result,
debugger diagnosis, and correction.

Once all tests pass, temporarily change the purchase comparison from
>= to > and mark it:
// INJECTED FAULT FOR TEST VALIDATION

Rerun tests and record the failing tests, exact messages, and why they
detect the fault. Commit and push the temporary fault, then remove it,
rerun all tests, and commit and push the passing final version.

## GitHub sequence

Commit and push this plan before implementing tests. Make separate,
meaningful commits for tests, fault corrections and BUGS.md, the
temporary injected fault, and the final restored implementation.