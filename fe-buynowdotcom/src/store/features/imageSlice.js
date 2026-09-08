import { createSlice, createAsyncThunk} from '@reduxjs/toolkit';
import { api } from '../../components/services/api';


/* export const uploadImages = createAsyncThunk(
    'image/uploadImage', async (productId, files) => {
        const formData = new FormData();

        if(Array.isArray(files)){
            files.forEach((file) => {
                formData.append("images", file)
            });    
        }
        else{
            formData.append("images", files);
        }

        formData.append("productId", productId);

        

        const response = await api.post('images/upload', formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        });

        return response.data.data;
    }) */

export const uploadImages = createAsyncThunk(
    "image/uploadImage",
    async ({ productId, files }, { rejectWithValue }) => {
        try {
            const formData = new FormData();

            files.forEach((file) => {
                formData.append("images", file);
            });

            formData.append("productId", String(productId));

            // Check exactly what will be sent
            for (const [key, value] of formData.entries()) {
                console.log(
                    "FormData:",
                    key,
                    value
                );
            }

            const response = await api.post(
                "/images/upload",
                formData
            );

            return response.data.data;

        } catch (error) {
            console.log(
                "Status:",
                error.response?.status
            );

            console.log(
                "Backend response:",
                error.response?.data
            );

            return rejectWithValue(
                error.response?.data || error.message
            );
        }
    }
);

export const updateProductImage = createAsyncThunk(
    "images/updateProductImage",
    async({productId, imageId, file}, {rejectWithValue}) => {

        try{
            const formData = new FormData();
            formData.append("file", file);
            formData.append("productId", String(productId));


            const response = await api.put(
                `/images/${imageId}`,
                formData
            );

            return response.data.data;


        }catch(error){
            console.log(
                "Status:",
                error.response?.status
            );
            console.log(
                "Backend response:",
                error.response?.data
            );
             return rejectWithValue(
                error.response?.data || error.message
            );

        }
    }
);   


const initialState = {
    isLoading : false,
    images: [],
    errorMessage: null,
};

const imageSlice = createSlice({
    name: 'image',
    initialState,
    reducers: {},
    extraReducers: (builder) => {
        builder.addCase(uploadImages.fulfilled, (state, action) => {
            state.images = action.payload;
            state.isLoading = false;
            state.errorMessage = null;
        })
        .addCase(uploadImages.pending, (state) => {
            state.isLoading = true;
            state.errorMessage = null;
        })
        .addCase(uploadImages.rejected, (state, action) => {
            state.isLoading = false;
            state.errorMessage = action.payload || 'Failed to upload images';
        })
        .addCase(updateProductImage.fulfilled, (state, action) => {
            const updatedImage = action.payload;
            // const index = state.images.findIndex(image => image.id === updatedImage.id);
            // if (index !== -1) {
            //     state.images[index] = updatedImage;
            // }
            state.images = [...state.images.filter(image => image.id !== updatedImage.id), updatedImage];
            state.isLoading = false;
            state.errorMessage = null;
        })
        .addCase(updateProductImage.pending, (state) => {
            state.isLoading = true;
            state.errorMessage = null;
        })
        .addCase(updateProductImage.rejected, (state, action) => {
            state.isLoading = false;
            state.errorMessage = action.payload || 'Failed to update image';
        });
    
    }
});


export default imageSlice.reducer;