package aledep.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

	public static SessionFactory getSessionFactory() {
		StandardServiceRegistry registrity = new StandardServiceRegistryBuilder()
				.configure()
				.build();
		
		return new MetadataSources(registrity).buildMetadata().buildSessionFactory();
	}
}
