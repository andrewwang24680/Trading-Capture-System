CREATE TABLE `Role` (
                        `role_id` INT PRIMARY KEY AUTO_INCREMENT,
                        `role_name` VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `User` (
                        `user_id` INT NOT NULL AUTO_INCREMENT,
                        `first_name` VARCHAR(255) DEFAULT NULL,
                        `last_name` VARCHAR(255) DEFAULT NULL,
                        `email` VARCHAR(255) DEFAULT NULL,
                        `password` VARCHAR(255) DEFAULT NULL,
                        `role_id` INT DEFAULT NULL,
                        PRIMARY KEY (`user_id`),
                        UNIQUE (`email`),
                        CONSTRAINT `fk_user_role`
                            FOREIGN KEY (`role_id`)
                                REFERENCES `Role`(`role_id`)
                                ON DELETE SET NULL
                                ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Order` (
                         `order_id` VARCHAR(255) NOT NULL,
                         `cl_order_id` VARCHAR(255) DEFAULT NULL,
                         `order_status` INT DEFAULT NULL,
                         `side` INT DEFAULT NULL,
                         `order_type` INT DEFAULT NULL,
                         `price` VARCHAR(50) DEFAULT NULL,
                         `price_type` INT DEFAULT NULL,
                         `currency` VARCHAR(20) DEFAULT NULL,
                         `instrument_name` VARCHAR(255) DEFAULT NULL,
                         `settle_type` INT DEFAULT NULL,
                         `settle_date` VARCHAR(10) DEFAULT NULL,
                         `trade_date` VARCHAR(10) DEFAULT NULL,
                         `creation_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
                         `interested_party` VARCHAR(255) DEFAULT NULL,
                         `user_id` INT DEFAULT NULL,
                         `order_quantity` VARCHAR(50) DEFAULT NULL,
                         PRIMARY KEY (`order_id`),
                         CONSTRAINT `fk_order_user`
                             FOREIGN KEY (`user_id`)
                                 REFERENCES `User`(`user_id`)
                                 ON DELETE SET NULL
                                 ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;