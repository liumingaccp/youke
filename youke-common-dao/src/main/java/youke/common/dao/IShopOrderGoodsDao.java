package youke.common.dao;

import youke.common.model.TShopOrderGoods;

public interface IShopOrderGoodsDao {
    int deleteByPrimaryKey(Long id);

    int insertSelective(TShopOrderGoods record);

    TShopOrderGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TShopOrderGoods record);

    TShopOrderGoods selectByGoodsId(Long goodsId);
}