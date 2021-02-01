package com.lovemesomecoding.pizzaria.dto;

import java.util.Date;

public class PaymentReadDTO {

	private Long id;

	private String uid;

	private String type;

	private Boolean paid;

	private String description;

	private Double amountPaid;

	private OrderPaymentMethodReadDTO paymentMethod;

	private Date createdAt;

	private Date updatedAt;

	public PaymentReadDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getPaid() {
		return paid;
	}

	public void setPaid(Boolean paid) {
		this.paid = paid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(Double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public OrderPaymentMethodReadDTO getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(OrderPaymentMethodReadDTO paymentMethod) {
		this.paymentMethod = paymentMethod;
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

}
