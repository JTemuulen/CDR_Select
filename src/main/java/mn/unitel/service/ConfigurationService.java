package mn.unitel.service;

import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import mn.unitel.entity.Configuration;
import mn.unitel.entity.Query;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Startup
@Singleton
public class ConfigurationService {
    private Map<String, Query> queries;

    @PostConstruct
    void init() {
        queries = new HashMap<>();
        readQueries();
    }

    public Query getQuery(String queryName) {return queries.get(queryName);}

    public void readQueries() {
        try{
            File configFile = new File("config/configuration.xml");

            JAXBContext context = JAXBContext.newInstance(Configuration.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            Configuration configuration = (Configuration) unmarshaller.unmarshal(configFile);

            for (Query query : configuration.getQueries()) {
                System.out.println("Query ID: " + query.getId());

                queries.put(query.getId(), query);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
