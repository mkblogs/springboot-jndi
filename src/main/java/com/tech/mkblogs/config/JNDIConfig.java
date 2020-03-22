package com.tech.mkblogs.config;

import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Component
@PropertySource(value = "classpath:db.properties")
@ConfigurationProperties("springboot.datasource")
@Log4j2
@Data
public class JNDIConfig {

	 private String username;
	 private String password;
	 private String driverClassName;
	 private String url;
	 
	@Lazy
    @Bean(destroyMethod="")
    public DataSource jndiDataSource() throws Exception {
    	log.info("in side jndi ds");
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();           // create JNDI data source
        bean.setJndiName("java:/comp/env/jndiDataSource");  // jndiDataSource is name of JNDI data source 
        bean.setProxyInterface(DataSource.class);
        bean.setLookupOnStartup(true);
        bean.afterPropertiesSet();
        return (DataSource) bean.getObject();
    }
	
	@Bean
	public TomcatServletWebServerFactory tomcatFactory() {
		log.info("initializing tomcat factory... ");
		return new TomcatServletWebServerFactory() {
			
			@Override
			protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
				System.setProperty("catalina.useNaming", "true");
				tomcat.enableNaming();
				return new TomcatWebServer(tomcat, getPort() >= 0);
			}

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
		};
	}
}
