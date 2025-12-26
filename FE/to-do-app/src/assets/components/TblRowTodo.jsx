import React from 'react'

const TblRowTodo = ({
    index = 0,
    todo = { title: "", description: "", dueDate: "", completed: false },
    handleEditTodo = () => { },
    handleDeleteTodo = () => { }
}) => {


    const fnEditTodo = () => {
        handleEditTodo(index);
    }

    const fnDeleteTodo = () => {
        handleDeleteTodo(index);
    }

    return (
        <tr key={index}>
            <td>{index + 1}</td>
            <td>{todo.title}</td>
            <td>{todo.description}</td>
            <td>{todo.dueDate}</td>
            <td>{todo.completed ? "Yes" : "No"}</td>
            <td className='action-group'>
                <button onClick={() => fnEditTodo(index)}>Edit</button>
                <button onClick={() => fnDeleteTodo(index)}>Delete</button>
            </td>
        </tr>
    )
}

export default TblRowTodo