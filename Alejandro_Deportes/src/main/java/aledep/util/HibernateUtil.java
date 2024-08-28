package aledep.util;

import org.hibernate.SessionFactory;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

//public class HibernateUtil {
//
//	public static SessionFactory getSessionFactory() {
//		StandardServiceRegistry registrity = new StandardServiceRegistryBuilder()
//				.configure()
//				.build();
//		
//		return new MetadataSources(registrity).buildMetadata().buildSessionFactory();
//	}
//}
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Cierra caches y pools de conexiones
        getSessionFactory().close();
    }
}
