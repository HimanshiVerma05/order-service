package com.assignment.microservices.order.entity;

import java.time.Instant;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.assignment.microservices.order.enums.OrderStatus;

@Entity
@Table(name="ORDER_DATA")
public class Order {
	
	

	private Long orderId;
	
	
	private Long userId;
	
	
	private OrderStatus orderStatus;
	
	
	private String address;
	
	
	private Integer totalQty;
	
	
	private String remarks;
	
	
	private Double amount;
	
	
	private Long service;
	
	
	private Calendar lastupdateTimeStamp;
	
	
	private Long providerId;
	
	@Column(name="providerId" , nullable = true)
	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	
	private Calendar creationTimeStamp;
	
	@Column(name="orderStatus", nullable = true)
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Column(name="address", nullable = true)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name="totalQty", nullable = true)
	public Integer getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Integer totalQty) {
		this.totalQty = totalQty;
	}

	@Column(name="remarks", nullable = true)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name="amount", nullable = true)
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name="service", nullable = true)
	public Long getService() {
		return service;
	}

	public void setService(Long service) {
		this.service = service;
	}

	@Column(name="creationTimeStamp", nullable = true)
	public Calendar getCreationTimeStamp() {
		return creationTimeStamp;
	}

	public void setCreationTimeStamp(Calendar creationTimeStamp) {
		this.creationTimeStamp = creationTimeStamp;
	}

	
	@Column(name="lastupdateTimeStamp", nullable = true)
	public Calendar getLastupdateTimeStamp() {
		return lastupdateTimeStamp;
	}

	public void setLastupdateTimeStamp(Calendar lastupdateTimeStamp) {
		this.lastupdateTimeStamp = lastupdateTimeStamp;
	}

	
	//@Column(name="orderId", nullable = false)
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	@Column(name="userId", nullable = false)
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	

	

}

