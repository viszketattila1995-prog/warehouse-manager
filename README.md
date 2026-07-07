# Warehouse Management

Egy konzolos raktárkezelő szimuláció Java 21-ben, Maven build-del.
A console-based warehouse management simulation written in Java 21, built with Maven.

---

## Magyar

### Leírás

A program egy raktárt szimulál, amelyben beszállítókat és termékeket lehet nyilvántartani,
árut lehet bevételezni és kivételezni, termékek között átvezetni, valamint egy termék
teljes mozgásnaplóját lekérdezni. Minden készletmozgás naplózódik.

### Terméktípusok

- **normal** – alaptermék; a készlete nem mehet 0 alá.
- **preorderable** – előrendelhető termék; a készlete egy megadott hiánylimitig mínuszba mehet.
- **perishable** – romlandó termék; egy szavatossági napszámot tárol.

### Felépítés

- `model` – a termékek (`Product`, `PreorderableProduct`, `PerishableProduct`) és a `Supplier`.
- `transaction` – a `StockMovement` napló-bejegyzés és a `MovementType` enum.
- `service` – a `WarehouseService`, amely a tényleges raktár-műveleteket végzi.
- `engine` – a `WarehouseEngine`, amely beolvassa és értelmezi a parancsokat.
- `ui` – az `UI`, amely a konzol be- és kimenetét kezeli.
- `exception` – az egyedi kivétel-osztályok.

### Indítás

```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.attila.Main"
```

Vagy IntelliJ-ből a `Main` osztály futtatásával.

### Parancsok

A parancs neve és a paraméterek között `|` a határoló, a paraméterek szóközzel elválasztva.

| Parancs | Formátum | Leírás |
|---|---|---|
| beszállító regisztrálása | `registerSupplier \| <név>` | Új beszállító felvétele |
| termék felvétele | `addProduct \| <beszállító> <azonosító> <típus> [extra]` | Új termék (extra: limit vagy napok) |
| bevételezés | `receive \| <azonosító> <mennyiség>` | Készlet növelése |
| kivételezés | `ship \| <azonosító> <mennyiség>` | Készlet csökkentése |
| átvezetés | `transfer \| <forrás> <cél> <mennyiség>` | Áru átmozgatása, 5% kezelési veszteséggel |
| napló | `history \| <azonosító>` | Egy termék mozgásnaplója |
| kilépés | `quit` | Program leállítása |

### Példa

```
registerSupplier | Samsung
addProduct | Samsung 101 normal
receive | 101 50
ship | 101 20
history | 101
quit
```

---

## English

### Description

The program simulates a warehouse where you can register suppliers and products,
receive and ship goods, transfer stock between products, and query the full movement
history of a product. Every stock movement is logged.

### Product types

- **normal** – base product; its stock cannot go below 0.
- **preorderable** – its stock may go negative up to a given overdraft limit.
- **perishable** – stores a shelf-life value in days.

### Structure

- `model` – products (`Product`, `PreorderableProduct`, `PerishableProduct`) and `Supplier`.
- `transaction` – the `StockMovement` log entry and the `MovementType` enum.
- `service` – `WarehouseService`, which performs the actual warehouse operations.
- `engine` – `WarehouseEngine`, which reads and interprets commands.
- `ui` – `UI`, handling console input and output.
- `exception` – the custom exception classes.

### Running

```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.attila.Main"
```

Or run the `Main` class from IntelliJ.

### Commands

Use `|` between the command name and the parameters; separate parameters with spaces.

| Command | Format | Description |
|---|---|---|
| register supplier | `registerSupplier \| <name>` | Add a new supplier |
| add product | `addProduct \| <supplier> <id> <type> [extra]` | Add a product (extra: limit or days) |
| receive | `receive \| <id> <amount>` | Increase stock |
| ship | `ship \| <id> <amount>` | Decrease stock |
| transfer | `transfer \| <source> <goal> <amount>` | Move stock, with a 5% handling loss |
| history | `history \| <id>` | Movement history of a product |
| quit | `quit` | Stop the program |

### Example

```
registerSupplier | Samsung
addProduct | Samsung 101 normal
receive | 101 50
ship | 101 20
history | 101
quit
```

---

Built as an OOP practice project: inheritance, polymorphism, custom exceptions,
a service layer and a command-line interface.
