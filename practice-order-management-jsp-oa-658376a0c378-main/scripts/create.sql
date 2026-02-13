CREATE TABLE `Order` (
                         `order_id` varchar(255) NOT NULL,
                         `cl_order_id` varchar(255) DEFAULT NULL,
                         `order_status` int DEFAULT NULL,
                         `side` int DEFAULT NULL,
                         `order_type` int DEFAULT NULL,
                         `price` varchar(50) DEFAULT NULL,
                         `price_type` int DEFAULT NULL,
                         `currency` varchar(20) DEFAULT NULL,
                         `instrument_name` varchar(255) DEFAULT NULL,
                         `settle_type` int DEFAULT NULL,
                         `settle_date` varchar(10) DEFAULT NULL,
                         `trade_date` varchar(10) DEFAULT NULL,
                         `creation_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                         `interested_party` varchar(255) DEFAULT NULL,
                         `user_id` INT DEFAULT NULL,
                         `order_quantity` varchar(50) DEFAULT NULL,
                         PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `User` (
                        `user_id` INT NOT NULL AUTO_INCREMENT,
                        `first_name` varchar(255) DEFAULT NULL,
                        `last_name` varchar(255) DEFAULT NULL,
                        `email` varchar(255) DEFAULT NULL,
                        `password` varchar(255) DEFAULT NULL,
                        PRIMARY KEY (`user_id`),
                        UNIQUE (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `Role` (
                        `role_id` INT PRIMARY KEY AUTO_INCREMENT,
                        `role_name` VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `UserRole` (
                            `user_id` INT NOT NULL,
                            `role_id` INT NOT NULL,
                            PRIMARY KEY (`user_id`, `role_id`),
                            CONSTRAINT `fk_userrole_user`
                                FOREIGN KEY (`user_id`)
                                    REFERENCES `User`(`user_id`)
                                    ON DELETE CASCADE
                                    ON UPDATE CASCADE,
                            CONSTRAINT `fk_userrole_role`
                                FOREIGN KEY (`role_id`)
                                    REFERENCES `Role`(`role_id`)
                                    ON DELETE CASCADE
                                    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;