# Spring Boot Coding Challenge: Currency Exchange Service

## Objective
You will build a **Spring Boot REST API** for a **Currency Exchange Service**.  
The service should allow users to:
- Store exchange rates by fetching **live data** from an external API.
- Retrieve the **latest exchange rate** for a given currency pair.
- Convert an **amount** between two currencies **only if the exchange rate is stored in the database**.

📌 **Third Party API:** Use [ExchangeRate API](https://www.exchangerate-api.com/docs/java-currency-api) to fetch live exchange rates.

⏳ **Time Limit:** **90–120 minutes**

⚠ **Important:** Treat this as an **Online Assessment**. Try to complete the challenge **without referencing documentation or past code**. The goal is to assess your ability to recall and apply knowledge under time constraints.

---  

## **Requirements**

### **1️⃣ Project Setup**
- Set up a **Spring Boot application from scratch**.
- Manually configure the required **dependencies**.

### **2️⃣ Database & Persistence**
- Use **JPA** with an **H2** or **MySQL** database.
- Define entities to store **currency exchange rates**.
- Consider what fields are necessary to support the required API functionality.

### **3️⃣ API Design**
You are required to design and implement **three RESTful APIs**:
- Fetch the latest exchange rate for a given currency pair from the **live API** and **store it in the database**.
- Retrieve the **most recently stored exchange rate** for a given currency pair.
- Convert an **amount** from one currency to another using the **stored exchange rate**.


### **4️⃣ General Considerations**
- Follow **RESTful API principles**.
- Ensure **proper validation and error handling** (e.g., invalid currencies, missing exchange rates).
- Use **meaningful HTTP status codes**.
- Implement **unit tests** at least for the **service layer** to validate business logic.
- Provide a **Postman collection** to test your endpoints.


Good luck! 🚀  
