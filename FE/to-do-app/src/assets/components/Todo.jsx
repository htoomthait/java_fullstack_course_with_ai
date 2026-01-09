import React from 'react'
import { useState } from 'react';
import TblRowTodo from './TblRowTodo';


const initialInputValue = {
    initFrm: true,
    title: "",
    title_err_msg: "",
    description: "",
    description_err_msg: "",
    dueDate: "",
    due_date_err_msg: "",
    completed: false,
    validate_pass: false
};


export const Todo = () => {

    const [todos, setTodos] = useState([]);
    const [inputValue, setInputValue] = useState(initialInputValue);
    const [editIndex, setEditIndex] = useState(null);
    const [showForm, setShowForm] = useState(false);

    const handleInputOnChange = (e) => {
        const {name, value}  = e.target;
        
        setInputValue(prev => {
            const updatedInput = { ...prev, [name]: value };
            
            return updatedInput;
        });

        console.log(`init form : `+inputValue.initFrm)

        if(inputValue.initFrm === false){
            console.log(`validating form on change after add new...`);
            fnValidateForm();
        }
        

        
    }

    const handleAddTodo = () => {

        setInputValue( prev => ({ ...prev, initFrm: false }) );

        console.log(`btn add  init frm` + inputValue.initFrm);

        console.log(`you clicked add button`)

        if(fnValidateForm() === true){
             console.log(`you added new task named: ${inputValue.title}`)
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

        
        

        
        setShowForm(false);


    }

    const fnValidateForm = () => {
        let validatePass = null;

        console.log(`validating form...`);
        
        
        // validate title is missing
        if(inputValue.title.trim() === ""){
            setInputValue({ ...inputValue, 
                    title_err_msg: "Title is missing!",
                    validate_pass: false
            })
            validatePass = false;
        }else{
             setInputValue({ ...inputValue, 
                    title_err_msg: "",
                    validate_pass: true
            })
            validatePass = true;
        }

        console.log(`validation result: ${validatePass}`);

        return validatePass;
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
                            name="title"
                            value={inputValue.title}
                            onChange={(e) => {
                                //setInputValue({ ...inputValue, title: e.target.value } )
                                //fnValidateForm()

                                handleInputOnChange(e);

                            }}
                        />
                        <span className={`err-msg`} > {inputValue.title_err_msg}</span>
                    </div>

                    <div className="input-block">
                        <label>Description: </label>
                        <input
                            type="text"
                            placeholder="Description"
                            name="description"
                            value={inputValue.description}
                            onChange={(e) => {
                                handleInputOnChange(e);
                            }}
                        />
                        <span className={`err-msg`} > {inputValue.description_err_msg}  </span>
                    </div>

                    <div className="input-block">
                        <label>Due Date: </label>

                        <input
                            type="date"
                            placeholder="Due Date"
                            name="dueDate"
                            value={inputValue.dueDate}
                            onChange={(e) => {
                                handleInputOnChange(e);
                            }}
                        />
                        <span className={`err-msg`} > {inputValue.due_date_err_msg}</span>
                    </div>

                    <div className="input-block">
                        <label>
                            Completed:
                            <input
                                type="checkbox"
                                name="completed"
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
