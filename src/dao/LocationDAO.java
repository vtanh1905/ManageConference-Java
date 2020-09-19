package dao;

import entities.ConferenceEntity;
import entities.LocationEntity;
import entities.UserEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.HibernateUtils;

import java.util.List;

public class LocationDAO {
    public static Session locationSession;

    public static int save(LocationEntity locationEntity){
        try {
            locationSession = HibernateUtils.getSessionFactory().openSession();
            locationSession.save(locationEntity);
            locationSession.beginTransaction().commit();

            locationSession.close();
            return 1;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<LocationEntity> getAll(){
        locationSession = HibernateUtils.getSessionFactory().openSession();
        String hql = "SELECT a from LocationEntity a";
        Query query = locationSession.createQuery(hql);
        List<LocationEntity> temp = query.getResultList();
        locationSession.close();
        return temp;
    }

    public static LocationEntity getById(String id){
        locationSession = HibernateUtils.getSessionFactory().openSession();
        String hql = "SELECT a from LocationEntity a where a.id = '"+ id + "'";
        Query query = locationSession.createQuery(hql);
        LocationEntity temp = (LocationEntity) query.getSingleResult();
        locationSession.close();
        return temp;
    }

    public static LocationEntity getByConferenceId(int id){
        locationSession = HibernateUtils.getSessionFactory().openSession();
        String hql = "SELECT a from LocationEntity a, ConferenceEntity b where b.id = '"+ id + "' and a.id = b.locationId";
        Query query = locationSession.createQuery(hql);
        LocationEntity temp = (LocationEntity) query.getSingleResult();
        locationSession.close();
        return temp;
    }
}
