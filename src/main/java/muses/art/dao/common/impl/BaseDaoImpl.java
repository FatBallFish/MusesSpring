package muses.art.dao.common.impl;

import muses.art.dao.common.BaseDao;
import org.hibernate.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;


@SuppressWarnings("Duplicates")
@Repository
public class BaseDaoImpl<T> implements BaseDao<T> {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * 获得当前事物的session
     *
     * @return org.hibernate.Session
     */
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Serializable save(T o) {
        if (o != null) {
            return this.getCurrentSession().save(o);
        }
        return null;
    }

    @Override
    public T get(Class<T> c, Serializable id) {
        return (T) this.getCurrentSession().get(c, id);
    }

    @Override
    public T get(String hql) {
        Query q = this.getCurrentSession().createQuery(hql);
        List<T> l = q.list();
        if (l != null && l.size() > 0) {
            return l.get(0);
        }
        return null;
    }

    @Override
    public T get(String hql, Map<String, Object> params) {
        Query q = loadHQLParams(hql, params);
        List<T> l = q.list();
        return l == null || l.size() == 0 ? null : l.get(0);
    }

    @Override
    public void delete(T o) {
        if (o != null) {
            this.getCurrentSession().delete(o);
            this.getCurrentSession().flush();
        }
    }

    @Override
    public void update(T o) {
        if (o != null) {
            this.getCurrentSession().merge(o);
            this.getCurrentSession().flush();
        }
    }

    @Override
    public void saveOrUpdate(T o) {
        if (o != null) {
            this.getCurrentSession().saveOrUpdate(o);
        }
    }

    @Override
    public List<T> find(String hql) {
        Query q = this.getCurrentSession().createQuery(hql);
        return q.list();
    }

    @Override
    public List<T> find(String hql, Map<String, Object> params) {
        Query q = loadHQLParams(hql, params);
        return q.list();
    }

    @Override
    public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
        Query q = loadHQLParams(hql, params);
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    @Override
    public List<T> find(String hql, int page, int rows) {
        Query q = this.getCurrentSession().createQuery(hql);
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    @Override
    public Long count(String hql) {
        Query q = this.getCurrentSession().createQuery(hql);
        return (Long) q.uniqueResult();
    }

    @Override
    public Long count(String hql, Map<String, Object> params) {
        Query q = loadHQLParams(hql, params);
        return (Long) q.uniqueResult();
    }

    @Override
    public int executeHql(String hql) {
        Query q = this.getCurrentSession().createQuery(hql);
        return q.executeUpdate();
    }

    @Override
    public int executeHql(String hql, Map<String, Object> params) {
        Query q = loadHQLParams(hql, params);
        return q.executeUpdate();
    }

    @Override
    public List<Object[]> findBySql(String sql) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        return q.list();
    }

    @Override
    public List<Object[]> findBySql(String sql, int page, int rows) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    @Override
    public List<Object[]> findBySql(String sql, Map<String, Object> params) {
        SQLQuery q = loadSQLParams(sql, params);
        return q.list();
    }

    @Override
    public List<Object[]> findBySql(String sql, Map<String, Object> params, int page, int rows) {
        SQLQuery q = loadSQLParams(sql, params);
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    @Override
    public int executeSql(String sql) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        return q.executeUpdate();
    }

    @Override
    public int executeSql(String sql, Map<String, Object> params) {
        SQLQuery q = loadSQLParams(sql, params);
        return q.executeUpdate();
    }

    @Override
    public BigInteger countBySql(String sql) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        return (BigInteger) q.uniqueResult();
    }

    @Override
    public BigInteger countBySql(String sql, Map<String, Object> params) {
        SQLQuery q = loadSQLParams(sql, params);
        return (BigInteger) q.uniqueResult();
    }

    @Override
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setSourceNamingConvention(NamingConventions.JAVABEANS_MUTATOR)
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    private Query loadHQLParams(String hql, Map<String, Object> params) {
        Query q = this.getCurrentSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q;
    }

    private SQLQuery loadSQLParams(String sql, Map<String, Object> params) {
        SQLQuery q = this.getCurrentSession().createSQLQuery(sql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q;
    }
}
