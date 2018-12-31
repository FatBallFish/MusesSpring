package muses.art.service.trade.impl;

import muses.art.dao.trade.CartDao;
import muses.art.dao.trade.OrderDao;
import muses.art.entity.trade.Cart;
import muses.art.entity.trade.Order;
import muses.art.entity.trade.OrderCommodity;
import muses.art.model.trade.OrderModel;
import muses.art.service.trade.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private CartDao cartDao;

    @Override
    public List<OrderModel> listOrders(Integer userId) {
        String SQL = "from Order where userId=:id";
        Map<String, Object> map = new HashMap<>();
        map.put("id", userId);
        List<Order> orders = orderDao.find(SQL, map);

        return entity2model(orders);
    }

    @Override
    public Boolean deleteOrder(Integer id) {
        Order order = orderDao.get(Order.class, id);
        if (order == null) return false;
        orderDao.delete(order);
        return true;
    }

    @Override
    public Boolean updateOrder(Integer id, String payStatus) {
        Order order = orderDao.get(Order.class, id);
        if (order == null) return false;
        order.setPayStatus(payStatus);
        if (payStatus == "payed") order.setPayTime(new Timestamp(System.currentTimeMillis()));
        orderDao.update(order);
        return true;
    }

    @Override
    public Boolean createOrderFromCart(ArrayList<Integer> cartIds, Integer addressId) {
        ArrayList<OrderCommodity> orderCommodities = new ArrayList<>();
        Order order = new Order();
        cartIds.forEach(cartId -> {
            OrderCommodity orderCommodity = new OrderCommodity();
            Cart cart = cartDao.get(Cart.class, cartId);
            if (cart == null) return;
            orderCommodity.setCommodityId(cart.getCommodityId());
            orderCommodity.setOrderId(order.getId());
            orderCommodities.add(orderCommodity);
        });
        if (cartIds.size() != orderCommodities.size()) return false;
        order.setOrderCommodities(orderCommodities);
        order.setPayStatus("-1");
        order.setAddressId(addressId);
        return true;
    }

    @Override
    public OrderModel findOrderById(int id) {
        Order order = orderDao.get(Order.class,id);
        OrderModel orderModel = new OrderModel();
        BeanUtils.copyProperties(order,orderModel);
        return orderModel;
    }

    @Override
    public List<OrderModel> listOrderByPage(int userId, int start, int max) {
        String hql = "from Order o where o.user.id=:userId";
        Map<String,Object> map = new HashMap<>();
        map.put("userId", userId);
        List<Order> orders = orderDao.find(hql,map,start,max);
        List<OrderModel> orderModels = new ArrayList<OrderModel>();
        for(Order order : orders) {
            OrderModel orderModel = new OrderModel();
            BeanUtils.copyProperties(order,orderModel);
            orderModels.add(orderModel);
        }
        return orderModels;
    }

    private OrderModel entity2model(Order order) {
        if (order != null) {
            OrderModel orderModel = new OrderModel();
            orderModel.setAddressId(order.getAddressId());
            orderModel.setId(order.getId());
            orderModel.setOrderAmount(order.getOrderAmount());
            orderModel.setOrderSN(order.getOrderSN());
            orderModel.setPayStatus(order.getPayStatus());
            orderModel.setPayTime(order.getPayTime());
            orderModel.setPostScript(order.getPostScript());
            orderModel.setTradeNo(order.getTradeNo());
            orderModel.setUserId(order.getUserId());
            return orderModel;
        } else {
            return null;
        }
    }

    private List<OrderModel> entity2model(List<Order> orders) {
        if (orders != null && orders.size() > 0) {
            List<OrderModel> orderModels = new ArrayList<>();
            for (Order order : orders) {
                orderModels.add(entity2model(order));
            }
            return orderModels;
        } else {
            return null;
        }
    }
}
