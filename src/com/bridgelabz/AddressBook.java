package com.bridgelabz;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class AddressBook {
    Connection connection;

    private static Connection getConnection() {
        String URL_JD = "jdbc:mysql://localhost:3306/address_book_service";
        String USER_NAME = "root";
        String PASSWORD = "Panu@9146";
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Drivers loaded!!");
            connection = DriverManager.getConnection(URL_JD,USER_NAME,PASSWORD);
            System.out.println("connection Established!!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public static void insertData(Contacts add) throws SQLException {
        Connection connection = getConnection();
        try {
            if (connection != null) {
                connection.setAutoCommit(false);
                Statement statement = connection.createStatement();
                String sql = "insert into address_book(first_name,last_name,address,city,state,zip,phone_number,email,address_book_name,type,date_added)" +
                        "values('" + add.getFirstName() + "','" + add.getLastName() + "','" + add.getAddress() + "','" + add.getCity() +
                        "','" + add.getState() + "','" + add.getZip() + "','" + add.getPhoneNumber() + "','" +
                        add.getEmailId() + "','" + add.getBookName() + "','" + add.getContactType() + "','" + add.getDateAdded() + "');";
                int result = statement.executeUpdate(sql);
                connection.commit();
                if (result > 0) {
                    System.out.println("Contact Inserted");
                }
                connection.setAutoCommit(true);
            }
        } catch (SQLException sqlException) {
            System.out.println("Insertion Rollbacked");
            connection.rollback();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }


    public List<Contacts> retrieveData() {
        ResultSet resultSet = null;
        List<Contacts> addressBookList = new ArrayList<Contacts>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "select * from address_book";
            resultSet = statement.executeQuery(sql);
            int count = 0;
            while (resultSet.next()) {
                Contacts contactInfo = new Contacts();
                contactInfo.setFirstName(resultSet.getString("first_name"));
                contactInfo.setLastName(resultSet.getString("last_name"));
                contactInfo.setAddress(resultSet.getString("address"));
                contactInfo.setCity(resultSet.getString("city"));
                contactInfo.setState(resultSet.getString("state"));
                contactInfo.setZip(resultSet.getInt("zip"));
                contactInfo.setPhoneNumber(resultSet.getString("phone_number"));
                contactInfo.setEmailId(resultSet.getString("email"));
                contactInfo.setBookName(resultSet.getString("address_book_name"));
                contactInfo.setContactType(resultSet.getString("type"));
                contactInfo.setDateAdded(resultSet.getDate("date_added").toLocalDate());

                addressBookList.add(contactInfo);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return addressBookList;

    }
    public void updateCityByZip(String address, String city, String state, int zip, int srNo) {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String query = "Update address_book set address=" + "'" + address + "'" + ", " + "city=" + "'" + city + "'" + ", " + "state=" + "'" + state + "'" + ", " + "zip=" + zip + " where srNo=" + srNo + ";";
            int result = statement.executeUpdate(query);
            System.out.println(result);
            if (result > 0) {
                System.out.println("Address Updated Successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Contacts> findAllForParticularDate(LocalDate date) {
        ResultSet resultSet = null;
        List<Contacts> addressBookList = new ArrayList<Contacts>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "select * from address_book where date_added between cast(' "+ date + "'" +" as date)  and date(now());";
            resultSet = statement.executeQuery(sql);
            int count = 0;
            while (resultSet.next()) {
                Contacts contactInfo = new Contacts();
                contactInfo.setFirstName(resultSet.getString("firstName"));
                contactInfo.setLastName(resultSet.getString("Lastname"));
                contactInfo.setAddress(resultSet.getString("address"));
                contactInfo.setCity(resultSet.getString("city"));
                contactInfo.setState(resultSet.getString("state"));
                contactInfo.setZip(resultSet.getInt("zip"));
                contactInfo.setPhoneNumber(resultSet.getString("phoneNumber"));
                contactInfo.setEmailId(resultSet.getString("email"));
                contactInfo.setBookName(resultSet.getString("bookName"));
                contactInfo.setContactType(resultSet.getString("contactType"));
                contactInfo.setDateAdded(resultSet.getDate("Date_added").toLocalDate());

                addressBookList.add(contactInfo);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return addressBookList;
    }
    public int countByCiy(String city) {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "select count(first_name) from address_book where city=" + "'" + city + "';";
            ResultSet result = statement.executeQuery(sql);
            result.next();
            int count = result.getInt(1);


            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countByState(String state) {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "select count(first_name) from address_ook where city=" + "'" + state + "';";
            ResultSet result = statement.executeQuery(sql);
            result.next();
            int count = result.getInt(1);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
