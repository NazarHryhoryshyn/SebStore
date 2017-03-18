package ua.sombra.webstore.service.databaseService.impls;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.sombra.webstore.dao.interfaces.OrderDAO;
import ua.sombra.webstore.entity.Orders;
import ua.sombra.webstore.service.databaseService.interfaces.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderDAO orderDao;

	@Override
	public void addOrder(Orders order){
		orderDao.addOrder(order);
	}
	
	@Override
	public Orders findById(Integer id) {
		return orderDao.findById(id);
	}
	
	@Override
	public void changeStatus(int orderId, String newStatus){
		orderDao.changeStatus(orderId, newStatus);
	}
	
	public List<Orders> listOrders(){
		return orderDao.listOrders();
	}
}
