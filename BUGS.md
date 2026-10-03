# Bug Records

## Bug 1: Constructor accesses an invalid array index

- Test: constructorStartsEmptyWithZeroBalance
- Expected: Machine construction succeeds, all four slots are empty,
  and balance is 0.
- Actual: java.lang.ArrayIndexOutOfBoundsException:
  Index 4 out of bounds for length 4.
- Diagnosis: A breakpoint on itemArray[i] = null showed i reaching 4.
  The array only has valid indexes 0–3, but the loop used i <= NUM_SLOTS.
- Fix: Changed the loop condition to i < NUM_SLOTS.
- Verification: Reran the constructor test; it passed.

## Bug 2: Valid deposits below 1 are rejected

- Test: insertMoneyHandlesAmountPartitions
- Expected: Deposits of 0, 0.01, 0.50, and 0.99 are accepted.
- Actual: VendingMachineException:
  Invalid amount.  Amount must be >= 0
- Diagnosis: With amount = 0, stepping over amount < 1 reached
  the exception statement. The condition rejected valid amounts.
- Fix: Changed amount < 1 to amount < 0.
- Verification: The constructor test and all eight deposit cases passed.