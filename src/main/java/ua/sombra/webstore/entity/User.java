package ua.sombra.webstore.entity;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import ua.sombra.webstore.entity.Role;

@Entity
@Table(name = "user")
public class User implements Comparable<User> {
	
	private int id;
	private String firstname;
	private String lastname;
	private String email;
	private String telephone;
	private String sex;
	private String password;
	private String confirmPassword;
	private String login;

	private Set<Role> roles = new HashSet<Role>(0);
    private Set<Product> products = new HashSet<Product>(0);
    private Set<Orders> orders = new HashSet<Orders>(0);
	    
    
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
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
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
	
	@Column(name="login", nullable = false, length = 45)
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
	          inverseJoinColumns = @JoinColumn(name = "role_id"))
	@JsonManagedReference
	public Set<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	   @JoinTable(name = "cart", joinColumns = @JoinColumn(name = "user_id"),
	           inverseJoinColumns = @JoinColumn(name = "product_id"))
	@JsonManagedReference
	public Set<Product> getProducts() {
		return products;
	}
	
	public void setProducts(Set<Product> products) {
		if(products!=null){
		this.products = products;
		}
	}
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonManagedReference
    public Set<Orders> getOrders() {
		return orders;
	}

	public void setOrders(Set<Orders> orders) {
		this.orders = orders;
	}

	@Override
	public int compareTo(User u) {
		return Integer.compare(u.getId(), this.getId());
	}
	
	@Override
	public String toString() {
		String roles = "[";
		for(Role r : getRoles()){
			roles += r.getName()+" ";
		}
		roles += "]";
		return "User{"
                + "id=" + this.id
                + ", firstname='" + this.firstname + "'"
                + ", lastname='" + this.lastname + "'"
                + ", email='" + this.email + "'"
                + ", telephone=" + this.telephone
                + ", sex='" + this.sex + "'"	
                + ", login='" + this.login + "'"
                + ", roles='" + roles
                + "}";
	}
}
