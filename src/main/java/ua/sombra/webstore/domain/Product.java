package ua.sombra.webstore.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="product")
public class Product {
	
	private int id;
	private String name;
	private BigDecimal price;
	private String producer;
	private String country;
	private int amountOnWarehouse;
	private int weight;
	
	private Category category;
	
	private Set<Photo> photos;
	private Set<User> users;
	private Set<Orders> orders;
	private Set<ProductExtraFeatures> productExtraFeatures;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "name", length = 45, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "price", nullable = false)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Column(name = "producer", length = 45, nullable = true)
	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	@Column(name = "country", length = 45, nullable = true)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name="amount_on_warehouse", nullable = false)
	public int getAmountOnWarehouse() {
		return amountOnWarehouse;
	}

	public void setAmountOnWarehouse(int amountOnWarehouse) {
		this.amountOnWarehouse = amountOnWarehouse;
	}

	@Column(name = "weight", nullable = true)
	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}	
	
	@OneToMany(mappedBy = "product")
	public Set<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(Set<Photo> photos) {
		this.photos = photos;
	}

	@ManyToMany(mappedBy = "products")
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	 @ManyToMany(mappedBy = "products", fetch = FetchType.EAGER)
	public Set<Orders> getOrders() {
		return orders;
	}

	public void setOrders(Set<Orders> orders) {
		this.orders = orders;
	}
	
	@OneToMany(mappedBy = "product")
	public Set<ProductExtraFeatures> getProductExtraFeatures() {
		return productExtraFeatures;
	}

	public void setProductExtraFeatures(Set<ProductExtraFeatures> productExtraFeatures) {
		this.productExtraFeatures = productExtraFeatures;
	}
}






