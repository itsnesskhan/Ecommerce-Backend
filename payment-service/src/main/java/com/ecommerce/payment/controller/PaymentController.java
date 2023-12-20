package com.ecommerce.payment.controller;

import com.ecommerce.payment.model.PaymentInfo;
import com.ecommerce.payment.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RequestMapping("/api/payment")
@Controller
@RequiredArgsConstructor
public class PaymentController {


    @Autowired
    private final Environment env;

    @Autowired
    private final PaymentRepository paymentRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String payment(Model model){
        model.addAttribute("rzp_key_id", env.getProperty("rzp_key_id"));
        model.addAttribute("rzp_currency", env.getProperty("rzp_currency"));
        model.addAttribute("rzp_company_name", env.getProperty("rzp_company_name"));
        return "payment";

    }

    @GetMapping("/createOrderId/{amount}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> createPaymentOrderId(@PathVariable String amount) {
        Map<String, String> response = new HashMap<>();
        try {
            RazorpayClient razorpay = new RazorpayClient(env.getProperty("rzp_key_id"), env.getProperty("rzp_key_secret"));
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount);
            orderRequest.put("currency", env.getProperty("rzp_currency"));
            orderRequest.put("receipt", "order_rcptid_11");

            Order order = razorpay.orders.create(orderRequest);
            String orderId = order.get("id");

            response.put("orderId", orderId);

            return ResponseEntity.ok(response);
        } catch (RazorpayException e) {
            e.printStackTrace();
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @RequestMapping(value = {"/success/{amount}/{contactCount}/{companyName}/{currency}/{description}"}, method = RequestMethod.POST)
    public String paymentSuccess(Model model,
                                 @RequestParam("razorpay_payment_id") String razorpayPaymentId,
                                 @RequestParam("razorpay_order_id") String razorpayOrderId,
                                 @RequestParam("razorpay_signature") String razorpaySignature,
                                 @PathVariable Float amount,
                                 @PathVariable Integer contactCount,
                                 @PathVariable String companyName,
                                 @PathVariable String currency,
                                 @PathVariable String description
    ){
        System.out.println("Save all data, which on success we get!");
        System.out.println("signature "+razorpaySignature);


        return "redirect:/api/payment/";
    }
}