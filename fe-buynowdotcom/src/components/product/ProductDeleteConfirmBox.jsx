
import React from 'react'
import { Button, Modal } from 'react-bootstrap'

const ProductDeleteConfirmBox = ({
    showDeleteModal,
    setShowDeleteModal,
    confirmDelete,
    productToDelete
}) => {

    return (
        <>
            <Modal
                show={showDeleteModal}
                onHide={() => setShowDeleteModal(false)}
                centered
            >
                <Modal.Header closeButton>
                    <Modal.Title>Confirm Delete Product</Modal.Title>
                </Modal.Header>

                <Modal.Body>
                    Are you sure you want to delete this product with ID {productToDelete}?
                    <br />
                    <strong>This action cannot be undone.</strong>
                </Modal.Body>

                <Modal.Footer>
                    <Button
                        variant="secondary"
                        onClick={() => setShowDeleteModal(false)}
                    >
                        Cancel
                    </Button>

                    <Button
                        variant="danger"
                        onClick={confirmDelete}
                    >
                        Delete
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    )
}

export default ProductDeleteConfirmBox