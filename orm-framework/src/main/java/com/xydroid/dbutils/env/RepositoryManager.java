package com.xydroid.dbutils.env;

import com.xydroid.dbutils.persistence.SqliteEnv;
import com.xydroid.dbutils.persistence.helper.DatabaseHelper;
import com.xydroid.dbutils.persistence.proxy.Proxy;
import com.xydroid.dbutils.persistence.repository.Repository;

import java.util.HashMap;
import java.util.Map;

public class RepositoryManager {
    private static HashMap<String, DatabaseHelper> dbMap = new HashMap<String, DatabaseHelper>();
    private static RepositoryManager Instance = new RepositoryManager();
    private Map<String, Map<Class, Repository>> repositoryMap = new HashMap<>();

    public static RepositoryManager getInstance() {
        return Instance;
    }

    private RepositoryManager() {
    }

    public Repository getRepostory(SqliteEnv env, Class<? extends Repository> clazz) {
        String envKey = env.toString();
        Map<Class, Repository> envRepository = repositoryMap.get(envKey);
        if (null == envRepository) {
            envRepository = new HashMap<Class, Repository>();
            repositoryMap.put(envKey, envRepository);
        }

        Repository repository = envRepository.get(clazz);
        if (null == repository) {
            repository = (Repository)initRepostoryProxy(clazz);
            envRepository.put(clazz, repository);
            repositoryMap.put(envKey, envRepository);
        }
        return repository;
    }

    public void remove(SqliteEnv env) {
        repositoryMap.remove(env.toString());
    }

    private Object initRepostoryProxy(Class<? extends Repository> clazz) {
        return java.lang.reflect.Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
                new Proxy(clazz));
    }
}
