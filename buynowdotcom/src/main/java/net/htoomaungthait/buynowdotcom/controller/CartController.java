package net.htoomaungthait.buynowdotcom.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.htoomaungthait.buynowdotcom.common.BaseController;
import net.htoomaungthait.buynowdotcom.common.response.ApiResponse;
import net.htoomaungthait.buynowdotcom.dto.response.CartDetailDto;
import net.htoomaungthait.buynowdotcom.service.cart.ICartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("${api.prefix}/carts")
@RequiredArgsConstructor
public class CartController extends BaseController {

    private final ICartService cartService;

    @GetMapping("/user/{userId}/cart")
    public ResponseEntity<ApiResponse<CartDetailDto>> getUserCart(
            @PathVariable @Valid @Min(value = 1, message = "Id must be greater than or equal to 1") Long userId){

        String statusCode = "CART_001";

        return makeResponse(
                HttpStatus.OK.value(),
                statusCode,
                "success",
                getStatusMessageByCode(statusCode),
                cartService.getCartDetailByUserId(userId)
        );

    }


    @DeleteMapping("/cart/{cartId}/clear")
    public ResponseEntity<ApiResponse<String>> clearCart(
            @PathVariable @Valid @Min(value = 1, message = "Id must be greater than or equal to 1") Long cartId ){

        log.info("Clearing cart {}", cartId);
        cartService.clearCart(cartId);
        String statusCode = "CART_002";

        return makeResponse(
                HttpStatus.OK.value(),
                statusCode,
                "success",
                getStatusMessageByCode(statusCode),
                null

        );
    }

}
