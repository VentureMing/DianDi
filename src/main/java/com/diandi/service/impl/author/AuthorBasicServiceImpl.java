package com.diandi.service.impl.author;

import com.diandi.dao.author.AuthorBasicDao;
import com.diandi.dao.author.AuthorImageDao;
import com.diandi.entity.author.AuthorBasic;
import com.diandi.manager.ImageManager;
import com.diandi.service.author.AuthorBasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2017/12/19.
 *
 * @author DuanJiaNing
 */
@Service
public class AuthorBasicServiceImpl implements AuthorBasicService {

    @Autowired
    private AuthorBasicDao basicDao;

    @Autowired
    private AuthorImageDao imageDao;

    @Autowired
    private ImageManager imageManager;


    @Override
    public int insertAuthorBasic(int authorId, int profileId, String phone, String nickName, String intro) {

        AuthorBasic basicInfo = new AuthorBasic();
        basicInfo.setAuthorId(authorId);
        basicInfo.setProfileId(profileId < 0 ? null : profileId);
        basicInfo.setNickName(nickName);
        basicInfo.setIntro(intro);
        basicInfo.setPhone(phone);
        int effect = basicDao.insert(basicInfo);
        if (effect <= 0) return -1;

//        if (profileId > 0)
//            imageManager.imageInsertHandle(authorId, profileId);

        return effect;
    }

    @Override
    public boolean updateAuthorBasic(int authorId, int profileId, String newPhone, String newNickName, String newIntro) {

        AuthorBasic basicInfo = basicDao.getBasicInfoByAuthorId(authorId);
        if (basicInfo == null) return false;
        Integer oldProfileId = basicInfo.getProfileId();

        basicInfo.setProfileId(profileId == -1 ? null : profileId);
        basicInfo.setPhone(newPhone);
        basicInfo.setNickName(newNickName);
        basicInfo.setIntro(newIntro);
        int effect = basicDao.update(basicInfo);
        if (effect <= 0) return false;

        if (profileId > 0)
            imageManager.imageUpdateHandle(authorId, profileId, oldProfileId);

        return true;
    }

    @Override
    public boolean deleteAuthorBasic(int bloggerId) {

        AuthorBasic profile = basicDao.getBasicInfoByAuthorId(bloggerId);

        int effect = basicDao.delete(bloggerId);
        if (effect <= 0) return false;

        Integer id;
        if ((id = profile.getProfileId()) != null)
            imageDao.updateUseCountMinus(id);

        return true;
    }

    @Override
    public AuthorBasic getAuthorBasic(int bloggerId) {
        return basicDao.getBasicInfoByAuthorId(bloggerId);
    }

    @Override
    public AuthorBasic getAuthorBasicByPhone(String phone) {
        return basicDao.getBasicInfoByPhone(phone);
    }

    @Override
    public AuthorBasic getAuthorBasicByNickName(String nickName) {
        return basicDao.getBasicInfoByNickName(nickName);
    }
}
