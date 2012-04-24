dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
    //show_sql = true
}
// environment specific settings
environments {
    development {
        dataSource {
            driverClassName = "org.postgresql.Driver"
            dialect = net.sf.hibernate.dialect.PostgreSQLDialect
            dbCreate = "create-drop" 
            url="jdbc:postgresql://localhost:5432/cashcard_development"
            username = "postgres"
            password = "postgres"
        }
    }
    test {
        dataSource {
            driverClassName = "org.postgresql.Driver"
            dialect = net.sf.hibernate.dialect.PostgreSQLDialect
            dbCreate = "create-drop" 
            url="jdbc:postgresql://localhost:5432/cashcard_test"
            username = "postgres"
            password = "postgres"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:prodDb;MVCC=TRUE"
            pooled = true
            properties {
               maxActive = -1
               minEvictableIdleTimeMillis=1800000
               timeBetweenEvictionRunsMillis=1800000
               numTestsPerEvictionRun=3
               testOnBorrow=true
               testWhileIdle=true
               testOnReturn=true
               validationQuery="SELECT 1"
            }
        }
    }
}
