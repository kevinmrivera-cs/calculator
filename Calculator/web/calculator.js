"use strict";

const display = document.querySelector("#display");
const status = document.querySelector("#status");
const buttons = document.querySelector("#buttons");
const operators = new Set(["+", "-", "*", "/"]);

let expression = "";
let showingResult = false;

function isOperator(value) {
    return operators.has(value);
}

function showStatus(message = "", isError = false) {
    status.textContent = message;
    status.classList.toggle("error", isError);
}

function render() {
    display.value = (expression || "0")
        .replaceAll("*", "×")
        .replaceAll("/", "÷");
}

function currentNumber() {
    const parts = expression.split(/[+\-*/]/);
    return parts.at(-1);
}

function appendNumber(number) {
    if (showingResult) {
        expression = "";
        showingResult = false;
    }

    expression += number;
    showStatus();
    render();
}

function appendDecimal() {
    if (showingResult) {
        expression = "";
        showingResult = false;
    }

    if (currentNumber().includes(".")) {
        return;
    }

    if (expression === "" || isOperator(expression.at(-1))) {
        expression += "0";
    }
    expression += ".";
    showStatus();
    render();
}

function appendOperator(operator) {
    if (expression === "") {
        if (operator === "-") {
            expression = "-";
        }
        render();
        return;
    }

    if (expression === "-") {
        return;
    }

    const lastCharacter = expression.at(-1);
    const previousCharacter = expression.at(-2);
    const hasUnaryMinus = lastCharacter === "-"
        && isOperator(previousCharacter);

    if (hasUnaryMinus) {
        expression = expression.slice(0, -2) + operator;
    } else if (operator === "-" && isOperator(lastCharacter)
            && lastCharacter !== "-") {
        expression += operator;
    } else if (isOperator(lastCharacter)) {
        expression = expression.slice(0, -1) + operator;
    } else {
        expression += operator;
    }

    showingResult = false;
    showStatus();
    render();
}

function clearExpression() {
    expression = "";
    showingResult = false;
    showStatus();
    render();
}

function removeLastCharacter() {
    expression = expression.slice(0, -1);
    showingResult = false;
    showStatus();
    render();
}

async function calculate() {
    if (expression === "" || isOperator(expression.at(-1))) {
        showStatus("Finish the expression before calculating.", true);
        return;
    }

    showStatus("Calculating...");

    try {
        const response = await fetch("/api/calculate", {
            method: "POST",
            headers: {"Content-Type": "text/plain; charset=utf-8"},
            body: expression
        });
        const message = await response.text();

        if (!response.ok) {
            throw new Error(message);
        }

        expression = message;
        showingResult = true;
        showStatus("Calculated by the Java backend.");
        render();
    } catch (error) {
        showStatus(error.message || "The server could not calculate this expression.", true);
    }
}

buttons.addEventListener("click", event => {
    const button = event.target.closest("button");
    if (!button) {
        return;
    }

    const action = button.dataset.action;
    const value = button.dataset.value;

    if (action === "number") {
        appendNumber(value);
    } else if (action === "operator") {
        appendOperator(value);
    } else if (action === "decimal") {
        appendDecimal();
    } else if (action === "backspace") {
        removeLastCharacter();
    } else if (action === "clear") {
        clearExpression();
    } else if (action === "calculate") {
        calculate();
    }
});

document.addEventListener("keydown", event => {
    if (/^\d$/.test(event.key)) {
        appendNumber(event.key);
    } else if (isOperator(event.key)) {
        appendOperator(event.key);
    } else if (event.key === ".") {
        appendDecimal();
    } else if (event.key === "Backspace") {
        removeLastCharacter();
    } else if (event.key === "Escape") {
        clearExpression();
    } else if (event.key === "Enter" || event.key === "=") {
        calculate();
    } else {
        return;
    }

    event.preventDefault();
});

render();
