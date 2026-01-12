import React from 'react'
import { useState } from 'react';
import TblRowTodo from './TblRowTodo';


const initialInputValue = {
    initFrm: true,
    title: "",
    description: "",
    dueDate: "",
    completed: false,
};

const initInputErrMsg = {
    title_err_msg: "",
    description_err_msg: "",
    due_date_err_msg: ""
}


export const Todo = () => {

    const [todos, setTodos] = useState([]);
    const [inputValue, setInputValue] = useState(initialInputValue);
    const [editIndex, setEditIndex] = useState(null);
    const [showForm, setShowForm] = useState(false);
    const [errorMessages, setErrorMessages] = useState(initInputErrMsg);

    const handleInputOnChange = (e) => {
        const {name, value}  = e.target;
        
        setInputValue(prev => {
            const updatedInput = { ...prev, [name]: value };
            
            return updatedInput;
        });

        if(inputValue.initFrm === false){

            fnValidateForm();
        }
        

        
    }

    const handleAddTodo = () => {

        setInputValue( prev => ({ ...prev, initFrm: false }) );

        


        if(fnValidateForm() === true){
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

            setShowForm(false);
        }

        
        

        
        


    }

    const fnValidateForm = () => {
        let validatePass = true;

        const errorMessages = {...initInputErrMsg};

        
        
        // validate title is missing
        if(!inputValue.title.trim()){
            errorMessages.title_err_msg = "Title is missing!";
            validatePass = false;
        }else{
            errorMessages.title_err_msg = "";
            validatePass = true;
        }

        // validate description is missing
        if(!inputValue.description.trim()){
            errorMessages.description_err_msg = "Description is missing!";
            validatePass = false;
        }else{
            errorMessages.description_err_msg = "";
            validatePass = true;
        }

        // validate due date is missing
        if(!inputValue.dueDate.trim()){
            errorMessages.due_date_err_msg = "Due date is missing!";
            validatePass = false;
        }else{
            errorMessages.due_date_err_msg = "";
            validatePass = true;
        }

        setErrorMessages( prev => ({ ...prev, ...errorMessages }) );

        return validatePass;
    }

    const handleDeleteTodo = (index) => {
        const updatedTodos = todos.filter((_, i) => i !== index);
        setTodos(updatedTodos);
    }

    const handleEditTodo = (index) => {
        console.log("Editing todo at index:", index);
        const inputToEdit = todos[index];
        console.log("inputToEdit:", inputToEdit);

        setInputValue(prev => 
            ({ ...prev, 
                title: inputToEdit.title,
                description: inputToEdit.description,
                dueDate: inputToEdit.dueDate,
                completed: inputToEdit.completed,
            })
        );
        setEditIndex(index);
        setShowForm(true);
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
                               
                                handleInputOnChange(e);

                            }}
                        />
                        <span className={`err-msg`} > {errorMessages.title_err_msg}</span>
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
                        <span className={`err-msg`} > {errorMessages.description_err_msg}  </span>
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
                            onBlur={(e) => {
                                handleInputOnChange(e);
                            }}
                        />
                        <span className={`err-msg`} > {errorMessages.due_date_err_msg}</span>
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
