package com.lovemesomecoding.pizzaria.dto;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class OrderReadDTO {

	private Long id;
	
	private String uid;

	private Set<LineItemDTO> lineItems;

	private int totalItemCount;

	private double total;

	private AddressDTO location;

	private boolean delivered;

	private Date deliveredAt;

	private Date createdAt;

	private Date updatedAt;

	private PaymentReadDTO payment;

	private boolean paid;

	private Date paidAt;

	public OrderReadDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Set<LineItemDTO> getLineItems() {
		return lineItems;
	}

	public void setLineItems(Set<LineItemDTO> lineItems) {
		this.lineItems = lineItems;

		if (this.lineItems != null) {
			this.lineItems.forEach((lineItem) -> {
				this.totalItemCount += lineItem.getCount();
			});
		}
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public AddressDTO getLocation() {
		return location;
	}

	public void setLocation(AddressDTO location) {
		this.location = location;
	}

	public boolean isDelivered() {
		return delivered;
	}

	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}

	public Date getDeliveredAt() {
		return deliveredAt;
	}

	public void setDeliveredAt(Date deliveredAt) {
		this.deliveredAt = deliveredAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public PaymentReadDTO getPayment() {
		return payment;
	}

	public void setPayment(PaymentReadDTO payment) {
		this.payment = payment;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getTotalItemCount() {
		this.totalItemCount = 0;
		if (this.lineItems != null) {
			this.lineItems.forEach((lineItem) -> {
				this.totalItemCount += lineItem.getCount();
			});
		}
		return totalItemCount;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public Date getPaidAt() {
		return paidAt;
	}

	public void setPaidAt(Date paidAt) {
		this.paidAt = paidAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
