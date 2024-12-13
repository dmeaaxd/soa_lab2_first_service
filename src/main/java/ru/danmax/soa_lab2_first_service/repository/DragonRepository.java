package ru.danmax.soa_lab2_first_service.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.danmax.soa_lab2_first_service.config.DatabaseSessionManager;
import ru.danmax.soa_lab2_first_service.entity.Dragon;
import ru.danmax.soa_lab2_first_service.entity.enums.DragonCharacter;
import ru.danmax.soa_lab2_first_service.exception.DatabaseException;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DragonRepository {

    @Inject
    private DatabaseSessionManager sessionManager;

    public void save(Dragon dragon) throws DatabaseException {
        Session session = sessionManager.getSession();
        try {
            session.beginTransaction();
            session.persist(dragon);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            throw new DatabaseException(e.getMessage());
        } finally {
            sessionManager.closeSession(session);
        }
    }

    public void update(Dragon dragon) throws DatabaseException {
        Session session = sessionManager.getSession();
        try {
            session.beginTransaction();
            session.merge(dragon);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            throw new DatabaseException(e.getMessage());
        } finally {
            sessionManager.closeSession(session);
        }
    }

    public List<Dragon> findAll(String sort,
                                String filter,
                                Integer page,
                                Integer size) throws DatabaseException {
        List<Dragon> dragons;
        Session session = sessionManager.getSession();
        try {
            session.beginTransaction();
            Query<Dragon> query = session.createQuery(createFindAllHqlQuery(sort, filter), Dragon.class);

            if (size != null && size > 0) {
                query.setMaxResults(size);
                if (page != null && page >= 0) {
                    query.setFirstResult(page * size);
                }
            }

            dragons = query.getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            throw new DatabaseException(e.getMessage());
        } finally {
            sessionManager.closeSession(session);
        }
        return dragons;
    }

    public Dragon findById(Integer id) throws DatabaseException {
        if (id == null) {
            throw new DatabaseException("Id cannot be null");
        }
        Dragon dragon;
        Session session = sessionManager.getSession();
        try {
            session.beginTransaction();
            dragon = session.createQuery("FROM Dragon WHERE id = :id", Dragon.class)
                    .setParameter("id", id)
                    .getSingleResultOrNull();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            throw new DatabaseException(e.getMessage());
        } finally {
            sessionManager.closeSession(session);
        }
        return dragon;
    }

    public List<Dragon> findAllByNameSubstring(String name) throws DatabaseException {
        name = name + "%";
        List<Dragon> dragons;
        Session session = sessionManager.getSession();
        try {
            session.beginTransaction();
            dragons = session
                    .createQuery("FROM Dragon WHERE name LIKE :name", Dragon.class)
                    .setParameter("name",  name)
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            throw new DatabaseException(e.getMessage());
        } finally {
            sessionManager.closeSession(session);
        }
        return dragons;
    }

    public List<Dragon> findAllFilterByKiller(String passportId) throws DatabaseException {
        List<Dragon> dragons;
        Session session = sessionManager.getSession();
        try {
            session.beginTransaction();
            dragons = session
                    .createQuery("FROM Dragon WHERE killer.passportId < :passportId", Dragon.class)
                    .setParameter("passportId",  passportId)
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            throw new DatabaseException(e.getMessage());
        } finally {
            sessionManager.closeSession(session);
        }
        return dragons;
    }

    public List<Dragon> findAllFilterByCharacter(DragonCharacter dragonCharacter) throws DatabaseException {
        List<Dragon> dragons;
        Session session = sessionManager.getSession();
        try {
            session.beginTransaction();
            dragons = session
                    .createQuery("FROM Dragon", Dragon.class)
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            throw new DatabaseException(e.getMessage());
        } finally {
            sessionManager.closeSession(session);
        }

        List<Dragon> result = new ArrayList<>();
        for (Dragon dragon : dragons) {
            if (dragon.getCharacter() != null && (dragon.getCharacter().ordinal() > dragonCharacter.ordinal())){
                result.add(dragon);
            }
        }
        return result;
    }


    public void deleteById(Integer id) throws DatabaseException {
        if (id == null) {
            throw new DatabaseException("Id cannot be null");
        }
        Session session = sessionManager.getSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE Dragon WHERE id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            throw new DatabaseException(e.getMessage());
        } finally {
            sessionManager.closeSession(session);
        }
    }


    private String createFindAllHqlQuery(String sort,
                                         String filter) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FROM Dragon ");

        if (filter != null && !filter.isEmpty()) {
            stringBuilder.append("WHERE ").append(filter).append(" ");
        }

        if (sort != null && !sort.isEmpty()) {
            stringBuilder.append("ORDER BY ").append(sort).append(" ");
        }

        return stringBuilder.toString();
    }
}
