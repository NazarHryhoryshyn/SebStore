package ua.sombra.webstore.entity;

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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="product")
public class Product implements Comparable<Product> {
	
	private int id;
	private String name;
	private BigDecimal price;
	private String producer;
	private String country;
	private int amountOnWarehouse;
	private int weight;
	
	private Category category;
	
	private Set<Photo> photos = new HashSet<Photo>(0);
	private Set<User> users = new HashSet<User>(0);
	private Set<Orders> orders = new HashSet<Orders>(0);
	private Set<ProductExtraFeature> productExtraFeatures = new HashSet<ProductExtraFeature>(0);
	
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", nullable = false)
	@JsonBackReference
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}	
	
	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
	@JsonManagedReference
	public Set<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(Set<Photo> photos) {
		this.photos = photos;
	}

	@ManyToMany(mappedBy = "products", fetch = FetchType.EAGER)
	@JsonBackReference
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	 @ManyToMany(mappedBy = "products", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	 @JsonBackReference
	public Set<Orders> getOrders() {
		return orders;
	}

	public void setOrders(Set<Orders> orders) {
		this.orders = orders;
	}
	
	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonManagedReference
	public Set<ProductExtraFeature> getProductExtraFeatures() {
		return productExtraFeatures;
	}

	public void setProductExtraFeatures(Set<ProductExtraFeature> productExtraFeatures) {
		this.productExtraFeatures = productExtraFeatures;
	}
	
	public boolean hasFeature(String featureName){
		if(getProductExtraFeatures() == null || getProductExtraFeatures().size() == 0){
			return false;
		}
		for(ProductExtraFeature pef : getProductExtraFeatures()){
			if(pef.getName().equals(featureName)){
				return true;
			}
		}
		return false;
	}
	
	public ProductExtraFeature getExtraFeatureByName(String featureName){
		for(ProductExtraFeature pef : getProductExtraFeatures()){
			if(pef.getName().equals(featureName)){
				return pef;
			}
		}
	return null;
	}

	@Override
	public int compareTo(Product p) {
		return Integer.compare(p.getId(), this.getId());
	}
	
	@Override
	public String toString() {
		return "Product{"
                + "id=" + this.id
                + ", name='" + this.name + "'" 
                + ", price=" + this.price
                + ", producer='" + this.producer + "'"	
                + ", country='" + this.country + "'"
                + ", amountOnWarehouse=" + this.amountOnWarehouse
                + ", weight=" + this.weight
                + ", category='" + this.category.getName() + "'"
                + "}";
	}
}


