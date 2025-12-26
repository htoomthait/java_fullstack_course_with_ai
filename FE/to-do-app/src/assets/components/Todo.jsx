import React from 'react'
import { useState } from 'react';
import TblRowTodo from './TblRowTodo';


const initialInputValue = {
    title: "",
    description: "",
    dueDate: "",
    completed: false
};


export const Todo = () => {

    const [todos, setTodos] = useState([]);
    const [inputValue, setInputValue] = useState(initialInputValue);
    const [editIndex, setEditIndex] = useState(null);
    const [showForm, setShowForm] = useState(false);

    const handleAddTodo = () => {
        if (inputValue.title.trim() === "") return;
        if (editIndex !== null) {
            const updatedTodos = [...todos];
            updatedTodos[editIndex] = inputValue;
            setTodos(updatedTodos);
            setEditIndex(null);
            setInputValue(initialInputValue);
        } else {
            setTodos([...todos, inputValue]);
            setInputValue(
                initialInputValue);
        }


    }

    const handleDeleteTodo = (index) => {
        const updatedTodos = todos.filter((_, i) => i !== index);
        setTodos(updatedTodos);
    }

    const handleEditTodo = (index) => {
        setInputValue(todos[index]);
        setEditIndex(index);
    }

    const handleToggleCompleted = (index) => {
        const updatedTodos = [...todos];
        updatedTodos[index].completed = !updatedTodos[index].completed;
        setTodos(updatedTodos);
    }

    return (
        <div>
            <h2> Welcome from the to do componment !!</h2>
            <div className='input-form'>
                <div>
                    <div className="input-block">
                        <label>Todo Form</label>
                        <button onClick={() => setShowForm(!showForm)}>{showForm ? "Hide Form" : "Add New"}</button>
                    </div>
                </div>
                <div className={`form-fields ${showForm ? "" : "hidden"}`}>

                    <div className="input-block">
                        <label>Title: </label>
                        <input
                            type="text"
                            placeholder="Title"
                            value={inputValue.title}
                            onChange={(e) => setInputValue({ ...inputValue, title: e.target.value })}
                        />
                    </div>

                    <div className="input-block">
                        <label>Description: </label>
                        <input
                            type="text"
                            placeholder="Description"
                            value={inputValue.description}
                            onChange={(e) => setInputValue({ ...inputValue, description: e.target.value })}
                        />
                    </div>

                    <div className="input-block">
                        <label>Due Date: </label>

                        <input
                            type="date"
                            placeholder="Due Date"
                            value={inputValue.dueDate}
                            onChange={(e) => setInputValue({ ...inputValue, dueDate: e.target.value })}
                        />
                    </div>

                    <div className="input-block">
                        <label>
                            Completed:
                            <input
                                type="checkbox"
                                checked={inputValue.completed}
                                onChange={(e) => setInputValue({ ...inputValue, completed: e.target.checked })}
                            />
                        </label>


                    </div>

                    <button onClick={handleAddTodo}>{editIndex !== null ? "Update Todo" : "Add Todo"}</button>
                </div>
            </div>
            <div className="tbl-container">
                <table>
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Title</th>
                            <th>Description</th>
                            <th>Due Date</th>
                            <th>Completed</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>


                        {todos.map((todo, index) => {
                            return (
                                <TblRowTodo
                                    key={index}
                                    index={index}
                                    todo={todo}
                                    handleEditTodo={handleEditTodo}
                                    handleDeleteTodo={handleDeleteTodo}
                                />
                            )
                        })}

                    </tbody>
                </table>
            </div>

        </div>

    )
}
