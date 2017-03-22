package ua.sombra.webstore.service.databaseService.impls;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.dao.interfaces.OrderDAO;
import ua.sombra.webstore.entity.Orders;
import ua.sombra.webstore.entity.Product;
import ua.sombra.webstore.entity.User;
import ua.sombra.webstore.entity.wrappers.PageInfo;
import ua.sombra.webstore.service.databaseService.interfaces.OrderService;
import ua.sombra.webstore.service.databaseService.interfaces.SecurityService;
import ua.sombra.webstore.service.databaseService.interfaces.UserService;
import ua.sombra.webstore.service.paging.PageMaker;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDAO orderDao;

	@Autowired
	private SecurityService securityService;
	

	@Autowired
	private UserService userService;
	
	@Override
	public void addOrder(Orders order){
		orderDao.create(order);
	}
	
	@Override
	public Orders findById(Integer id) {
		return orderDao.findById(id);
	}
	
	@Override
	public void changeStatus(int orderId, String newStatus){
		orderDao.changeStatus(orderId, newStatus);
	}
	
	@Override
	public List<Orders> listOrders(){
		return orderDao.listAll();
	}
	
	@Transactional
	@Override
	public void makeOrder(String address, String receiver, String paymentType, String deliveryType, 
			String phone, String email, String cardNumber, String cardTreeNumber, String cardTermOf){
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		if(u.getProducts().size() > 0){
			Orders newOrder = new Orders();
			
			newOrder.setAddress(address);
			newOrder.setReceiver(receiver);
			newOrder.setPaymentType(paymentType);
			newOrder.setDeliveryType(deliveryType);
			newOrder.setPhone(phone);
			newOrder.setEmail(email);
			newOrder.setCardNumber(cardNumber);
			newOrder.setCardThreeNumbers(cardTreeNumber);
			newOrder.setCardTermOf(cardTermOf);
			
			Date d = new Date();
			newOrder.setUser(u);
			newOrder.setProducts(u.getProducts());
			newOrder.setDate(d);
			newOrder.setDeliveryPrice(new BigDecimal(10));
			newOrder.setStatus("in processing");
			addOrder(newOrder);
			for(Product p : u.getProducts()){
				userService.removeReferenceToProduct(u.getId(), p.getId());
			}
		}
	}
	
	@Override
	public Set<Orders> sortedOrderSet(){
		Set<Orders> allOrders = new TreeSet<Orders>();
	
		for(Orders o : listOrders()){
			allOrders.add(o);
		}
		
		return allOrders;
	}
	
	@Override
	public PageInfo<Orders> getPageInfoOrders(int page){
		Set<Orders> allOrders = sortedOrderSet();
		
		PageMaker<Orders> pgMaker = new PageMaker<Orders>();
		pgMaker.setObjects(allOrders);
		
		PageInfo<Orders> orderInfo = new PageInfo<Orders>();
		orderInfo.setPage(page);
		orderInfo.SetValuesWithPageMaker(pgMaker);
		
		return orderInfo;
	}
}
