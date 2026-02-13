-- Insert Dummy Users
INSERT INTO `User` (first_name, last_name, email, password)
VALUES
    ('Jane', 'Doe', 'jane@gmail.com', 'abcd'),
    ('John', 'Doe', 'john@gmail.com', 'abcd'),
    ('Adam', 'Smith', 'adam@gmail.com', 'abcd'),
    ('Alex', 'Smith', 'alex@gmail.com', 'abcd'),
    ('Admin', 'Admin', 'admin@gmail.com', 'abcd');

-- Insert Dummy Orders
INSERT INTO `Order` (
    order_id, cl_order_id, order_status, side, order_type, price, price_type, currency,
    instrument_name, settle_type, settle_date, trade_date, interested_party, user_id, order_quantity
)
VALUES
    ('ORD001', 'CL001', 0, 0, 1, '100.00', 0, 'USD', 'Apple Inc. Common Stock', 1, '2024-12-31', '2024-12-28', 'jane@gmail.com', 1, '10'),
    ('ORD002', 'CL002', 1, 1, 0, '200.50', 1, 'EUR', 'Alphabet Inc. Class A Common Stock', 2, '2024-12-30', '2024-12-28', 'janet@gmail.com', 1, '20'),
    ('ORD003', 'CL003', 2, 0, 1, '150.00', 2, 'JPY', 'HSBC Holdings PLC Class A Common Stock', 3, '2024-12-29', '2024-12-27', 'johnny@gmail.com', 2, '15'),
    ('ORD004', 'CL004', 0, 1, 1, '250.75', 1, 'AUD', 'Shell PLC Common Stock', 0, '2024-12-28', '2024-12-27', 'joshua@gmail.com', 2, '25'),
    ('ORD005', 'CL005', 1, 0, 0, '300.00', 0, 'USD', 'Meta Platforms, Inc Class A Common Stock', 2, '2024-12-31', '2024-12-28', 'adam@gmail.com', 3, '30'),
    ('ORD006', 'CL006', 2, 1, 1, '400.25', 1, 'EUR', 'Walt Disney Company (The) Common Stock', 3, '2024-12-30', '2024-12-27', 'adam@gmail.com', 3, '40'),
    ('ORD007', 'CL007', 0, 0, 1, '500.00', 0, 'JPY', 'Apple Inc. Common Stock', 1, '2024-12-29', '2024-12-26', 'alex@gmail.com', 4, '50'),
    ('ORD008', 'CL008', 1, 1, 0, '600.75', 2, 'AUD', 'Alphabet Inc. Class A Common Stock', 0, '2024-12-28', '2024-12-26', 'alexandar@gmail.com', 4, '60'),
    ('ORD009', 'CL009', 2, 0, 0, '700.00', 1, 'USD', 'Shell PLC Common Stock', 3, '2024-12-31', '2024-12-28', 'janet@gmail.com', 1, '70'),
    ('ORD010', 'CL010', 0, 1, 1, '800.25', 0, 'EUR', 'HSBC Holdings PLC Class A Common Stock', 2, '2024-12-30', '2024-12-28', 'johnny@gmail.com', 1, '80');

-- Insert roles into the Role table
INSERT INTO `Role` (`role_id`, `role_name`) VALUES
                                                (1, 'investor'),
                                                (2, 'admin');

-- Assign roles to users in the UserRole table
-- User IDs 1 to 4 are assigned the 'investor' role
INSERT INTO `UserRole` (`user_id`, `role_id`) VALUES
                                                  (1, 1),
                                                  (2, 1),
                                                  (3, 1),
                                                  (4, 1);

-- User ID 5 is assigned the 'admin' role
INSERT INTO `UserRole` (`user_id`, `role_id`) VALUES (5, 2);