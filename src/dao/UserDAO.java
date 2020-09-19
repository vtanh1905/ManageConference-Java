package dao;

import entities.UserEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.HibernateUtils;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserDAO {
    public static Session userSession;

    public static int save(UserEntity userEntity){
        try {
            userSession = HibernateUtils.getSessionFactory().openSession();

            String hql = "FROM UserEntity a Where a.username= '" + userEntity.getUsername() + "'";
            Query query = userSession.createQuery(hql);

            if(query.getResultList().size() > 0) {
                System.out.println("Tai khoan da ton tai!");
                return 0;
            }

            userEntity.setPassword(hashMD5Password(userEntity.getPassword()));
            userEntity.setIsLocked(0);
            userEntity.setRole("USER");

            userSession.save(userEntity);

            userSession.beginTransaction().commit();
            userSession.close();
            return 1;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String hashMD5Password(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());

        byte[] digest = md.digest();

        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

    public static UserEntity login(String username, String password){
        try{
            userSession = HibernateUtils.getSessionFactory().openSession();
            String hashPass = hashMD5Password(password);

            String hql = "SELECT a from UserEntity a WHERE a.password='"+ hashPass +"' and a.username='"+ username +"'";
            Query query = userSession.createQuery(hql);
            List<UserEntity> listUser = query.getResultList();
            if(listUser.size() == 0) {
                userSession.close();
                return null;
            }
            UserEntity user = (UserEntity)listUser.get(0);
            userSession.close();
            return user;

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Da co loi!");
            userSession.close();
        }

        return null;
    }

    public static List<UserEntity> getAll(int limit, int offset, int filter_type, int sort_type, String textSearch){
        userSession = HibernateUtils.getSessionFactory().openSession();
        String hql = "SELECT a from UserEntity a where a.fullname like '%"+ textSearch +"%' ORDER BY a.fullname " + (sort_type == 0 ? "desc" : "asc");
        if(filter_type != -1){
            hql = "SELECT a from UserEntity a WHERE a.fullname like '%"+ textSearch +"%' and a.isLocked= " + filter_type + " ORDER BY a.fullname " + (sort_type == 0 ? "desc" : "asc");
        }
        Query query = userSession.createQuery(hql);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        List<UserEntity> temp = query.getResultList();
        userSession.close();
        return temp;
    }

    public static List<UserEntity> getAllWithoutAdmin(int limit, int offset, int filter_type, int sort_type, String textSearch){
        userSession = HibernateUtils.getSessionFactory().openSession();
        String hql = "SELECT a from UserEntity a where a.role='USER' and a.fullname like '%"+ textSearch +"%' ORDER BY a.fullname " + (sort_type == 0 ? "desc" : "asc");
        if(filter_type != -1){
            hql = "SELECT a from UserEntity a WHERE a.role='USER' and a.fullname like '%"+ textSearch +"%' and a.isLocked= " + filter_type + " ORDER BY a.fullname " + (sort_type == 0 ? "desc" : "asc");
        }
        Query query = userSession.createQuery(hql);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        List<UserEntity> temp = query.getResultList();
        userSession.close();
        return temp;
    }

    public static int changeBlockUser(int IDUser, int lockChange){
        userSession = HibernateUtils.getSessionFactory().openSession();
        Query query = userSession.createQuery("select u from UserEntity u where u.id= "+ IDUser);
        List<UserEntity> result = query.getResultList();
        UserEntity userEntity = result.get(0);
        userEntity.setIsLocked(lockChange);
        userSession.update(userEntity);

        userSession.beginTransaction().commit();
        userSession.close();
        return 1;
    }

    public static int size(int filter_type){
        userSession = HibernateUtils.getSessionFactory().openSession();
        String hql = "SELECT count(a) from UserEntity a";
        if(filter_type != -1){
            hql = "SELECT count(a) from UserEntity a WHERE a.isLocked= " + filter_type;
        }
        Query query = userSession.createQuery(hql);
        int size = Integer.parseInt("" + query.getSingleResult());
        userSession.close();
        return size;
    }

    public static int size2(int filter_type, int sort_type, String textSearch){
        userSession = HibernateUtils.getSessionFactory().openSession();
        String hql = "SELECT COUNT(a) from UserEntity a where a.role='USER' and a.fullname like '%"+ textSearch +"%' ORDER BY a.fullname " + (sort_type == 0 ? "desc" : "asc");
        if(filter_type != -1){
            hql = "SELECT COUNT(a) from UserEntity a WHERE a.role='USER' and a.fullname like '%"+ textSearch +"%' and a.isLocked= " + filter_type + " ORDER BY a.fullname " + (sort_type == 0 ? "desc" : "asc");
        }
        Query query = userSession.createQuery(hql);
        int size = Integer.parseInt("" + query.getSingleResult());
        userSession.close();
        return size;
    }


    public static UserEntity getByID(int userID){
        userSession = HibernateUtils.getSessionFactory().openSession();
        String hql = "SELECT a from UserEntity a where a.id='"+ userID +"'";
        Query query = userSession.createQuery(hql);
        UserEntity userEntity = (UserEntity) query.getSingleResult();
        userSession.close();
        return userEntity;
    }

    public static int update(UserEntity userEntity){
        userSession = HibernateUtils.getSessionFactory().openSession();
        userSession.update(userEntity);
        userSession.beginTransaction().commit();
        userSession.close();
        return 1;
    }
}
