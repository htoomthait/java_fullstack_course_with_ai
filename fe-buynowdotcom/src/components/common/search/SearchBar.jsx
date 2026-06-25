import React from 'react'

const SearchBar = () => {
    return (
        <>
            <div className="search-bar input-group input-group-sm">
                <select className="form-control-sm" name="category" id="category">
                    <option value="all">All Categories</option>
                    <option value="tabs">Tabs</option>
                    <option value="gadget">Gadget</option>
                </select>

                <input
                    type="text"
                    className="form-control-sm"
                    placeholder="Search for products..." />

                <button className="search-button btn btn-primary btn-sm"> Clear Filter</button>
            </div>
        </>
    )
}

export default SearchBar