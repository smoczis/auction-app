package com.example.auctionapp.user;

import com.example.auctionapp.auction.AuctionStatus;
import com.example.auctionapp.exception.ErrorDetails;
import com.example.auctionapp.exception.NoSuchUserException;
import com.example.auctionapp.userAuction.UserAuction;
import com.example.auctionapp.userAuction.UserAuctionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserAuctionService uaService;


    @Autowired
    public UserController(UserService userService, UserAuctionService uaService) {
        this.uaService = uaService;
        this.userService = userService;
    }

    @ApiOperation(value = "Get all users",
            notes = "Helper method for reminding userToken",
            response = UserResponse.class)
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.findAll().stream().map(u -> createResponse(u.getUsername())));
    }

    @ApiOperation(value = "Create new user",
            notes = "For creating new users with unique username")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = UserResponse.class),
            @ApiResponse(code = 400, message = "BAD_REQUEST", response = ErrorDetails.class),
    })
    @PostMapping
    public ResponseEntity<?> createAuthToken(@RequestBody UserDTO user) throws Exception {
        userService.save(user);
        return ResponseEntity.ok(createResponse(user.getUsername()));
    }

    @ApiOperation(value = "Get balance for user",
            notes = "User is being retrieved from db based on user token",
            response = UserResponse.class)
    @GetMapping("{userToken}/balance")
    public ResponseEntity<?> getUserBalance(@PathVariable String userToken) throws NoSuchUserException {
        return ResponseEntity.ok(createResponse(userService.findUserById(userToken).getUsername()));
    }

    @ApiOperation(value = "Deposit BIDs for user",
            notes = "Deposit BIDs for user based on userToken;" +
                    " balance to add is being passed in body as bare Integer")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = UserResponse.class),
            @ApiResponse(code = 400, message = "BAD_REQUEST", response = ErrorDetails.class),
    })
    @PutMapping("{userToken}/balance")
    public ResponseEntity<?> depositBidsForUser(@PathVariable String userToken, @RequestBody Integer bids) throws NoSuchUserException {
        User user = userService.findUserById(userToken);
        userService.updateUserBalance(user.getUsername(), bids);
        return ResponseEntity.ok(createResponse(user.getUsername()));
    }

    @ApiOperation(value = "Get auctions for user",
            notes = "Retrieve all auctions for given userToken; " +
                    "Auctions are ordered by time to finish; Returns custom response")
    @GetMapping("/{userToken}/auctions")
    public ResponseEntity<?> getAuctionsForUser(@PathVariable String userToken) throws NoSuchUserException {
        User user = userService.findUserById(userToken);
        return ResponseEntity.ok(user.getUserAuctions().stream()
                .sorted(Comparator.comparing(UserAuction::getOffered))
                .map(a -> uaService.createAuctionResponse(a.getAuction())));
    }

    @ApiOperation(value = "Get won auctions for user",
            notes = "Retrieve all finished auctions won by user; " +
                    "Auctions are ordered by time to finish; Returns custom response")
    @GetMapping("/{userToken}/auctions/won")
    public ResponseEntity<?> getWonAuctions(@PathVariable String userToken) throws NoSuchUserException {
        User user = userService.findUserById(userToken);
        return ResponseEntity.ok(user.getUserAuctions().stream()
                .filter(ua -> ua.getUser().getId().equals(userToken) && ua.getAuction().getStatus() == AuctionStatus.FINISHED)
                .sorted(Comparator.comparing(UserAuction::getOffered))
                .map(a -> uaService.createAuctionResponse(a.getAuction())));
    }

    private UserResponse createResponse(String username) {
        User user = userService.findUserByUsername(username);
        return new UserResponse(username, user.getBids(), user.getId());
    }
}
