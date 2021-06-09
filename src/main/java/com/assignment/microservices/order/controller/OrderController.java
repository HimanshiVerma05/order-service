package com.assignment.microservices.order.controller;

import java.time.Instant;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.assignment.microservices.order.repository.OrderRepository;
import com.assignment.microservices.order.service.INotificationService;
import com.assignment.microservices.order.controller.feign.ServiceProxy;
import com.assignment.microservices.order.controller.feign.UserServiceProxy;
import com.assignment.microservices.order.entity.Order;
import com.assignment.microservices.order.dao.Services;
import com.assignment.microservices.order.dao.User;
import com.assignment.microservices.order.enums.EventEnum;
import com.assignment.microservices.order.enums.OrderStatus;

@RestController
public class OrderController {
   
	@Value("${user.service.url}")
	private String userServiceUrl;
	
	@Value("${services.service.url}")
	private String servicesUrl;
	
	 /** The notification service. */
    @Autowired
    private INotificationService notificationService;

	 @Autowired
	    private RestTemplate template;
	 
	@Autowired
	private UserServiceProxy userproxy;
	
	@Autowired
	private ServiceProxy serviceproxy;
	
	@Autowired
	private Environment env;
	
	@Autowired
	OrderRepository orderRepository;
	
	@GetMapping("getAllOrders")
	public List<Order> getAllOrders()
	{
		
			return orderRepository.findAll();
		
	}
	
	@GetMapping("getOrderById/{id}")
	public ResponseEntity<Order> getOrderById( @PathVariable(value = "id") Long orderId)
	{
		Optional<Order> orderFind = orderRepository.findById(orderId);
		
		Order order=new Order();
		if(orderFind.isPresent())
		{
			order=orderFind.get();
			return ResponseEntity.ok().body(order);
		}
		return null;
	}
	
	@PutMapping(value = "updateProviderId/{orderId}/{providerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <Order> updateProviderId(@PathVariable(value = "orderId") Long orderId,
			@PathVariable(value = "providerId") Long providerId)
	{
		 Optional<Order> orderFind = orderRepository.findById(orderId);
			Order order=new Order();
			if(orderFind.isPresent())
			{
				order=orderFind.get();
				order.setProviderId(providerId);
				order.setLastupdateTimeStamp(Calendar.getInstance());
				//get user email
				
				String user_email= getUserEmail(order.getUserId());
				//sending notification to user 
				try {
				sendNotificationForOrderStatus(EventEnum.PROVIDER_ASSIGNED,user_email);
			}
			catch(Exception ex) {
				System.out.println("error in pushing to rabbit mq");
			}
				final Order updatedOrder = orderRepository.save(order);
			    return ResponseEntity.ok(updatedOrder);
			}
			return null;
	}
	@PutMapping(value = "updateOrderStatus/{id}/{orderStatus}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <Order> updateOrderStatus(@PathVariable(value = "id") Long orderId,
			@PathVariable(value = "orderStatus") OrderStatus orderStatus)
	{
       Optional<Order> orderFind = orderRepository.findById(orderId);
		Order order=new Order();
		if(orderFind.isPresent())
		{
			order=orderFind.get();
			order.setOrderStatus(orderStatus);
			order.setLastupdateTimeStamp(Calendar.getInstance());
			//get user email
			
			String user_email= getUserEmail(order.getUserId());
			//sending notification to user 
			//CREATED,CONFIRMED,UNCONFIRMED,IN_TRANSIT, SHIPPED, DELIVERED.
			try {
			if(orderStatus.equals(OrderStatus.CREATED))
			{
				sendNotificationForOrderStatus(EventEnum.ORDER_PLACED_NOTIFICATION,user_email);
			}
			else if(orderStatus.equals(OrderStatus.CONFIRMED))
			{
				sendNotificationForOrderStatus(EventEnum.ORDER_CONFIRMED_NOTIFICATION,user_email);
			}
			else if(orderStatus.equals(OrderStatus.CANCELLED))
			{
				sendNotificationForOrderStatus(EventEnum.ORDER_CANCELLED_NOTIFICATION,user_email);
			}
			
			else if(orderStatus.equals(OrderStatus.COMPLETED))
			{
				sendNotificationForOrderStatus(EventEnum.ORDER_PLACED_NOTIFICATION,user_email);
			}
			}
			catch(Exception ex) {
				System.out.println("error in pushing to rabbit mq");
			}
			
			final Order updatedOrder = orderRepository.save(order);
		    return ResponseEntity.ok(updatedOrder);
			
		}
		return null;
	}
	
	@PostMapping(value = "createOrder", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Long createOrder(@RequestBody Order newOrder)
	{
  	
		System.out.println("inside method plcae order");
		Order order = new Order();
		
		order.setAddress(newOrder.getAddress());
		//get Amount from service webservice and then set.HIMANSHI
		
		order.setAmount(getServicePrice(newOrder.getService()));
		
		order.setCreationTimeStamp(Calendar.getInstance());
		order.setTotalQty(newOrder.getTotalQty());
		order.setRemarks(newOrder.getRemarks());
		order.setService(newOrder.getService());
		order.setUserId(newOrder.getUserId());
		//order.setProviderId(newOrder.getProviderId());
		order.setLastupdateTimeStamp(Calendar.getInstance());
		order.setOrderStatus(OrderStatus.CREATED);
		
		String user_email= getUserEmail(newOrder.getUserId());
		//sending notification to user .
		try {
		sendNotificationForOrderStatus(EventEnum.ORDER_PLACED_NOTIFICATION,user_email);
		}
		catch(Exception ex) {
			System.out.println("error in pushing to rabbit mq");
		}
		final Order newOrders = orderRepository.save(order);
	     return newOrders.getOrderId();
			
	}
	
	
	public String getUserEmail(Long userId)
	{
		
			
		//ResponseEntity<User> response  = userproxy.getUserById(userId);
		String completeurl= userServiceUrl+ "/"+userId;
		User user = template.getForObject(completeurl , User.class);		
		
		System.out.println("user name through rest template is "+ user.getName());
		
		return user.getEmail();
	}
	
	public Double getServicePrice(Long serviceId)
	{
		
			
		//ResponseEntity<User> response  = serviceproxy.getServiceById(serviceId);
		String completeurl= servicesUrl+ "/"+serviceId;
		System.out.println("complete url for serviceprovider is "+completeurl);
		Services service = template.getForObject(completeurl , Services.class);		
		
		//System.out.println("user name through rest template is "+ service.getPrice());
		
		return service.getPrice();
	}
	
	public void sendNotificationForOrderStatus(EventEnum event,String email) 
	{
		
		try {
			Map<String, String> paramMap=new HashMap<String,String>();
			//paramMap.put("email",email);
			
			this.notificationService.setAndPublishNotificationTrigger(event,
					email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("exception occurred.");
			e.printStackTrace();
			
		}
	}
}
