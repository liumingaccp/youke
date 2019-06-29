package youke.common.dao;

import youke.common.model.TAdminNotice;

public interface IAdminNoticeDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TAdminNotice record);

    int insertSelective(TAdminNotice record);

    TAdminNotice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TAdminNotice record);

    int updateByPrimaryKey(TAdminNotice record);

    TAdminNotice selectSystemNotices();
}