-- --------------------------------------------------------
-- 主機:                           localhost
-- 伺服器版本:                        11.0.3-MariaDB-1:11.0.3+maria~ubu2204 - mariadb.org binary distribution
-- 伺服器作業系統:                      debian-linux-gnu
-- HeidiSQL 版本:                  12.5.0.6677
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- 傾印 DB_store 的資料庫結構
CREATE DATABASE IF NOT EXISTS `DB_store` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `DB_store`;

-- 傾印  資料表 DB_store.available_products 結構
CREATE TABLE IF NOT EXISTS `available_products` (
  `available_product_id` int(100) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT NULL,
  `price` int(100) DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  `updated_at` date DEFAULT NULL,
  PRIMARY KEY (`available_product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 取消選取資料匯出。

-- 傾印  資料表 DB_store.categories 結構
CREATE TABLE IF NOT EXISTS `categories` (
  `category_id` varchar(100) NOT NULL,
  `parent_id` varchar(100) DEFAULT NULL,
  `name` varchar(10) DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  `updated_at` date DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 取消選取資料匯出。

-- 傾印  資料表 DB_store.employees 結構
CREATE TABLE IF NOT EXISTS `employees` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  `password` varchar(100) NOT NULL,
  `is_delete` tinyint(1) DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  `updated_at` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=123524 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 取消選取資料匯出。

-- 傾印  資料表 DB_store.employee_roles 結構
CREATE TABLE IF NOT EXISTS `employee_roles` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `employee_id` int(100) NOT NULL DEFAULT 0,
  `role_id` int(100) NOT NULL DEFAULT 0,
  `created_at` date DEFAULT NULL,
  `updated_at` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Employee_Roles_ibfk_2` (`role_id`),
  KEY `Employee_Roles_ibfk_1` (`employee_id`),
  CONSTRAINT `employee_roles_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `employee_roles_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 取消選取資料匯出。

-- 傾印  資料表 DB_store.Orders 結構
CREATE TABLE IF NOT EXISTS `Orders` (
  `order_id` int(100) NOT NULL DEFAULT 0,
  `user_id` varchar(10) NOT NULL,
  `total_amount` int(100) DEFAULT NULL,
  `order_status` varchar(100) DEFAULT NULL,
  `payment_status` varchar(100) DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  `updated_at` date DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 取消選取資料匯出。

-- 傾印  資料表 DB_store.Order_Items 結構
CREATE TABLE IF NOT EXISTS `Order_Items` (
  `item_id` varchar(10) NOT NULL,
  `order_id` int(100) NOT NULL DEFAULT 0,
  `product_id` int(100) NOT NULL DEFAULT 0,
  `quantity` int(100) DEFAULT NULL,
  `subtotal` int(100) DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  `updated_at` date DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `Order_Items_fk1` (`order_id`),
  KEY `Order_Items_fk2` (`product_id`),
  CONSTRAINT `Order_Items_fk1` FOREIGN KEY (`order_id`) REFERENCES `Orders` (`order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Order_Items_fk2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 取消選取資料匯出。

-- 傾印  資料表 DB_store.products 結構
CREATE TABLE IF NOT EXISTS `products` (
  `product_id` int(100) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `price` int(100) DEFAULT NULL,
  `stock_quantity` int(100) DEFAULT NULL,
  `image_url` text DEFAULT NULL,
  `created_at` date DEFAULT NULL,
  `updated_at` date DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 取消選取資料匯出。

-- 傾印  資料表 DB_store.product_categories 結構
CREATE TABLE IF NOT EXISTS `product_categories` (
  `product_category_id` int(100) NOT NULL AUTO_INCREMENT,
  `product_id` int(100) NOT NULL,
  `category_id` varchar(100) NOT NULL,
  `created_at` date DEFAULT NULL,
  `updated_at` date DEFAULT NULL,
  PRIMARY KEY (`product_category_id`),
  KEY `Product_Categories_ibfk_1` (`product_id`),
  KEY `Product_Categories_ibfk_2` (`category_id`),
  CONSTRAINT `product_categories_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `product_categories_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 取消選取資料匯出。

-- 傾印  資料表 DB_store.roles 結構
CREATE TABLE IF NOT EXISTS `roles` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) NOT NULL,
  `created_at` date DEFAULT NULL,
  `updated_at` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=123349 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 取消選取資料匯出。

-- 傾印  資料表 DB_store.users 結構
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `is_deleted` tinytext DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 取消選取資料匯出。

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
