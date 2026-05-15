package net.htoomaungthait.buynowdotcom.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import net.htoomaungthait.buynowdotcom.common.BaseController;
import net.htoomaungthait.buynowdotcom.common.response.ApiResponse;
import net.htoomaungthait.buynowdotcom.dto.resp.UserRespDto;
import net.htoomaungthait.buynowdotcom.model.Cart;
import net.htoomaungthait.buynowdotcom.model.CartItem;
import net.htoomaungthait.buynowdotcom.model.User;
import net.htoomaungthait.buynowdotcom.service.cart.ICartItemService;
import net.htoomaungthait.buynowdotcom.service.cart.ICartService;
import net.htoomaungthait.buynowdotcom.service.user.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/cart-items")
@RequiredArgsConstructor
public class CartItemController extends BaseController {

    private final ICartItemService cartItemService;
    private final IUserService userService;
    private final ICartService cartService;


    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse<CartItem>> addItemToCart(
                                Long userId,
                                @RequestParam Long productId,
                                @RequestParam int quantity){

        User user = userService.getUserMById(userId);
        Cart userCart = cartService.initializeNewCartForUser(user);
        CartItem cartItem = cartItemService.addItemToCart(userCart.getId(), productId, quantity);
        String statusCode = "CARTIM_001";

        return makeResponse(
                HttpStatus.OK.value(),
                statusCode,
                "success",
                getStatusMessageByCode(statusCode),
                cartItem
        );

    }

    @DeleteMapping("cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse<CartItem>> removeItemFromCart(
            @PathVariable @Valid @Min(value = 1, message = "Id must be greater than or equal to 1") Long cartId,
            @PathVariable @Valid @Min(value = 1, message = "Id must be greater than or equal to 1") Long itemId
    ){
            cartItemService.removeItemFromCart(cartId, itemId);
            String statusCode = "CARTIM_002";

            return makeResponse(
                    HttpStatus.OK.value(),
                    statusCode,
                    "success",
                    getStatusMessageByCode(statusCode),
                    null
            );
    }

    @PatchMapping("cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse<CartItem>> updateCartItem(
            @PathVariable @Valid @Min(value = 1, message = "Id must be greater than or equal to 1") Long cartId,
            @PathVariable @Valid @Min(value = 1, message = "Id must be greater than or equal to 1") Long itemId,
            @RequestParam @Valid @Min(value = 1, message = "Id must be greater than or equal to 1") int quantity
    ){
        cartItemService.updateItemQuantity(cartId, itemId, quantity);
        String statusCode = "CARTIM_003";

        return makeResponse(
                HttpStatus.OK.value(),
                statusCode,
                "success",
                getStatusMessageByCode(statusCode),
                null
        );
    }


}
