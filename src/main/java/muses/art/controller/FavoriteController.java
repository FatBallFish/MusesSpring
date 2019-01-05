package muses.art.controller;

import muses.art.model.base.StatusModel;
import muses.art.model.operation.FavCommodityModel;
import muses.art.service.operation.UserFavCommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/favorite/commodity")
public class FavoriteController {

    @Autowired
    private UserFavCommodityService userFavCommodityService;

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public @ResponseBody StatusModel addFavCommodity(@RequestBody FavCommodityModel f) {
        if (userFavCommodityService.findFavCommodityByUserIdAndCommodityId(f.getUserId(), f.getCommodityId()) != null) {
            return new StatusModel("请勿重复添加收藏", StatusModel.ERROR);
        } else {
            userFavCommodityService.addFavCommodity(f.getUserId(), f.getCommodityId());
            return new StatusModel("添加收藏成功", StatusModel.OK);
        }
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public @ResponseBody StatusModel deleteFavCommodity(@PathVariable Integer id) {
        boolean flag = userFavCommodityService.deleteFavCommodity(id);
        if (flag) {
            return new StatusModel("移除收藏成功", StatusModel.OK);
        } else {
            return new StatusModel("移除收藏失败", StatusModel.ERROR);
        }
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody StatusModel listFavCommodity(@RequestBody FavCommodityModel f) {
        List<FavCommodityModel> userFavCommodities = userFavCommodityService.findFavCommodityByUserId(f.getUserId());
        if (userFavCommodities != null) {
            return new StatusModel("加载收藏夹成功", StatusModel.OK);
        } else {
            return new StatusModel("加载收藏夹失败", StatusModel.ERROR);
        }
    }

    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public @ResponseBody StatusModel deleteALlFavCommodity(@RequestBody FavCommodityModel f) {
        boolean flag = userFavCommodityService.deleteAllFavCommodity(f.getUserId());
        if (flag) {
            return new StatusModel("清空收藏夹成功", StatusModel.OK);
        } else {
            return new StatusModel("清空收藏夹失败", StatusModel.ERROR);
        }
    }
}