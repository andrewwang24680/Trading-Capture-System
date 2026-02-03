INSERT INTO `Role` (`role_id`, `role_name`) VALUES
                                                (1, 'Admin'),
                                                (2, 'Trader'),
                                                (3, 'Viewer');

INSERT INTO `User` (`user_id`, `first_name`, `last_name`, `email`, `password`, `role_id`) VALUES
                                                                                              (1, 'Alice', 'Smith', 'alice.smith@example.com', 'password123', 1),
                                                                                              (2, 'Bob', 'Johnson', 'bob.johnson@example.com', 'password456', 2),
                                                                                              (3, 'Charlie', 'Brown', 'charlie.brown@example.com', 'password789', 1);

INSERT INTO `Order` (`order_id`, `cl_order_id`, `order_status`, `side`, `order_type`, `price`, `price_type`, `currency`, `instrument_name`, `settle_type`, `settle_date`, `trade_date`, `creation_time`, `interested_party`, `user_id`, `order_quantity`) VALUES
                                                                                                                                                                                                                                                              ('ORD001', 'CL001', 1, 1, 1, '100.50', 1, 'USD', 'Stock_A', 1, '2024-12-31', '2024-12-15', CURRENT_TIMESTAMP, 'Party_A', 1, '1000'),
                                                                                                                                                                                                                                                              ('ORD002', 'CL002', 2, 2, 2, '200.75', 2, 'USD', 'Stock_B', 2, '2024-12-30', '2024-12-14', CURRENT_TIMESTAMP, 'Party_B', 2, '2000'),
                                                                                                                                                                                                                                                              ('ORD003', 'CL003', 3, 1, 1, '150.00', 1, 'EUR', 'Stock_C', 1, '2024-12-29', '2024-12-13', CURRENT_TIMESTAMP, 'Party_C', 3, '1500');
