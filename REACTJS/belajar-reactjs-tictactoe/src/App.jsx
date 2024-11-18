// import { useState } from 'react'
import reactLogo from "./assets/react.svg";
import "./App.css";

function Square() {
    return <button className="square">X</button>;
}

export default function App() {
    return (
        <>
            <div>
                <img src={reactLogo} className="logo react" alt="React logo" />
            </div>
            <h1>React JS - Tic Tac Toe</h1>
            <div className="card">
                <button>New Game</button>
            </div>

            <div className="board">
                <Square />
                <Square />
                <Square />
                <Square />
                <Square />
                <Square />
                <Square />
                <Square />
                <Square />
            </div>
        </>
    );
}
