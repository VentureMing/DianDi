package com.diandi.service.impl.author;


import com.diandi.dao.author.AuthorSettingDao;
import com.diandi.entity.author.AuthorSetting;
import com.diandi.service.author.AuthorSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2018/3/26.
 *
 * @author DuanJiaNing
 */
@Service
public class AuthorSettingServiceImpl implements AuthorSettingService {

    @Autowired
    private AuthorSettingDao settingDao;

    @Override
    public AuthorSetting getSetting(int authorId) {
        return settingDao.getSetting(authorId);
    }

    @Override
    public boolean updateMainPageNavPos(int authorId, int pos) {
        int effect = settingDao.updateMainPageNavPos(authorId, pos);
        if (effect <= 0) return false;

        return true;
    }

}
