# Java Expression Calculator

A full-stack calculator with a Java backend and a vanilla JavaScript frontend. The application validates mathematical expressions, converts infix notation to postfix notation, and evaluates the result using a custom stack data structure.

## Features

- Addition, subtraction, multiplication, and division
- Decimal and negative numbers
- PEMDAS operator precedence
- Left-to-right evaluation for operators with equal precedence
- Custom bounded stack with a 100-item maximum
- Custom queue and array-list data structures
- JavaScript button and keyboard input
- Java HTTP endpoint for calculations
- Input validation and division-by-zero handling
- JUnit test coverage
- Complete Java documentation

## Technologies

- Java
- JavaScript
- HTML
- CSS
- JUnit 5
- Java HTTP Server
- IntelliJ IDEA

## How It Works

```text
User enters an expression
        ↓
JavaScript builds the expression
        ↓
POST /api/calculate
        ↓
Java validates and tokenizes the expression
        ↓
Infix expression becomes postfix
        ↓
Custom stack evaluates the postfix expression
        ↓
Result returns to JavaScript
```

For example:

```text
Infix:   2 + 3 * 4
Postfix: 2 3 4 * +
Result:  14
```

## Running the Project

1. Clone or download the repository.
2. Open the project in IntelliJ IDEA.
3. Set the project SDK to Java 17 or newer.
4. Open `src/server/CalculatorServer.java`.
5. Run `CalculatorServer.main()`.
6. Open `http://localhost:8080` in a browser.

The IntelliJ console should display:

```text
Calculator running at http://localhost:8080
```

## API

### Calculate an expression

```text
POST /api/calculate
Content-Type: text/plain
```

Example request body:

```text
2 + 3 * 4
```

Example response:

```text
14
```

## Project Structure

```text
src/
├── dataStructures/
│   ├── MyArrayList.java
│   ├── MyQueueList.java
│   └── MyStackList.java
├── model/
│   ├── Expression.java
│   └── Calculation.java
├── server/
│   └── CalculatorServer.java
└── test/
    ├── MyArrayListTest.java
    ├── MyQueueListTest.java
    ├── MyStackListTest.java
    ├── ExpressionTest.java
    └── CalculationTest.java

web/
├── index.html
├── style.css
└── calculator.js
```

## Current Limitations

- Parentheses are not supported yet.
- The server is intended as a learning implementation.
- The custom queue is available for future request processing or calculation history.

## Future Improvements

- Parentheses support
- Calculation history
- A positive/negative button
- Displaying postfix notation
- Maven or Gradle build configuration
- Public deployment

## AI-Assisted Development

This project was created as an AI-assisted learning project. I defined the requirements, reviewed and tested the implementation, studied the algorithms and data structures, and iterated on the project with an AI coding assistant.

## Author

Kevin Munoz-Rivera
