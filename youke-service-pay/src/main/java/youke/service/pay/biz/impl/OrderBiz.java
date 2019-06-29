package youke.service.pay.biz.impl;

import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.dao.*;
import youke.common.exception.BusinessException;
import youke.common.model.*;
import youke.common.utils.DateUtil;
import youke.common.utils.IDUtil;
import youke.common.utils.StringUtil;
import youke.facade.pay.util.OrderType;
import youke.facade.pay.vo.OpenOrderVo;
import youke.facade.pay.vo.OrderPayVo;
import youke.facade.pay.vo.ShopPackageVo;
import youke.service.pay.biz.IOrderBiz;
import youke.service.pay.biz.IQueueBiz;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderBiz extends Base implements IOrderBiz {

    @Resource
    private IYoukeOrderDao youkeOrderDao;
    @Resource
    private IYoukeBankDao youkeBankDao;
    @Resource
    private IMobcodeOrderDao mobcodeOrderDao;
    @Resource
    private IMobcodePackageDao mobcodePackageDao;
    @Resource
    private IOpenPackageDao openPackageDao;
    @Resource
    private IOpenDiscountDao openDiscountDao;
    @Resource
    private IOpenOrderDao openOrderDao;
    @Resource
    private IMobcodeDao mobcodeDao;
    @Resource
    private IYoukeDao youkeDao;
    @Resource
    private IConfigDao configDao;
    @Resource
    private IQueueBiz queueBiz;

    public String addMobcodeOrder(Integer packageId, Integer num, Integer userId, String youkeId) {
        TMobcodePackage mobcodePackage = mobcodePackageDao.selectByPrimaryKey(packageId);
        TMobcodeOrder order = new TMobcodeOrder();
        order.setCreatetime(new Date());
        order.setNum(num);
        order.setOid(IDUtil.getRandomIntId());
        order.setPrice(mobcodePackage.getPrice());
        order.setState(0);
        order.setTitle(mobcodePackage.getTitle() + "X" + num);
        order.setTotalcount(mobcodePackage.getNum() * num);
        order.setTotalprice(mobcodePackage.getPrice() * num);
        order.setUserid(userId);
        order.setYoukeid(youkeId);
        mobcodeOrderDao.insertSelective(order);
        return order.getOid();
    }

    public String addOpenOrder(Integer packageId, Integer num, Integer userId, String youkeId) {
        TOpenPackage openPackage = openPackageDao.selectByPrimaryKey(packageId);
        if (openPackage == null)
            throw new BusinessException("无效的订购套餐");

        int totalPrice = openPackage.getPrice() * num;
        //查询优惠卷
        TOpenDiscount discount = openDiscountDao.selectDiscountByPriceLimit(youkeId, totalPrice);

        TOpenOrder order = new TOpenOrder();
        order.setCreatetime(new Date());
        if (discount != null) {
            if (totalPrice > discount.getMoney()) {
                order.setDiscount(discount.getMoney());
            } else {
                order.setDiscount(0);
            }
        } else {
            order.setDiscount(0);
        }
        order.setMarketprice(openPackage.getMarketprice());
        order.setNum(num);
        order.setOid(IDUtil.getRandomIntId());
        order.setPackageid(packageId);
        order.setTotalyear(openPackage.getYear() * num);
        TYouke youke = youkeDao.selectByPrimaryKey(youkeId);
        int extShopNum = youke.getExtshopnum();
        if (extShopNum == 0) {
            order.setTitle(openPackage.getTitle());
            order.setPrice(openPackage.getPrice());
            order.setTotalprice(totalPrice - order.getDiscount());
        } else {
            TConfig config = configDao.selectByPrimaryKey("ext_shop_price");
            int extShopPrice = Integer.parseInt(config.getVal());
            order.setTitle(openPackage.getTitle() + "+多店铺包" + extShopNum + "个");
            order.setPrice(openPackage.getPrice() + extShopNum * extShopPrice);
            order.setTotalprice((openPackage.getPrice() + extShopNum * extShopPrice) * num - order.getDiscount());
        }
        Date endTime = youke.getEndtime();
        if (endTime == null || endTime.getTime() < new Date().getTime()) {
            order.setSerbegdate(new Date());
            order.setSerenddate(DateUtil.addYears(new Date(), order.getTotalyear()));
        } else {
            order.setSerbegdate(endTime);
            order.setSerenddate(DateUtil.addYears(endTime, order.getTotalyear()));
        }
        order.setUserid(userId);
        order.setYoukeid(youkeId);
        order.setState(0);
        openOrderDao.insertSelective(order);
        return order.getOid();
    }

    public String addYoukeOrder(Integer price, Integer userId, String youkeId) {
        TYoukeOrder order = new TYoukeOrder();
        order.setCreatetime(new Date());
        order.setOid(IDUtil.getRandomIntId());
        order.setPrice(price);
        order.setState(0);
        order.setTotalprice(price + price / 100);
        order.setTitle("账户充值" + price / 100 + "元");
        order.setUserid(userId);
        order.setYoukeid(youkeId);
        youkeOrderDao.insertSelective(order);
        return order.getOid();
    }

    public TMobcodeOrder getMobcodeOrder(String oid) {
        return mobcodeOrderDao.selectByPrimaryKey(oid);
    }

    public OrderPayVo getOrderPayVo(String oid, String orderType) {
        OrderPayVo orderPayVo = null;
        if (OrderType.OPEN.equals(orderType) || OrderType.SHOP.equals(orderType)) {
            TOpenOrder order = openOrderDao.selectByPrimaryKey(oid);
            orderPayVo = new OrderPayVo(order.getOid(), order.getTitle(), order.getTotalprice());
        } else if (OrderType.MOBC.equals(orderType)) {
            TMobcodeOrder order = mobcodeOrderDao.selectByPrimaryKey(oid);
            orderPayVo = new OrderPayVo(order.getOid(), order.getTitle(), order.getTotalprice());
        } else if (OrderType.ACCO.equals(orderType)) {
            TYoukeOrder order = youkeOrderDao.selectByPrimaryKey(oid);
            orderPayVo = new OrderPayVo(order.getOid(), order.getTitle(), order.getTotalprice());
        }
        return orderPayVo;
    }

    public int getMobcodeOrderState(String oid) {
        return mobcodeOrderDao.selectOrderState(oid);
    }

    public int getOpenOrderState(String oid) {
        return openOrderDao.selectOrderState(oid);
    }

    public int getYoukeOrderState(String oid) {
        return youkeOrderDao.selectOrderState(oid);
    }

    public void updateMobcodeOrder(String oid, Integer price, String payType, String ranstr) {
        int count = mobcodeOrderDao.updateOrderPayed(oid, price, new Date(), payType, ranstr);
        if (count > 0) {
            TMobcodeOrder order = mobcodeOrderDao.selectByPrimaryKey(oid);
            mobcodeDao.updateAddCount(order.getTotalcount(), order.getYoukeid());
            //发送模板通知
            Map<String, String> map = youkeDao.selectYoukeOpenMap(order.getYoukeid());
            if (map != null) {
                queueBiz.sendSysRecharge(map.get("openId"), map.get("title"), "短信", price);
            }
        }
    }

    public void updateOpenOrder(String oid, Integer price, String payType, String ranstr) {
        int count = openOrderDao.updateOrderPayed(oid, price, new Date(), payType, ranstr);
        if (count > 0) {
            TOpenOrder order = openOrderDao.selectByPrimaryKey(oid);
            //更新优惠卷状态
            if (order.getDiscount() > 0) {
                Integer discountId = openDiscountDao.selectDiscountIdByMoney(order.getDiscount());
                if (discountId != null) {
                    openDiscountDao.updateStateUsed(discountId);
                }
            }
            TYouke youke = youkeDao.selectByPrimaryKey(order.getYoukeid());
            if (youke.getVip() == 0) {
                mobcodeDao.updateAddCount(100, youke.getId());
            }
            youkeDao.updateOpenVip(order.getPackageid(), empty(youke.getOpentime()) ? order.getSerbegdate() : youke.getOpentime(), order.getSerenddate(), order.getYoukeid());
            //发送模板通知
            Map<String, String> map = youkeDao.selectYoukeOpenMap(order.getYoukeid());
            if (map != null) {
                queueBiz.sendSysOpenAccount(map.get("openId"), order.getPackageid(), price, map.get("title"), DateUtil.formatDate(order.getSerenddate(), "yyyy-MM-dd"));
            }
        }
    }

    public void updateShopOrder(String oid, Integer price, String payType, String ranstr) {
        int count = openOrderDao.updateOrderPayed(oid, price, new Date(), payType, ranstr);
        if (count > 0) {
            TOpenOrder order = openOrderDao.selectByPrimaryKey(oid);
            youkeDao.updateExtShopNum(order.getYoukeid());
        }
    }

    @Override
    public void updateYoukeOrder(String oid, Integer price, String payType, String ranstr) {
        int count = youkeOrderDao.updateOrderPayed(oid, price, new Date(), payType, ranstr);
        if (count > 0) {
            TYoukeOrder order = youkeOrderDao.selectByPrimaryKey(oid);
            youkeDao.updateMoney(price, order.getYoukeid());
            int leftprice = youkeDao.selectMoney(order.getYoukeid());
            TYoukeBank bank = new TYoukeBank();
            bank.setClassify("账户充值");
            bank.setCreatetime(new Date());
            bank.setLeftprice(leftprice);
            bank.setPrice(price);
            bank.setTitle("账户充值" + StringUtil.FenToYuan(price) + "元");
            bank.setPaytype(payType);
            bank.setYoukeid(order.getYoukeid());
            youkeBankDao.insertSelective(bank);
        }
    }


    public List<TOpenPackage> getOpenPackages() {
        return openPackageDao.selectPackages();
    }

    public OpenOrderVo getOpenOrderVo(String oid) {
        TOpenOrder order = openOrderDao.selectByPrimaryKey(oid);
        OpenOrderVo orderVo = new OpenOrderVo();
        orderVo.setCompany(youkeDao.selectCompany(order.getYoukeid()));
        orderVo.setDiscount(order.getDiscount());
        orderVo.setOid(oid);
        orderVo.setPrice(order.getPrice());
        orderVo.setTitle(order.getTitle());
        orderVo.setTotalPrice(order.getTotalprice());
        orderVo.setYear(order.getTotalyear());
        orderVo.setSerTerm(DateUtil.formatDate(order.getSerbegdate(), "yyyy-MM-dd") + " ~ " + DateUtil.formatDate(order.getSerenddate(), "yyyy-MM-dd"));
        return orderVo;
    }

    @Override
    public String addUpOpenOrder(int packageId, Integer userId, String dykId) {
        TYouke youke = youkeDao.selectByPrimaryKey(dykId);
        if(packageId==youke.getVip()){
            throw new BusinessException("选择的套餐版本必须高于当前版本");
        }
        if (youke.getVip()!=9&&packageId<youke.getVip()) {
            throw new BusinessException("选择的套餐版本必须高于当前版本");
        }
        if (youke.getEndtime().getTime() > new Date().getTime()) {
            long lastDay = (youke.getEndtime().getTime() - new Date().getTime()) / (24 * 60 * 60 * 1000);
            TOpenPackage openPackage1 = openPackageDao.selectByPrimaryKey(youke.getVip());
            TOpenPackage openPackage2 = openPackageDao.selectByPrimaryKey(packageId);
            int lastPrice = (int) Math.ceil((double) (openPackage2.getPrice() - openPackage1.getPrice()) / 365) * (int) lastDay;
            TOpenOrder order = new TOpenOrder();
            order.setDiscount(0);
            order.setOid(IDUtil.getRandomIntId());
            order.setCreatetime(new Date());
            order.setTitle(openPackage2.getTitle());
            order.setPrice(lastPrice);
            order.setTotalprice(lastPrice);
            order.setMarketprice(lastPrice);
            order.setState(0);
            order.setUserid(userId);
            order.setYoukeid(dykId);
            order.setPackageid(packageId);
            order.setNum(1);
            order.setSerbegdate(youke.getOpentime());
            order.setSerenddate(youke.getEndtime());
            int totalYear = DateUtil.yearDateDiff(DateUtil.formatDateTime(youke.getOpentime()), DateUtil.formatDateTime(youke.getEndtime()));
            order.setTotalyear(totalYear);
            openOrderDao.insertSelective(order);
            return order.getOid();
        } else {
            return addOpenOrder(packageId, 1, userId, dykId);
        }
    }

    @Override
    public String addShopOrder(int userId, String youkeId) {
        TYouke youke = youkeDao.selectByPrimaryKey(youkeId);
        TConfig config = configDao.selectByPrimaryKey("ext_shop_price");
        int extShopPrice = Integer.parseInt(config.getVal());
        TOpenOrder order = new TOpenOrder();
        order.setCreatetime(new Date());
        order.setMarketprice(extShopPrice);
        order.setNum(1);
        order.setOid(IDUtil.getRandomIntId());
        order.setPackageid(0);
        order.setTotalyear(1);
        order.setTitle("多店铺绑定包1个");
        order.setPrice(extShopPrice);
        order.setTotalprice(extShopPrice);
        order.setSerbegdate(youke.getOpentime());
        order.setSerenddate(youke.getEndtime());
        order.setUserid(userId);
        order.setYoukeid(youkeId);
        order.setState(0);
        openOrderDao.insertSelective(order);
        return order.getOid();
    }

    @Override
    public ShopPackageVo getShopPackage(String dykId) {
        TYouke youke = youkeDao.selectByPrimaryKey(dykId);
        TConfig config = configDao.selectByPrimaryKey("ext_shop_price");
        int extShopPrice = Integer.parseInt(config.getVal());
        ShopPackageVo packageVo = new ShopPackageVo();
        packageVo.setTitle("多店铺绑定包");
        packageVo.setContent("新增1家绑定店铺");
        packageVo.setNum(1);
        packageVo.setPrice(extShopPrice);
        packageVo.setPackageId(1);
        packageVo.setSerTerm(DateUtil.formatDate(youke.getOpentime(), "yyyy-MM-dd") + " ~ " + DateUtil.formatDate(youke.getEndtime(), "yyyy-MM-dd"));
        return packageVo;
    }
}
