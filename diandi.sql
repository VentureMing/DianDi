SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `author_account`
-- ----------------------------
DROP TABLE IF EXISTS `author_account`;
CREATE TABLE `author_account` (
  `author_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '作者id',
  `author_name` varchar(50) NOT NULL COMMENT '用户名',
  `author_password` varchar(100) NOT NULL COMMENT '密码',
  `register_date` datetime NOT NULL COMMENT '注册时间',
  PRIMARY KEY (`author_id`),
  UNIQUE KEY `author_name` (`author_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `article`
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `article_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `author_id` int(11) unsigned NOT NULL COMMENT '文章所属作者id',
  `category_ids` varchar(100) DEFAULT NULL COMMENT '文章所属类别id（用特定字符分隔）',
  `label_ids` varchar(100) DEFAULT NULL COMMENT '文章包含的标签（用特定字符分隔）',
  `state` int(11) NOT NULL DEFAULT '0' COMMENT '文章状态（公开，私有，审核中，回收站...）',
  `title` varchar(80) NOT NULL COMMENT '文章标题',
  `content` longtext NOT NULL COMMENT '文章主体内容html格式',
  `content_md` longtext NOT NULL COMMENT '文章主体内容md格式',
  `summary` varchar(400) NOT NULL COMMENT '文章摘要',
  `release_date` datetime NOT NULL COMMENT '首次发布日期',
  `nearest_modify_date` datetime NOT NULL COMMENT '最后一次修改时间',
  `key_words` varchar(400) DEFAULT NULL COMMENT '文章关键字（空格分隔）',
  `word_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '文章字数',
  PRIMARY KEY (`article_id`),
  UNIQUE KEY `author_and_title` (`author_id`,`title`),#检查author_id和title组合的唯一性
  CONSTRAINT `article_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `author_account` (`author_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `author_image`
-- ----------------------------
DROP TABLE IF EXISTS `author_image`;
CREATE TABLE `author_image` (
  `image_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '照片id',
  `author_id` int(11) unsigned NOT NULL COMMENT '照片所属作者id',
  `describe` text COMMENT '照片描述',
  `image_category` int(11) NOT NULL DEFAULT '0' COMMENT '照片类别',
  `path` varchar(230) NOT NULL COMMENT '照片保存位置',
  `title` varchar(200) NOT NULL COMMENT '照片标题',
  `upload_date` datetime NOT NULL COMMENT '照片上传日期',
  `use_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '图片被引用次数',
  PRIMARY KEY (`image_id`),
  KEY `author_id` (`author_id`),
  CONSTRAINT `author_image_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `author_account` (`author_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `author_basic`
-- ----------------------------
DROP TABLE IF EXISTS `author_basic`;
CREATE TABLE `author_basic` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '作者资料id',
  `author_id` int(11) unsigned NOT NULL COMMENT '作者id',
  `profile_id` int(10) unsigned DEFAULT NULL COMMENT '作者头像',
  `nick_name` varchar(30) DEFAULT NULL COMMENT '昵称',#最多10个汉字
  `phone` varchar(20) DEFAULT NULL COMMENT '电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `intro` text COMMENT '一句话简介',
  PRIMARY KEY (`id`),
  UNIQUE KEY `author_id` (`author_id`),
  UNIQUE KEY `phone` (`phone`),
  KEY `profile_id` (`profile_id`),
  CONSTRAINT `author_basic_ibfk_2` FOREIGN KEY (`profile_id`) REFERENCES `author_image` (`image_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `author_basic_ibfk_3` FOREIGN KEY (`author_id`) REFERENCES `author_account` (`author_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `author_setting`
-- ----------------------------
DROP TABLE IF EXISTS `author_setting`;
CREATE TABLE `author_setting` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `author_id` int(10) unsigned NOT NULL,
  `main_page_nav_pos` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '作者主页个人信息栏位置，0为左，1为右',
  PRIMARY KEY (`id`),
  KEY `author_id` (`author_id`),
  CONSTRAINT `author_setting_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `author_account` (`author_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `author_category`
-- ----------------------------
DROP TABLE IF EXISTS `article_category`;
CREATE TABLE `article_category` (
  `category_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '文章类别id',
  `author_id` int(10) unsigned DEFAULT NULL COMMENT '创建该类别的作者',
  `image_id` int(10) unsigned DEFAULT NULL COMMENT '类别图标',
  `title` varchar(20) NOT NULL COMMENT '类别名',
  `describe` text COMMENT '类别描述',
  `create_date` datetime NOT NULL COMMENT '类别创建时间',
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `author_id` (`author_id`,`title`),
  KEY `image_id` (`image_id`),
  CONSTRAINT `article_category_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `author_account` (`author_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `article_category_ibfk_2` FOREIGN KEY (`image_id`) REFERENCES `author_image` (`image_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `article_collect`
-- ----------------------------
DROP TABLE IF EXISTS `article_collect`;
CREATE TABLE `article_collect` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '收藏表记录id',
  `article_id` int(11) unsigned NOT NULL COMMENT '收藏的文章id',
  `collector_id` int(10) unsigned NOT NULL COMMENT '收藏者id',
  `collect_date` datetime NOT NULL COMMENT '收藏时间',
  `category_id` int(10) unsigned DEFAULT '0' COMMENT '收藏到自己的哪一个类别下',
  PRIMARY KEY (`id`),
  UNIQUE KEY `article_and_collector` (`article_id`,`collector_id`),
  KEY `author_id` (`collector_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `article_collect_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `article_collect_ibfk_2` FOREIGN KEY (`collector_id`) REFERENCES `author_account` (`author_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `article_comment`
-- ----------------------------
DROP TABLE IF EXISTS `article_comment`;
CREATE TABLE `article_comment` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `article_id` int(10) unsigned NOT NULL COMMENT '评论对应的文章id',
  `commentator_id` int(10) unsigned DEFAULT NULL COMMENT '评论者id',
  `listener_id` int(10) unsigned DEFAULT NULL COMMENT '被评论者id',
  `content` text NOT NULL COMMENT '评论内容',
  `release_date` datetime NOT NULL COMMENT '评论时间',
  `state` int(11) NOT NULL DEFAULT '0' COMMENT '状态（审核中...）',
  PRIMARY KEY (`id`),
  KEY `article_id` (`article_id`),
  KEY `commentator_id` (`commentator_id`),
  KEY `listener_id` (`listener_id`),
  CONSTRAINT `article_comment_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `article_comment_ibfk_2` FOREIGN KEY (`commentator_id`) REFERENCES `author_account` (`author_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `article_comment_ibfk_3` FOREIGN KEY (`listener_id`) REFERENCES `author_account` (`author_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `article_complain`
-- ----------------------------
DROP TABLE IF EXISTS `article_complain`;
CREATE TABLE `article_complain` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '表id',
  `complainer_id` int(10) unsigned NOT NULL COMMENT '投诉者id',
  `article_id` int(10) unsigned NOT NULL COMMENT '投诉的文章',
  `content` varchar(255) NOT NULL COMMENT '投诉理由',
  `time` datetime NOT NULL COMMENT '投诉时间',
  PRIMARY KEY (`id`),
  KEY `article_id` (`article_id`),
  KEY `complainer_id` (`complainer_id`),
  CONSTRAINT `article_complain_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `article_complain_ibfk_2` FOREIGN KEY (`complainer_id`) REFERENCES `author_account` (`author_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `article_label`
-- ----------------------------
DROP TABLE IF EXISTS `article_label`;
CREATE TABLE `article_label` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `author_id` int(10) unsigned NOT NULL COMMENT '创建该标签的作者',
  `title` varchar(20) NOT NULL COMMENT '标签名',
  `create_date` datetime NOT NULL COMMENT '标签创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `author_and_title` (`author_id`,`title`),
  KEY `author_id` (`author_id`),
  CONSTRAINT `article_label_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `author_account` (`author_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `article_like`
-- ----------------------------
DROP TABLE IF EXISTS `article_like`;
CREATE TABLE `article_like` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '表id',
  `article_id` int(10) unsigned NOT NULL COMMENT '被喜欢的文章',
  `liker_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '仰慕者（给出like的人）articleId，未注册读者用0表示',
  `like_date` datetime NOT NULL COMMENT '时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `liker_and_article` (`liker_id`,`article_id`),
  KEY `article_like_ibfk_1` (`article_id`),
  CONSTRAINT `article_like_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `article_like_ibfk_2` FOREIGN KEY (`liker_id`) REFERENCES `author_account` (`author_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `article_statistics`
-- ----------------------------
DROP TABLE IF EXISTS `article_statistics`;
CREATE TABLE `article_statistics` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '表id',
  `article_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '文章id',
  `comment_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '评论次数',
  `view_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '浏览次数',
  `reply_comment_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '作者回复该文章评论的次数',
  `collect_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '收藏次数',
  `complain_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '投诉次数',
  `share_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '分享次数',
  `admire_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '赞赏次数',
  `like_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '喜欢次数',
  `release_date` date NOT NULL COMMENT '发布时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `article_id` (`article_id`),
  CONSTRAINT `article_statistics_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `article_admire`
-- ----------------------------
DROP TABLE IF EXISTS `article_admire`;
CREATE TABLE `article_admire` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '赞赏记录表id',
  `article_id` int(10) unsigned NOT NULL COMMENT '交易对应的文章id',
  `paier_id` int(11) unsigned NOT NULL COMMENT '付钱者id',
  `money` float(10,0) unsigned NOT NULL DEFAULT '0' COMMENT '金额',
  `tran_date` datetime NOT NULL COMMENT '交易时间',
  PRIMARY KEY (`articleId`),
  KEY `article_id` (`article_id`),
  KEY `paier_id` (`paier_id`),
  CONSTRAINT `article_admire_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `article_admire_ibfk_2` FOREIGN KEY (`paier_id`) REFERENCES `author_account` (`author_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
