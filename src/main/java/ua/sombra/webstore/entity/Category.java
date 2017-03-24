package ua.sombra.webstore.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
@Table(name="category")
public class Category {
	
	private int id;
	private String name;
	private boolean isSub;
	private int mainCategoryId;
	
	private Set<Feature> features = new HashSet<Feature>(0);
	private Set<Product> products = new HashSet<Product>(0);
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name="name", length = 45, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="is_sub", nullable = false)
	public boolean getIsSub() {
		return isSub;
	}

	public void setIsSub(boolean isSub) {
		this.isSub = isSub;
	}

	@Column(name="main_category_id")
	public int getMainCategoryId() {
		return mainCategoryId;
	}

	public void setMainCategoryId(int mainCategoryId) {
		this.mainCategoryId = mainCategoryId;
	}

	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "additional_features", joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id"))
	@JsonManagedReference
	public Set<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(Set<Feature> features) {
		this.features = features;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
	@JsonManagedReference
	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Category{" +
                "id=" + this.id +
                ", name='" + this.name + "'" +
                ", isSub=" + this.isSub +
                ", mainCategoryId=" + this.mainCategoryId +
                '}';
	}
}
