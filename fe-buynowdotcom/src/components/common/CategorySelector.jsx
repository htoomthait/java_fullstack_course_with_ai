import React, { useEffect } from 'react'
import { useDispatch, useSelector } from 'react-redux';
import { getAllCategories, addCategory } from '../../store/features/categorySlice';

const CategorySelector = ({
    selectedCategory,
    onCategoryChange,
    newCategory,
    showNewCategoryInput,
    setNewCategory,
    setShowNewCategoryInput
}) => {
    const dispatch = useDispatch();
    const categories = useSelector((state) => state.category.categories);

    useEffect(() => {
        dispatch(getAllCategories());
    }, []);

    // useEffect(() => {
    //     console.log("Category selector list: ", categories)
    // }, [categories])

    const handleAddNewCategory = () => {
        if (newCategory !== undefined && newCategory !== '') {
            dispatch(addCategory({ name: newCategory }));
            onCategoryChange(newCategory);
            setShowNewCategoryInput(false);
            setNewCategory('');
        }
    }

    const handleCategoryChange = (e) => {
        if (e.target.value === 'New') {
            setShowNewCategoryInput(true);
        } else {
            setShowNewCategoryInput(false);
            onCategoryChange(e.target.value);
        }
    }

    const handleNewCategoryChange = (e) => {
        setNewCategory(e.target.value);
    }


    return (
        <>
            <div className="mb-3">
                <label className="form-label">Category</label>
                <select
                    className="form-select"
                    required
                    value={selectedCategory}
                    onChange={handleCategoryChange}

                >
                    <option value="">All Categories</option>
                    <option value="New">Add New Category</option>
                    {categories.map((category, index) => (
                        <option key={index} value={category.name}>
                            {category.name}
                        </option>
                    ))}
                </select>
                {showNewCategoryInput && (
                    <div className="input-group">
                        <input
                            type="text"
                            className="form-control"
                            placeholder="Enter new category name"
                            value={newCategory}
                            onChange={handleNewCategoryChange}
                        />
                        <button
                            type="button"
                            className="btn btn-secondary btn-sm"
                            onClick={handleAddNewCategory}>
                            Add Category
                        </button>
                    </div>
                )}
            </div>
        </>
    )
}

export default CategorySelector