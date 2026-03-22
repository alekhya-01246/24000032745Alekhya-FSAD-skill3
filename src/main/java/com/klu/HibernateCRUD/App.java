package com.klu.HibernateCRUD;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.klu.entity.Product;

import java.util.List;

public class App {

    public static void main(String[] args) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        // 1️⃣ INSERT 8 PRODUCTS
        session.save(new Product("Laptop", "HP", 60000, 5));
        session.save(new Product("Phone", "Samsung", 30000, 10));
        session.save(new Product("Tablet", "Samsung", 25000, 7));
        session.save(new Product("Mouse", "Logitech", 1200, 25));
        session.save(new Product("Keyboard", "Logitech", 3500, 15));
        session.save(new Product("Monitor", "LG", 14000, 8));
        session.save(new Product("Charger", "Samsung", 1200, 40));
        session.save(new Product("Headset", "Sony", 4500, 12));

        tx.commit();
        session.close();

        System.out.println("✅ Products Inserted");

        session = HibernateUtil.getSessionFactory().openSession();

        // 2️⃣ SORT BY PRICE (ASC)
        System.out.println("\n--- Price Ascending ---");
        Query<Product> q1 =
                session.createQuery("from Product order by price asc", Product.class);
        q1.list().forEach(System.out::println);

        // 3️⃣ SORT BY PRICE (DESC)
        System.out.println("\n--- Price Descending ---");
        Query<Product> q2 =
                session.createQuery("from Product order by price desc", Product.class);
        q2.list().forEach(System.out::println);

        // 4️⃣ SORT BY QUANTITY (HIGH → LOW)
        System.out.println("\n--- Quantity Descending ---");
        Query<Product> q3 =
                session.createQuery("from Product order by quantity desc", Product.class);
        q3.list().forEach(System.out::println);

        // 5️⃣ PAGINATION
        System.out.println("\n--- First 3 Products ---");
        Query<Product> q4 =
                session.createQuery("from Product order by id", Product.class);
        q4.setFirstResult(0);
        q4.setMaxResults(3);
        q4.list().forEach(System.out::println);

        System.out.println("\n--- Next 3 Products ---");
        q4.setFirstResult(3);
        q4.setMaxResults(3);
        q4.list().forEach(System.out::println);

        // 6️⃣ AGGREGATE FUNCTIONS
        Long total =
                session.createQuery("select count(*) from Product", Long.class)
                        .uniqueResult();
        System.out.println("\nTotal Products = " + total);

        Long available =
                session.createQuery(
                        "select count(*) from Product where quantity > 0",
                        Long.class).uniqueResult();
        System.out.println("Products with quantity > 0 = " + available);

        Object[] minMax =
                (Object[]) session.createQuery(
                        "select min(price), max(price) from Product")
                        .uniqueResult();
        System.out.println("Min Price = " + minMax[0]);
        System.out.println("Max Price = " + minMax[1]);

        // 7️⃣ GROUP BY DESCRIPTION
        System.out.println("\n--- Group By Description ---");
        List<Object[]> group =
                session.createQuery(
                        "select description, count(*) from Product group by description")
                        .list();

        for (Object[] row : group) {
            System.out.println(row[0] + " → " + row[1]);
        }

        // 8️⃣ PRICE RANGE
        System.out.println("\n--- Price Between 2000 and 20000 ---");
        Query<Product> q5 =
                session.createQuery(
                        "from Product where price between 2000 and 20000",
                        Product.class);
        q5.list().forEach(System.out::println);
     // 9️⃣ LIKE QUERIES
        System.out.println("\n--- Name Starts with 'M' ---");
        session.createQuery(
                "from Product where name like 'M%'", Product.class)
                .list().forEach(System.out::println);

        System.out.println("\n--- Name Ends with 'r' ---");
        session.createQuery(
                "from Product where name like '%r'", Product.class)
                .list().forEach(System.out::println);

        System.out.println("\n--- Name Contains 'top' ---");
        session.createQuery(
                "from Product where name like '%top%'", Product.class)
                .list().forEach(System.out::println);

        System.out.println("\n--- Name Length = 5 ---");
        session.createQuery(
                "from Product where length(name)=5", Product.class)
                .list().forEach(System.out::println);

        session.close();
        System.out.println("\n✅ ALL HQL OPERATIONS DONE");
    }
}



