import React from 'react'

const Products = () => {
    return (
        <>
            <div className="d-flex justify-content-center">
                <div className="col-md-6 mt-2">
                    <div className="search-bar input-group">
                        Search comming here....
                    </div>
                </div>
            </div>
            <div className="d-flex ">
                <aside className="sidebar" style={{ width: '250px', padding: '1rem' }}>
                    Sidebar comming here....
                </aside>

                <section style={{ flex: 1, padding: '1rem', }}>
                    Products will be displayed here....
                </section>

                <div className="pagination">
                    pagination comming here....
                </div>
            </div>
        </>

    )
}

export default Products