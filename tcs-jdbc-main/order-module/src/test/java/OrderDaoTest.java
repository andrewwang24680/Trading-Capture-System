import com.drill.dao.impl.OrderDaoImpl;
import lombok.var;
import org.junit.jupiter.api.*;
import org.h2.jdbcx.JdbcDataSource;
import com.drill.entity.Order;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderDaoTest {

    private Connection connection;
    private OrderDaoImpl orderDao; // DAO 实现类

    @BeforeAll
    void setupDatabase() throws Exception {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=MYSQL");
        dataSource.setUser("sa");
        dataSource.setPassword("");

        connection = dataSource.getConnection();

        executeSqlFile(connection, "src/test/resources/schema.sql");

        executeSqlFile(connection, "src/test/resources/data.sql");

        orderDao = new OrderDaoImpl();
    }

    @AfterAll
    void closeDatabase() throws Exception {
        connection.close();
    }

    // 辅助方法：加载并执行 SQL 文件
    private void executeSqlFile(Connection connection, String filePath) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath));
             Statement stmt = connection.createStatement()) {
            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sql.append(line);
                // 如果是分号结尾，则执行语句
                if (line.trim().endsWith(";")) {
                    stmt.execute(sql.toString());
                    sql.setLength(0); // 清空 StringBuilder
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute SQL file: " + filePath, e);
        }
    }

    @Test
    void testGetOrderById() {
        var result = orderDao.getOrderById("ORD001");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("ORD001", result.get().getOrderId());
    }

    @Test
    void testCreateOrder() {
        Order newOrder = Order.builder()
                .orderId("ORD003")
                .clOrderId("CL003")
                .orderStatus(3)
                .orderQuantity("300")
                .userId(1)
                .build();

        String createdOrderId = orderDao.createOrder(newOrder);
        Assertions.assertEquals("ORD003", createdOrderId);

        // 验证数据库
        var result = orderDao.getOrderById("ORD003");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("ORD003", result.get().getOrderId());
    }

    @Test
    void testDeleteOrder() {
        boolean isDeleted = orderDao.deleteOrder("ORD001");
        Assertions.assertTrue(isDeleted);

        // 验证是否删除
        var result = orderDao.getOrderById("ORD001");
        Assertions.assertFalse(result.isPresent());
    }
}
