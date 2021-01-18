package com.lovemesomecoding.pizzaria.dto;

import java.math.BigInteger;
import java.util.Date;

public class OrderAdminSearchResponseItemDTO {

	private BigInteger orderId;
	private String orderUid;
	private Double total;
	private Character delivered;
	private Character paid;
	private Date paidAt;
	private Date updatedAt;
	private String city;
	private String state;
	private String customerUid;
	private String customerName;
	
	public OrderAdminSearchResponseItemDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OrderAdminSearchResponseItemDTO(BigInteger orderId, String orderUid, Double total, Character delivered, Character paid, Date paidAt,
			Date updatedAt, String city, String state, String customerUid, String customerName) {
		super();
		this.orderId = orderId;
		this.orderUid = orderUid;
		this.total = total;
		this.delivered = delivered;
		this.paid = paid;
		this.paidAt = paidAt;
		this.updatedAt = updatedAt;
		this.city = city;
		this.state = state;
		this.customerUid = customerUid;
		this.customerName = customerName;
	}



	public BigInteger getOrderId() {
		return orderId;
	}
	public void setOrderId(BigInteger orderId) {
		this.orderId = orderId;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Character getDelivered() {
		return delivered;
	}
	public void setDelivered(Character delivered) {
		this.delivered = delivered;
	}
	public Character getPaid() {
		return paid;
	}
	public void setPaid(Character paid) {
		this.paid = paid;
	}
	public Date getPaidAt() {
		return paidAt;
	}
	public void setPaidAt(Date paidAt) {
		this.paidAt = paidAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCustomerUid() {
		return customerUid;
	}
	public void setCustomerUid(String customerUid) {
		this.customerUid = customerUid;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getOrderUid() {
		return orderUid;
	}

	public void setOrderUid(String orderUid) {
		this.orderUid = orderUid;
	}
}
