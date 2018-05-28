package com.diandi.service.impl.author;


import com.diandi.service.author.AuthorLinkService;
import org.springframework.stereotype.Service;

/**
 * Created on 2017/12/19.
 *
 * @author DuanJiaNing
 */
@Service
public class AuthorLinkServiceImpl implements AuthorLinkService {

//    @Autowired
//    private BloggerLinkDao linkDao;
//
//    @Autowired
//    private BloggerPictureDao pictureDao;
//
//    @Autowired
//    private DataFillingManager fillingManager;
//
//    @Autowired
//    private BloggerProperties authorProperties;
//
//    @Autowired
//    private StringConstructorManager constructorManager;
//
//    @Autowired
//    private ImageManager imageManager;
//
//    @Override
//    public ResultBean<List<BloggerLinkDTO>> listBloggerLink(int bloggerId, int offset, int rows) {
//
//        List<BloggerLink> list = linkDao.listBlogLinkByBloggerId(bloggerId, offset, rows);
//
//        List<BloggerLinkDTO> result = new ArrayList<>();
//        for (BloggerLink link : list) {
//            Integer iconId = link.getIconId();
//            BloggerPicture icon = iconId == null ?
//                    pictureDao.getBloggerUniquePicture(authorProperties.getPictureManagerBloggerId(),
//                            DEFAULT_BLOGGER_LINK_ICON.getCode()) :
//                    pictureDao.getPictureById(iconId);
//
//            // 默认的链接图标可能没有上传
//            if (icon != null)
//                icon.setPath(constructorManager.constructPictureUrl(icon, DEFAULT_BLOGGER_LINK_ICON));
//
//            BloggerLinkDTO dto = fillingManager.bloggerLinkToDTO(link, icon);
//            result.add(dto);
//        }
//
//        return CollectionUtils.isEmpty(result) ? null : new ResultBean<>(result);
//    }
//
//    @Override
//    public int insertBloggerLink(int bloggerId, int iconId, String title, String url, String bewrite) {
//
//        // 新增链接
//        BloggerLink link = new BloggerLink();
//        link.setBewrite(bewrite);
//        link.setBloggerId(bloggerId);
//        link.setIconId(iconId < 0 ? null : iconId);
//        link.setTitle(title);
//        link.setUrl(url);
//        int effect = linkDao.insert(link);
//        if (effect < 0) return -1;
//
//        // 修改图片可见性，引用次数
//        imageManager.imageInsertHandle(bloggerId, iconId);
//
//        return link.getArticleId();
//    }
//
//    @Override
//    public boolean updateBloggerLink(int linkId, int newIconId, String newTitle, String newUrl, String newBewrite) {
//
//        // 修改链接
//        BloggerLink link = linkDao.getLink(linkId);
//        Integer oldIconId = link.getIconId();
//        link.setBewrite(newBewrite);
//        link.setIconId(newIconId < 0 ? null : newIconId);
//        link.setTitle(newTitle);
//        link.setUrl(newUrl);
//        link.setArticleId(linkId);
//        int effect = linkDao.update(link);
//        if (effect < 0) return false;
//
//        // 修改图片可见性，引用次数
//        imageManager.imageUpdateHandle(link.getBloggerId(), newIconId, oldIconId);
//
//        return true;
//    }
//
//    @Override
//    public boolean deleteBloggerLink(int linkId) {
//        BloggerLink link = linkDao.getLink(linkId);
//        if (link == null) return false;
//        if (link.getIconId() != null) pictureDao.updateUseCountMinus(link.getIconId());
//
//        int effect = linkDao.delete(linkId);
//        if (effect < 0) return false;
//        return true;
//    }
//
//    @Override
//    public boolean getLinkForCheckExist(int linkId) {
//        Integer articleId = linkDao.getLinkForCheckExist(linkId);
//        if (articleId == null || articleId != linkId) return false;
//        return true;
//    }
}
