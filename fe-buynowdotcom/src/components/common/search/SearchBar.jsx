import React from 'react'

const SearchBar = ({
    value,
    onChange
}) => {
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
                    value={value}
                    onChange={onChange}
                    placeholder="Search for products...(e.g. tv)" />

                <button className="search-button btn btn-primary btn-sm"> Clear Filter</button>
            </div>
        </>
    )
}

export default SearchBar