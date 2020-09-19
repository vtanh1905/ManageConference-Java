package dao;

import entities.ConferenceEntity;
import entities.LocationEntity;
import entities.PartakerEntity;
import entities.UserEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.HibernateUtils;

import java.util.ArrayList;
import java.util.List;

public class PartakerDAO {
    public static Session partakerSession;

    public static int save(PartakerEntity partakerEntity){
        try {
            partakerSession = HibernateUtils.getSessionFactory().openSession();
            partakerEntity.setIsApproved(1);

            LocationEntity locationEntity = LocationDAO.getByConferenceId(partakerEntity.getConferenceId());
            String hql = "SELECT COUNT(p) from PartakerEntity p where p.conferenceId='"+ partakerEntity.getConferenceId() +"'";
            Query query = partakerSession.createQuery(hql);
            int peopleCurrent = Integer.parseInt("" + query.getSingleResult());
            if(peopleCurrent >= locationEntity.getMaxPeople()){
                partakerSession.close();
                return 0;
            }


            partakerSession.save(partakerEntity);
            partakerSession.beginTransaction().commit();
            partakerSession.close();
            return 1;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // IsAppreove 0 kh co gi , 1 la chờ, 2 đã đăng ký, -1 bị từ chối
    public static int getStatus(int userID, int conferenceID){
        partakerSession = HibernateUtils.getSessionFactory().openSession();
        String hql = "SELECT a from PartakerEntity a where a.userId= "+userID+" and a.conferenceId="+conferenceID;
        Query query = partakerSession.createQuery(hql);
        List<PartakerEntity> partakerEntityList = query.getResultList();
        partakerSession.close();
        System.out.println(partakerEntityList.size());
        if(partakerEntityList.size() != 0){
            return partakerEntityList.get(0).getIsApproved();
        }
        return 0;
    }

    public static int remove(int userID, int conferenceID){
        partakerSession = HibernateUtils.getSessionFactory().openSession();
        String hql = "SELECT a from PartakerEntity a where a.conferenceId='"+ conferenceID+"' and a.userId='"+ userID +"'";
        Query query = partakerSession.createQuery(hql);
        List<PartakerEntity> result = (List<PartakerEntity>) query.getResultList();
        PartakerEntity partakerEntity = result.get(0);
        partakerSession.remove(partakerEntity);
        partakerSession.beginTransaction().commit();
        partakerSession.close();
        return 1;
    }

    public static int updateStatus(int userID, int conferenceID, int status){
        partakerSession = HibernateUtils.getSessionFactory().openSession();

        String hql = "SELECT b.maxPeople from ConferenceEntity a, LocationEntity b where a.id='"+ conferenceID +"' and a.locationId=b.id";
        Query query = partakerSession.createQuery(hql);
        int maxPeople =  Integer.parseInt("" + query.getSingleResult());
        hql = "SELECT COUNT(p) from PartakerEntity p where p.conferenceId='"+ conferenceID +"' and p.isApproved=2";
        query = partakerSession.createQuery(hql);
        int peopleCurrent = Integer.parseInt("" + query.getSingleResult());
        if(peopleCurrent >= maxPeople){
            partakerSession.close();
            return 0;
        }

        hql = "SELECT a from PartakerEntity a where a.conferenceId='"+ conferenceID+"' and a.userId='"+ userID +"'";
        query = partakerSession.createQuery(hql);
        List<PartakerEntity> result = (List<PartakerEntity>) query.getResultList();
        PartakerEntity partakerEntity = result.get(0);
        partakerEntity.setIsApproved(status);
        partakerSession.update(partakerEntity);
        partakerSession.beginTransaction().commit();
        partakerSession.close();
        return 1;
    }



    public static int getNumberApproved(int conferenceId){
        partakerSession = HibernateUtils.getSessionFactory().openSession();
        String hql = "SELECT count(a) from PartakerEntity a where a.conferenceId='"+ conferenceId +"' and a.isApproved=2";
        Query query = partakerSession.createQuery(hql);
        int size = Integer.parseInt("" + query.getSingleResult());
        partakerSession.close();
        return size;
    }

    public static int getNumberWaitApproved(int conferenceId){
        partakerSession = HibernateUtils.getSessionFactory().openSession();
        String hql = "SELECT count(a) from PartakerEntity a where a.conferenceId='"+ conferenceId +"' and a.isApproved=1";
        Query query = partakerSession.createQuery(hql);
        int size = Integer.parseInt("" + query.getSingleResult());
        partakerSession.close();
        return size;
    }

    public static List<UserEntity>  getPartakers(int conferenceId, int limit, int offset){
        partakerSession = HibernateUtils.getSessionFactory().openSession();
        String hql = "SELECT b from PartakerEntity a, UserEntity b where a.conferenceId='"+ conferenceId+"' and a.userId=b.id";
        Query query = partakerSession.createQuery(hql);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        List<UserEntity> temp = query.getResultList();
        partakerSession.close();
        return temp;
    }

    public static List<ConferenceEntity> getConferenceOfUser(int userID, int limit, int offset,int filter_type, int sort_type, String textSearch){
        partakerSession = HibernateUtils.getSessionFactory().openSession();
        String hql = "SELECT b from PartakerEntity a, ConferenceEntity b where a.userId='"+ userID +"' and a.conferenceId=b.id and b.name like '%"+ textSearch +"%' ORDER BY b.name " + (sort_type == 0 ? "asc" : "desc");
        if(filter_type != 0){
            hql = "SELECT b from PartakerEntity a, ConferenceEntity b where a.userId='"+ userID +"' and a.conferenceId=b.id and a.isApproved='" + filter_type + "' and b.name like '%"+ textSearch +"%' ORDER BY a.id " + (sort_type == 0 ? "asc" : "desc");
        }
        Query query = partakerSession.createQuery(hql);
        List<ConferenceEntity> temp = query.getResultList();
        partakerSession.close();
        return temp;
    }

    public static int getUserWillJoin(int userID){
        partakerSession = HibernateUtils.getSessionFactory().openSession();
        String hql = "SELECT count(a) from PartakerEntity a where a.userId='"+ userID +"' and (a.isApproved=2 or a.isApproved=1)";
        Query query = partakerSession.createQuery(hql);
        int size = Integer.parseInt("" + query.getSingleResult());
        partakerSession.close();
        return size;
    }

    public static int getUserApproved(int userID){
        partakerSession = HibernateUtils.getSessionFactory().openSession();
        String hql = "SELECT count(a) from PartakerEntity a where a.userId='"+ userID +"' and a.isApproved=2";
        Query query = partakerSession.createQuery(hql);
        int size = Integer.parseInt("" + query.getSingleResult());
        partakerSession.close();
        return size;
    }

    public static int getUserWaitApproved(int userID){
        partakerSession = HibernateUtils.getSessionFactory().openSession();
        String hql = "SELECT count(a) from PartakerEntity a where a.userId='"+ userID +"' and a.isApproved=1";
        Query query = partakerSession.createQuery(hql);
        int size = Integer.parseInt("" + query.getSingleResult());
        partakerSession.close();
        return size;
    }
}
