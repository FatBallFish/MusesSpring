package muses.art.service.operation;

import muses.art.entity.operation.UserFavCommodity;
import muses.art.model.operation.FavCommodityModel;

import java.util.List;


public interface UserFavCommodityService { // 用户收藏商品

    /**
     * 添加收藏
     * @param userId 用户id
     * @param commodityId 商品id
     * @return
     */
    Boolean addFavCommodity(int userId, int commodityId);

    /**
     * 取消收藏
     * @param id 收藏记录id
     * @return
     */
    Boolean deleteFavCommodity(int id);

    /**
     * 根据收藏记录的编号查找收藏
     * @param id 收藏id
     * @return
     */
    FavCommodityModel findFavCommodityById(int id);

    /**
     * 查找用户收藏的所有商品
     * @param userId 用户id
     * @return
     */
    List<FavCommodityModel> findFavCommodityByUserId(int userId);

    /**
     * 查找用户是否收藏某个商品
     * @param userId 用户id
     * @param commodityId 商品id
     * @return
     */
    List<UserFavCommodity> findFavCommodityByUserIdAndCommodityId(int userId, int commodityId);

    /**
     * 清空某用户的收藏夹
     * @param userId 用户id
     * @return
     */
    Boolean deleteAllFavCommodity(Integer userId);
}
