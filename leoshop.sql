/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : leoshop

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-07-08 07:34:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `ADDRESS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `SEND_PLACE` varchar(255) NOT NULL,
  `SEND_MAN` varchar(255) NOT NULL,
  `SEND_PHONE` varchar(255) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  PRIMARY KEY (`ADDRESS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES ('1', '山西省太原市经济开发区', '张三', '13101253654', '1');
INSERT INTO `address` VALUES ('2', '山西省太原市高新开发区', '李四', '15214654434', '1');
INSERT INTO `address` VALUES ('3', '山西省太原市迎泽区', '王五', '15365482135', '1');
INSERT INTO `address` VALUES ('4', '山西省太原市杏花岭区', '赵六', '13245698782', '1');
INSERT INTO `address` VALUES ('5', '山西省太原市尖草坪区', '李雷', '13865421321', '1');
INSERT INTO `address` VALUES ('6', '山西省太原市万柏林区', '韩梅梅', '15935125213', '1');
INSERT INTO `address` VALUES ('15', '山西太原', '李丽', '15135156678', '24');
INSERT INTO `address` VALUES ('16', '山西太原', '李丽', '15135155578', '1');
INSERT INTO `address` VALUES ('17', '山西省太原市万柏林区', '张崇斌', '13133167458', '23');
INSERT INTO `address` VALUES ('18', '北京 朝阳区', '文章', '13133167458', '27');
INSERT INTO `address` VALUES ('19', '山西太原', '文章', '13133167458', '1');

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart` (
  `CART_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUCT_ID` int(11) NOT NULL DEFAULT '0',
  `SALE_COUNT` int(11) NOT NULL DEFAULT '0',
  `USER_ID` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`CART_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES ('34', '53', '1', '26');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `COMMENT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `LEVEL` int(1) NOT NULL DEFAULT '5',
  `CONTENT` varchar(255) NOT NULL DEFAULT '好评！',
  `ORDER_ID` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`COMMENT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('7', '5', '好评！', '46');
INSERT INTO `comment` VALUES ('8', '1', '差评！', '58');
INSERT INTO `comment` VALUES ('9', '2', '好评！', '63');
INSERT INTO `comment` VALUES ('10', '5', '好评！', '72');
INSERT INTO `comment` VALUES ('11', '2', '不好', '67');
INSERT INTO `comment` VALUES ('12', '4', '宝贝收到了，还不错', '76');
INSERT INTO `comment` VALUES ('13', '5', '好评！', '77');
INSERT INTO `comment` VALUES ('14', '5', '非常好@@@@@@@@@@@@@@@@@@@@@@#############￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥￥', '53');
INSERT INTO `comment` VALUES ('15', '4', '好评！', '55');
INSERT INTO `comment` VALUES ('16', '5', '好评！', '54');
INSERT INTO `comment` VALUES ('17', '4', '好评！', '52');
INSERT INTO `comment` VALUES ('18', '4', '好评！', '51');
INSERT INTO `comment` VALUES ('19', '5', '好评！', '50');
INSERT INTO `comment` VALUES ('20', '4', '好评！', '48');
INSERT INTO `comment` VALUES ('21', '4', '好评！', '49');
INSERT INTO `comment` VALUES ('22', '5', '好评！', '47');
INSERT INTO `comment` VALUES ('23', '3', '好评！', '59');
INSERT INTO `comment` VALUES ('24', '4', '好评！', '60');
INSERT INTO `comment` VALUES ('25', '5', '好评！', '61');
INSERT INTO `comment` VALUES ('26', '5', '好评！', '71');
INSERT INTO `comment` VALUES ('27', '5', '好评！', '64');
INSERT INTO `comment` VALUES ('28', '3', '好评！', '65');
INSERT INTO `comment` VALUES ('29', '4', '好评！', '66');
INSERT INTO `comment` VALUES ('30', '3', '好评！', '62');
INSERT INTO `comment` VALUES ('31', '4', '好评！', '57');
INSERT INTO `comment` VALUES ('32', '2', '差', '56');
INSERT INTO `comment` VALUES ('33', '4', '好评！', '73');
INSERT INTO `comment` VALUES ('34', '4', '好评！', '74');

-- ----------------------------
-- Table structure for manager
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager` (
  `MANAGER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MANAGER_NAME` varchar(255) NOT NULL,
  `MANAGER_PASSWORD` varchar(255) NOT NULL,
  PRIMARY KEY (`MANAGER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of manager
-- ----------------------------
INSERT INTO `manager` VALUES ('1', 'admin', 'admin');
INSERT INTO `manager` VALUES ('2', 'zs', '123');
INSERT INTO `manager` VALUES ('8', 'root', 'root');
INSERT INTO `manager` VALUES ('10', 'admin123', 'admin1234');

-- ----------------------------
-- Table structure for manager_role
-- ----------------------------
DROP TABLE IF EXISTS `manager_role`;
CREATE TABLE `manager_role` (
  `MANAGER_ROLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MANAGER_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`MANAGER_ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of manager_role
-- ----------------------------
INSERT INTO `manager_role` VALUES ('1', '1', '1');
INSERT INTO `manager_role` VALUES ('2', '2', '2');
INSERT INTO `manager_role` VALUES ('4', '8', '1');
INSERT INTO `manager_role` VALUES ('6', '10', '2');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `MENU_ID` int(11) NOT NULL,
  `MENU_NAME` varchar(255) NOT NULL,
  `PARENT_MENU` int(11) DEFAULT '0',
  `MENU_URL` varchar(255) DEFAULT '',
  `MENU_STATUS` int(1) NOT NULL DEFAULT '1' COMMENT '1-->启用//0-->禁用',
  `NOTE` varchar(255) DEFAULT '',
  PRIMARY KEY (`MENU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '系统管理', '0', '', '1', '');
INSERT INTO `menu` VALUES ('2', '业务管理', '0', '', '1', '');
INSERT INTO `menu` VALUES ('11', '管理员列表', '1', 'Admin/getManagers.bg', '1', '');
INSERT INTO `menu` VALUES ('12', '权限管理', '1', 'Admin/getPermissions.bg', '1', '');
INSERT INTO `menu` VALUES ('13', '菜单管理', '1', 'Menu/getMenus.bg', '1', '');
INSERT INTO `menu` VALUES ('21', '会员管理', '2', 'User/queryUsers.bg', '1', '');
INSERT INTO `menu` VALUES ('22', '商品管理', '2', 'Product/queryProducts.bg', '1', '');
INSERT INTO `menu` VALUES ('23', '订单管理', '2', 'Order/queryOrders.bg', '1', '');
INSERT INTO `menu` VALUES ('24', '修改密码', '2', 'Admin/editPassword.jsp', '1', '');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `ORDER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ORDER_NUM` varchar(255) NOT NULL DEFAULT '',
  `ORDER_TIME` datetime NOT NULL,
  `ORDER_STATUS` int(1) NOT NULL DEFAULT '0' COMMENT '0-->已下单//1-->已付款//2-->已发货//3-->已完成',
  `NOTE` varchar(100) DEFAULT '',
  `USER_ID` int(11) NOT NULL DEFAULT '0',
  `SEND_PLACE` varchar(255) NOT NULL DEFAULT '',
  `SEND_MAN` varchar(255) NOT NULL DEFAULT '',
  `SEND_PHONE` varchar(255) NOT NULL DEFAULT '',
  `PRODUCT_ID` int(11) NOT NULL DEFAULT '0',
  `PRODUCT_NAME` varchar(255) NOT NULL DEFAULT '',
  `PRODUCT_PRICE` double(11,2) NOT NULL DEFAULT '0.00',
  `SALE_COUNT` int(11) NOT NULL DEFAULT '0',
  `VISIBLE` int(1) NOT NULL DEFAULT '1' COMMENT '0-->用户前台删除,订单列表不显示//1-->用户未删除',
  PRIMARY KEY (`ORDER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('46', '14663234467091', '2016-06-19 16:04:03', '3', '快点儿发货~\\(≧▽≦)/~啦啦啦', '1', '山西省太原市经济开发区', '张三', '13101253654', '54', '帕莎太阳镜女2016新款方形墨镜潮偏光眼镜防眩光帕沙炫彩膜T60070', '428.00', '1', '1');
INSERT INTO `orders` VALUES ('47', '146641231563024', '2016-06-20 16:45:13', '3', '', '24', '山西太原', '李丽', '15135156678', '52', '暴龙太阳镜女2016年新款安妮.海瑟薇明星同款时尚偏光墨镜BL5002', '558.32', '1', '1');
INSERT INTO `orders` VALUES ('48', '146641237339624', '2016-06-20 16:46:07', '3', '快点儿发货，O(∩_∩)O谢谢', '24', '山西太原', '李丽', '15135156678', '51', '法拉维迪2016女士圆脸太阳镜潮 大框个性方形复古偏光镜开车墨镜', '79.01', '1', '1');
INSERT INTO `orders` VALUES ('49', '146641237339624', '2016-06-20 16:46:07', '3', '快点儿发货，O(∩_∩)O谢谢', '24', '山西太原', '李丽', '15135156678', '55', '跑男金晨姜妍小宋佳李玟冰冰吴昕邓超同款太阳眼镜墨镜女潮2016', '179.85', '1', '1');
INSERT INTO `orders` VALUES ('50', '146641239233024', '2016-06-20 16:46:27', '3', '', '24', '山西太原', '李丽', '15135156678', '55', '跑男金晨姜妍小宋佳李玟冰冰吴昕邓超同款太阳眼镜墨镜女潮2016', '179.85', '1', '1');
INSERT INTO `orders` VALUES ('51', '146641240124924', '2016-06-20 16:46:40', '3', '', '24', '山西太原', '李丽', '15135156678', '53', '雷朋3025彩膜112 雷朋3025偏光男女太阳镜 樱花粉色反光近视墨镜', '600.34', '1', '1');
INSERT INTO `orders` VALUES ('52', '146641242315324', '2016-06-20 16:46:57', '3', '', '24', '山西太原', '李丽', '15135156678', '54', '帕莎太阳镜女2016新款方形墨镜潮偏光眼镜防眩光帕沙炫彩膜T60070', '428.00', '1', '1');
INSERT INTO `orders` VALUES ('53', '146641244620124', '2016-06-20 16:47:22', '3', '', '24', '山西太原', '李丽', '15135156678', '59', '海伦凯勒太阳镜女2016潮圆脸偏光镜女司机墨镜加钱可配近视太阳镜', '258.00', '1', '1');
INSERT INTO `orders` VALUES ('54', '146641248117724', '2016-06-20 16:47:54', '3', '', '24', '山西太原', '李丽', '15135156678', '62', '杨幂明星同款墨镜女潮偏光2016新款郑爽太阳镜超轻优雅配近视眼镜', '138.00', '4', '1');
INSERT INTO `orders` VALUES ('55', '146641252773324', '2016-06-20 16:48:40', '3', '', '24', '山西太原', '李丽', '15135156678', '58', '正品代购凯伦太阳镜女偏光彩膜箭头墨镜女士太阳镜2016新款潮圆脸', '683.00', '1', '1');
INSERT INTO `orders` VALUES ('56', '14664125675021', '2016-06-20 16:49:19', '3', '', '1', '山西省太原市经济开发区', '张三', '13101253654', '55', '跑男金晨姜妍小宋佳李玟冰冰吴昕邓超同款太阳眼镜墨镜女潮2016', '179.85', '1', '1');
INSERT INTO `orders` VALUES ('57', '14664125699751', '2016-06-20 16:49:28', '3', '', '1', '山西省太原市经济开发区', '张三', '13101253654', '51', '法拉维迪2016女士圆脸太阳镜潮 大框个性方形复古偏光镜开车墨镜', '79.01', '1', '1');
INSERT INTO `orders` VALUES ('58', '14664125819121', '2016-06-20 16:49:40', '3', '', '1', '山西省太原市万柏林区', '韩梅梅', '15935125213', '55', '跑男金晨姜妍小宋佳李玟冰冰吴昕邓超同款太阳眼镜墨镜女潮2016', '179.85', '1', '1');
INSERT INTO `orders` VALUES ('59', '14664128657891', '2016-06-20 16:54:25', '3', '快点儿发货，O(∩_∩)O谢谢', '1', '山西省太原市尖草坪区', '李雷', '13865421321', '60', 'Kimball2016V潮牌太阳镜女DIDI A明星同款墨镜女偏光韩版潮GM眼镜', '178.00', '1', '1');
INSERT INTO `orders` VALUES ('60', '14664128657891', '2016-06-20 16:54:25', '3', '快点儿发货，O(∩_∩)O谢谢', '1', '山西省太原市尖草坪区', '李雷', '13865421321', '54', '帕莎太阳镜女2016新款方形墨镜潮偏光眼镜防眩光帕沙炫彩膜T60070', '428.00', '3', '1');
INSERT INTO `orders` VALUES ('61', '14664128657891', '2016-06-20 16:54:25', '3', '快点儿发货，O(∩_∩)O谢谢', '1', '山西省太原市尖草坪区', '李雷', '13865421321', '53', '雷朋3025彩膜112 雷朋3025偏光男女太阳镜 樱花粉色反光近视墨镜', '600.34', '1', '1');
INSERT INTO `orders` VALUES ('62', '14664134317701', '2016-06-20 17:03:45', '3', '', '1', '山西省太原市尖草坪区', '李雷', '13865421321', '58', '正品代购凯伦太阳镜女偏光彩膜箭头墨镜女士太阳镜2016新款潮圆脸', '683.00', '1', '1');
INSERT INTO `orders` VALUES ('63', '14664135150151', '2016-06-20 17:05:13', '3', '', '1', '山西省太原市杏花岭区', '赵六', '13245698782', '53', '雷朋3025彩膜112 雷朋3025偏光男女太阳镜 樱花粉色反光近视墨镜', '600.34', '1', '1');
INSERT INTO `orders` VALUES ('64', '14664148526701', '2016-06-20 17:27:26', '3', 'fda', '1', '山西太原', '李丽', '15135155578', '50', '太阳镜女偏光镜墨镜女圆脸个性女潮复古可配近视太阳眼镜2016', '58.86', '2', '1');
INSERT INTO `orders` VALUES ('65', '14664148526701', '2016-06-20 17:27:26', '3', 'fda', '1', '山西太原', '李丽', '15135155578', '50', '太阳镜女偏光镜墨镜女圆脸个性女潮复古可配近视太阳眼镜2016', '58.86', '2', '1');
INSERT INTO `orders` VALUES ('66', '14664148526701', '2016-06-20 17:27:26', '3', 'fda', '1', '山西太原', '李丽', '15135155578', '50', '太阳镜女偏光镜墨镜女圆脸个性女潮复古可配近视太阳眼镜2016', '58.86', '2', '1');
INSERT INTO `orders` VALUES ('67', '14664150406861', '2016-06-20 17:30:34', '3', '', '1', '山西省太原市尖草坪区', '李雷', '13865421321', '50', '太阳镜女偏光镜墨镜女圆脸个性女潮复古可配近视太阳眼镜2016', '58.86', '1', '1');
INSERT INTO `orders` VALUES ('68', '14668130642571', '2016-06-25 08:04:15', '2', '', '1', '山西省太原市经济开发区', '张三', '13101253654', '50', '太阳镜女偏光镜墨镜女圆脸个性女潮复古可配近视太阳眼镜2016', '58.86', '1', '1');
INSERT INTO `orders` VALUES ('69', '14668133657281', '2016-06-25 08:09:16', '2', '测试减库存', '1', '山西省太原市万柏林区', '韩梅梅', '15935125213', '52', '暴龙太阳镜女2016年新款安妮.海瑟薇明星同款时尚偏光墨镜BL5002', '558.32', '1', '1');
INSERT INTO `orders` VALUES ('70', '14668136664541', '2016-06-25 08:14:19', '2', '测试减库存', '1', '山西省太原市万柏林区', '韩梅梅', '15935125213', '50', '太阳镜女偏光镜墨镜女圆脸个性女潮复古可配近视太阳眼镜2016', '58.86', '2', '1');
INSERT INTO `orders` VALUES ('71', '14668139216321', '2016-06-25 08:18:35', '3', '测试减库存', '1', '山西省太原市万柏林区', '韩梅梅', '15935125213', '50', '太阳镜女偏光镜墨镜女圆脸个性女潮复古可配近视太阳眼镜2016', '58.86', '4', '1');
INSERT INTO `orders` VALUES ('72', '14668143261931', '2016-06-25 08:25:19', '3', '测试减库存', '1', '山西省太原市万柏林区', '韩梅梅', '15935125213', '50', '太阳镜女偏光镜墨镜女圆脸个性女潮复古可配近视太阳眼镜2016', '58.86', '6', '1');
INSERT INTO `orders` VALUES ('73', '146683978759318723', '2016-06-25 15:29:48', '3', 'hello ', '1', '山西省太原市经济开发区', '张三', '13101253654', '52', '暴龙太阳镜女2016年新款安妮.海瑟薇明星同款时尚偏光墨镜BL5002', '558.32', '1', '1');
INSERT INTO `orders` VALUES ('74', '146683978759318723', '2016-06-25 15:29:48', '3', 'hello ', '1', '山西省太原市经济开发区', '张三', '13101253654', '54', '帕莎太阳镜女2016新款方形墨镜潮偏光眼镜防眩光帕沙炫彩膜T60070', '428.00', '1', '1');
INSERT INTO `orders` VALUES ('75', '146684344528023655', '2016-06-25 16:30:45', '0', '', '23', '山西省太原市万柏林区', '张崇斌', '13133167458', '50', '太阳镜女偏光镜墨镜女圆脸个性女潮复古可配近视太阳眼镜2016', '58.86', '1', '1');
INSERT INTO `orders` VALUES ('76', '1466855279800246305', '2016-06-25 19:48:00', '3', 'dagagfdahgfsh', '24', '山西太原', '李丽', '15135156678', '50', '太阳镜女偏光镜墨镜女圆脸个性女潮复古可配近视太阳眼镜2016', '58.86', '1', '1');
INSERT INTO `orders` VALUES ('77', '1466863088219277353', '2016-06-25 21:58:08', '3', '测试立即购买不通过购物车', '27', '北京 朝阳区', '文章', '13133167458', '52', '暴龙太阳镜女2016年新款安妮.海瑟薇明星同款时尚偏光墨镜BL5002', '558.32', '1', '1');
INSERT INTO `orders` VALUES ('78', '149945916851337921', '2017-07-08 04:26:09', '2', 'rgtrghthhtr', '1', '山西省太原市经济开发区', '张三', '13101253654', '50', '太阳镜女偏光镜墨镜女圆脸个性女潮复古可配近视太阳眼镜2016', '58.86', '2', '1');
INSERT INTO `orders` VALUES ('79', '149945916851337921', '2017-07-08 04:26:09', '2', 'rgtrghthhtr', '1', '山西省太原市经济开发区', '张三', '13101253654', '54', '帕莎太阳镜女2016新款方形墨镜潮偏光眼镜防眩光帕沙炫彩膜T60070', '428.00', '2', '1');
INSERT INTO `orders` VALUES ('80', '149945916851337921', '2017-07-08 04:26:09', '2', 'rgtrghthhtr', '1', '山西省太原市经济开发区', '张三', '13101253654', '62', '杨幂明星同款墨镜女潮偏光2016新款郑爽太阳镜超轻优雅配近视眼镜', '138.00', '2', '1');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `PRODUCT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUCT_NAME` varchar(255) NOT NULL DEFAULT '',
  `PRODUCT_PRICE` double(11,2) NOT NULL DEFAULT '0.00',
  `PRODUCT_DESC` varchar(255) DEFAULT '',
  `PRODUCT_IMAGE_PATH` varchar(255) DEFAULT '',
  `STORE_NUM` int(11) NOT NULL DEFAULT '0',
  `PRODUCT_STATUS` int(1) DEFAULT '1' COMMENT '0-->下架/1-->上架',
  PRIMARY KEY (`PRODUCT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('50', '太阳镜女偏光镜墨镜女圆脸个性女潮复古可配近视太阳眼镜2016', '58.86', '太阳镜女偏光镜墨镜女圆脸个性女潮复古可配近视太阳眼镜2016\r\n司机驾驶 旅游休闲 时尚装扮 可配近视 太阳镜', 'upload/2836b986-317f-47a8-9b19-775c6f73395e.jpg', '23159', '1');
INSERT INTO `product` VALUES ('51', '法拉维迪2016女士圆脸太阳镜潮 大框个性方形复古偏光镜开车墨镜', '79.01', '法拉维迪2016女士圆脸太阳镜潮 大框个性方形复古偏光镜开车墨镜', 'upload/f4af5e26-ad2d-4321-b9a1-94a0b18ee28a.jpg', '8490', '1');
INSERT INTO `product` VALUES ('52', '暴龙太阳镜女2016年新款安妮.海瑟薇明星同款时尚偏光墨镜BL5002', '558.32', '暴龙太阳镜女2016年新款安妮.海瑟薇明星同款时尚偏光墨镜BL5002\r\n安妮.海瑟薇 明星同款 高清偏光 太阳镜女', 'upload/c6441e18-ef7c-4392-911c-32152a7b5e5e.jpg', '234242', '1');
INSERT INTO `product` VALUES ('53', '雷朋3025彩膜112 雷朋3025偏光男女太阳镜 樱花粉色反光近视墨镜', '600.34', '雷朋3025彩膜112 雷朋3025偏光男女太阳镜 樱花粉色反光近视墨镜\r\n可选配近视镜 炫出你的色彩', 'upload/64a34e68-d96f-412b-b82c-40a232c85d7d.jpg', '1000', '1');
INSERT INTO `product` VALUES ('54', '帕莎太阳镜女2016新款方形墨镜潮偏光眼镜防眩光帕沙炫彩膜T60070', '428.00', '帕莎太阳镜女2016新款方形墨镜潮偏光眼镜防眩光帕沙炫彩膜T60070\r\n2016款 C.JYAO 上海时装周 走秀款', 'upload/3cb85958-30cd-4eb5-9600-1d35305fc626.jpg', '2097', '1');
INSERT INTO `product` VALUES ('55', '跑男金晨姜妍小宋佳李玟冰冰吴昕邓超同款太阳眼镜墨镜女潮2016', '179.85', '跑男金晨姜妍小宋佳李玟冰冰吴昕邓超同款太阳眼镜墨镜女潮2016\r\n火遍全球的平面蛤蟆镜来袭，宋佳，李冰冰，李玟，陈乔恩，韩庚，黄渤，邓超等众多明星都拥有了它，您也值得拥有！', 'upload/293bf758-692e-4241-9e12-ceaa3392b69f.jpg', '1834', '1');
INSERT INTO `product` VALUES ('56', '暴龙BOLON2016新款太阳镜男时尚蛤蟆镜高清偏光驾驶镜BL8001', '558.00', '暴龙BOLON2016新款太阳镜男时尚蛤蟆镜高清偏光驾驶镜BL8001\r\n暴龙2016 新款 高清偏光', 'upload/dc821307-94d8-427c-8863-acda866aca26.jpg', '1251', '1');
INSERT INTO `product` VALUES ('57', '2016新款海伦凯勒偏光太阳镜女 时尚水钻开车驾驶墨镜潮H8312', '338.00', '2016新款海伦凯勒偏光太阳镜女 时尚水钻开车驾驶墨镜潮H8312\r\n林志玲亲手设 镂空设计 加上水晶作点', 'upload/dfb22a1a-ce56-4b5c-a418-770308b3f2cf.jpg', '455', '1');
INSERT INTO `product` VALUES ('58', '正品代购凯伦太阳镜女偏光彩膜箭头墨镜女士太阳镜2016新款潮圆脸', '680.10', '正品代购凯伦太阳镜女偏光彩膜箭头墨镜女士太阳镜2016新款潮圆脸\r\n热卖爆款。100%专柜正品，支持任何形式专柜验货对比。全场顺丰包邮!优雅个性 潮人气质女神必备。', 'upload/0aeb3c78-3ece-4a94-821c-7b0cff6981ef.jpg', '117', '1');
INSERT INTO `product` VALUES ('59', '海伦凯勒太阳镜女2016潮圆脸偏光镜女司机墨镜加钱可配近视太阳镜', '258.00', '海伦凯勒太阳镜女2016潮圆脸偏光镜女司机墨镜加钱可配近视太阳镜\r\n知性优雅 经典百搭 获奖产品', 'upload/1c21b5f7-243a-49dd-a77a-cced8acc3822.jpg', '871', '1');
INSERT INTO `product` VALUES ('60', 'Kimball2016V潮牌太阳镜女DIDI A明星同款墨镜女偏光韩版潮GM眼镜', '178.00', 'Kimball2016V潮牌太阳镜女DIDI A明星同款墨镜女偏光韩版潮GM眼镜\r\n进口偏光镜片 手造板材镜框 百搭款式 可配近视', 'upload/3dc251ea-6a00-4af2-aa5b-6a0c6678d7ae.jpg', '225', '1');
INSERT INTO `product` VALUES ('61', '海伦凯勒太阳镜女潮防紫外线眼睛开车彩膜反光明星款品牌偏光墨镜', '299.00', '海伦凯勒太阳镜女潮防紫外线眼睛开车彩膜反光明星款品牌偏光墨镜\r\n花样姐姐同款 隐藏框设计 尼龙镜片 明星范', 'upload/ddfd5090-ed39-4ec0-946d-b1266644dfdd.jpg', '3', '1');
INSERT INTO `product` VALUES ('62', '杨幂明星同款墨镜女潮偏光2016新款郑爽太阳镜超轻优雅配近视眼镜', '138.00', '杨幂明星同款墨镜女潮偏光2016新款郑爽太阳镜超轻优雅配近视眼镜\r\n大幂幂最常用的一款墨镜，多个场合均可看到佩戴，佩戴效果非常抢镜，喜欢的亲还不赶紧下手！王菲、李湘也佩戴同款，保证一手货源，库存充足，专柜品质，价格实在。买就送专柜套盒，全国包邮。', 'upload/5f9626b1-4b0d-4913-913d-16fca93f0517.jpg', '1621', '1');
INSERT INTO `product` VALUES ('63', '帕森（PARZIN）太阳镜女款墨镜 防紫外线时尚偏光太阳眼镜6216 亮黑色', '99.12', '夏日至简小脸术 护目遮脸两不误 大框就是这么任性', 'upload/565ce49f-1355-46eb-88ca-198edacdef9d.jpg', '1882', '1');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(255) NOT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理员');
INSERT INTO `role` VALUES ('2', '业务管理员');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `ROLE_MENU_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int(11) NOT NULL,
  `MENU_ID` int(11) NOT NULL,
  PRIMARY KEY (`ROLE_MENU_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('1', '1', '1');
INSERT INTO `role_menu` VALUES ('2', '1', '2');
INSERT INTO `role_menu` VALUES ('3', '2', '2');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(255) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `USER_STATUS` int(1) NOT NULL DEFAULT '1' COMMENT '默认1：标记启用，0：标记禁用账户',
  `TRUENAME` varchar(255) DEFAULT '',
  `PHONE` varchar(255) DEFAULT '',
  `ADDRESS` varchar(255) DEFAULT '',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'zs123456', 'zs123456-', '1', '张三', '13101253654', '太原');
INSERT INTO `user` VALUES ('23', 'leoshopuser1', 'leoshopuser1-', '1', '李四', '13513612345', '太原');
INSERT INTO `user` VALUES ('24', 'leoshopuser2', 'leoshopuser2-', '1', '', '', '');
INSERT INTO `user` VALUES ('25', 'leoshopuser3', 'leoshopuser3-8', '1', '', '', '');
INSERT INTO `user` VALUES ('26', 'leoshopuser4', 'leoshopuser4-', '1', '', '', '');
INSERT INTO `user` VALUES ('27', 'leoshopuser5', 'user5-', '1', '韩梅梅', '13456789916', '北京');
INSERT INTO `user` VALUES ('28', 'leoshopuser6', 'leoshopuser6-', '0', '', '', '');
INSERT INTO `user` VALUES ('29', 'leoshopuser7', 'leoshopuser7-', '0', '', '', '');
INSERT INTO `user` VALUES ('30', 'leoshopuser8', 'leoshopuser8-', '0', '', '', '');
INSERT INTO `user` VALUES ('31', 'userleoshp11', 'userleoshp11-', '1', '', '', '');
INSERT INTO `user` VALUES ('32', 'userleoshp112', 'userleoshp112-', '1', '', '', '');
INSERT INTO `user` VALUES ('33', 'userleoshp113', 'userleoshp113-', '1', '', '', '');
INSERT INTO `user` VALUES ('34', 'userleoshp1133', 'userleoshp1133-', '1', '', '', '');
INSERT INTO `user` VALUES ('35', 'userleoshp11331', '1', '1', '', '', '');
INSERT INTO `user` VALUES ('36', 'userleoshp11332', 'user5-', '1', '', '', '');
INSERT INTO `user` VALUES ('37', 'leoshopuser11', 'leoshopuser11-', '1', '', '', '');
