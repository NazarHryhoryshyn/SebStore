package ua.sombra.webstore.service.databaseService.impls;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.Constants;
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

	private static final Logger log = Logger.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private OrderDAO orderDao;

	@Autowired
	private SecurityService securityService;
	

	@Autowired
	private UserService userService;
	
	@Override
	public void create(Orders order){
		try{
			orderDao.create(order);
		log.info(order+" successfully created");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@Override
	public Orders findById(Integer id) {
		try{
			return orderDao.findById(id);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public void changeStatus(int orderId, String newStatus){
		try{
			orderDao.changeStatus(orderId, newStatus);
			log.info("Status in order with id="+orderId+" successfully changed on"+newStatus);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@Override
	public List<Orders> listOrders(){
		try{
			return orderDao.listAll();
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	@Transactional
	@Override
	public void makeOrder(String address, String receiver, String paymentType, String deliveryType, 
		String phone, String email, String cardNumber, String cardTreeNumber, String cardTermOf){
		try{
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
				newOrder.setStatus(Constants.DEFAULT_ORDER_STATUS);
				create(newOrder);
				for(Product p : u.getProducts()){
					userService.removeReferenceToProduct(u.getId(), p.getId());
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@Override
	public Set<Orders> sortedOrderSet(){
		Set<Orders> allOrders = new TreeSet<Orders>();
		try{
			for(Orders o : listOrders()){
				allOrders.add(o);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return allOrders;
	}
	
	@Override
	public PageInfo<Orders> getPageInfoOrders(int page){
		try{
			Set<Orders> allOrders = sortedOrderSet();
			
			PageMaker<Orders> pgMaker = new PageMaker<Orders>();
			pgMaker.setObjects(allOrders);
			
			PageInfo<Orders> orderInfo = new PageInfo<Orders>();
			orderInfo.setPage(page);
			orderInfo.SetValuesWithPageMaker(pgMaker);
			
			return orderInfo;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
}
