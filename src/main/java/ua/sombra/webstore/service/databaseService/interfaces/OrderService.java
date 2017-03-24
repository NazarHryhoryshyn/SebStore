package ua.sombra.webstore.service.databaseService.interfaces;

import java.util.List;
import java.util.Set;

import ua.sombra.webstore.entity.Orders;
import ua.sombra.webstore.entity.wrappers.PageInfo;

public interface OrderService {

	public void create(Orders order);

	public Orders findById(Integer id);
	
	public void changeStatus(int orderId, String newStatus);

	public List<Orders> listOrders();

	public Set<Orders> sortedOrderSet();
	
	public void makeOrder(String address, String receiver, String paymentType, String deliveryType, 
			String phone, String email, String cardNumber, String cardTreeNumber, String cardTermOf);
	

	public PageInfo<Orders> getPageInfoOrders(int page);
}
