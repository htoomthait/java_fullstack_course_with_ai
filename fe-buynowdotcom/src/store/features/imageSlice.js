import { createSlice, createAsyncThunk} from '@reduxjs/toolkit';
import { api } from '../../components/services/api';


export const uploadImage = createAsyncThunk(
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

        formdData.append("productId", productId);

        const response = await api.post('images/upload', formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        });

        return response.data.data;
    })


const initialState = {};

const imageSlice = createSlice({
    name: 'image',
    initialState,
    reducers: {},
    extraReducers: (builder) => {
        builder.addCase(uploadImage.fulfilled, (state, action) => {
            state.images = action.payload;
        })
    
    }
});