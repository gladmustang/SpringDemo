package com.pwc.faast.notesservice.config;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.*;

public class DynamicDataSourceCreator {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceCreator.class);


    /**
     * 别名
     */
    private final static ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();

    /**
     * 由于部分数据源配置不同，所以在此处添加别名，避免切换数据源出现某些参数无法注入的情况
     */
    static {
        aliases.addAliases("url", new String[]{"jdbc-url"});
        aliases.addAliases("username", new String[]{"user"});
    }

    /**
     * 存储我们注册的数据源
     */
//    private Map<String, DataSource> customDataSources = new HashMap<String, DataSource>();
    private static Map<Object, Object> customDataSources = new HashMap<>();
    private static DynamicDataSource dataSource = new DynamicDataSource();
    /**
     * 参数绑定工具 springboot2.0新推出
     */
//    private Binder binder = new Binder();

    public static DataSource createDynamicDataSource() {
        // 获取所有数据源配置
        Map config, defauleDataSourceProperties;
        DBConfig.init();
        defauleDataSourceProperties = DBConfig.master;
        // 获取数据源类型
        String typeStr = null;
        // 获取数据源类型
        Class<? extends DataSource> clazz = getDataSourceType(typeStr);
        // 绑定默认数据源参数 也就是主数据源
        DataSource consumerDatasource, defaultDatasource = bind(clazz, defauleDataSourceProperties);
        DynamicDataSourceContextHolder.dataSourceIds.add(DBConfig.master.get("key"));
        logger.info("注册默认数据源成功, key = " + DBConfig.master.get("key"));
        // 获取其他数据源配置
        //List<Map> configs = binder.bind("spring.datasource.cluster", Bindable.listOf(Map.class)).get();
        List<Map> configs = DBConfig.cluster;

        generateCustomDataSources(configs);

//        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
        dataSource.setDefaultTargetDataSource(defaultDatasource);
        dataSource.setTargetDataSources(customDataSources);
        logger.info("注册数据源成功，一共注册{}个数据源", customDataSources.keySet().size() + 1);
        return dataSource;
    }

    private static void generateCustomDataSources(List<Map> configs) {
        // 遍历从数据源
        Map config, defauleDataSourceProperties;
        for (int i = 0; i < configs.size(); i++) {
            config = configs.get(i);
            Class<? extends DataSource> clazz = getDataSourceType((String) config.get("type"));
            defauleDataSourceProperties = config;
            // 绑定参数
            DataSource consumerDatasource = bind(clazz, defauleDataSourceProperties);
            // 获取数据源的key，以便通过该key可以定位到数据源
            String key = config.get("key").toString();
            customDataSources.put(key, consumerDatasource);
            // 数据源上下文，用于管理数据源与记录已经注册的数据源key

            DynamicDataSourceContextHolder.dataSourceIds.add(key);
            logger.info("注册数据源{}成功", key);
        }

    }

    public static void addNewDataSource(Map config) {
        Map defauleDataSourceProperties = config;
        Class<? extends DataSource> clazz = getDataSourceType((String) config.get("type"));
        defauleDataSourceProperties = config;
        // 绑定参数
        DataSource consumerDatasource = bind(clazz, defauleDataSourceProperties);
        // 获取数据源的key，以便通过该key可以定位到数据源
        String key = config.get("key").toString();
        // 数据源上下文，用于管理数据源与记录已经注册的数据源key
        DynamicDataSourceContextHolder.dataSourceIds.add(key);
        logger.info("注册数据源{}成功", key);
        dataSource.addDataSource(key, consumerDatasource);

    }


    /**
     * 通过字符串获取数据源class对象
     *
     * @param typeStr
     * @return
     */
    private static Class<? extends DataSource> getDataSourceType(String typeStr) {
        Class<? extends DataSource> type;
        try {
            if (StringUtils.hasLength(typeStr)) {
                // 字符串不为空则通过反射获取class对象
                type = (Class<? extends DataSource>) Class.forName(typeStr);
            } else {
                // 默认为hikariCP数据源，与springboot默认数据源保持一致
                type = HikariDataSource.class;
            }
            return type;
        } catch (Exception e) {
            throw new IllegalArgumentException("can not resolve class with type: " + typeStr); //无法通过反射获取class对象的情况则抛出异常，该情况一般是写错了，所以此次抛出一个runtimeexception
        }
    }

    /**
     * 绑定参数，以下三个方法都是参考DataSourceBuilder的bind方法实现的，目的是尽量保证我们自己添加的数据源构造过程与springboot保持一致
     *
     * @param result
     * @param properties
     */
    private static void bind(DataSource result, Map properties) {
        ConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
        Binder binder = new Binder(new ConfigurationPropertySource[]{source.withAliases(aliases)});
        // 将参数绑定到对象
        binder.bind(ConfigurationPropertyName.EMPTY, Bindable.ofInstance(result));
    }

    private static  <T extends DataSource> T bind(Class<T> clazz, Map properties) {
        ConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
        Binder binder = new Binder(new ConfigurationPropertySource[]{source.withAliases(aliases)});
        // 通过类型绑定参数并获得实例对象
        return binder.bind(ConfigurationPropertyName.EMPTY, Bindable.of(clazz)).get();
    }

    /**
     * @param clazz
     * @param sourcePath 参数路径，对应配置文件中的值，如: spring.datasource
     * @param <T>
     * @return
     */
//    private <T extends DataSource> T bind(Class<T> clazz, String sourcePath) {
//        Map properties = binder.bind(sourcePath, Map.class).get();
//        return bind(clazz, properties);
//    }

}
