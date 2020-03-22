# Spring Boot JNDI Example
In this example read the `db.properties` file and created JNDI DataSource using Tomcat JNDI Context
###### Code for setting
```java
 protected void postProcessContext(Context context) {
	 	log.info("in side post process");
        ContextResource resource = new ContextResource();
        resource.setName("jndiDataSource");
        resource.setType(DataSource.class.getName());
        resource.setProperty("driverClassName", driverClassName);
        resource.setProperty("url", url);
        resource.setProperty("username", username);
        resource.setProperty("password", password);
        context.getNamingResources().addResource(resource);
    }
```
###### Code for reading the from JNDI Context
```java
 JndiObjectFactoryBean bean = new JndiObjectFactoryBean(); 
 bean.setJndiName("java:/comp/env/jndiDataSource");
 bean.setProxyInterface(DataSource.class);
 bean.setLookupOnStartup(true);
 bean.afterPropertiesSet(); 
```