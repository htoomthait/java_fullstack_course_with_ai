package net.htoomaungthait.buynowdotcom.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import net.htoomaungthait.buynowdotcom.common.BaseController;
import net.htoomaungthait.buynowdotcom.common.response.ApiResponse;
import net.htoomaungthait.buynowdotcom.dto.response.OrderRespDto;
import net.htoomaungthait.buynowdotcom.model.Order;
import net.htoomaungthait.buynowdotcom.service.order.IOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
public class OrderController extends BaseController {

    private final IOrderService orderService;

    @PostMapping("/user/order")
    public ResponseEntity<ApiResponse<OrderRespDto>> placeOrder(@RequestParam @Valid @Min(value = 1, message = "Id must be greater than or equal to 1")  Long userId){

        Order order = orderService.placeOrder(userId);
        String statusCode = "ORD_001";

        return makeResponse(
                HttpStatus.CREATED.value(),
                statusCode,
                "success",
                getStatusMessageByCode(statusCode),
                OrderRespDto.fromOrder(order)
        );


    }

    @GetMapping("/user/{userId}/order")
    public ResponseEntity<ApiResponse<List<Order>>> getUserOrders(
            @PathVariable @Valid @Min(value = 1, message = "Id must be greater than or equal to 1")  Long userId)
    {
        List<Order> userOrderList = orderService.getUserOrder(userId);
        String statusCode = !userOrderList.isEmpty() ? "ORD_002" : "ORD_003";

        return makeResponse(
                HttpStatus.OK.value(),
                statusCode,
                "success",
                getStatusMessageByCode(statusCode),
                userOrderList

        );
    }

}
