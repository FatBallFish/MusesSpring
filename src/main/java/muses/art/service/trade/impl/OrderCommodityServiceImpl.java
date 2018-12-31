package muses.art.service.trade.impl;

import muses.art.dao.commodity.CommodityDao;
import muses.art.dao.trade.OrderCommodityDao;
import muses.art.entity.commodity.Commodity;
import muses.art.entity.trade.OrderCommodity;
import muses.art.model.trade.OrderCommodityModel;
import muses.art.service.trade.OrderCommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrderCommodityServiceImpl implements OrderCommodityService {

    @Autowired
    private OrderCommodityDao orderCommodityDao;
    @Autowired
    private CommodityDao commodityDao;

    @Override
    public Boolean add(Integer orderId, Integer commodityId) {
        if (findExist(orderId, commodityId)) return false;
        OrderCommodityModel orderCommodityModel = new OrderCommodityModel();
        Commodity commodity = commodityDao.get(Commodity.class, commodityId);
        orderCommodityModel.setBrief(commodity.getBrief());
        orderCommodityModel.setPrice(commodity.getDiscountPrice());
        orderCommodityModel.setCommodityId(commodityId);
        orderCommodityModel.setOrderId(orderId);
        orderCommodityDao.save(orderCommodityDao.getModelMapper().map(orderCommodityModel, OrderCommodity.class));
        return true;
    }

    @Override
    public Boolean delete(Integer id) {
        OrderCommodity orderCommodity = orderCommodityDao.get(OrderCommodity.class, id);
        if (orderCommodity != null) return false;
        orderCommodityDao.delete(orderCommodity);
        return true;
    }

    public Boolean findExist(Integer orderId, Integer commodityId) {
        String SQL = "from Cart where userId=:uid and commodityId=:cid";
        Map<String, Object> map = new HashMap<>();
        map.put("uid", orderId);
        map.put("cid", commodityId);
        List<OrderCommodity> orderCommodities = orderCommodityDao.find(SQL, map);
        return !orderCommodities.isEmpty();
    }
}