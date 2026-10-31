package com.ramesh.ecommerce.service;

import com.ramesh.ecommerce.dto.AddToCartRequest;
import com.ramesh.ecommerce.dto.CartItemResponse;
import com.ramesh.ecommerce.dto.CartResponse;
import com.ramesh.ecommerce.dto.UpdateCartItemRequest;
import com.ramesh.ecommerce.model.Cart;
import com.ramesh.ecommerce.model.CartItem;
import com.ramesh.ecommerce.model.Product;
import com.ramesh.ecommerce.model.User;
import com.ramesh.ecommerce.repository.CartItemRepository;
import com.ramesh.ecommerce.repository.CartRepository;
import com.ramesh.ecommerce.repository.ProductRepository;
import com.ramesh.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    // find existing cart or create new cart
    public Cart getOrCreateCart(String email)
    {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));

        return cartRepository.findByUserId(user.getId()).orElseGet(()->{
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
        });
    }

    // view cart
    @Transactional
    public CartResponse getCart(String email)
    {
        Cart cart=getOrCreateCart(email);
        return converrtCartToCartResponse(cart);
    }

    @Transactional
    public CartResponse addItem(String email, AddToCartRequest request)
    {
        Cart cart=getOrCreateCart(email);
        Product product = productRepository.findById(request.getProductId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found"));

        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId()).orElse(null);

        if(item!=null)
        {
            item.setQuantity(item.getQuantity()+ request.getQuantity());
            cartItemRepository.save(item);
        }
        else
        {
            item = new CartItem();
            item.setProduct(product);
            item.setQuantity(request.getQuantity());
            cart.addItem(item);
            cartItemRepository.save(item);
        }

        return converrtCartToCartResponse(cart);
    }

    @Transactional
    public CartResponse updateQuantity(String email, Long productId, UpdateCartItemRequest request)
    {
        Cart cart = getOrCreateCart(email);
        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(),productId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Product is not present in your cart"));

        item.setQuantity(request.getQuantity());
        cartItemRepository.save(item);
        return  converrtCartToCartResponse(cart);

    }

    @Transactional
    public CartResponse removeItem(String email, Long productId)
    {
        Cart cart = getOrCreateCart(email);
        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(),productId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Product is not present in your cart"));

        cart.removeItem(item);
        cartRepository.save(cart);
        return converrtCartToCartResponse(cart);
    }


    @Transactional
    public CartResponse clearCart(String email)
    {
        Cart cart = getOrCreateCart(email);

        for(CartItem item :  new ArrayList<>(cart.getItems()))
        {
            cart.removeItem(item);
        }

        cartRepository.save(cart);
        return converrtCartToCartResponse(cart);
    }



    public CartResponse converrtCartToCartResponse(Cart cart)
    {
        List<CartItemResponse> items = new ArrayList<>();
        double totalAmount=0.0;

        for(CartItem item : cart.getItems())
        {
            Product product = item.getProduct();
            double unitPrice = product.getId();
            Integer quantity = item.getQuantity();
            double lineTotal = unitPrice*quantity;

            CartItemResponse response = new CartItemResponse(product.getId(), product.getName(), unitPrice,quantity,lineTotal);
            items.add(response);
            totalAmount+=lineTotal;
        }

        return new CartResponse(cart.getId(),items,totalAmount);
    }


}
