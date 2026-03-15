# SOLID Principles — Reading Guide

This repo demonstrates each SOLID principle with a **ProblematicCode** folder (what to avoid)
and a **BetterCode** folder (the correct approach). Read the problematic code first to
understand the pain point, then the better code to see the fix.

---

## Example 1 — Single Responsibility Principle (SRP)

> **"A class should have only one reason to change."**

### Problematic Code — `Example1/ProblematicCode/`

| File | Problem |
|------|---------|
| `Employee.java` | One class doing everything — it holds data, computes salary, prints reports, and manages persistence. |

**What goes wrong:** `Employee` has four reasons to change:
- Pay rules change → edit `computeSalary()`
- Report format changes → edit `printPerformanceReport()`
- Storage changes → edit `updateEmployeeData()` / `fetchEmployeeData()`
- Data fields change → edit the fields themselves

Every unrelated change risks breaking the whole class.

---

### Better Code — `Example1/BetterCode/`

| Step | File | What to read |
|------|------|--------------|
| 1 | `Employee.java` | Data-only class — holds id, name, address with getters/setters. Nothing else. |
| 2 | `EmployeeSalaryCalculator.java` | Isolated salary logic — only reason to change is a pay-rule update. |
| 3 | `EmployeePerformanceReportGenerator.java` | Isolated report logic — only reason to change is a report format update. |

**Key insight:** Each class now has exactly one reason to change. If salary rules change,
only `EmployeeSalaryCalculator` is touched. If the report format changes, only
`EmployeePerformanceReportGenerator` is touched. `Employee` never changes for either reason.

---

## Example 2 — Open/Closed Principle (OCP)

> **"Classes should be open for extension but closed for modification."**

### Problematic Code — `Example2/ProblematicCode/`

| File | Problem |
|------|---------|
| `NotificationType.java` | An enum that owns all four sending methods — SMS, Email, Push, WhatsApp all crammed into one place. |
| `NotificationSender.java` | A switch statement that manually routes each enum value to the right method. |

**What goes wrong:** Every time a new channel is added (e.g. Slack):
1. A new constant must be added to the enum.
2. A new method must be added to the enum.
3. A new `case` must be added to the switch in `NotificationSender`.

Two existing classes must be modified — violating OCP.

---

### Better Code — `Example2/BetterCode/`

| Step | File | What to read |
|------|------|--------------|
| 1 | `Notifier.java` | The abstraction — defines the `send(String message)` contract. Read the comments at the top for the full rationale. |
| 2 | `SMSNotifier.java` | Concrete implementation for SMS. |
| 3 | `EmailNotifier.java` | Concrete implementation for Email. |
| 4 | `PushNotifier.java` | Concrete implementation for Push notifications. |
| 5 | `WhatsAppNotifier.java` | Concrete implementation for WhatsApp. |
| 6 | `NotificationSender.java` | The sender — loops over a `List<Notifier>` and calls `send()`. No switch needed. |

**Key insight:** To add Slack, create `SlackNotifier implements Notifier`. `NotificationSender`
requires zero changes — it is closed for modification but open for extension.

---

## Example 3 — Liskov Substitution Principle (LSP)

> **"Objects of a subclass should be substitutable for objects of the superclass
> without altering the correctness of the program."**

### Problematic Code — `Example3/ProblematicCode/`

| File | Problem |
|------|---------|
| `Rectangle.java` | Base class with independent `setWidth()` and `setHeight()`. Callers expect `area = width × height`. |
| `Square.java` | Extends `Rectangle` but both setters secretly overwrite each other to keep sides equal. |
| `AreaCalculator.java` | Calls `setWidth(5)` then `setHeight(3)` and expects area = 15. Run `main()` to see it print 9 for a Square. |

**What goes wrong:** Passing a `Square` where a `Rectangle` is expected silently produces
wrong results. The subclass breaks the parent's contract — LSP is violated.

```
rect.setWidth(5);
rect.setHeight(3);
rect.getArea(); // Rectangle → 15 ✓   Square → 9 ✗
```

---

### Better Code — `Example3/BetterCode/`

| Step | File | What to read |
|------|------|--------------|
| 1 | `Shape.java` | Common abstraction — a `getArea()` contract every shape must honour. |
| 2 | `Rectangle.java` | Implements `Shape` independently. Width and height are private and can never be interfered with. |
| 3 | `Square.java` | Implements `Shape` independently — does NOT extend `Rectangle`. Enforces its own invariant. |
| 4 | `AreaCalculator.java` | Accepts any `Shape`. Run `main()` to see both substitutions produce correct results. |

**Key insight:** LSP is about behavioural contracts, not real-world "is-a" relationships.
A Square is mathematically a Rectangle, but it is not a valid behavioural subtype of
a mutable Rectangle. Removing the forced hierarchy and sharing an interface instead fixes this.

---

## Example 4 — Interface Segregation Principle (ISP)

> **"Clients should not be forced to depend on interfaces they do not use."**

This example also uses the **Strategy Pattern** for reward calculation.

### Problematic Code — `Example4/ProblematicCode/`

| File | Problem |
|------|---------|
| `CreditCard.java` | One fat interface with all features — payments, rewards, travel perks, and business tools. |
| `BasicCard.java` | Only needs `makePayment()` but must implement 6 other methods that all throw `UnsupportedOperationException`. |
| `TravelCard.java` | Has no use for business methods but is forced to implement `generateExpenseReport()` and `addEmployeeCard()`. |
| `BusinessCard.java` | Has no use for travel methods but is forced to implement `accessLounge()` and `getTravelInsurance()`. |

**What goes wrong:** Every card is polluted with methods it cannot support. Any change to the
fat interface forces every card class to be recompiled and potentially edited, even if the
change is irrelevant to that card.

---

### Better Code — `Example4/BetterCode/`

#### Step 1 — Read the segregated interfaces

| File | Purpose |
|------|---------|
| `Payable.java` | Every card implements this — the universal minimum. |
| `Rewardable.java` | Only reward-capable cards implement this. |
| `TravelBenefits.java` | Only travel-tier cards implement this. |
| `ExpenseManagement.java` | Only business-tier cards implement this. |

#### Step 2 — Read the reward strategies (Strategy Pattern)

| File | Purpose |
|------|---------|
| `RewardStrategy.java` | Strategy contract — defines `calculate(amount)` and `rewardType()`. |
| `CashbackStrategy.java` | 1% cashback per dollar. |
| `MilesStrategy.java` | 2 air miles per dollar. |
| `PointsStrategy.java` | 3 loyalty points per dollar. |

#### Step 3 — Read the card implementations

| File | Implements | Reward Strategy |
|------|-----------|-----------------|
| `BasicCard.java` | `Payable` only | None |
| `PremiumCard.java` | `Payable`, `Rewardable` | Injected (e.g. `CashbackStrategy`) |
| `TravelCard.java` | `Payable`, `Rewardable`, `TravelBenefits` | Injected (e.g. `MilesStrategy`) |
| `BusinessCard.java` | `Payable`, `Rewardable`, `ExpenseManagement` | Injected (e.g. `PointsStrategy`) |

#### Step 4 — Run the demo

Open `Main.java` and run `main()`. It demonstrates:
- Each card doing only what it supports — no exceptions thrown.
- Strategy being swapped at runtime (TravelCard switching from miles to points at the end).

**Key insight:** No card is ever forced to implement a feature it does not support.
Adding a new card type (e.g. `StudentCard implements Payable, Rewardable`) requires
zero changes to any existing class. The strategy pattern also makes reward logic
swappable without touching the card classes.

---

## Example 5 — Dependency Inversion Principle (DIP)

> **"High-level modules should not depend on low-level modules. Both should depend on
> abstractions. Abstractions should not depend on details. Details should depend on abstractions."**

### Problematic Code — `Example5/ProblematicCode/`

| File | Problem |
|------|---------|
| `MySQLDatabase.java` | Low-level concrete class for MySQL persistence. |
| `EmailService.java` | Low-level concrete class for email notifications. |
| `OrderService.java` | High-level business logic that directly instantiates both concrete classes with `new`. |

**What goes wrong:** `OrderService` hard-codes its dependencies:

```java
private MySQLDatabase database = new MySQLDatabase();    // ← concrete
private EmailService emailService = new EmailService();  // ← concrete
```

- Switching to PostgreSQL requires editing `OrderService`.
- Switching to SMS requires editing `OrderService`.
- Unit testing requires a real database and a real email server — there is no way to inject fakes.
- High-level business logic is tightly coupled to low-level infrastructure choices.

---

### Better Code — `Example5/BetterCode/`

#### Step 1 — Read the abstractions

| File | Purpose |
|------|---------|
| `OrderRepository.java` | Abstraction for data persistence — `saveOrder(orderId, item)`. |
| `NotificationService.java` | Abstraction for notifications — `sendConfirmation(orderId)`. |

#### Step 2 — Read the low-level implementations (details)

| File | Purpose |
|------|---------|
| `MySQLOrderRepository.java` | Implements `OrderRepository` for MySQL. |
| `PostgreSQLOrderRepository.java` | Implements `OrderRepository` for PostgreSQL. |
| `EmailNotificationService.java` | Implements `NotificationService` via email. |
| `SMSNotificationService.java` | Implements `NotificationService` via SMS. |

#### Step 3 — Read the high-level module

| File | Purpose |
|------|---------|
| `OrderService.java` | Business logic only. Depends on `OrderRepository` and `NotificationService` interfaces injected via constructor. Never uses `new` on a concrete class. |

#### Step 4 — Read the composition root

| File | Purpose |
|------|---------|
| `Main.java` | The only place that knows about concrete classes. Wires abstractions to implementations. Run `main()` to see MySQL+Email in Setup 1 and PostgreSQL+SMS in Setup 2 — with zero changes to `OrderService`. |

**Key insight:** DIP flips the dependency arrow. Instead of `OrderService → MySQLDatabase`,
both depend on the `OrderRepository` abstraction: `OrderService → OrderRepository ← MySQLDatabase`.
The high-level module is now fully insulated from infrastructure decisions. Swapping a database
or notification channel is a one-line change in `Main.java`.

```
Without DIP:  OrderService ──depends on──▶ MySQLDatabase (concrete)
With DIP:     OrderService ──depends on──▶ OrderRepository (interface) ◀── MySQLDatabase
```

---

## Quick Reading Order (All Examples)

```
── Example 1 (SRP) ──────────────────────────────────────────────────────────
ProblematicCode/Employee.java                  ← one class doing everything
BetterCode/Employee.java                       ← data only
BetterCode/EmployeeSalaryCalculator.java       ← salary only
BetterCode/EmployeePerformanceReportGenerator  ← report only

── Example 2 (OCP) ──────────────────────────────────────────────────────────
ProblematicCode/NotificationType.java          ← enum with all methods
ProblematicCode/NotificationSender.java        ← switch statement
BetterCode/Notifier.java                       ← interface
BetterCode/SMSNotifier → Email → Push → WhatsApp → NotificationSender

── Example 3 (LSP) ──────────────────────────────────────────────────────────
ProblematicCode/Rectangle.java + Square.java   ← broken inheritance
ProblematicCode/AreaCalculator.java            ← run main() to see wrong output
BetterCode/Shape.java                          ← interface
BetterCode/Rectangle.java + Square.java        ← independent implementations
BetterCode/AreaCalculator.java                 ← run main() to see correct output

── Example 4 (ISP) ──────────────────────────────────────────────────────────
ProblematicCode/CreditCard.java                ← fat interface
ProblematicCode/BasicCard → TravelCard → BusinessCard  ← forced throws
BetterCode/Payable → Rewardable → TravelBenefits → ExpenseManagement
BetterCode/RewardStrategy → Cashback → Miles → Points
BetterCode/BasicCard → PremiumCard → TravelCard → BusinessCard
BetterCode/Main.java                           ← run main()

── Example 5 (DIP) ──────────────────────────────────────────────────────────
ProblematicCode/MySQLDatabase.java + EmailService.java   ← concrete low-level
ProblematicCode/OrderService.java              ← hard-codes new MySQLDatabase()
BetterCode/OrderRepository.java               ← abstraction for persistence
BetterCode/NotificationService.java           ← abstraction for notifications
BetterCode/MySQLOrderRepository → PostgreSQLOrderRepository  ← low-level details
BetterCode/EmailNotificationService → SMSNotificationService ← low-level details
BetterCode/OrderService.java                  ← depends on abstractions only
BetterCode/Main.java                          ← run main() to see both setups
```
