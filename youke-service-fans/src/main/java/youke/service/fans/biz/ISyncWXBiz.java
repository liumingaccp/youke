package youke.service.fans.biz;

/**
 * 同步微信业务
 */
public interface ISyncWXBiz {
    /**
     * 初始化导入粉丝
     */
    void saveDownFans();

    /**
     * 初始化导入粉丝黑名单
     */
    void saveDownFansBlack();

    /**
     * 初始化导入微信标签（同时同步标签下的粉丝）
     */
    void saveDownTags();

    /**
     * 上传同步标签
     */
    void saveUploadTags();
}
