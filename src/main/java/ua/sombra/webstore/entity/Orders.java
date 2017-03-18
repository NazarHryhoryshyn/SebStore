package ua.sombra.webstore.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="orders")
public class Orders {
	
	private Integer id;
	private String status;
	private String deliveryType;
	private Date date;
	private String paymentType;
	private String receiver;
	private String phone;
	private String email;
	private BigDecimal deliveryPrice;
	private String address;
	private String cardNumber;
	private String cardTermOf;
	private String cardThreeNumbers;

	private User user;	

	private Set<Product> products = new HashSet<Product>(0);
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "status", nullable = false)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "delivery_type", nullable = false)
	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	@Column(name = "date", nullable = false)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name = "payment_type", nullable = false)
	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	@Column(name = "receiver", nullable = false)
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@Column(name = "phone", nullable = true)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "email", nullable = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "delivery_price")
	public BigDecimal getDeliveryPrice() {
		return deliveryPrice;
	}

	public void setDeliveryPrice(BigDecimal deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}

	@Column(name = "address", length = 250 ,nullable = true)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "card_number", length = 16, nullable = true)
	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	@Column(name = "card_term_of", length = 5, nullable = true)
	public String getCardTermOf() {
		return cardTermOf;
	}

	public void setCardTermOf(String cardTermOf) {
		this.cardTermOf = cardTermOf;
	}

	@Column(name = "card_three_numbers", nullable = true)
	public String getCardThreeNumbers() {
		return cardThreeNumbers;
	}

	public void setCardThreeNumbers(String cardThreeNumbers) {
		this.cardThreeNumbers = cardThreeNumbers;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	@JsonBackReference
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	  @JoinTable(name = "orders_products", joinColumns = @JoinColumn(name = "order_id"),
	          inverseJoinColumns = @JoinColumn(name = "product_id"))
	@JsonManagedReference
	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
}




