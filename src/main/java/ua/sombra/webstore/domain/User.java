package ua.sombra.webstore.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import ua.sombra.webstore.domain.Role;
import ua.sombra.webstore.enums.Sex;

@Entity
@Table(name = "user")
public class User {
	
	private int id;
	private String firstname;
	private String lastname;
	private String email;
	private String telephone;
	private Sex sex;
	private String password;
	private String confirmPassword;
	
	private Set<Role> roles;
    private Set<Product> products;
	
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}
    
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="first_name", length = 45)
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	@Column(name="last_name", length = 45, nullable = true)
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	@Column(name="email", length = 45, nullable = false)
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name="telephone", length = 15, nullable = true)
	public String getTelephone() {
		return telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Column(name="sex", nullable = true)
	public String getSex() {
		return sex.name();
	}
	
	public void setSex(String sex) {
		this.sex = Sex.valueOf(sex);
	}
	
	@Column(name="password", length = 100, nullable = false)
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Transient
	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	@ManyToMany
	  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
	          inverseJoinColumns = @JoinColumn(name = "role_id"))
	public Set<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	@ManyToMany
	   @JoinTable(name = "cart", joinColumns = @JoinColumn(name = "user_id"),
	           inverseJoinColumns = @JoinColumn(name = "product_id"))
	public Set<Product> getProducts() {
		return products;
	}
	
	public void setProducts(Set<Product> products) {
		this.products = products;
	}
}
