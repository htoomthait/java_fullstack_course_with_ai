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
        <tr key={index} className={index % 2 === 0 ? "even-row" : "odd-row"}>
            <td>{index + 1}</td>
            <td>{todo.title}</td>
            <td>{todo.description}</td>
            <td>{todo.dueDate}</td>
            <td>{todo.completed ? "Yes" : "No"}</td>
            <td className='action-group'>
                <button className="button-bw" onClick={() => fnEditTodo(index)}>Edit</button>
                <button className="button-bw" onClick={() => fnDeleteTodo(index)}>Delete</button>
            </td>
        </tr>
    )
}

export default TblRowTodo