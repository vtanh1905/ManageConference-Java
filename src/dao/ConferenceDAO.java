package dao;

import entities.ConferenceEntity;
import entities.UserEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.HibernateUtils;

import java.util.List;

public class ConferenceDAO {
    public static Session conferenceSession;

    public static int save(ConferenceEntity conferenceEntity){
        try {
            conferenceSession = HibernateUtils.getSessionFactory().openSession();

            String hql = "SELECT a from ConferenceEntity a where a.locationId='"+ conferenceEntity.getLocationId() +"'";
            Query query = conferenceSession.createQuery(hql);
            List<ConferenceEntity> temp = query.getResultList();

            for (int i = 0; i < temp.size(); ++i){
                if(Long.parseLong(conferenceEntity.getTimeStartAt()) >= Long.parseLong(temp.get(i).getTimeStartAt()) && Long.parseLong(conferenceEntity.getTimeStartAt()) <= Long.parseLong(temp.get(i).getTimeEndAt())){
                    conferenceSession.beginTransaction().commit();
                    conferenceSession.close();
                    return 0;
                }
            }

            conferenceSession.save(conferenceEntity);
            conferenceSession.beginTransaction().commit();
            conferenceSession.close();
            return 1;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<ConferenceEntity> getAll(int limit, int offset){
        conferenceSession = HibernateUtils.getSessionFactory().openSession();
        String hql = "SELECT a from ConferenceEntity a";
        Query query = conferenceSession.createQuery(hql);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        List<ConferenceEntity> temp = query.getResultList();
        conferenceSession.close();
        return temp;
    }

    public static List<ConferenceEntity> getAll(int limit, int offset, String textSearch){
        conferenceSession = HibernateUtils.getSessionFactory().openSession();
        String hql = "SELECT a from ConferenceEntity a where a.name like '%"+ textSearch +"%'";
        Query query = conferenceSession.createQuery(hql);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        List<ConferenceEntity> temp = query.getResultList();
        conferenceSession.close();
        return temp;
    }

    public static int size(String textSearch){
        conferenceSession = HibernateUtils.getSessionFactory().openSession();
        String hql = "SELECT COUNT(a) from ConferenceEntity a where a.name like '%"+ textSearch +"%'";
        Query query = conferenceSession.createQuery(hql);
        int size = Integer.parseInt("" + query.getSingleResult());
        conferenceSession.close();
        return size;
    }

    public static int update(ConferenceEntity conferenceEntity){
        conferenceSession = HibernateUtils.getSessionFactory().openSession();
        String hql = "SELECT a from ConferenceEntity a where a.locationId='"+ conferenceEntity.getLocationId() +"' and a.id!='"+ conferenceEntity.getId() +"'";
        Query query = conferenceSession.createQuery(hql);

        List<ConferenceEntity> temp = query.getResultList();

        for (int i = 0; i < temp.size(); ++i){
            if(Long.parseLong(conferenceEntity.getTimeStartAt()) >= Long.parseLong(temp.get(i).getTimeStartAt()) && Long.parseLong(conferenceEntity.getTimeStartAt()) <= Long.parseLong(temp.get(i).getTimeEndAt())){
                conferenceSession.beginTransaction().commit();
                conferenceSession.close();
                return 0;
            }
        }
        conferenceSession.update(conferenceEntity);
        conferenceSession.beginTransaction().commit();
        conferenceSession.close();
        return 1;
    }
}
