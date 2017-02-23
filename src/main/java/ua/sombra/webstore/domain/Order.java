package ua.sombra.webstore.domain;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ua.sombra.webstore.enums.DeliveryType;
import ua.sombra.webstore.enums.OrderStatus;
import ua.sombra.webstore.enums.PaymentType;

@Entity
@Table(name="order")
public class Order {
	
	private int id;
	private OrderStatus status;
	private DeliveryType deliveryType;
	private Date date;
	private PaymentType paymentType;
	private String receiver;
	private String phone;
	private String email;
	private BigDecimal deliveryPrice;
	private String address;
	private String cardNumber;
	private String cardTermOf;
	private int cardThreeNumbers;
	
	
	private User user;	

	private Set<ProductsInOrder> productsInOrders = new HashSet<ProductsInOrder>(0);
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "status", nullable = false)
	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	@Column(name = "delivery_type", nullable = false)
	public DeliveryType getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(DeliveryType deliveryType) {
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
	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
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

	@Column(name = "delivery_price", nullable = false)
	public BigDecimal getDeliveryPrice() {
		return deliveryPrice;
	}

	public void setDeliveryPrice(BigDecimal deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
	public Set<ProductsInOrder> getProductsInOrders() {
		return productsInOrders;
	}

	public void setProductsInOrders(Set<ProductsInOrder> productsInOrders) {
		this.productsInOrders = productsInOrders;
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

	@Column(name = "get_card_term_of", length = 5, nullable = true)
	public String getCardTermOf() {
		return cardTermOf;
	}

	public void setCardTermOf(String cardTermOf) {
		this.cardTermOf = cardTermOf;
	}

	@Column(name = "card_three_numbers", nullable = true)
	public int getCardThreeNumbers() {
		return cardThreeNumbers;
	}

	public void setCardThreeNumbers(int cardThreeNumbers) {
		this.cardThreeNumbers = cardThreeNumbers;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}




