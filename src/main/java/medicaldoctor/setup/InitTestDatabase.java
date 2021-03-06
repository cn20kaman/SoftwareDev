package medicaldoctor.setup;

import medicaldoctor.core.DatabaseScope;
import medicaldoctor.entities.User;
import medicaldoctor.util.Encryption;

public final class InitTestDatabase {

    private InitTestDatabase() {
    }

    private static DatabaseScope s;

    public static void main(String[] args) throws Exception {
        try (DatabaseScope s_ = new DatabaseScope()) {
            s = s_;
            s.beginTransaction();
            deletePreviousData();
            insertUsers();
            s.commit();
        } catch (Exception e) {
            s.rollback();
            throw e;
        } finally {
            DatabaseScope._shutdown();
        }
    }

    private static void deletePreviousData() {
        s.runUpdate("DELETE FROM User");
    }

    private static void insertUsers() throws Exception {
        Encryption encryption = new Encryption();
        User user;

        user = new User();
        user.setFirstName("Network");
        user.setLastName("Admin");
        user.setUserName("admin");
        user.setPasswordHashAndSalt(encryption.hashPassword("password123"));
        user.save();
    }

}
